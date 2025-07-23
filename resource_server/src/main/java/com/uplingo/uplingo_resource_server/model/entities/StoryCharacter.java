package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;

@Entity
@Table(name = "story_character")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryCharacter {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "story_id", nullable = false)
  private UUID storyId;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "character_type", length = 20, nullable = false)
  private CharacterTypeEnum characterType;

  @Column(name = "created_by", nullable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
