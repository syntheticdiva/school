package school.project.util;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import school.project.dto.SchoolEntityDTO;
import school.project.service.SchoolService;

import java.util.Arrays;
import java.util.List;

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
    void testHandleListRequest() {
        int page = 0;
        int pageSize = 10;
        Sort sort = Sort.by("id").ascending();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        List<SchoolEntityDTO> schools = Arrays.asList(new SchoolEntityDTO(), new SchoolEntityDTO());
        Page<SchoolEntityDTO> schoolPage = new PageImpl<>(schools);

        when(schoolService.getAllSchoolsPaged(pageRequest)).thenReturn(schoolPage);

        Page<SchoolEntityDTO> result = schoolHandler.handleListRequest(page, pageSize);

        assertEquals(schoolPage, result);
        verify(schoolService).getAllSchoolsPaged(pageRequest);
    }

    @Test
    void testHandleCreateRequest() {
        SchoolEntityDTO result = schoolHandler.handleCreateRequest();
        assertNotNull(result);
    }

    @Test
    void testHandleSaveRequestSuccess() {
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        HandlerResult result = schoolHandler.handleSaveRequest(schoolDTO, bindingResult);

        assertTrue(result.isSuccess());
        assertEquals("School created successfully!", result.getMessage());
        verify(schoolService).save(schoolDTO);
    }

    @Test
    void testHandleSaveRequestValidationError() {
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        HandlerResult result = schoolHandler.handleSaveRequest(schoolDTO, bindingResult);

        assertFalse(result.isSuccess());
        assertEquals("Validation errors occurred", result.getMessage());
        verify(schoolService, never()).save(any());
    }

    @Test
    void testHandleSaveRequestException() {
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(schoolService).save(schoolDTO);

        HandlerResult result = schoolHandler.handleSaveRequest(schoolDTO, bindingResult);

        assertFalse(result.isSuccess());
        assertEquals("Error creating school", result.getMessage());
    }

    @Test
    void testHandleUpdateRequestSuccess() {
        Long id = 1L;
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        HandlerResult result = schoolHandler.handleUpdateRequest(id, schoolDTO, bindingResult);

        assertTrue(result.isSuccess());
        assertEquals("School updated successfully!", result.getMessage());
        verify(schoolService).save(schoolDTO);
        assertEquals(id, schoolDTO.getId());
    }

    @Test
    void testHandleUpdateRequestValidationError() {
        Long id = 1L;
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        HandlerResult result = schoolHandler.handleUpdateRequest(id, schoolDTO, bindingResult);

        assertFalse(result.isSuccess());
        assertEquals("Validation errors occurred", result.getMessage());
        verify(schoolService, never()).save(any());
    }

    @Test
    void testHandleDeleteRequestSuccess() {
        Long id = 1L;

        HandlerResult result = schoolHandler.handleDeleteRequest(id);

        assertTrue(result.isSuccess());
        assertEquals("School deleted successfully!", result.getMessage());
        verify(schoolService).deleteById(id);
    }

    @Test
    void testHandleDeleteRequestException() {
        Long id = 1L;
        doThrow(new RuntimeException("Test exception")).when(schoolService).deleteById(id);

        HandlerResult result = schoolHandler.handleDeleteRequest(id);

        assertFalse(result.isSuccess());
        assertEquals("Error deleting school", result.getMessage());
    }

    @Test
    void testHandleEditRequest() {
        Long id = 1L;
        SchoolEntityDTO schoolDTO = new SchoolEntityDTO();
        when(schoolService.findById(id)).thenReturn(schoolDTO);

        SchoolEntityDTO result = schoolHandler.handleEditRequest(id);

        assertEquals(schoolDTO, result);
        verify(schoolService).findById(id);
    }
}