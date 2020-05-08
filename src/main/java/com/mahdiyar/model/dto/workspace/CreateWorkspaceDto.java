package com.mahdiyar.model.dto.workspace;

import com.mahdiyar.model.enums.VisibilityType;
import com.mahdiyar.model.enums.WorkSpaceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class CreateWorkspaceDto {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private WorkSpaceType type;
    @NotBlank
    private VisibilityType visibility;
    @NotNull
    private Set<String> teamIds;
    @NotNull
    private Set<String> userIds;

}
