package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.UserEnergy;

@Repository
public interface UserEnergyRepository extends JpaRepository<UserEnergy, UUID> {
  Integer countByUserIdAndConsumedFalseAndValidUntilAfter(UUID userId, LocalDateTime now);
}
