package school.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import school.project.dto.SchoolEntityDTO;
import school.project.entity.SchoolEntity;
import school.project.exception.ResourceNotFoundException;
import school.project.mapper.SchoolMapper;
import school.project.repository.SchoolRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchoolServiceImplTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolMapper schoolMapper;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        SchoolEntityDTO inputDto = new SchoolEntityDTO();
        SchoolEntity entity = new SchoolEntity();
        SchoolEntity savedEntity = new SchoolEntity();
        SchoolEntityDTO outputDto = new SchoolEntityDTO();

        when(schoolMapper.toEntity(inputDto)).thenReturn(entity);
        when(schoolRepository.save(entity)).thenReturn(savedEntity);
        when(schoolMapper.toDTO(savedEntity)).thenReturn(outputDto);

        SchoolEntityDTO result = schoolService.save(inputDto);

        assertNotNull(result);
        assertEquals(outputDto, result);
        verify(schoolRepository).save(entity);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SchoolEntity entity = new SchoolEntity();
        SchoolEntityDTO dto = new SchoolEntityDTO();

        when(schoolRepository.findById(id)).thenReturn(Optional.of(entity));
        when(schoolMapper.toDTO(entity)).thenReturn(dto);

        SchoolEntityDTO result = schoolService.findById(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 1L;
        when(schoolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> schoolService.findById(id));
    }

    @Test
    void testGetAllSchoolsPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        SchoolEntity entity1 = new SchoolEntity();
        SchoolEntity entity2 = new SchoolEntity();
        Page<SchoolEntity> entityPage = new PageImpl<>(Arrays.asList(entity1, entity2));

        SchoolEntityDTO dto1 = new SchoolEntityDTO();
        SchoolEntityDTO dto2 = new SchoolEntityDTO();

        when(schoolRepository.findAll(pageable)).thenReturn(entityPage);
        when(schoolMapper.toDTO(entity1)).thenReturn(dto1);
        when(schoolMapper.toDTO(entity2)).thenReturn(dto2);

        Page<SchoolEntityDTO> result = schoolService.getAllSchoolsPaged(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(dto1));
        assertTrue(result.getContent().contains(dto2));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        when(schoolRepository.existsById(id)).thenReturn(true);

        schoolService.deleteById(id);

        verify(schoolRepository).deleteById(id);
    }

    @Test
    void testDeleteByIdNotFound() {
        Long id = 1L;
        when(schoolRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> schoolService.deleteById(id));
    }
}