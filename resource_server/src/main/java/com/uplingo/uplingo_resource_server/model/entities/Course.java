package com.uplingo.uplingo_resource_server.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  private CourseNameEnum name;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String description;

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
