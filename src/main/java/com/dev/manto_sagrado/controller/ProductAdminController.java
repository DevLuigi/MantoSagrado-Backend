package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminRequestDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminResponseDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductImageAdminResponseDTO;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductImageAdmin;
import com.dev.manto_sagrado.service.ProductAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductAdminController {
    @Autowired
    private ProductAdminService service;

    @GetMapping
    public ResponseEntity<List<ProductAdminResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<ProductAdminResponseDTO> save(@Valid @RequestBody ProductAdminRequestDTO data) {
        Optional<ProductAdminResponseDTO> inserted = service.save(ProductAdminRequestDTO.newProduct(data));
        return inserted.isPresent() ? ResponseEntity.ok().body(inserted.get()) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductAdminResponseDTO> updateById(@PathVariable("productId") long id, @Valid @RequestBody ProductAdminRequestDTO data) {
        Optional<ProductAdminResponseDTO> product = service.updateById(id, data);
        return product.isPresent() ? ResponseEntity.ok().body(product.get()) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<Void> updateById(@PathVariable("productId") long id){
        Optional<ProductAdmin> product = service.handleStatusById(id);
        return product.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<List<ProductImageAdminResponseDTO>> listAllImagesByProduct(@PathVariable Long productId) {
        List<ProductImageAdminResponseDTO> images = service.listAllImagesByProduct(productId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(images);
    }

    @PostMapping("/{productId}/image/isMain/{isMain}")
    public ResponseEntity<Void> saveImage(@PathVariable Long productId, @PathVariable Boolean isMain, @RequestParam("file") MultipartFile file) {
        Optional<ProductImageAdmin> image = service.saveImage(productId, isMain, file);
        return image.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{productId}/image")
    public ResponseEntity<Void> deleteAllImagesByProduct(@PathVariable Long productId) {
        return service.deleteAllImagesByProduct(productId) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
}
