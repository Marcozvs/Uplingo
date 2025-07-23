package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {}
