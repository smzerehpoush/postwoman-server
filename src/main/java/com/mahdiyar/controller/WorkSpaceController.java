package com.mahdiyar.controller;

import com.mahdiyar.context.RequestContext;
import com.mahdiyar.exceptions.ServiceException;
import com.mahdiyar.model.BaseCollectionModel;
import com.mahdiyar.model.CollectionModel;
import com.mahdiyar.model.dto.UpdateWorkspaceMembersDto;
import com.mahdiyar.model.dto.collection.CollectionStructureModel;
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
    @PutMapping("{workspace-id}/members")
    public ResponseEntity<Integer> addTeamsOrUsers(
            @PathVariable("workspace-id") String workspaceId,
            @Valid @RequestBody UpdateWorkspaceMembersDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.addMembers(requestDto, workspaceId, requestContext.getUser()));
    }

    @ApiOperation("Remove Teams/Users to workspace")
    @AuthRequired
    @DeleteMapping("{workspace-id}/members")
    public ResponseEntity<Integer> removeTeamsOrUsers(
            @PathVariable("workspace-id") String workspaceId,
            @Valid @RequestBody UpdateWorkspaceMembersDto requestDto)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.removeMembers(requestDto, workspaceId, requestContext.getUser()));
    }

    @ApiOperation("Get Workspace Collections")
    @AuthRequired
    @GetMapping("{workspace-id}/collections")
    public ResponseEntity<List<BaseCollectionModel>> getCollections(
            @PathVariable("workspace-id") String workspaceId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.getCollections(workspaceId, requestContext.getUser()));
    }

    @ApiOperation("Get Workspace Collection")
    @AuthRequired
    @GetMapping("{workspace-id}/collections/{collection-id}")
    public ResponseEntity<CollectionModel> removeTeamsOrUsers(
            @PathVariable("workspace-id") String workspaceId,
            @PathVariable("collection-id") String collectionId)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.getCollection(workspaceId, collectionId, requestContext.getUser()));
    }

    @ApiOperation("Update Workspace Collection")
    @AuthRequired
    @PutMapping("{workspace-id}/collections/{collection-id}")
    public ResponseEntity<Void> updateWorkspaceCollection(
            @PathVariable("workspace-id") String workspaceId,
            @PathVariable("collection-id") String collectionId,
            @RequestBody CollectionStructureModel collectionStructureModel)
            throws ServiceException {
        return ResponseEntity.ok(workSpaceService.updateWorkspaceCollection(
                workspaceId, collectionId, collectionStructureModel, requestContext.getUser()));
    }
}
