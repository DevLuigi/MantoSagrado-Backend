package com.dev.manto_sagrado.domain.user.dto;

import com.dev.manto_sagrado.domain.user.Enum.Gender;
import com.dev.manto_sagrado.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;

    public static User newUser(UserRequestDTO user) {
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .lastName(user.getLastName())
                .cpf(user.getCpf())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }
}
