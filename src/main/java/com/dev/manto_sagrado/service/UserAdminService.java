package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import com.dev.manto_sagrado.domain.userAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import com.dev.manto_sagrado.repository.UserAdminRepository;
import org.apache.catalina.User;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
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
        if (repository.findByEmail(user.getEmail()).isPresent()) return false;

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return true;
    }

    public UserLoginResponseDTO login(UserAdminRequestDTO request) {
        Optional<UserAdmin> userByEmail = repository.findByEmail(request.getEmail());

        if (userByEmail.isEmpty()) return null;

        UserAdmin user = userByEmail.get();
        if (!encoder.matches(request.getPassword(), user.getPassword())) return null;

        return UserLoginResponseDTO.fromUserAdmin(user);
    }

    public Optional<UserAdmin> updateById(long id, UserAdminRequestDTO data) {
        if (!repository.existsById(id)) return Optional.empty();
        if (!repository.existsById(data.getId())) return Optional.empty();

        UserAdmin userAdmin = repository.findById(id).get();
        if (!userAdmin.getUserGroup().equals(Group.ADMIN)) {
            return Optional.empty();
        }

        UserAdmin user;
        if (userAdmin.getId() == data.getId()) {
            user = updateAll(data, userAdmin);
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
