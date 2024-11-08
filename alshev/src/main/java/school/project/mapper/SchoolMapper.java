package school.project.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import school.project.dto.SchoolCreateDTO;
import school.project.dto.SchoolEntityDTO;
import school.project.entity.SchoolEntity;
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

}