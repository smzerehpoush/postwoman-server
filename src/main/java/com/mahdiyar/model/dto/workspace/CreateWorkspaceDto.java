package com.mahdiyar.model.dto.workspace;

import com.mahdiyar.model.enums.VisibilityType;
import com.mahdiyar.model.enums.WorkSpaceType;
import io.swagger.annotations.ApiModel;
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
@ApiModel
public class CreateWorkspaceDto {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private WorkSpaceType type;
    @NotNull
    private VisibilityType visibility;
    @NotNull
    private Set<String> teamIds;
    @NotNull
    private Set<String> userIds;

}
