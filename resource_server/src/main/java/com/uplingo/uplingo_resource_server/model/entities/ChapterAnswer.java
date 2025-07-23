package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chapter_answer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterAnswer {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "chapter_id", nullable = false)
  private UUID chapterId;

  @Column(name = "character_id", nullable = false)
  private UUID characterId;

  @Enumerated(EnumType.STRING)
  @Column(name = "character_type", nullable = false)
  private CharacterTypeEnum characterType;

  @Column(name = "character_name", columnDefinition = "TEXT")
  private String characterName;

  @Column(name = "answer_index", nullable = false)
  private Integer answerIndex;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private boolean correct;

  @Column(nullable = false)
  private Integer step;

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
