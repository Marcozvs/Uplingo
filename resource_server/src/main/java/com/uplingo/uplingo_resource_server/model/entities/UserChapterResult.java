package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;

import com.uplingo.uplingo_resource_server.model.enums.GradeEnum;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_chapter_result")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChapterResult {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "chapter_id", nullable = false)
  private UUID chapterId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 5)
  private GradeEnum grade;

  @Column(name = "xp_earned", nullable = false)
  private Integer xpEarned;

  @Column(name = "completed_at", nullable = false)
  private LocalDateTime completedAt;

  @Column(name = "created_by", nullable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "updated_by")
  private UUID updatedBy;
}
