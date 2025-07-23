package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.UserEnergy;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyUpdateDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEnergyMapper {

    UserEnergy toEntity(UserEnergyCreateDTO dto);

    UserEnergy toEntity(UserEnergyUpdateDTO dto);

    void updateEntityFromDto(UserEnergyUpdateDTO dto, @MappingTarget UserEnergy entity);

    UserEnergyResponseDTO toResponseDto(UserEnergy entity);

    UserEnergyResponseItemDTO toResponseItemDto(UserEnergy entity);

    List<UserEnergyResponseItemDTO> toResponseItemDtoList(List<UserEnergy> entities);
}
