package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.address.dto.AddressRequestDTO;
import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.dto.ClientResponseDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.client.dto.ClientLoginResponseDTO;
import com.dev.manto_sagrado.service.ClientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
    public ResponseEntity<Client> save(@Valid @RequestBody ClientRequestDTO data) {
        Optional<Client> inserted = service.save(ClientRequestDTO.newUser(data));
        return inserted.isPresent() ? ResponseEntity.ok().body(inserted.get()) : ResponseEntity.badRequest().build();
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

    @PostMapping("/{clientId}/address")
    public ResponseEntity<Void> createAddress(@PathVariable("clientId") long clientId, @Valid @RequestBody AddressRequestDTO data) {
        boolean address = service.createAddress(clientId, data);
        return address ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{clientId}/address")
    public ResponseEntity<Optional<List<Address>>> listAllByClient(@PathVariable("clientId") long clientId) {
        Optional<List<Address>> addresses = service.listAllAddressesByClient(clientId);
        return addresses.isPresent() ? ResponseEntity.ok().body(addresses) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{clientId}/address/delivery")
    public ResponseEntity<Optional<List<Address>>> listAllByClientAndDelivery(@PathVariable("clientId") long clientId) {
        Optional<List<Address>> addresses = service.listAllAddressesByClientAndDelivery(clientId);
        return addresses.isPresent() ? ResponseEntity.ok().body(addresses) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{clientId}/address/{addressId}")
    public ResponseEntity<Void> updateAddressById(@PathVariable("addressId") long addressId, @PathVariable("clientId") long clientId) {
        Optional<Address> address = service.updateAddressById(addressId, clientId);
        return address.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") long clientId) {
        boolean deleted = service.deleteClient(clientId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
}
