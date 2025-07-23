package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import com.uplingo.uplingo_resource_server.model.entities.ChapterNarration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChapterNarrationRepository extends JpaRepository<ChapterNarration, UUID> {
  List<ChapterNarration> findByChapterId(UUID chapterId);

  long countByChapterId(UUID chapterId);

  List<ChapterNarration> findTop1ByChapterIdOrderByStepDesc(UUID chapterId);

  void deleteByChapterId(UUID chapterId);
}
