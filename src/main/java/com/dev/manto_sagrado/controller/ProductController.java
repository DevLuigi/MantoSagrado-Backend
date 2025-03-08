package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.product.dto.ProductRequestDTO;
import com.dev.manto_sagrado.domain.product.dto.ProductResponseDTO;
import com.dev.manto_sagrado.domain.product.entity.Product;
import com.dev.manto_sagrado.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductRequestDTO data) {
        boolean inserted = service.save(ProductRequestDTO.newProduct(data));
        return inserted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateById(@PathVariable("productId") long id, @Valid @RequestBody ProductRequestDTO data) {
        Optional<Product> product = service.updateById(id, data);
        return product.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<Void> updateById(@PathVariable("productId") long id){
        Optional<Product> product = service.handleStatusById(id);
        return product.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
