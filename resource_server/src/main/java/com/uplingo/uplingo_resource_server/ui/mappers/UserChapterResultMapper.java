package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.UserChapterResult;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultUpdateDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserChapterResultMapper {

    UserChapterResult toEntity(UserChapterResultCreateDTO dto);

    UserChapterResult toEntity(UserChapterResultUpdateDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserChapterResultUpdateDTO dto, @MappingTarget UserChapterResult entity);

    UserChapterResultResponseDTO toResponseDto(UserChapterResult entity);

    UserChapterResultResponseItemDTO toResponseItemDto(UserChapterResult entity);

    List<UserChapterResultResponseItemDTO> toResponseItemDtoList(List<UserChapterResult> entities);
}
