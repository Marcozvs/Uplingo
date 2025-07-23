package com.uplingo.uplingo_authorization_server.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_authorization_server.model.entities.AppUser;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);
}
