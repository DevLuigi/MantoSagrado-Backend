package com.dev.manto_sagrado.domain.product.entity;

import com.dev.manto_sagrado.domain.product.Enum.KitType;
import com.dev.manto_sagrado.domain.product.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Nome deve ser preenchido")
    private String name;

    @NotNull(message = "Nome do time deve ser preenchido")
    private String teamName;

    @NotNull(message = "Temporada deve ser preenchida")
    private String season;

    @NotNull(message = "Tipo de uniforme deve ser preenchido")
    @Enumerated(EnumType.STRING)
    private KitType kitType;

    @NotNull(message = "Marca deve ser preenchida")
    private String brand;

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
}
