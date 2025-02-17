package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
