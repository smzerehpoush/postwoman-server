package com.mahdiyar.service;

import com.mahdiyar.exceptions.GeneralDuplicateException;
import com.mahdiyar.exceptions.GeneralNotFoundException;
import com.mahdiyar.exceptions.InvalidRequestException;
import com.mahdiyar.model.dto.team.CreateTeamDto;
import com.mahdiyar.model.dto.team.TeamDto;
import com.mahdiyar.model.entity.TeamEntity;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mahdiyar
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamDto create(CreateTeamDto requestDto, UserEntity owner) throws InvalidRequestException, GeneralDuplicateException {
        Set<UserEntity> members = findTeamMembers(requestDto);
        validateRequest(requestDto, owner);
        TeamEntity teamEntity = new TeamEntity(requestDto.getName(), requestDto.getDescription(), owner, members);
        teamEntity = teamRepository.saveAndFlush(teamEntity);
        logger.info("team created with id [{}]", teamEntity.getId());
        return new TeamDto(teamEntity, teamEntity.getMembers());
    }

    private void validateRequest(CreateTeamDto requestDto, UserEntity owner) throws InvalidRequestException, GeneralDuplicateException {
        if (requestDto.getName() == null)
            throw new InvalidRequestException();
        if (teamRepository.existsByOwner_idAndName(owner.getId(), requestDto.getName()))
            throw new GeneralDuplicateException("team-name", requestDto.getName());
    }

    private Set<UserEntity> findTeamMembers(CreateTeamDto requestDto) {
        Set<UserEntity> teamMembers =
                new HashSet<>(requestDto.getEmailList().size() + requestDto.getUsernameList().size());
        teamMembers.addAll(findTeamMembersWithEmail(requestDto.getEmailList()));
        teamMembers.addAll(findTeamMembersWithUsername(requestDto.getUsernameList()));
        return teamMembers;
    }

    private List<UserEntity> findTeamMembersWithEmail(List<String> emailList) {
        return userService.getAllByEmail(emailList);
    }

    private List<UserEntity> findTeamMembersWithUsername(List<String> usernameList) {
        return userService.getAllByUsername(usernameList);
    }

    public List<TeamDto> get() {
        return teamRepository.findAll().stream()
                .map(i -> new TeamDto(i, i.getMembers())).collect(Collectors.toList());
    }

    public TeamEntity findByUniqueId(String uniqueId) throws GeneralNotFoundException {
        TeamEntity team = teamRepository.findByUniqueId(uniqueId);
        if (team == null)
            throw new GeneralNotFoundException("team", "uniqueId", uniqueId);
        else return team;
    }

    public TeamDto get(String teamId) throws GeneralNotFoundException {
        TeamEntity teamEntity = findByUniqueId(teamId);
        return new TeamDto(teamEntity, teamEntity.getMembers());
    }

    @Transactional(rollbackFor = Exception.class)
    public TeamDto join(String teamId, UserEntity user) throws GeneralNotFoundException {
        TeamEntity teamEntity = findByUniqueId(teamId);
        if (teamEntity.getMembers() == null)
            teamEntity.setMembers(new HashSet<>());
        teamEntity.getMembers().add(user);
        teamEntity = teamRepository.save(teamEntity);
        return new TeamDto(teamEntity, teamEntity.getMembers());
    }
}
