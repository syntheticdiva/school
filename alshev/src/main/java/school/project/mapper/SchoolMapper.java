package school.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import school.project.dto.SchoolEntityDTO;
import school.project.entity.SchoolEntity;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    SchoolEntityDTO toDTO(SchoolEntity schoolEntity);
    SchoolEntity toEntity(SchoolEntityDTO schoolEntityDTO);
}
