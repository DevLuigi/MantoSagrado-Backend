package com.dev.manto_sagrado.domain.userAdmin.dto;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import com.dev.manto_sagrado.domain.userAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDTO {
    private long id;
    private String email;
    private Group userGroup;

    public static UserLoginResponseDTO fromUserAdmin(UserAdmin user) {
        return UserLoginResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userGroup(user.getUserGroup())
                .build();
    }
}
