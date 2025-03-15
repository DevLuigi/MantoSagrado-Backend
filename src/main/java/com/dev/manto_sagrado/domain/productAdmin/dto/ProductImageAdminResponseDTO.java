package com.dev.manto_sagrado.domain.productAdmin.dto;

import com.dev.manto_sagrado.domain.productAdmin.entity.ProductImageAdmin;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.UrlResource;

@Data
@Builder
public class ProductImageAdminResponseDTO {
    private byte[] file;
    private String imagePath;
    private Boolean isMain;

    public static ProductImageAdminResponseDTO fromProductImage(ProductImageAdmin image, byte[] file) {
        return ProductImageAdminResponseDTO.builder()
                .file(file)
                .imagePath(image.getImagePath())
                .isMain(image.getIsMain())
                .build();
    }
}
