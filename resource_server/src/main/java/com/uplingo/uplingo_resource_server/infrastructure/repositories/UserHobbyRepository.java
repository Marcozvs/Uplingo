package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.UserHobby;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, UUID> {
  void deleteAllByUserId(UUID userId);
}
