package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.address.Enum.AddressDefaultStatus;
import com.dev.manto_sagrado.domain.address.Enum.AddressType;
import com.dev.manto_sagrado.domain.address.dto.AddressRequestDTO;
import com.dev.manto_sagrado.domain.address.dto.AddressResponseDTO;
import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.dto.ClientResponseDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.client.dto.ClientLoginResponseDTO;
import com.dev.manto_sagrado.exception.*;
import com.dev.manto_sagrado.infrastructure.utils.CpfValidator;
import com.dev.manto_sagrado.repository.AddressRepository;
import com.dev.manto_sagrado.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressRepository addressRepository;

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

    public Optional<Client> save(Client client) {
        if (repository.findByEmail(client.getEmail()).isPresent()) return Optional.empty();

        if(!CpfValidator.isValid(client.getCpf()))
            throw new InvalidCpfException("CPF inválido");

        if (!client.getName().matches("^[\\p{L}]{3,}\\s[\\p{L}]{3,}$")) {
            throw new InvalidClientNameException("O nome deve conter 2 palavras com no mínimo 3 letras cada.");
        }

        client.setPassword(encoder.encode(client.getPassword()));
        Client newClient = repository.save(client);

        return Optional.of(newClient);
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
        clientUpdated.setEmail(oldClient.getEmail());
        clientUpdated.setPassword(encoder.encode(data.getPassword()));
        return clientUpdated;
    }

    public boolean createAddress(long idClient, AddressRequestDTO address) {
        if (!repository.existsById(idClient))
            throw new ClientNotFoundException("Cliente não encontrado");

        if (idClient != address.getClient().getId()) {
            throw new ClientNotFoundException("Clientes diferentes na requisição");
        }

        addressRepository.save(AddressRequestDTO.newAddress(address));
        return true;
    }

    public Optional<List<Address>> listAllAddressesByClient(long idClient) {
        if (!repository.existsById(idClient)) return Optional.empty();
        return Optional.of(addressRepository.findAllByClient(repository.findById(idClient).get()));
    }

    public Optional<List<Address>> listAllAddressesByClientAndDelivery(long idClient) {
        if (!repository.existsById(idClient)) return Optional.empty();
        return Optional.of(addressRepository.findAllByClientAndType(repository.findById(idClient).get(), AddressType.ENTREGA));
    }

    public Optional<Address> updateAddressById(long addressId, long clientId) {
        if (!addressRepository.existsById(addressId)) {
            throw new AddressNotFoundException("Endereço não encontrado");
        }

        if (!repository.existsById(clientId)) {
            throw new ClientNotFoundException("Cliente não encontrado");
        }

        Address address = addressRepository.findById(addressId).get();
        if (!AddressType.ENTREGA.equals(address.getType())) {
            throw new InvalidAddressTypeException("Tipo de endereço inválido");
        }

        address.setDefaultAddress(AddressDefaultStatus.DEFAULT);

        Client client = repository.findById(clientId).get();
        Address addressDefault = addressRepository.findByClientAndDefaultAddress(client, AddressDefaultStatus.DEFAULT);
        if (addressDefault != null) {
            addressDefault.setDefaultAddress(AddressDefaultStatus.NOT_DEFAULT);
            addressRepository.save(addressDefault);
        }

        return Optional.of(addressRepository.save(address));
    }

    public boolean deleteClient(long clientId) {
        if (!repository.existsById(clientId)) {
            throw new ClientNotFoundException("Cliente não encontrado");
        }

        Client client = repository.findById(clientId).get();
        addressRepository.deleteAllByClient(client);

        repository.deleteById(clientId);
        return true;
    }
}
