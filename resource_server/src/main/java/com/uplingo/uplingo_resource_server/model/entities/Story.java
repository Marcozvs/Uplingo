package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.NarratorTypeEnum;
import com.uplingo.uplingo_resource_server.model.enums.StoryStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "story")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Story {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "grammar_module_id", nullable = false)
  private UUID grammarModuleId;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String title;

  @Column(name = "back_over", nullable = false, columnDefinition = "TEXT")
  private String backOver;

  @Enumerated(EnumType.STRING)
  @Column(name = "story_status", length = 20, nullable = false)
  private StoryStatusEnum storyStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "genre", length = 20, nullable = false)
  private GenreEnum genre;

  @Enumerated(EnumType.STRING)
  @Column(name = "narrator_type", length = 20, nullable = false)
  private NarratorTypeEnum narratorType;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String setting;

  @Column(name = "created_by", nullable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
