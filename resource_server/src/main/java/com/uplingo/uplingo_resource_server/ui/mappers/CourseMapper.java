package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.Course;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseItemDTO;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    Course toEntity(CourseCreateDTO dto);

    Course toEntity(CourseUpdateDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CourseUpdateDTO dto, @MappingTarget Course entity);

    default CourseResponseDTO toResponseDto(Course course, List<GrammarModuleResponseItemDTO> modules) {
        return new CourseResponseDTO(
            course.getId(),
            course.getName(),
            course.getDescription(),
            course.getCreatedAt(),
            modules
        );
    }

    CourseResponseItemDTO toResponseItemDto(Course entity);

    List<CourseResponseItemDTO> toResponseItemDtoList(List<Course> entities);
}
