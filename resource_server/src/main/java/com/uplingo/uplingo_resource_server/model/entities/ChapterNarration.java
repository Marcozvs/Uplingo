package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chapter_narration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterNarration {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "chapter_id", nullable = false)
  private UUID chapterId;

  @Column(nullable = false)
  private Integer step;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

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
