package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.dto.ClientResponseDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.client.dto.ClientLoginResponseDTO;
import com.dev.manto_sagrado.exception.InvalidCpfException;
import com.dev.manto_sagrado.infrastructure.utils.CpfValidator;
import com.dev.manto_sagrado.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<ClientResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(ClientResponseDTO::fromUser)
                .collect(Collectors.toList());
    }

    public Optional<ClientResponseDTO> listById(Long id) {
        return repository.findById(id)
                .map(ClientResponseDTO::fromUser);
    }

    public boolean save(Client client) {
        if (repository.findByEmail(client.getEmail()).isPresent()) return false;

        if(!CpfValidator.isValid(client.getCpf()))
            throw new InvalidCpfException("CPF inválido");

        client.setPassword(encoder.encode(client.getPassword()));
        repository.save(client);

        return true;
    }

    public ClientLoginResponseDTO login(ClientRequestDTO request) {
        Optional<Client> userByEmail = repository.findByEmail(request.getEmail());
        if (userByEmail.isEmpty()) return null;

        Client client = userByEmail.get();
        if (!encoder.matches(request.getPassword(), client.getPassword())) return null;

        return ClientLoginResponseDTO.fromUser(client);
    }

    public Optional<Client> updateById(long id, ClientRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();
        if (!repository.existsById(data.getId())) return Optional.empty();

        if(!CpfValidator.isValid(data.getCpf()))
            throw new InvalidCpfException("CPF inválido");

        Client client = repository.findById(id).get();

        if (client.getId() == data.getId()) {
            client = updateAll(data, client);
        }

        return Optional.of(repository.save(client));
    }

    public Client updateAll(ClientRequestDTO data, Client oldClient) {
        Client clientUpdated = ClientRequestDTO.newUser(data);
        clientUpdated.setEmail(oldClient.getEmail()); // ensure the email will not be changed
        clientUpdated.setPassword(encoder.encode(data.getPassword()));
        return clientUpdated;
    }

}
