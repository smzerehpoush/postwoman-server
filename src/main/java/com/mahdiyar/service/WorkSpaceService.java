package com.mahdiyar.service;

import com.mahdiyar.exceptions.GeneralNotFoundException;
import com.mahdiyar.model.BaseCollectionModel;
import com.mahdiyar.model.CollectionModel;
import com.mahdiyar.model.dto.UpdateWorkspaceMembersDto;
import com.mahdiyar.model.dto.collection.CollectionStructureModel;
import com.mahdiyar.model.dto.workspace.CreateWorkspaceDto;
import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import com.mahdiyar.model.entity.TeamEntity;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.model.entity.WorkspaceEntity;
import com.mahdiyar.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mahdiyar
 */
@Service
@RequiredArgsConstructor
public class WorkSpaceService {
    private final WorkspaceRepository workspaceRepository;
    private UserService userService;
    private TeamService teamService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public WorkspaceDto create(CreateWorkspaceDto requestDto, UserEntity user) throws GeneralNotFoundException {
        Set<TeamEntity> teams = findTeams(requestDto.getTeamIds());
        Set<UserEntity> users = findUsers(requestDto.getUserIds());
        WorkspaceEntity workspaceEntity =
                new WorkspaceEntity(requestDto.getName(),
                        requestDto.getDescription(),
                        user,
                        requestDto.getType(),
                        requestDto.getVisibility(),
                        teams,
                        users
                );
        workspaceEntity = workspaceRepository.save(workspaceEntity);
        return new WorkspaceDto(workspaceEntity);
    }

    private Set<UserEntity> findUsers(Set<String> userIds) throws GeneralNotFoundException {
        Set<UserEntity> users = new HashSet<>(userIds.size());
        for (String userId : userIds) {
            users.add(userService.findByUniqueId(userId));
        }
        return users;
    }

    private Set<TeamEntity> findTeams(Set<String> teamIds) throws GeneralNotFoundException {
        Set<TeamEntity> teams = new HashSet<>(teamIds.size());
        for (String teamId : teamIds) {
            teams.add(teamService.findByUniqueId(teamId));
        }
        return teams;
    }

    public List<WorkspaceDto> get() {
        return workspaceRepository.findAll().stream().map(WorkspaceDto::new).collect(Collectors.toList());
    }

