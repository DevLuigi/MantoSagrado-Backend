package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.user.dto.UserRequestDTO;
import com.dev.manto_sagrado.domain.user.dto.UserResponseDTO;
import com.dev.manto_sagrado.domain.user.entity.User;
import com.dev.manto_sagrado.domain.user.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.exception.InvalidCpfException;
import com.dev.manto_sagrado.infrastructure.utils.CpfValidator;
import com.dev.manto_sagrado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<UserResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(UserResponseDTO::fromUser)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> listById(Long id) {
        return repository.findById(id)
                .map(UserResponseDTO::fromUser);
    }

    public boolean save(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) return false;

        if(!CpfValidator.isValid(user.getCpf()))
            throw new InvalidCpfException("CPF inválido");

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return true;
    }

    public UserLoginResponseDTO login(UserRequestDTO request) {
        Optional<User> userByEmail = repository.findByEmail(request.getEmail());
        if (userByEmail.isEmpty()) return null;

        User user = userByEmail.get();
        if (!encoder.matches(request.getPassword(), user.getPassword())) return null;

        return UserLoginResponseDTO.fromUser(user);
    }

    public Optional<User> updateById(long id, UserRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();
        if (!repository.existsById(data.getId())) return Optional.empty();

        if(!CpfValidator.isValid(data.getCpf()))
            throw new InvalidCpfException("CPF inválido");

        User user = repository.findById(id).get();

        if (user.getId() == data.getId()) {
            user = updateAll(data, user);
        }

        return Optional.of(repository.save(user));
    }

    public User updateAll(UserRequestDTO data, User oldUser) {
        User userUpdated = UserRequestDTO.newUser(data);
        userUpdated.setEmail(oldUser.getEmail()); // ensure the email will not be changed
        userUpdated.setPassword(encoder.encode(data.getPassword()));
        return userUpdated;
    }

}
