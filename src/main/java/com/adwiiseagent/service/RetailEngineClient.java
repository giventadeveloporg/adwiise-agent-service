package com.adwiiseagent.service;

import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Forwards register uploads to the AI engine. Owner day uploads post through the feature store path.
 */
@Component
public class RetailEngineClient {

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
        WebClient
            .create(base)
            .post()
            .uri("/v1/inbox")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .toBodilessEntity()
            .block(Duration.ofSeconds(30));
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
        Map<String, Object> body = WebClient
            .create(baseUrl())
            .post()
            .uri("/v1/uploads/day")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(Map.class)
            .block(Duration.ofSeconds(120));
        if (body == null) {
            throw new IllegalStateException("Engine day upload returned an empty body");
        }
        return body;
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
