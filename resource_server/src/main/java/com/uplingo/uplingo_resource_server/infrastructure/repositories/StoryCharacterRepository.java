package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;

@Repository
public interface StoryCharacterRepository extends JpaRepository<StoryCharacter, UUID> {
  List<StoryCharacter> findByStoryId(UUID storyId);
  void deleteByStoryId(UUID storyId);
}
