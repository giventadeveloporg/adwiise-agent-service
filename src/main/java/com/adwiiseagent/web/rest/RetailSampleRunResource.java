package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.service.BatchJobService;
import com.adwiiseagent.service.RetailEngineClient;
import com.adwiiseagent.service.dto.RetailSampleRunRequest;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Temporary stand-in for a customer file connection. Runs a bundled sample or an uploaded register file.
 */
@RestController
@RequestMapping("/api/retail-sample-runs")
public class RetailSampleRunResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailSampleRunResource.class);
    private static final String ENTITY_NAME = "retailSampleRun";

    private final BatchJobService batchJobService;
    private final RetailEngineClient retailEngineClient;

    public RetailSampleRunResource(BatchJobService batchJobService, RetailEngineClient retailEngineClient) {
        this.batchJobService = batchJobService;
        this.retailEngineClient = retailEngineClient;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> runSample(
        @RequestBody RetailSampleRunRequest request,
        @RequestHeader(value = "X-Tenant-ID", required = false) String tenantHeader
    ) {
        applyTenant(request, tenantHeader);
        if (!StringUtils.hasText(request.getTenantId())) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantrequired");
        }
        if (!StringUtils.hasText(request.getSource())) {
            request.setSource("sample");
        }
        LOG.debug("REST request to run retail sample tenant={} retailerType={}", request.getTenantId(), request.getRetailerType());
        return ResponseEntity.ok(batchJobService.triggerRetailSampleRun(request));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> runUpload(
        @RequestParam String retailerType,
        @RequestParam(required = false) String siteType,
        @RequestParam(required = false) String metricDate,
        @RequestParam(required = false) String tenantId,
        @RequestPart("registerFile") MultipartFile registerFile,
        @RequestPart(value = "fuelFile", required = false) MultipartFile fuelFile,
        @RequestHeader(value = "X-Tenant-ID", required = false) String tenantHeader
    ) throws java.io.IOException {
        String tenant = StringUtils.hasText(tenantHeader) ? tenantHeader : tenantId;
        if (!StringUtils.hasText(tenant)) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantrequired");
        }
        if (registerFile == null || registerFile.isEmpty()) {
            throw new BadRequestAlertException("A register file is required", ENTITY_NAME, "registerrequired");
        }
        byte[] fuelBytes = fuelFile == null || fuelFile.isEmpty() ? null : fuelFile.getBytes();
        String fuelName = fuelFile == null ? null : fuelFile.getOriginalFilename();
        retailEngineClient.saveInbox(tenant, registerFile.getBytes(), registerFile.getOriginalFilename(), fuelBytes, fuelName);

        RetailSampleRunRequest request = new RetailSampleRunRequest();
        request.setTenantId(tenant);
        request.setRetailerType(retailerType);
        request.setSiteType(StringUtils.hasText(siteType) ? siteType : "RETAIL");
        request.setMetricDate(metricDate);
        request.setSource("inbox");
        request.setPost(false);
        LOG.debug("REST request to run uploaded retail files tenant={} retailerType={}", tenant, retailerType);
        return ResponseEntity.ok(batchJobService.triggerRetailSampleRun(request));
    }

    private static void applyTenant(RetailSampleRunRequest request, String tenantHeader) {
        if (request == null) {
            return;
        }
        if (StringUtils.hasText(tenantHeader)) {
            request.setTenantId(tenantHeader);
        }
    }
}
