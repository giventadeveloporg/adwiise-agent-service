package com.adwiiseagent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
 */
@Service
public class BedrockImageTextService {

    private static final Logger log = LoggerFactory.getLogger(BedrockImageTextService.class);
    private static final String PROMPT =
        "List only the real words you can read in this image. Return lowercase words separated by single spaces. " +
        "Do not include punctuation, symbols, or guesses. If there are no readable words, return nothing.";

    private final BedrockRuntimeClient client;
    private final String modelId;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public BedrockImageTextService(
        @Value("${aws.s3.access-key}") String accessKey,
        @Value("${aws.s3.secret-key}") String secretKey,
        @Value("${aws.s3.region}") String region,
        @Value("${aws.bedrock.image-text-model-id:anthropic.claude-3-haiku-20240307-v1:0}") String modelId,
        ObjectMapper objectMapper
    ) {
        this.client =
            BedrockRuntimeClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.modelId = modelId;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    public String readWords(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return "";
        }
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(imageUrl.trim())).GET().timeout(Duration.ofSeconds(60)).build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.warn("[ImageSearchText] Download failed for {}: HTTP {}", imageUrl, response.statusCode());
                return "";
            }
            String mediaType = mediaType(imageUrl, response.headers().firstValue("content-type").orElse(""));
            String encoded = Base64.getEncoder().encodeToString(response.body());
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
            InvokeModelResponse modelResponse = client.invokeModel(
                InvokeModelRequest
                    .builder()
                    .modelId(modelId)
                    .contentType("application/json")
                    .accept("application/json")
                    .body(SdkBytes.fromUtf8String(body))
                    .build()
            );
            JsonNode root = objectMapper.readTree(modelResponse.body().asUtf8String());
            String text = root.path("content").path(0).path("text").asText("");
            return tokenize(text);
        } catch (Exception e) {
            log.warn("[ImageSearchText] Bedrock read failed for {}: {}", imageUrl, e.getMessage());
            return "";
        }
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
}
