package com.mahdiyar.model.dto.user;

import com.mahdiyar.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class UserDto {
    public UserDto(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
    }

    private String username;
    private String email;
}
