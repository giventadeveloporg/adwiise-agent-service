package com.adwiiseagent.service;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Forwards a temporary register upload to the AI engine inbox. The file is not stored in Postgres.
 */
@Component
public class RetailEngineClient {

    private final String engineBaseUrl;

    public RetailEngineClient(@Value("${retail.engine.base-url:http://127.0.0.1:8090}") String engineBaseUrl) {
        this.engineBaseUrl = engineBaseUrl;
    }

    public void saveInbox(String tenantId, byte[] registerBytes, String registerName, byte[] fuelBytes, String fuelName) {
        String base = engineBaseUrl.endsWith("/") ? engineBaseUrl.substring(0, engineBaseUrl.length() - 1) : engineBaseUrl;
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

    private static ByteArrayResource named(byte[] bytes, String filename) {
        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
    }
}
