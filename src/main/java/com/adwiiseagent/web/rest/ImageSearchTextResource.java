package com.adwiiseagent.web.rest;

import com.adwiiseagent.service.ImageSearchTextService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reads uploaded images with Bedrock and stores spaced words for search.
 */
@RestController
@RequestMapping("/api/image-search-text")
public class ImageSearchTextResource {

    private final ImageSearchTextService imageSearchTextService;

    public ImageSearchTextResource(ImageSearchTextService imageSearchTextService) {
        this.imageSearchTextService = imageSearchTextService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> extract(@RequestBody ImageSearchTextRequest request) {
        int words = imageSearchTextService.refresh(request.entityType(), request.entityId());
        return ResponseEntity.ok(Map.of("words", words, "imageTextSource", "BEDROCK"));
    }

    public record ImageSearchTextRequest(String entityType, Long entityId) {}
}
