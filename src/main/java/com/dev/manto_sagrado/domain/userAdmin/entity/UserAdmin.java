package com.dev.manto_sagrado.domain.userAdmin.entity;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "users")
@Entity(name = "UserAdmin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class UserAdmin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Nome deve ser preenchido")
    private String name;

    @Email @NotNull(message = "Email deve ser preenchido")
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Group userGroup;

    @NotNull
    private String cpf;
}