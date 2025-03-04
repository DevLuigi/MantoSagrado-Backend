package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.product.dto.ProductRequestDTO;
import com.dev.manto_sagrado.domain.product.dto.ProductResponseDTO;
import com.dev.manto_sagrado.domain.product.entity.Product;
import com.dev.manto_sagrado.domain.product.Enum.Status;
import com.dev.manto_sagrado.exception.InvalidProductException;
import com.dev.manto_sagrado.infrastructure.utils.ProductValidator;
import com.dev.manto_sagrado.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository repository;

    public List<ProductResponseDTO> listAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ProductResponseDTO::fromProduct)
                .collect(Collectors.toList());
    }

    public boolean save(Product product) {
        if (!ProductValidator.isValid(product))
            throw new InvalidProductException("Produto inválido");

        repository.save(product);
        return true;
    }

    public Optional<Product> updateById(long id, ProductRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();

        Product product = repository.findById(id).get();
        if (product.getId() != data.getId()) return Optional.empty();

        product.setName(data.getName());
        product.setTeamName(data.getTeamName());
        product.setSeason(data.getSeason());
        product.setKitType(data.getKitType());
        product.setBrand(data.getBrand());
        product.setDescription(data.getDescription());
        product.setQuantity(data.getQuantity());
        product.setPrice(data.getPrice());
        product.setStatus(data.getStatus());

        if (!ProductValidator.isValid(product))
            throw new InvalidProductException("Produto inválido");

        return Optional.of(repository.save(product));
    }

    public Optional<Product> handleStatusById(long id){
        if (!repository.existsById(id)) return Optional.empty();
        Product product = repository.findById(id).get();

        if(product.getStatus().equals(Status.ATIVADO)){
            product.setStatus(Status.DESATIVADO);
        }
        else {
            product.setStatus(Status.ATIVADO);
        }
        return Optional.of(repository.save(product));
    }
}
