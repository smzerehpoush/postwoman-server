package com.mahdiyar.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class UpdateWorkspaceMembersDto {
    @NotNull
    private Set<String> teamIds;
    @NotNull
    private Set<String> userIds;
}
