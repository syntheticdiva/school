package school.project.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    void testAddSchoolListAttributes() {
        // Arrange
        List<SchoolEntityDTO> schools = Arrays.asList(new SchoolEntityDTO(), new SchoolEntityDTO());
        Page<SchoolEntityDTO> schoolPage = new PageImpl<>(schools);

        // Act
        schoolModelHelper.addSchoolListAttributes(model, schoolPage);

        // Assert
        verify(model).addAttribute("schools", schools);
        verify(model).addAttribute("currentPage", schoolPage.getNumber());
        verify(model).addAttribute("totalPages", schoolPage.getTotalPages());
        verify(model).addAttribute("totalItems", schoolPage.getTotalElements());
    }

    @Test
    void testAddSchoolFormAttributes() {
        // Arrange
        SchoolEntityDTO school = new SchoolEntityDTO();

        // Act
        schoolModelHelper.addSchoolFormAttributes(model, school);

        // Assert
        verify(model).addAttribute("school", school);
    }

    @Test
    void testAddResultAttributesSuccess() {
        // Arrange
        HandlerResult result = new HandlerResult(true, "Success message");

        // Act
        schoolModelHelper.addResultAttributes(redirectAttributes, result);

        // Assert
        verify(redirectAttributes).addFlashAttribute("successMessage", "Success message");
        verify(redirectAttributes, never()).addFlashAttribute(eq("errorMessage"), anyString());
    }

    @Test
    void testAddResultAttributesError() {
        // Arrange
        HandlerResult result = new HandlerResult(false, "Error message");

        // Act
        schoolModelHelper.addResultAttributes(redirectAttributes, result);

        // Assert
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Error message");
        verify(redirectAttributes, never()).addFlashAttribute(eq("successMessage"), anyString());
    }
}