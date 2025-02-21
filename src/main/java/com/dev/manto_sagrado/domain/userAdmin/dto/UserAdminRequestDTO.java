package com.dev.manto_sagrado.domain.userAdmin.dto;

import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminRequestDTO {
    private long id;
    private String name;
    private String email;
    private String password;

    public static UserAdmin newUserAdmin(UserAdminRequestDTO user) {
        return UserAdmin.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
