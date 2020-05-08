package com.mahdiyar.model.dto.team;

import com.mahdiyar.model.dto.user.UserDto;
import com.mahdiyar.model.entity.TeamEntity;
import com.mahdiyar.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class TeamDto {
    private String uniqueId;
    private String name;
    private String description;
    private int membersCount;
    private UserDto owner;
    private Set<UserDto> members;

    public TeamDto(TeamEntity teamEntity, Set<UserEntity> members) {
        this.uniqueId = teamEntity.getUniqueId();
        this.name = teamEntity.getName();
        this.description = teamEntity.getDescription();
        this.owner = new UserDto(teamEntity.getOwner());
        this.membersCount = members.size();
        this.members = members.stream().map(UserDto::new).collect(Collectors.toSet());
    }
}
