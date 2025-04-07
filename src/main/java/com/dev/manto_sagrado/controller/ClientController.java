package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.dto.ClientResponseDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.client.dto.ClientLoginResponseDTO;
import com.dev.manto_sagrado.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Optional<ClientResponseDTO>> listById(@PathVariable("clientId") long id) {
        return ResponseEntity.ok().body(service.listById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ClientRequestDTO data) {
        boolean inserted = service.save(ClientRequestDTO.newUser(data));
        return inserted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientRequestDTO user) {
        ClientLoginResponseDTO response = service.login(user);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateById(@PathVariable("clientId") long id, @Valid @RequestBody ClientRequestDTO data) {
        Optional<Client> user = service.updateById(id, data);
        return user.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
