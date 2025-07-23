package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import com.uplingo.uplingo_resource_server.model.entities.UserChapterResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserChapterResultRepository extends JpaRepository<UserChapterResult, UUID> {

  @Query("SELECT COALESCE(SUM(ucr.xpEarned), 0) FROM UserChapterResult ucr WHERE ucr.userId = :userId")
  int sumXpEarnedByUserId(@Param("userId") UUID userId);

  List<UserChapterResult> findByChapterId(UUID chapterId);

  Optional<UserChapterResult> findByUserIdAndChapterId(UUID userId, UUID chapterId);

  List<UserChapterResult> findByUserId(UUID userId);

  void deleteByChapterId(UUID chapterId);

  boolean existsByUserIdAndChapterId(UUID userId, UUID chapterId);
}
