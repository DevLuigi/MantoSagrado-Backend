package com.dev.manto_sagrado.domain.user.dto;

import com.dev.manto_sagrado.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDTO {
    private long id;
    private String email;

    public static UserLoginResponseDTO fromUser(User user) {
        return UserLoginResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
