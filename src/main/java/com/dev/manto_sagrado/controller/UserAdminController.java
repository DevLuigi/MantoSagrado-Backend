package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/admin")
public class UserAdminController {
    @Autowired
    private UserAdminService service;

    @GetMapping
    public ResponseEntity<List<UserAdminResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAdminRequestDTO user) {
        UserLoginResponseDTO response = service.login(user);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }
}
