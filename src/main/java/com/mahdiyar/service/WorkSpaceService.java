package com.mahdiyar.service;

import com.mahdiyar.exceptions.GeneralNotFoundException;
import com.mahdiyar.model.dto.workspace.CreateWorkspaceDto;
import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import com.mahdiyar.model.entity.TeamEntity;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.model.entity.WorkspaceEntity;
import com.mahdiyar.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mahdiyar
 */
@Service
@RequiredArgsConstructor
public class WorkSpaceService {
    private final WorkspaceRepository workspaceRepository;
    private final UserService userService;
    private final TeamService teamService;

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
}
