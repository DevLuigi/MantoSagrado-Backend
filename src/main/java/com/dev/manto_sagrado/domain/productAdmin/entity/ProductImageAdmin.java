package com.dev.manto_sagrado.domain.productAdmin.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "images")
@Entity(name = "product_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductImageAdmin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne @JoinColumn(name = "fk_produto", nullable = false)
    private ProductAdmin product;

    @NonNull
    private String imagePath;

    @NonNull
    private String fileName;

    @NonNull
    private Boolean isMain;
}
