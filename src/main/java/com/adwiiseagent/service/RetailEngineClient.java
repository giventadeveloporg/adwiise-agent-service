package com.adwiiseagent.service;

import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

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
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Engine day upload returned an empty body from " + baseUrl());
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

    private ResponseStatusException engineUnreachable(WebClientRequestException ex) {
        LOG.error("Retail engine unreachable at {}", baseUrl(), ex);
        return new ResponseStatusException(
            HttpStatus.SERVICE_UNAVAILABLE,
            "Retail AI engine is unreachable at " + baseUrl() + ". Start the engine on that URL (or set RETAIL_ENGINE_BASE_URL).",
            ex
        );
    }

    private ResponseStatusException engineHttpError(WebClientResponseException ex) {
        int code = ex.getStatusCode().value();
        LOG.error("Retail engine HTTP {} from {}: {}", code, baseUrl(), ex.getResponseBodyAsString());
        HttpStatus mapped = code >= 500 ? HttpStatus.BAD_GATEWAY : (code == 404 ? HttpStatus.BAD_GATEWAY : HttpStatus.BAD_REQUEST);
        String detail = ex.getResponseBodyAsString();
        if (detail == null || detail.isBlank()) {
            detail = "Retail AI engine returned HTTP " + code;
        } else if (detail.length() > 400) {
            detail = detail.substring(0, 400);
        }
        return new ResponseStatusException(mapped, detail, ex);
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
