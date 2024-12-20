package school.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import school.dto.NoificationResDto;
import school.dto.SchoolCreateDTO;
import school.dto.SchoolEntityDTO;
import school.entity.SchoolEntity;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    // Маппинг для создания
    SchoolEntity toEntity(SchoolCreateDTO createDTO);

    // Маппинг для преобразования Entity в EntityDTO
    SchoolEntityDTO toDto(SchoolEntity entity);

    // Маппинг Entity из EntityDTO
    SchoolEntity toEntity(SchoolEntityDTO entityDTO);

    // Маппинг для обновления существующей сущности
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SchoolEntityDTO entityDTO, @MappingTarget SchoolEntity entity);

    // Метод для преобразования SchoolEntityDTO в NoificationResDto
    @Mapping(target = "id", source = "schoolEntityDTO.id")
    @Mapping(target = "name", source = "schoolEntityDTO.name")
    @Mapping(target = "address", source = "schoolEntityDTO.address")
    NoificationResDto toNotificationResDto(SchoolEntityDTO schoolEntityDTO);
}