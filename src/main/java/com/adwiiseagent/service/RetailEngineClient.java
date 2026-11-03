package com.adwiiseagent.service;

import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Forwards register uploads to the AI engine. Owner day uploads post through the feature store path.
 */
@Component
public class RetailEngineClient {

    private static final Logger LOG = LoggerFactory.getLogger(RetailEngineClient.class);

    private final String engineBaseUrl;

    public RetailEngineClient(@Value("${retail.engine.base-url:http://127.0.0.1:8090}") String engineBaseUrl) {
        this.engineBaseUrl = engineBaseUrl;
    }

    public void saveInbox(String tenantId, byte[] registerBytes, String registerName, byte[] fuelBytes, String fuelName) {
        String base = baseUrl();
        org.springframework.http.client.MultipartBodyBuilder builder = new org.springframework.http.client.MultipartBodyBuilder();
        builder.part("tenantId", tenantId);
        builder.part("registerFile", named(registerBytes, registerName == null ? "register.csv" : registerName));
        if (fuelBytes != null && fuelBytes.length > 0) {
            builder.part("fuelFile", named(fuelBytes, fuelName == null ? "fuel.csv" : fuelName));
        }
        try {
            WebClient
                .create(base)
                .post()
                .uri("/v1/inbox")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toBodilessEntity()
                .block(Duration.ofSeconds(30));
        } catch (WebClientRequestException ex) {
            throw engineUnreachable(ex);
        } catch (WebClientResponseException ex) {
            throw engineHttpError(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadDay(
        String tenantId,
        String stationCode,
        String metricDate,
        String retailerType,
        String siteType,
        byte[] registerBytes,
        String registerName,
        byte[] fuelBytes,
        String fuelName
    ) {
        org.springframework.http.client.MultipartBodyBuilder builder = new org.springframework.http.client.MultipartBodyBuilder();
        builder.part("tenantId", tenantId);
        builder.part("stationCode", stationCode);
        builder.part("metricDate", metricDate);
        builder.part("retailerType", retailerType);
        builder.part("siteType", siteType == null || siteType.isBlank() ? "RETAIL" : siteType);
        builder.part("registerFile", named(registerBytes, registerName == null ? "register.csv" : registerName));
        if (fuelBytes != null && fuelBytes.length > 0) {
            builder.part("fuelFile", named(fuelBytes, fuelName == null ? "fuel.csv" : fuelName));
        }
        try {
            Map<String, Object> body = WebClient
                .create(baseUrl())
                .post()
                .uri("/v1/uploads/day")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(120));
            if (body == null || body.isEmpty()) {
                throw new IllegalStateException("Engine day upload returned an empty body from " + baseUrl());
            }
            return body;
        } catch (WebClientRequestException ex) {
            throw engineUnreachable(ex);
        } catch (WebClientResponseException ex) {
            throw engineHttpError(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> triggerRun(Map<String, Object> body) {
        try {
            Map<String, Object> result = WebClient
                .create(baseUrl())
                .post()
                .uri("/v1/runs/trigger")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body == null ? Map.of() : body)
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(120));
            return result == null ? Map.of() : result;
        } catch (WebClientRequestException ex) {
            throw engineUnreachable(ex);
        } catch (WebClientResponseException ex) {
            throw engineHttpError(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> chat(Map<String, Object> body) {
        try {
            Map<String, Object> result = WebClient
                .create(baseUrl())
                .post()
                .uri("/v1/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body == null ? Map.of() : body)
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(60));
            return result == null ? Map.of() : result;
        } catch (WebClientRequestException ex) {
            throw engineUnreachable(ex);
        } catch (WebClientResponseException ex) {
            throw engineHttpError(ex);
        }
    }

    private IllegalStateException engineUnreachable(WebClientRequestException ex) {
        LOG.error("Retail engine unreachable at {}", baseUrl(), ex);
        return new IllegalStateException(
            "Retail AI engine is unreachable at " +
            baseUrl() +
            ". Set RETAIL_ENGINE_BASE_URL on the backend to a running engine that serves POST /v1/uploads/day.",
            ex
        );
    }

    private IllegalStateException engineHttpError(WebClientResponseException ex) {
        LOG.error("Retail engine HTTP {} from {}: {}", ex.getStatusCode().value(), baseUrl(), ex.getResponseBodyAsString());
        return new IllegalStateException(
            "Retail AI engine returned HTTP " + ex.getStatusCode().value() + ": " + ex.getResponseBodyAsString(),
            ex
        );
    }

    private String baseUrl() {
        return engineBaseUrl.endsWith("/") ? engineBaseUrl.substring(0, engineBaseUrl.length() - 1) : engineBaseUrl;
    }

    private static ByteArrayResource named(byte[] bytes, String filename) {
        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
    }
}
