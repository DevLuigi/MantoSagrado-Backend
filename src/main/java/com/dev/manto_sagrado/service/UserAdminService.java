package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import com.dev.manto_sagrado.domain.userAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import com.dev.manto_sagrado.exception.InvalidCpfException;
import com.dev.manto_sagrado.exception.InvalidEmailException;
import com.dev.manto_sagrado.exception.UserDeactivatedException;
import com.dev.manto_sagrado.infrastructure.utils.CpfValidator;
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
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new InvalidEmailException("O e-mail informado já está em uso. Por favor, utilize outro");

        if(!CpfValidator.isValid(user.getCpf()))
            throw new InvalidCpfException("CPF inválido. Por favor, verifique os dígitos");

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return true;
    }

    public UserLoginResponseDTO login(UserAdminRequestDTO request) {
        Optional<UserAdmin> userByEmail = repository.findByEmail(request.getEmail());
        if (userByEmail.isEmpty()) return null;

        UserAdmin user = userByEmail.get();
        if(user.getStatus().equals(Status.DESATIVADO))
            throw new UserDeactivatedException("Usuário está desativado e não pode fazer login.");

        if (!encoder.matches(request.getPassword(), user.getPassword())) return null;

        return UserLoginResponseDTO.fromUserAdmin(user);
    }

    public Optional<UserAdmin> updateById(long id, UserAdminRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();
        if (!repository.existsById(data.getId())) return Optional.empty();

        if(!CpfValidator.isValid(data.getCpf()))
            throw new InvalidCpfException("CPF inválido. Por favor, verifique os dígitos");

        UserAdmin user = repository.findById(id).get();
        if (!user.getUserGroup().equals(Group.ADMIN)) {
            return Optional.empty();
        }

        if (user.getId() == data.getId()) {
            user = updateAll(data, user);
        } else {
            user = updateGroup(data);
        }

        return Optional.of(repository.save(user));
    }

    public UserAdmin updateAll(UserAdminRequestDTO data, UserAdmin oldUser) {
        UserAdmin userUpdated = UserAdminRequestDTO.newUserAdmin(data);
        userUpdated.setEmail(oldUser.getEmail()); // ensure the email will not be changed
        userUpdated.setPassword(encoder.encode(data.getPassword()));
        return userUpdated;
    }

    public UserAdmin updateGroup(UserAdminRequestDTO data) {
        UserAdmin user = repository.findById(data.getId()).get();
        user.setUserGroup(data.getUserGroup());
        return user;
    }
  
    public Optional<UserAdmin> handleStatusById(long id){
        if (!repository.existsById(id)) return Optional.empty();
        UserAdmin user = repository.findById(id).get();

        if(user.getStatus() .equals(Status.ATIVADO)){
            user.setStatus(Status.DESATIVADO);
        }
        else {
            user.setStatus(Status.ATIVADO);
        }
        return Optional.of(repository.save(user));
    }
}
