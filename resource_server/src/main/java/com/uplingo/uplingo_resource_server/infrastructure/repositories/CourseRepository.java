package com.uplingo.uplingo_resource_server.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uplingo.uplingo_resource_server.model.entities.Course;
import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
  boolean existsByName(CourseNameEnum name);
}
