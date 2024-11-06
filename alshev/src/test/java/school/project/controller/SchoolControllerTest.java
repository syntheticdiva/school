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
import school.project.dto.SchoolEntityDTO;
import school.project.util.HandlerResult;
import school.project.util.SchoolHandler;
import school.project.util.SchoolModelHelper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
    private Page<SchoolEntityDTO> schoolPage;
    private HandlerResult successResult;
    private HandlerResult failureResult;

    @BeforeEach
    void setUp() {
        schoolDTO = new SchoolEntityDTO();
        schoolPage = new PageImpl<>(Collections.singletonList(schoolDTO));
        successResult = new HandlerResult(true, "Success");
        failureResult = new HandlerResult(false, "Failure");
    }

    @Test
    void listSchools_ShouldReturnAllSchools() {
        when(schoolHandler.handleListRequest(0, 10)).thenReturn(schoolPage);

        String viewName = schoolController.listSchools(model, 0);

        assertEquals("all-schools", viewName);
        verify(modelHelper).addSchoolListAttributes(model, schoolPage);
    }

    @Test
    void listSchools_WithEmptyPageAndPageGreaterThanZero_ShouldRedirect() {
        when(schoolHandler.handleListRequest(1, 10))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        String viewName = schoolController.listSchools(model, 1);

        assertEquals("redirect:/schools?page=0", viewName);
    }

    @Test
    void showCreateForm_ShouldReturnCreateView() {
        when(schoolHandler.handleCreateRequest()).thenReturn(schoolDTO);

        String viewName = schoolController.showCreateForm(model);

        assertEquals(CREATE_VIEW, viewName);
        verify(modelHelper).addSchoolFormAttributes(model, schoolDTO);
    }

    @Test
    void saveSchool_WhenSuccessful_ShouldRedirect() {
        when(schoolHandler.handleSaveRequest(any(SchoolEntityDTO.class), any(BindingResult.class)))
                .thenReturn(successResult);

        String viewName = schoolController.saveSchool(schoolDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName); // Добавлен слэш после "redirect:"
        verify(modelHelper).addResultAttributes(redirectAttributes, successResult);
    }

    @Test
    void saveSchool_WhenFailed_ShouldReturnCreateView() {
        when(schoolHandler.handleSaveRequest(any(SchoolEntityDTO.class), any(BindingResult.class)))
                .thenReturn(failureResult);

        String viewName = schoolController.saveSchool(schoolDTO, bindingResult, redirectAttributes);

        assertEquals(CREATE_VIEW, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, failureResult);
    }

    @Test
    void showEditForm_ShouldReturnEditView() {
        when(schoolHandler.handleEditRequest(anyLong())).thenReturn(schoolDTO);

        String viewName = schoolController.showEditForm(1L, model);

        assertEquals(EDIT_VIEW, viewName);
        verify(modelHelper).addSchoolFormAttributes(model, schoolDTO);
    }

    @Test
    void updateSchool_WhenSuccessful_ShouldRedirect() {
        when(schoolHandler.handleUpdateRequest(anyLong(), any(SchoolEntityDTO.class), any(BindingResult.class)))
                .thenReturn(successResult);

        String viewName = schoolController.updateSchool(1L, schoolDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName); // Добавлен слэш после "redirect:"
        verify(modelHelper).addResultAttributes(redirectAttributes, successResult);
    }

    @Test
    void updateSchool_WhenFailed_ShouldReturnEditView() {
        when(schoolHandler.handleUpdateRequest(anyLong(), any(SchoolEntityDTO.class), any(BindingResult.class)))
                .thenReturn(failureResult);

        String viewName = schoolController.updateSchool(1L, schoolDTO, bindingResult, redirectAttributes);

        assertEquals(EDIT_VIEW, viewName);
        verify(modelHelper).addResultAttributes(redirectAttributes, failureResult);
    }

    @Test
    void deleteSchool_ShouldRedirect() {
        when(schoolHandler.handleDeleteRequest(anyLong())).thenReturn(successResult);

        String viewName = schoolController.deleteSchool(1L, redirectAttributes);

        assertEquals("redirect:/" + REDIRECT_SCHOOLS, viewName); // Добавлен слэш после "redirect:"
        verify(modelHelper).addResultAttributes(redirectAttributes, successResult);
    }
}