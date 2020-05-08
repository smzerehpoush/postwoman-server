package com.mahdiyar.model.dto.team;

import com.mahdiyar.model.dto.user.UserDto;
import com.mahdiyar.model.entity.TeamEntity;
import com.mahdiyar.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
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
    private List<UserDto> members;

    public TeamDto(TeamEntity teamEntity) {
        this.uniqueId = teamEntity.getUniqueId();
        this.name = teamEntity.getName();
        this.description = teamEntity.getDescription();
        this.owner = new UserDto(teamEntity.getOwner());
        final List<UserEntity> teamMembers = teamEntity.getMembers();
        this.membersCount = teamMembers.size();
        if (CollectionUtils.isEmpty(teamMembers))
            this.members = Collections.emptyList();
        else
            this.members = teamMembers.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
