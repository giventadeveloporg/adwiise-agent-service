package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailEngineClient;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Morning-brief helpers. Amplify cannot reach the internal engine ALB, so the admin
 * app calls these endpoints and the backend forwards to RETAIL_ENGINE_BASE_URL.
 */
@RestController
@RequestMapping("/api/retail-brief")
public class RetailBriefResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailBriefResource.class);
    private static final String ENTITY_NAME = "retailBrief";

    private final RetailAccessService retailAccessService;
    private final RetailEngineClient retailEngineClient;

    public RetailBriefResource(RetailAccessService retailAccessService, RetailEngineClient retailEngineClient) {
        this.retailAccessService = retailAccessService;
        this.retailEngineClient = retailEngineClient;
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(
        @RequestBody Map<String, Object> body,
        @RequestHeader(value = "X-Tenant-ID", required = false) String tenantHeader
    ) {
        retailAccessService.assertModuleAccess();
        String tenantId = firstText(tenantHeader, body.get("tenantId"));
        if (!StringUtils.hasText(tenantId)) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantrequired");
        }
        String metricDate = firstText(null, body.get("metricDate"));
        if (!StringUtils.hasText(metricDate)) {
            throw new BadRequestAlertException("metricDate is required", ENTITY_NAME, "daterequired");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("tenantId", tenantId);
        payload.put("metricDate", metricDate);
        payload.put("source", firstText("history", body.get("source")));
        Object post = body.get("post");
        payload.put("post", post == null ? Boolean.FALSE : post);
        if (body.get("stationCode") != null) {
            payload.put("stationCode", body.get("stationCode"));
        }
        if (body.get("stationId") != null) {
            payload.put("stationId", body.get("stationId"));
        }
        if (body.get("retailerType") != null) {
            payload.put("retailerType", body.get("retailerType"));
        }
        if (body.get("siteType") != null) {
            payload.put("siteType", body.get("siteType"));
        }
        LOG.debug("REST request to refresh retail brief tenant={} date={}", tenantId, metricDate);
        return ResponseEntity.ok(retailEngineClient.triggerRun(payload));
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(
        @RequestBody Map<String, Object> body,
        @RequestHeader(value = "X-Tenant-ID", required = false) String tenantHeader
    ) {
        retailAccessService.assertModuleAccess();
        String tenantId = firstText(tenantHeader, body.get("tenantId"));
        if (!StringUtils.hasText(tenantId)) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantrequired");
        }
        String question = firstText(null, body.get("question"));
        if (!StringUtils.hasText(question)) {
            throw new BadRequestAlertException("question is required", ENTITY_NAME, "questionrequired");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("tenantId", tenantId);
        payload.put("question", question);
        if (body.get("metricDate") != null) {
            payload.put("metricDate", body.get("metricDate"));
        }
        if (body.get("stationCode") != null) {
            payload.put("stationCode", body.get("stationCode"));
        }
        LOG.debug("REST request to ask retail brief tenant={}", tenantId);
        return ResponseEntity.ok(retailEngineClient.chat(payload));
    }

    private static String firstText(String preferred, Object fallback) {
        if (StringUtils.hasText(preferred)) {
            return preferred.trim();
        }
        if (fallback == null) {
            return null;
        }
        String text = String.valueOf(fallback).trim();
        return text.isEmpty() || "null".equals(text) ? null : text;
    }
}
