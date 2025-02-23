package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import com.dev.manto_sagrado.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAdminService {
    @Autowired
    private UserAdminRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<UserAdminResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(UserAdminResponseDTO::fromUserAdmin)
                .collect(Collectors.toList());
    }

    public boolean save(UserAdmin user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) return false;

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return true;
    }

    public UserLoginResponseDTO login(UserAdminRequestDTO request) {
        Optional<UserAdmin> userByEmail = repository.findByEmail(request.getEmail());

        if (userByEmail.isEmpty()) return null;

        UserAdmin user = userByEmail.get();
        if (!encoder.matches(request.getPassword(), user.getPassword())) return null;

        return UserLoginResponseDTO.fromUserAdmin(user);
    }
}
