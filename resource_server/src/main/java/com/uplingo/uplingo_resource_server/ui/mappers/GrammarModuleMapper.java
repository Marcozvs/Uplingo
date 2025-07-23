package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.GrammarModule;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleUpdateDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrammarModuleMapper {

    GrammarModule toEntity(GrammarModuleCreateDTO dto);

    GrammarModule toEntity(GrammarModuleUpdateDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(GrammarModuleUpdateDTO dto, @MappingTarget GrammarModule entity);

    GrammarModuleResponseDTO toResponseDto(GrammarModule entity);

    GrammarModuleResponseItemDTO toResponseItemDto(GrammarModule entity);

    List<GrammarModuleResponseItemDTO> toResponseItemDtoList(List<GrammarModule> entities);
}
