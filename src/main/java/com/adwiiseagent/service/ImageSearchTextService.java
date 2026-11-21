package com.adwiiseagent.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reads image words with Bedrock and stores them on the existing search columns.
 */
@Service
public class ImageSearchTextService {

    private final JdbcTemplate jdbcTemplate;
    private final BedrockImageTextService bedrockImageTextService;

    public ImageSearchTextService(JdbcTemplate jdbcTemplate, BedrockImageTextService bedrockImageTextService) {
        this.jdbcTemplate = jdbcTemplate;
        this.bedrockImageTextService = bedrockImageTextService;
    }

    @Transactional
    public int refresh(String entityType, Long entityId) {
        if (entityId == null) {
            throw new IllegalArgumentException("entityId is required");
        }
        String type = entityType == null ? "" : entityType.trim().toLowerCase(Locale.ROOT);
        return switch (type) {
            case "event_sponsors" -> refreshSponsor(entityId);
            case "event_details" -> refreshSimple(
                entityId,
                "SELECT email_header_image_url AS image_url, title, description, location FROM event_details WHERE id = ?",
                "UPDATE event_details SET ocr_text_from_images_upload = ?, search_text = ?, image_text_source = 'BEDROCK', updated_at = NOW() WHERE id = ?",
                List.of("title", "description", "location")
            );
            case "gallery_album" -> refreshSimple(
                entityId,
                "SELECT cover_image_url AS image_url, title, description FROM gallery_album WHERE id = ?",
                "UPDATE gallery_album SET ocr_text_from_images_upload = ?, search_text = ?, image_text_source = 'BEDROCK', updated_at = NOW() WHERE id = ?",
                List.of("title", "description")
            );
            case "focus_group" -> refreshSimple(
                entityId,
                "SELECT cover_image_url AS image_url, name, slug, description FROM focus_group WHERE id = ?",
                "UPDATE focus_group SET ocr_text_from_images_upload = ?, search_text = ?, image_text_source = 'BEDROCK', updated_at = NOW() WHERE id = ?",
                List.of("name", "slug", "description")
            );
            default -> throw new IllegalArgumentException("Unsupported entityType");
        };
    }

    private int refreshSponsor(Long id) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
            "SELECT logo_url, hero_image_url, banner_image_url, name, company_name, tagline, description, type FROM event_sponsors WHERE id = ?",
            id
        );
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Sponsor not found");
        }
        Map<String, Object> row = rows.get(0);
        String words = joinWords(
            bedrockImageTextService.readWords(string(row.get("logo_url"))),
            bedrockImageTextService.readWords(string(row.get("hero_image_url"))),
            bedrockImageTextService.readWords(string(row.get("banner_image_url")))
        );
        String search = joinWords(
            string(row.get("name")),
            string(row.get("company_name")),
            string(row.get("tagline")),
            string(row.get("description")),
            string(row.get("type")),
            words
        );
        jdbcTemplate.update(
            "UPDATE event_sponsors SET ocr_text_from_images_upload = ?, search_text = ?, image_text_source = 'BEDROCK', updated_at = NOW() WHERE id = ?",
            words,
            search,
            id
        );
        return countWords(words);
    }

    private int refreshSimple(Long id, String selectSql, String updateSql, List<String> textColumns) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql, id);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Record not found");
        }
        Map<String, Object> row = rows.get(0);
        String words = bedrockImageTextService.readWords(string(row.get("image_url")));
        List<String> parts = new ArrayList<>();
        for (String column : textColumns) {
            parts.add(string(row.get(column)));
        }
        parts.add(words);
        String search = joinWords(parts.toArray(String[]::new));
        jdbcTemplate.update(updateSql, words, search, id);
        return countWords(words);
    }

    private static String string(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String joinWords(String... parts) {
        Set<String> words = new LinkedHashSet<>();
        for (String part : parts) {
            if (part == null || part.isBlank()) continue;
            for (String word : part.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim().split("\\s+")) {
                if (word.length() > 1) words.add(word);
            }
        }
        return String.join(" ", words);
    }

    private static int countWords(String words) {
        if (words == null || words.isBlank()) return 0;
        return words.split("\\s+").length;
    }
}
