package com.adwiiseagent.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

/**
 * Reads visible words from an image with a Bedrock vision model and returns them as lowercase tokens.
 * S3 URLs are loaded from the configured media bucket. The host in the saved URL is ignored.
 */
@Service
public class BedrockImageTextService {

    private static final Logger log = LoggerFactory.getLogger(BedrockImageTextService.class);
    private static final String PROMPT =
        "List only the real words you can read in this image. Return lowercase words separated by single spaces. " +
        "Do not include punctuation, symbols, or guesses. If there are no readable words, return nothing.";

    private final BedrockRuntimeClient client;
    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final String modelId;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public BedrockImageTextService(
        AmazonS3 amazonS3,
        @Value("${aws.s3.bucket-name}") String bucketName,
        @Value("${aws.s3.access-key}") String accessKey,
        @Value("${aws.s3.secret-key}") String secretKey,
        @Value("${aws.bedrock.region:us-east-1}") String bedrockRegion,
        @Value("${aws.bedrock.image-text-model-id:us.anthropic.claude-sonnet-5-5}") String modelId,
        ObjectMapper objectMapper
    ) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
        this.client =
            BedrockRuntimeClient
                .builder()
                .region(Region.of(bedrockRegion))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.modelId = modelId;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    /**
     * Blank URLs are skipped. Storage failures are {@link Kind#UNREADABLE}.
     * A Bedrock rejection is {@link Kind#MODEL_FAILED}. {@link Kind#READ} means the vision model ran.
     */
    public ImageWordRead read(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return ImageWordRead.emptyUrl();
        }
        String trimmed = imageUrl.trim();
        try {
            LoadedImage image = load(trimmed);
            if (image.bytes().length == 0) {
                log.warn("[ImageSearchText] Empty image for {}", trimmed);
                return ImageWordRead.unreadable("The image file is empty");
            }
            return ImageWordRead.read(invoke(image.bytes(), image.mediaType()));
        } catch (ModelReadException e) {
            log.warn("[ImageSearchText] Bedrock model failed for {}: {}", trimmed, e.getMessage());
            return ImageWordRead.modelFailed(e.getMessage());
        } catch (Exception e) {
            log.warn("[ImageSearchText] Image read failed for {}: {}", trimmed, e.getMessage());
            return ImageWordRead.unreadable(e.getMessage() == null ? "Image could not be read from storage" : e.getMessage());
        }
    }

    private LoadedImage load(String imageUrl) throws IOException, InterruptedException {
        URI uri = URI.create(imageUrl);
        String host = uri.getHost() == null ? "" : uri.getHost().toLowerCase(Locale.ROOT);
        if (host.contains("amazonaws.com")) {
            return loadFromBucket(imageUrl, uri);
        }
        HttpRequest request = HttpRequest.newBuilder(uri).GET().timeout(Duration.ofSeconds(60)).build();
        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("HTTP " + response.statusCode());
        }
        byte[] body = response.body() == null ? new byte[0] : response.body();
        String header = response.headers().firstValue("content-type").orElse("");
        return new LoadedImage(body, mediaType(imageUrl, header));
    }

    private LoadedImage loadFromBucket(String imageUrl, URI uri) throws IOException {
        String key = objectKey(uri);
        if (key.isBlank()) {
            throw new IOException("S3 URL has no object key");
        }
        log.info("[ImageSearchText] Reading s3://{}/{}", bucketName, key);
        try (S3Object object = amazonS3.getObject(bucketName, key)) {
            byte[] bytes = object.getObjectContent().readAllBytes();
            String header = "";
            if (object.getObjectMetadata() != null && object.getObjectMetadata().getContentType() != null) {
                header = object.getObjectMetadata().getContentType();
            }
            return new LoadedImage(bytes, mediaType(imageUrl, header));
        } catch (AmazonS3Exception e) {
            throw new IOException("S3 HTTP " + e.getStatusCode() + " for key " + key, e);
        }
    }

    private String invoke(byte[] bytes, String mediaType) throws IOException, ModelReadException {
        String encoded = Base64.getEncoder().encodeToString(bytes);
        String body = objectMapper.writeValueAsString(
            objectMapper
                .createObjectNode()
                .put("anthropic_version", "bedrock-2023-05-31")
                .put("max_tokens", 400)
                .set(
                    "messages",
                    objectMapper
                        .createArrayNode()
                        .add(
                            objectMapper
                                .createObjectNode()
                                .put("role", "user")
                                .set(
                                    "content",
                                    objectMapper
                                        .createArrayNode()
                                        .add(
                                            objectMapper
                                                .createObjectNode()
                                                .put("type", "image")
                                                .set(
                                                    "source",
                                                    objectMapper
                                                        .createObjectNode()
                                                        .put("type", "base64")
                                                        .put("media_type", mediaType)
                                                        .put("data", encoded)
                                                )
                                        )
                                        .add(objectMapper.createObjectNode().put("type", "text").put("text", PROMPT))
                                )
                        )
                )
        );
        InvokeModelResponse modelResponse;
        try {
            modelResponse =
                client.invokeModel(
                    InvokeModelRequest
                        .builder()
                        .modelId(modelId)
                        .contentType("application/json")
                        .accept("application/json")
                        .body(SdkBytes.fromUtf8String(body))
                        .build()
                );
        } catch (Exception e) {
            throw new ModelReadException(shortBedrockMessage(e));
        }
        JsonNode root = objectMapper.readTree(modelResponse.body().asUtf8String());
        return tokenize(root.path("content").path(0).path("text").asText(""));
    }

    static String objectKey(URI uri) {
        String path = uri.getRawPath() == null ? "" : uri.getRawPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return URLDecoder.decode(path, StandardCharsets.UTF_8);
    }

    static String tokenize(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        String[] parts = text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim().split("\\s+");
        Set<String> words = new LinkedHashSet<>();
        for (String part : parts) {
            if (part.length() > 1) {
                words.add(part);
            }
        }
        return String.join(" ", words);
    }

    private static String mediaType(String url, String header) {
        String lower = (header + " " + url).toLowerCase(Locale.ROOT);
        if (lower.contains("png")) return "image/png";
        if (lower.contains("gif")) return "image/gif";
        if (lower.contains("webp")) return "image/webp";
        return "image/jpeg";
    }

    private record LoadedImage(byte[] bytes, String mediaType) {}

    public enum Kind {
        EMPTY_URL,
        UNREADABLE,
        MODEL_FAILED,
        READ,
    }

    public record ImageWordRead(Kind kind, String words, String detail) {
        public static ImageWordRead emptyUrl() {
            return new ImageWordRead(Kind.EMPTY_URL, "", "");
        }

        public static ImageWordRead unreadable(String detail) {
            return new ImageWordRead(Kind.UNREADABLE, "", detail == null ? "" : detail);
        }

        public static ImageWordRead modelFailed(String detail) {
            return new ImageWordRead(Kind.MODEL_FAILED, "", detail == null ? "" : detail);
        }

        public static ImageWordRead read(String words) {
            return new ImageWordRead(Kind.READ, words == null ? "" : words, "");
        }
    }

    private static String shortBedrockMessage(Exception error) {
        String message = error.getMessage() == null ? "Bedrock request failed" : error.getMessage();
        int service = message.indexOf(" (Service:");
        if (service > 0) {
            message = message.substring(0, service);
        }
        return message;
    }

    private static final class ModelReadException extends Exception {

        private ModelReadException(String message) {
            super(message);
        }
    }
}
