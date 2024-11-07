package school.project.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import school.project.dto.SchoolCreateDTO;
import school.project.dto.SchoolEntityDTO;
import school.project.service.SchoolService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchoolHandlerTest {

    @Mock
    private SchoolService schoolService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private SchoolHandler schoolHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleListRequest_ShouldReturnPagedResults() {
        // Given
        int page = 0;
        int pageSize = 10;
        Page<SchoolEntityDTO> expectedPage = new PageImpl<>(Arrays.asList(new SchoolEntityDTO(), new SchoolEntityDTO()));
        when(schoolService.getAllSchoolsPaged(PageRequest.of(page, pageSize))).thenReturn(expectedPage);

        // When
        Page<SchoolEntityDTO> result = schoolHandler.handleListRequest(page, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(schoolService).getAllSchoolsPaged(PageRequest.of(page, pageSize));
    }

    @Test
    void handleSaveRequest_WithValidData_ShouldReturnSuccessResult() {
        // Given
        SchoolCreateDTO schoolDTO = new SchoolCreateDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        HandlerResult result = schoolHandler.handleSaveRequest(schoolDTO, bindingResult);

        // Then
        assertTrue(result.isSuccess());
        assertEquals("School created successfully!", result.getMessage());
        verify(schoolService).create(schoolDTO);
    }

    @Test
    void handleSaveRequest_WithValidationErrors_ShouldReturnErrorResult() {
        // Given
        SchoolCreateDTO schoolDTO = new SchoolCreateDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        HandlerResult result = schoolHandler.handleSaveRequest(schoolDTO, bindingResult);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Validation errors occurred", result.getMessage());
        verify(schoolService, never()).create(any());
    }

    @Test
    void handleEditRequest_ShouldReturnSchool() {
        // Given
        Long id = 1L;
        SchoolEntityDTO expectedDTO = new SchoolEntityDTO();
        when(schoolService.findById(id)).thenReturn(expectedDTO);

        // When
        SchoolEntityDTO result = schoolHandler.handleEditRequest(id);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(schoolService).findById(id);
    }

    @Test
    void handleUpdateRequest_WithValidData_ShouldReturnSuccessResult() {
        // Given
        Long id = 1L;
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        HandlerResult result = schoolHandler.handleUpdateRequest(id, schoolDTO, bindingResult);

        // Then
        assertTrue(result.isSuccess());
        assertEquals("School updated successfully!", result.getMessage());
        verify(schoolService).update(id, schoolDTO);
    }

    @Test
    void handleUpdateRequest_WithValidationErrors_ShouldReturnErrorResult() {
        // Given
        Long id = 1L;
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        HandlerResult result = schoolHandler.handleUpdateRequest(id, schoolDTO, bindingResult);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Validation errors occurred", result.getMessage());
        verify(schoolService, never()).update(anyLong(), any());
    }

    @Test
    void handleDeleteRequest_ShouldReturnSuccessResult() {
        // Given
        Long id = 1L;

        // When
        HandlerResult result = schoolHandler.handleDeleteRequest(id);

        // Then
        assertTrue(result.isSuccess());
        assertEquals("School deleted successfully!", result.getMessage());
        verify(schoolService).deleteById(id);
    }

    @Test
    void handleDeleteRequest_WithException_ShouldReturnErrorResult() {
        // Given
        Long id = 1L;
        doThrow(new RuntimeException("Delete error")).when(schoolService).deleteById(id);

        // When
        HandlerResult result = schoolHandler.handleDeleteRequest(id);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Error deleting school", result.getMessage());
        verify(schoolService).deleteById(id);
    }
}