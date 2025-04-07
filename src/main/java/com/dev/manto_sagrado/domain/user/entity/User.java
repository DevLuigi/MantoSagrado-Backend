package com.dev.manto_sagrado.domain.user.entity;

import com.dev.manto_sagrado.domain.user.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Table(name = "clients")
@Entity(name = "Client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email @NotNull(message = "Email deve ser preenchido")
    private String email;

    @NotNull(message = "Senha deve ser preenchida")
    private String password;

    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    @NotNull(message = "Nome deve ser preenchido")
    private String name;

    @Size(min = 3, message = "O sobrenome deve ter no mínimo 3 caracteres")
    @NotNull(message = "Sobrenome deve ser preenchido")
    private String lastName;

    @NotNull(message = "CPF deve ser preenchido")
    private String cpf;

    @NotNull(message = "Data de nascimento deve ser preenchida")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate birthDate;

    @NotNull(message = "Genero deve ser preenchido")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

}
