package com.mahdiyar.controller;

import com.mahdiyar.context.RequestContext;
import com.mahdiyar.exceptions.ServiceException;
import com.mahdiyar.model.dto.team.CreateTeamDto;
import com.mahdiyar.model.dto.team.TeamDto;
import com.mahdiyar.security.AuthRequired;
import com.mahdiyar.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mahdiyar
 */
@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final RequestContext requestContext;

    @AuthRequired
    @PostMapping
    public ResponseEntity<TeamDto> create(@Valid @RequestBody CreateTeamDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(teamService.create(requestDto, requestContext.getUser()));
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> get() {
        return ResponseEntity.ok(teamService.get());
    }
}
