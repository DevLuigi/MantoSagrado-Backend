package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Long> {
//    Optional<UserAdmin> findByEmail(String email);
}
