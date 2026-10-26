package com.adwiiseagent.web.rest;

import com.adwiiseagent.service.RosterCatalogLookup;
import com.adwiiseagent.service.dto.TeamMemberSportDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Read model for squad-member sports. Writes go through {@code PUT/PATCH /api/team-members} via {@code sportCategoryIds}.
 */
@RestController
@RequestMapping("/api/team-member-sports")
public class TeamMemberSportResource {

    private final RosterCatalogLookup rosterCatalogLookup;

    public TeamMemberSportResource(RosterCatalogLookup rosterCatalogLookup) {
        this.rosterCatalogLookup = rosterCatalogLookup;
    }

    @GetMapping("")
    public ResponseEntity<List<TeamMemberSportDTO>> list(
        @RequestParam(name = "teamMemberId.equals", required = false) Long teamMemberId
    ) {
        if (teamMemberId == null) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(rosterCatalogLookup.listForMember(teamMemberId));
    }
}
