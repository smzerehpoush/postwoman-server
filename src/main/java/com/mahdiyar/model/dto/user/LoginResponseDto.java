package com.mahdiyar.model.dto.user;

import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    List<WorkspaceDto> workspaces;
}