    public WorkspaceEntity findByUniqueId(String uniqueId) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = workspaceRepository.findByUniqueId(uniqueId);
        if (workspaceEntity == null)
            throw new GeneralNotFoundException("workspace", "uniqueId", uniqueId);
        return workspaceEntity;
    }

    public WorkspaceDto get(String workspaceId) throws GeneralNotFoundException {
        return new WorkspaceDto(this.findByUniqueId(workspaceId));
    }

    public WorkspaceDto join(String workspaceId, String userId) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        UserEntity userEntity = userService.findByUniqueId(userId);
        if (workspaceEntity.getUsers() == null)
            workspaceEntity.setUsers(new HashSet<>());
        workspaceEntity.getUsers().add(userEntity);
        return new WorkspaceDto(workspaceRepository.save(workspaceEntity));
    }

    public List<WorkspaceEntity> getUserWorkspaces(UserEntity userEntity) {
        if (userEntity == null)
            return Collections.emptyList();
        List<WorkspaceEntity> workspaceEntities = new ArrayList<>();
        List<Long> userTeams = teamService.getUserTeams(userEntity);
        if (!CollectionUtils.isEmpty(userTeams))
            workspaceEntities.addAll(workspaceRepository.getUserWorkspacesByTeams(userTeams));
        workspaceEntities.addAll(workspaceRepository.getUserWorkspacesId(userEntity.getId()));
        workspaceEntities.addAll(workspaceRepository.getUserWorkspacesByOwners(userEntity.getId()));
        return workspaceEntities;
    }

    public int addMembers(UpdateWorkspaceMembersDto requestDto, String workspaceId, UserEntity user) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        int updatedCount = 0;
        updatedCount += addUsersToWorkspace(workspaceEntity, requestDto.getUserIds());
        updatedCount += addTeamsToWorkspace(workspaceEntity, requestDto.getTeamIds());
        return updatedCount;
    }

    private int addTeamsToWorkspace(WorkspaceEntity workspaceEntity, Set<String> teamIds) {
        if (CollectionUtils.isEmpty(teamIds))
            return 0;
        int count = 0;
        if (workspaceEntity.getTeams() == null)
            workspaceEntity.setTeams(new HashSet<>());
        for (String teamId : teamIds) {
            try {
                TeamEntity teamEntity = teamService.findByUniqueId(teamId);
                if (workspaceEntity.getTeams().add(teamEntity))
                    count++;
            } catch (GeneralNotFoundException e) {
//                do nothing
            }
        }
        workspaceRepository.saveAndFlush(workspaceEntity);
        return count;
    }

    private int addUsersToWorkspace(WorkspaceEntity workspaceEntity, Set<String> userIds) {
        if (CollectionUtils.isEmpty(userIds))
            return 0;
        int count = 0;
        if (workspaceEntity.getTeams() == null)
            workspaceEntity.setTeams(new HashSet<>());
        for (String userId : userIds) {
            try {
                UserEntity userEntity = userService.findByUniqueId(userId);
                if (workspaceEntity.getUsers().add(userEntity))
                    count++;
            } catch (GeneralNotFoundException e) {
//                do nothing
            }
        }
        workspaceRepository.saveAndFlush(workspaceEntity);
        return count;
    }

    private int removeTeamsFromWorkspace(WorkspaceEntity workspaceEntity, Set<String> teamIds) {
        if (CollectionUtils.isEmpty(teamIds))
            return 0;
        int count = 0;
        if (workspaceEntity.getTeams() == null)
            workspaceEntity.setTeams(new HashSet<>());
        for (String teamId : teamIds) {
            try {
                TeamEntity teamEntity = teamService.findByUniqueId(teamId);
                if (workspaceEntity.getTeams().remove(teamEntity))
                    count++;
            } catch (GeneralNotFoundException e) {
//                do nothing
            }
        }
        workspaceRepository.saveAndFlush(workspaceEntity);
        return count;
    }

    private int removeUsersFromWorkspace(WorkspaceEntity workspaceEntity, Set<String> userIds) {
        if (CollectionUtils.isEmpty(userIds))
            return 0;
        int count = 0;
        if (workspaceEntity.getTeams() == null)
            workspaceEntity.setTeams(new HashSet<>());
        for (String userId : userIds) {
            try {
                UserEntity userEntity = userService.findByUniqueId(userId);
                if (workspaceEntity.getUsers().remove(userEntity))
                    count++;
            } catch (GeneralNotFoundException e) {
//                do nothing
            }
        }
        workspaceRepository.saveAndFlush(workspaceEntity);
        return count;
    }

    public int removeMembers(UpdateWorkspaceMembersDto requestDto, String workspaceId, UserEntity user) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        int updatedCount = 0;
        updatedCount += removeUsersFromWorkspace(workspaceEntity, requestDto.getUserIds());
        updatedCount += removeTeamsFromWorkspace(workspaceEntity, requestDto.getTeamIds());
        return updatedCount;
    }

    public List<BaseCollectionModel> getCollections(String workspaceId, UserEntity user) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        checkUserInWorkspace(workspaceEntity, user);
        return null;
    }

    private void checkUserInWorkspace(WorkspaceEntity workspaceEntity, UserEntity user) {
        // TODO: 5/14/20 authenticate user in workspace

    }

    public CollectionModel getCollection(String workspaceId, String collectionId, UserEntity user) throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        checkUserInWorkspace(workspaceEntity, user);
        return null;
    }

    public Void updateWorkspaceCollection(
            String workspaceId,
            String collectionId,
            CollectionStructureModel collectionStructureModel,
            UserEntity user)
            throws GeneralNotFoundException {
        WorkspaceEntity workspaceEntity = findByUniqueId(workspaceId);
        checkUserInWorkspace(workspaceEntity, user);
        return null;
    }
}
