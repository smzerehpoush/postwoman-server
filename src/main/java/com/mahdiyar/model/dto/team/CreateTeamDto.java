package com.mahdiyar.model.dto.team;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class CreateTeamDto {
    private String name;
    private String description;
    @NotNull
    private List<String> emailList;
    @NotNull
    private List<String> usernameList;
}
