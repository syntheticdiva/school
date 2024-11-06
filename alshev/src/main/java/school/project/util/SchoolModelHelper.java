package school.project.util;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.project.dto.SchoolEntityDTO;

@Component
public class SchoolModelHelper {

    public void addSchoolListAttributes(Model model, Page<SchoolEntityDTO> schoolPage) {
        model.addAttribute("schools", schoolPage.getContent());
        model.addAttribute("currentPage", schoolPage.getNumber());
        model.addAttribute("totalPages", schoolPage.getTotalPages());
        model.addAttribute("totalItems", schoolPage.getTotalElements());
    }

    public void addSchoolFormAttributes(Model model, SchoolEntityDTO school) {
        model.addAttribute("school", school);
    }

    public void addResultAttributes(RedirectAttributes redirectAttributes, HandlerResult result) {
        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
        }
    }
}