package school.project.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.project.dto.SchoolCreateDTO;
import school.project.dto.SchoolEntityDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class SchoolModelHelperTest {

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private SchoolModelHelper schoolModelHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSchoolListAttributes_ShouldAddCorrectAttributes() {
        // Given
        List<SchoolEntityDTO> schools = Arrays.asList(new SchoolEntityDTO(), new SchoolEntityDTO());
        Pageable pageable = PageRequest.of(0, 10); // создаем Pageable объект
        Page<SchoolEntityDTO> schoolPage = new PageImpl<>(schools, pageable, schools.size());

        // When
        schoolModelHelper.addSchoolListAttributes(model, schoolPage);

        // Then
        verify(model).addAttribute("schools", schools);
        verify(model).addAttribute("currentPage", schoolPage.getNumber());
        verify(model).addAttribute("totalPages", schoolPage.getTotalPages());
        verify(model).addAttribute("totalItems", schoolPage.getTotalElements());
    }
    @Test
    void addSchoolFormAttributes_ShouldAddSchoolAttribute() {
        // Given
        SchoolCreateDTO school = new SchoolCreateDTO();

        // When
        schoolModelHelper.addSchoolFormAttributes(model, school);

        // Then
        verify(model).addAttribute("school", school);
    }

    @Test
    void addSchoolEditFormAttributes_ShouldAddSchoolAttribute() {
        // Given
        SchoolEntityDTO school = new SchoolEntityDTO();

        // When
        schoolModelHelper.addSchoolEditFormAttributes(model, school);

        // Then
        verify(model).addAttribute("school", school);
    }

    @Test
    void addResultAttributes_WithSuccessResult_ShouldAddSuccessMessage() {
        // Given
        HandlerResult result = new HandlerResult(true, "Operation successful");

        // When
        schoolModelHelper.addResultAttributes(redirectAttributes, result);

        // Then
        verify(redirectAttributes).addFlashAttribute("successMessage", "Operation successful");
        verify(redirectAttributes, never()).addFlashAttribute(eq("errorMessage"), anyString());
    }

    @Test
    void addResultAttributes_WithErrorResult_ShouldAddErrorMessage() {
        // Given
        HandlerResult result = new HandlerResult(false, "Operation failed");

        // When
        schoolModelHelper.addResultAttributes(redirectAttributes, result);

        // Then
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Operation failed");
        verify(redirectAttributes, never()).addFlashAttribute(eq("successMessage"), anyString());
    }
}