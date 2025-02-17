package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAdminService {
    @Autowired
    private UserAdminRepository repository;

    public List<UserAdminResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(UserAdminResponseDTO::fromUserAdmin)
                .collect(Collectors.toList());
    }
}
