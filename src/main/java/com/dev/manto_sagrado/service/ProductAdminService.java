package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminRequestDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminResponseDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductImageAdminResponseDTO;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import com.dev.manto_sagrado.domain.productAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductImageAdmin;
import com.dev.manto_sagrado.exception.ImagesNotFoundException;
import com.dev.manto_sagrado.exception.InputStreamException;
import com.dev.manto_sagrado.exception.InvalidProductException;
import com.dev.manto_sagrado.exception.ProductNotFoundException;
import com.dev.manto_sagrado.infrastructure.utils.ProductValidator;
import com.dev.manto_sagrado.repository.ProductsAdminRepository;
import com.dev.manto_sagrado.repository.ProductsImageAdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductAdminService {
    @Autowired
    private ProductsAdminRepository repository;

    @Autowired
    private ProductsImageAdminRepository imageRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public List<ProductAdminResponseDTO> listAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ProductAdminResponseDTO::fromProduct)
                .collect(Collectors.toList());
    }

    public Optional<ProductAdminResponseDTO> save(ProductAdmin product) {
        if (!ProductValidator.isValid(product))
            throw new InvalidProductException("Produto inválido");

        return Optional.of(ProductAdminResponseDTO.fromProduct(repository.save(product)));
    }

    public Optional<ProductAdminResponseDTO> updateById(long id, ProductAdminRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();

        ProductAdmin product = repository.findById(id).get();
        if (product.getId() != data.getId()) return Optional.empty();

        ProductAdmin productData = ProductAdminRequestDTO.newProduct(data);

        if (!ProductValidator.isValid(productData))
            throw new InvalidProductException("Produto inválido");

        return Optional.of(ProductAdminResponseDTO.fromProduct(repository.save(productData)));
    }

    public Optional<ProductAdmin> handleStatusById(long id){
        if (!repository.existsById(id)) return Optional.empty();
        ProductAdmin product = repository.findById(id).get();

        if(product.getStatus().equals(Status.ATIVADO)){
            product.setStatus(Status.DESATIVADO);
        }
        else {
            product.setStatus(Status.ATIVADO);
        }
        return Optional.of(repository.save(product));
    }

    public List<ProductImageAdminResponseDTO> listAllImagesByProduct(Long productId) {
        ProductAdmin product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        List<ProductImageAdmin> initialImagesList = imageRepository.findAllByProduct(product)
                .orElseThrow(() -> new ImagesNotFoundException("Nenhuma imagem foi encontrada para esse produto"));

        List<ProductImageAdminResponseDTO> finalImagesList = new ArrayList<>();
        for (ProductImageAdmin image : initialImagesList) {
            Path filePath = Paths.get(uploadDir, image.getFileName());
            try {
                if (Files.exists(filePath) && Files.size(filePath) > 0) {
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    finalImagesList.add(ProductImageAdminResponseDTO.fromProductImage(image, fileBytes));
                } else {
                    throw new RuntimeException("Arquivo de imagem não encontrado ou está vazio: " + image.getFileName());
                }
            } catch (IOException e) {
                throw new RuntimeException("Erro ao carregar a imagem: " + image.getFileName(), e);
            }
        }

        return finalImagesList;
    }

    public Optional<ProductImageAdmin> saveImage (Long productId, Boolean isMain, MultipartFile file) {
        ProductAdmin product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = "product_" + String.valueOf(product.getId()) + "_image_" + UUID.randomUUID() + extension;

        Path filePath = Paths.get(uploadDir, fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new InputStreamException("Falha ao copiar imagem");
        }

        ProductImageAdmin image = new ProductImageAdmin();
        image.setProduct(product);
        image.setFileName(fileName);
        image.setImagePath("http://localhost:8080/uploads/" + fileName);
        image.setIsMain(isMain);

        return Optional.of(imageRepository.save(image));
    }

    @Transactional
    public boolean deleteAllImagesByProduct(Long productId) {
        ProductAdmin product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        List<ProductImageAdmin> imagesList = imageRepository.findAllByProduct(product)
                .orElseThrow(() -> new ImagesNotFoundException("Nenhuma imagem foi encontrada para esse produto"));

        // deleta imagens da pasta uploads
        for(ProductImageAdmin image : imagesList) {
            try {
                Path path = Paths.get(uploadDir, image.getFileName());
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new InputStreamException("Falha ao copiar imagem");
            }
        }

        // deleta do BD e retorna quantidade de registros deletados
        return imageRepository.deleteAllByProduct(product) > 0;
    }
}
