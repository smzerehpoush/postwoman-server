package com.mahdiyar.model.dto.workspace;

import com.mahdiyar.model.dto.user.UserDto;
import com.mahdiyar.model.entity.WorkspaceEntity;
import com.mahdiyar.model.enums.VisibilityType;
import com.mahdiyar.model.enums.WorkSpaceType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class WorkspaceDto {
    private String uniqueId;
    private String name;
    private String description;
    private WorkSpaceType type;
    private VisibilityType visibility;
    private UserDto owner;

    public WorkspaceDto(WorkspaceEntity entity) {
        this.uniqueId = entity.getUniqueId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.type = entity.getType();
        this.visibility = entity.getVisibility();
        this.owner = entity.getOwner() == null ? null : new UserDto(entity.getOwner());
    }
}
