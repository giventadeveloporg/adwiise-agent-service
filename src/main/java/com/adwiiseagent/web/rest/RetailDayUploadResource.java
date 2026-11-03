package com.adwiiseagent.web.rest;

import com.adwiiseagent.domain.RetailLocation;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailEngineClient;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Owner or manager upload of one store day. Files go to the engine feature store and the day is saved.
 */
@RestController
@RequestMapping("/api/retail-day-uploads")
public class RetailDayUploadResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDayUploadResource.class);
    private static final String ENTITY_NAME = "retailDayUpload";

    private final RetailLocationRepository retailLocationRepository;
    private final RetailAccessService retailAccessService;
    private final RetailEngineClient retailEngineClient;

    public RetailDayUploadResource(
        RetailLocationRepository retailLocationRepository,
        RetailAccessService retailAccessService,
        RetailEngineClient retailEngineClient
    ) {
        this.retailLocationRepository = retailLocationRepository;
        this.retailAccessService = retailAccessService;
        this.retailEngineClient = retailEngineClient;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadDay(
        @RequestParam Long locationId,
        @RequestParam String metricDate,
        @RequestParam(required = false) String tenantId,
        @RequestPart("registerFile") MultipartFile registerFile,
        @RequestPart(value = "fuelFile", required = false) MultipartFile fuelFile,
        @RequestHeader(value = "X-Tenant-ID", required = false) String tenantHeader
    ) throws java.io.IOException {
        String tenant = StringUtils.hasText(tenantHeader) ? tenantHeader : tenantId;
        if (!StringUtils.hasText(tenant)) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantrequired");
        }
        if (!StringUtils.hasText(metricDate)) {
            throw new BadRequestAlertException("metricDate is required", ENTITY_NAME, "daterequired");
        }
        if (registerFile == null || registerFile.isEmpty()) {
            throw new BadRequestAlertException("A register file is required", ENTITY_NAME, "registerrequired");
        }
        retailAccessService.assertLocationIdAllowed(locationId);
        RetailLocation location = retailLocationRepository
            .findById(locationId)
            .orElseThrow(() -> new BadRequestAlertException("Location not found", ENTITY_NAME, "locationnotfound"));
        if (!tenant.equals(location.getTenantId())) {
            throw new BadRequestAlertException("Location is not in this tenant", ENTITY_NAME, "tenantmismatch");
        }
        String pack = StringUtils.hasText(location.getRetailerTypeCode()) ? location.getRetailerTypeCode().trim() : "GAS_STATION";
        boolean gas = "GAS_STATION".equals(pack) || "GAS_STATION_ENT".equals(pack);
        byte[] fuelBytes = fuelFile == null || fuelFile.isEmpty() ? null : fuelFile.getBytes();
        if (gas && fuelBytes == null) {
            throw new BadRequestAlertException("A fuel file is required for a gas station", ENTITY_NAME, "fuelrequired");
        }
        String fuelName = fuelFile == null ? null : fuelFile.getOriginalFilename();
        LOG.debug("REST request to upload retail day tenant={} location={} date={}", tenant, location.getLocationCode(), metricDate);
        Map<String, Object> result = retailEngineClient.uploadDay(
            tenant,
            location.getLocationCode(),
            metricDate,
            pack,
            "RETAIL",
            registerFile.getBytes(),
            registerFile.getOriginalFilename(),
            fuelBytes,
            fuelName
        );
        result.put("locationId", locationId);
        result.put("locationName", location.getLocationName());
        return ResponseEntity.ok(result);
    }
}
