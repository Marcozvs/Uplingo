package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
  List<Chapter> findByStoryId(UUID storyId);
  Optional<Chapter> findByStoryIdAndChapterIndex(UUID storyId, int chapterIndex);
}
