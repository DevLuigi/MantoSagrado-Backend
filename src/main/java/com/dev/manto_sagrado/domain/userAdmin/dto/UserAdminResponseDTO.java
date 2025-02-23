package com.dev.manto_sagrado.domain.userAdmin.dto;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAdminResponseDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private Group userGroup;
    private String cpf;

    public static UserAdminResponseDTO fromUserAdmin(UserAdmin user) {
        return UserAdminResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .userGroup(user.getUserGroup())
                .cpf(user.getCpf())
                .build();
    }
}
