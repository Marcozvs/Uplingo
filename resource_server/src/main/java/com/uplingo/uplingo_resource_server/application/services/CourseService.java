package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.CourseRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.GrammarModuleRepository;
import com.uplingo.uplingo_resource_server.model.entities.Course;
import com.uplingo.uplingo_resource_server.model.entities.GrammarModule;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.CourseMapper;
import com.uplingo.uplingo_resource_server.ui.mappers.GrammarModuleMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;
  private final GrammarModuleRepository grammarModuleRepository;
  private final CourseMapper courseMapper;
  private final GrammarModuleMapper grammarModuleMapper;

  @Transactional
  public ResponseDTO<CourseResponseDTO> create(CourseCreateDTO dto) {
    boolean exists = courseRepository.existsByName(dto.name());
    if (exists) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course with this name already exists");
    }

    Course course = courseMapper.toEntity(dto);

    UUID requesterId = AuthUtils.getUserId();
    
    course.setCreatedBy(requesterId);

    Course saved = courseRepository.save(course);

    List<GrammarModule> modules = grammarModuleRepository.findByCourseId(saved.getId());
    List<GrammarModuleResponseItemDTO> modulesDto = grammarModuleMapper.toResponseItemDtoList(modules);

    CourseResponseDTO responseDto = courseMapper.toResponseDto(saved, modulesDto);

    return ResponseDTO.success(responseDto, "Course created");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<CourseResponseDTO> readById(UUID id) {
    Optional<Course> opt = courseRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");

    Course course = opt.get();

    List<GrammarModule> modules = grammarModuleRepository.findByCourseId(id);

    List<GrammarModuleResponseItemDTO> modulesDto = grammarModuleMapper.toResponseItemDtoList(modules);

    CourseResponseDTO responseDto = courseMapper.toResponseDto(course, modulesDto);

    return ResponseDTO.success(responseDto);
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<CourseResponseItemDTO>> readAll() {
    List<Course> courses = courseRepository.findAll();
    return ResponseDTO.success(courseMapper.toResponseItemDtoList(courses));
  }

  @Transactional
  public ResponseDTO<CourseResponseDTO> update(UUID id, CourseUpdateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();

    Optional<Course> opt = courseRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");

    Course course = opt.get();

    courseMapper.updateEntityFromDto(dto, course);
    course.setUpdatedBy(requesterId);

    Course updated = courseRepository.save(course);

    List<GrammarModule> modules = grammarModuleRepository.findByCourseId(updated.getId());
    List<GrammarModuleResponseItemDTO> modulesDto = grammarModuleMapper.toResponseItemDtoList(modules);

    CourseResponseDTO responseDto = courseMapper.toResponseDto(updated, modulesDto);

    return ResponseDTO.success(responseDto, "Course updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<Course> opt = courseRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");

    courseRepository.deleteById(id);
    return ResponseDTO.success(null, "Course deleted");
  }
}
