package school.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.project.dto.SchoolCreateDTO;
import school.project.dto.SchoolEntityDTO;
import school.project.util.HandlerResult;
import school.project.util.SchoolHandler;
import school.project.util.SchoolModelHelper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static school.project.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {

    @Mock
    private SchoolHandler schoolHandler;

    @Mock
    private SchoolModelHelper modelHelper;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private SchoolController schoolController;

    private SchoolEntityDTO schoolDTO;
    private SchoolCreateDTO schoolCreateDTO;
    private Page<SchoolEntityDTO> schoolPage;

    @BeforeEach
    void setUp() {
        schoolDTO = new SchoolEntityDTO();
        schoolDTO.setId(1L);
        schoolDTO.setName("Test School");

        schoolCreateDTO = new SchoolCreateDTO();
        schoolCreateDTO.setName("New School");

        List<SchoolEntityDTO> schools = Arrays.asList(schoolDTO);
        schoolPage = new PageImpl<>(schools);
    }

    @Test
    void listSchools_ShouldReturnAllSchoolsView() {
        when(schoolHandler.handleListRequest(0, 10)).thenReturn(schoolPage);

        String viewName = schoolController.listSchools(model, 0);

        assertEquals("all-schools", viewName);
        verify(modelHelper).addSchoolListAttributes(model, schoolPage);
    }

    @Test
    void listSchools_WithEmptyPageAndNonZeroPageNumber_ShouldRedirect() {
        when(schoolHandler.handleListRequest(1, 10)).thenReturn(Page.empty());

        String viewName = schoolController.listSchools(model, 1);

        assertEquals("redirect:/schools?page=0", viewName);
    }

    @Test
    void saveSchool_WithValidData_ShouldRedirect() {
        HandlerResult successResult = new HandlerResult(true, "Success");
        when(schoolHandler.handleSaveRequest(eq(schoolCreateDTO), any(BindingResult.class))).thenReturn(successResult);

        String viewName = schoolController.saveSchool(schoolCreateDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, successResult);
    }

    @Test
    void saveSchool_WithInvalidData_ShouldReturnCreateView() {
        HandlerResult failureResult = new HandlerResult(false, "Failure");
        when(schoolHandler.handleSaveRequest(eq(schoolCreateDTO), any(BindingResult.class))).thenReturn(failureResult);

        String viewName = schoolController.saveSchool(schoolCreateDTO, bindingResult, redirectAttributes);

        assertEquals(CREATE_VIEW, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, failureResult);
    }

    @Test
    void showEditForm_ShouldReturnEditView() {
        when(schoolHandler.handleEditRequest(1L)).thenReturn(schoolDTO);

        String viewName = schoolController.showEditForm(1L, model);

        assertEquals(EDIT_VIEW, viewName);
        verify(model).addAttribute("school", schoolDTO);
        verify(model).addAttribute("schoolId", 1L);
    }

    @Test
    void updateSchool_WithValidData_ShouldRedirect() {
        HandlerResult successResult = new HandlerResult(true, "Success");
        when(schoolHandler.handleUpdateRequest(eq(1L), eq(schoolDTO), any(BindingResult.class))).thenReturn(successResult);

        String viewName = schoolController.updateSchool(1L, schoolDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, successResult);
    }

    @Test
    void updateSchool_WithInvalidData_ShouldReturnEditView() {
        HandlerResult failureResult = new HandlerResult(false, "Failure");
        when(schoolHandler.handleUpdateRequest(eq(1L), eq(schoolDTO), any(BindingResult.class))).thenReturn(failureResult);

        String viewName = schoolController.updateSchool(1L, schoolDTO, bindingResult, redirectAttributes);

        assertEquals(EDIT_VIEW, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, failureResult);
    }

    @Test
    void deleteSchool_ShouldRedirect() {
        HandlerResult result = new HandlerResult(true, "Deleted");
        when(schoolHandler.handleDeleteRequest(1L)).thenReturn(result);

        String viewName = schoolController.deleteSchool(1L, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, result);
    }
}