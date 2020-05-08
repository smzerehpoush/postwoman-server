package com.mahdiyar.controller;

import com.mahdiyar.context.RequestContext;
import com.mahdiyar.exceptions.ServiceException;
import com.mahdiyar.model.dto.workspace.CreateWorkspaceDto;
import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import com.mahdiyar.security.AuthRequired;
import com.mahdiyar.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mahdiyar
 */
@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;
    private final RequestContext requestContext;

    @AuthRequired
    @PostMapping
    public ResponseEntity<WorkspaceDto> create(@Valid @RequestBody CreateWorkspaceDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.create(requestDto, requestContext.getUser()));
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDto>> get() {
        return ResponseEntity.ok(workSpaceService.get());
    }

    @GetMapping("/{workspace-id}")
    public ResponseEntity<WorkspaceDto> get(@PathVariable("workspace-id") String workspaceId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.get(workspaceId));
    }

    @PostMapping("/{workspace-id}/join/{user-id}")
    @AuthRequired
    public ResponseEntity<WorkspaceDto> join(
            @PathVariable("workspace-id") String workspaceId,
            @PathVariable("user-id") String userId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.join(workspaceId, userId));
    }
}
