package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.user.dto.UserRequestDTO;
import com.dev.manto_sagrado.domain.user.dto.UserResponseDTO;
import com.dev.manto_sagrado.domain.user.entity.User;
import com.dev.manto_sagrado.domain.user.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("client")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Optional<UserResponseDTO>> listById(@PathVariable("clientId") long id) {
        return ResponseEntity.ok().body(service.listById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserRequestDTO data) {
        boolean inserted = service.save(UserRequestDTO.newUser(data));
        return inserted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO user) {
        UserLoginResponseDTO response = service.login(user);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateById(@PathVariable("clientId") long id, @Valid @RequestBody UserRequestDTO data) {
        Optional<User> user = service.updateById(id, data);
        return user.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
