package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.uplingo.uplingo_resource_server.model.enums.ChapterStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chapter")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "story_id", nullable = false)
  private UUID storyId;

  @Column(name = "chapter_index", nullable = false)
  private Integer chapterIndex;

  @Column(name = "current_dialog_index", nullable = false)
  private Integer currentDialogIndex;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String introduction;

  @Column(name = "correct_answers", nullable = false)
  private Integer correctAnswers;

  @Enumerated(EnumType.STRING)
  @Column(name = "chapter_status", length = 20, nullable = false)
  private ChapterStatusEnum chapterStatus;

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
