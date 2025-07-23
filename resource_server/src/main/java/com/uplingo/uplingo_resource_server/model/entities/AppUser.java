package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.uplingo.uplingo_resource_server.model.enums.LanguageEnum;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 320)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Type(ListArrayType.class)
  @Column(name = "roles", columnDefinition = "varchar[]")
  private List<String> roles;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "reasons_learn", columnDefinition = "TEXT")
  private String reasonsLearn;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_language", nullable = false, length = 20)
  private LanguageEnum userLanguage;

  @Column(name = "photo_url", nullable = false, columnDefinition = "TEXT")
  private String photoUrl;

  @Column(nullable = false)
  private boolean banned;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "updated_by")
  private UUID updatedBy;

  @Column(name = "banned_at")
  private LocalDateTime bannedAt;

  @Column(name = "banned_by")
  private UUID bannedBy;

  @Column(name = "first_time", nullable = false)
  private boolean firstTime;
}
