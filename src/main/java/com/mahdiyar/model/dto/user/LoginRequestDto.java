package com.mahdiyar.model.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    @NotBlank
    private String password;
    private String email;
}
