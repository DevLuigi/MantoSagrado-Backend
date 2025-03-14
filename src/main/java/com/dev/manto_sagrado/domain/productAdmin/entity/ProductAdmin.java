package com.dev.manto_sagrado.domain.productAdmin.entity;

import com.dev.manto_sagrado.domain.productAdmin.Enum.KitType;
import com.dev.manto_sagrado.domain.productAdmin.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity(name = "Product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class ProductAdmin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 200, message = "O nome não pode ter mais de 200 caracteres")
    @NotNull(message = "Nome deve ser preenchido")
    private String name;

    @NotNull(message = "Nome do time deve ser preenchido")
    private String teamName;

    @NotNull(message = "Temporada deve ser preenchida")
    private String season;

    @NotNull(message = "Tipo de uniforme deve ser preenchido")
    @Enumerated(EnumType.ORDINAL)
    private KitType kitType;

    @NotNull(message = "Marca deve ser preenchida")
    private String brand;

    @Size(max = 2000, message = "A descrição não pode ter mais de 2000 caracteres")
    @NotNull(message = "Descrição deve ser preenchida")
    private String description;

    @NotNull(message = "Quantidade no estoque deve ser preenchida")
    private int quantity;

    @NotNull(message = "Preço deve ser preenchido")
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Min(1) @Max(5) @NotNull(message = "Avaliação deve ser preenchida")
    private Double evaluation;
}
