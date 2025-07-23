package com.uplingo.uplingo_resource_server.ui.dtos.user_course_analytics;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

public record UserCourseAnalyticsUpdateDTO(
    CourseNameEnum course,
    Integer xp
) {}
