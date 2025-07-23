package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uplingo.uplingo_resource_server.model.entities.ChapterAnswer;

@Repository
public interface ChapterAnswerRepository extends JpaRepository<ChapterAnswer, UUID> {
  List<ChapterAnswer> findByChapterId(UUID chapterId);
  long countByChapterId(UUID chapterId);
  void deleteByChapterId(UUID chapterId);
}
