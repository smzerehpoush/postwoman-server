package com.mahdiyar.controller;

import com.mahdiyar.context.RequestContext;
import com.mahdiyar.exceptions.ServiceException;
import com.mahdiyar.model.dto.UpdateWorkspaceDto;
import com.mahdiyar.model.dto.workspace.CreateWorkspaceDto;
import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import com.mahdiyar.security.AuthRequired;
import com.mahdiyar.service.WorkSpaceService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Create new workspace")
    @AuthRequired
    @PostMapping
    public ResponseEntity<WorkspaceDto> create(@Valid @RequestBody CreateWorkspaceDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.create(requestDto, requestContext.getUser()));
    }

    @GetMapping("Get WorkSpaces List")
    public ResponseEntity<List<WorkspaceDto>> get() {
        return ResponseEntity.ok(workSpaceService.get());
    }

    @ApiOperation("Get Workspace")
    @GetMapping("/{workspace-id}")
    public ResponseEntity<WorkspaceDto> get(@PathVariable("workspace-id") String workspaceId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.get(workspaceId));
    }

    @ApiOperation("Join Workspace")
    @PostMapping("/{workspace-id}/join/{user-id}")
    @AuthRequired
    public ResponseEntity<WorkspaceDto> join(
            @PathVariable("workspace-id") String workspaceId,
            @PathVariable("user-id") String userId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.join(workspaceId, userId));
    }

    @ApiOperation("Add Teams/Users to workspace")
    @AuthRequired
    @PutMapping("{workspace-id}/users")
    public ResponseEntity<Integer> addTeamsOrUsers(
            @PathVariable("workspace-id") String workspaceId,
            @Valid @RequestBody UpdateWorkspaceDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.addTeamsOrUsers(requestDto, workspaceId, requestContext.getUser()));
    }

    @ApiOperation("Remove Teams/Users to workspace")
    @AuthRequired
    @DeleteMapping("{workspace-id}/users")
    public ResponseEntity<Integer> removeTeamsOrUsers(
            @PathVariable("workspace-id") String workspaceId,
            @Valid @RequestBody UpdateWorkspaceDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.removeTeamsOrUsers(requestDto, workspaceId, requestContext.getUser()));
    }

}
