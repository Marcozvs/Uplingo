package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.Story;
import com.uplingo.uplingo_resource_server.model.enums.StoryStatusEnum;

@Repository
public interface StoryRepository extends JpaRepository<Story, UUID> {
  boolean existsByUserIdAndStoryStatusNot(UUID userId, StoryStatusEnum status);
}
