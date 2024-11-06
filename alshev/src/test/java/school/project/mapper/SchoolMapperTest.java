package school.project.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import school.project.dto.SchoolEntityDTO;
import school.project.entity.SchoolEntity;

import static org.junit.jupiter.api.Assertions.*;

class SchoolMapperTest {

    private final SchoolMapper mapper = Mappers.getMapper(SchoolMapper.class);

    @Test
    void testMapperInstance() {
        assertNotNull(SchoolMapper.INSTANCE);
    }

    @Test
    void testToDTO() {
        // Подготовка тестовых данных
        SchoolEntity entity = new SchoolEntity();
        entity.setId(1L);
        entity.setName("Test School");
        entity.setAddress("Test Address");

        // Выполнение маппинга
        SchoolEntityDTO dto = mapper.toDTO(entity);

        // Проверка результатов
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getAddress(), dto.getAddress());
    }

    @Test
    void testToEntity() {
        // Подготовка тестовых данных
        SchoolEntityDTO dto = new SchoolEntityDTO();
        dto.setId(1L);
        dto.setName("Test School");
        dto.setAddress("Test Address");

        // Выполнение маппинга
        SchoolEntity entity = mapper.toEntity(dto);

        // Проверка результатов
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getAddress(), entity.getAddress());
    }

    @Test
    void testNullToDTO() {
        SchoolEntityDTO dto = mapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testNullToEntity() {
        SchoolEntity entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    void testPartialMapping() {
        // Тест маппинга с частично заполненными данными
        SchoolEntity entity = new SchoolEntity();
        entity.setName("Test School");
        // Не устанавливаем остальные поля

        SchoolEntityDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertNull(dto.getAddress());
    }

    @Test
    void testRoundTrip() {
        // Тест преобразования entity -> dto -> entity
        SchoolEntity originalEntity = new SchoolEntity();
        originalEntity.setId(1L);
        originalEntity.setName("Test School");
        originalEntity.setAddress("Test Address");

        SchoolEntityDTO dto = mapper.toDTO(originalEntity);
        SchoolEntity mappedEntity = mapper.toEntity(dto);

        assertEquals(originalEntity.getId(), mappedEntity.getId());
        assertEquals(originalEntity.getName(), mappedEntity.getName());
        assertEquals(originalEntity.getAddress(), mappedEntity.getAddress());
    }
}