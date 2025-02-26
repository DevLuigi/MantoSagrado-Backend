package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import com.dev.manto_sagrado.service.UserAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user/admin")
public class UserAdminController {
    @Autowired
    private UserAdminService service;

    @GetMapping
    public ResponseEntity<List<UserAdminResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserAdminRequestDTO data) {
        boolean inserted = service.save(UserAdminRequestDTO.newUserAdmin(data));
        return inserted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAdminRequestDTO user) {
        UserLoginResponseDTO response = service.login(user);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateById(@PathVariable("userId") long id, @Valid @RequestBody UserAdminRequestDTO data) {
        Optional<UserAdmin> user = service.updateById(id, data);
        return user.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> updateById(@PathVariable("userId") long id){
        Optional<UserAdmin> user = service.handleStatusById(id);
        return user.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
