package com.uplingo.uplingo_resource_server.ui.dtos.user_course_analytics;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

public record UserCourseAnalyticsResponseDTO(
    UUID id,
    UUID userId,
    CourseNameEnum course,
    Integer xp
) {}
