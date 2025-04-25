package com.dev.manto_sagrado.domain.address.entity;

import com.dev.manto_sagrado.domain.address.Enum.AddressDefaultStatus;
import com.dev.manto_sagrado.domain.address.Enum.AddressType;
import com.dev.manto_sagrado.domain.client.entity.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "addresses")
@Entity(name = "Address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Identificação deve ser preenchida")
    private String identification;

    @NotNull(message = "Tipo de endereço deve ser preenchido")
    @Enumerated(EnumType.ORDINAL)
    private AddressType type;

    @NotNull(message = "Logradouro deve ser preenchido")
    private String streetAddress;

    @NotNull(message = "Número deve ser preenchido")
    private int number;

    @NotNull(message = "Complemento deve ser preenchido")
    private String complement;

    @NotNull(message = "CEP deve ser preenchido")
    private String cep;

    @NotNull(message = "Bairro deve ser preenchido")
    private String neighborhood;

    @NotNull(message = "Cidade deve ser preenchida")
    private String city;

    @NotNull(message = "UF deve ser preenchida")
    private String uf;

    @NotNull(message = "Usuário deve ser preenchido")
    @ManyToOne
    @JoinColumn(name = "fk_user")
    private Client client;

    @NotNull(message = "Endereço padrão ou não deve ser preenchido")
    @Enumerated(EnumType.ORDINAL)
    private AddressDefaultStatus defaultAddress;
}
