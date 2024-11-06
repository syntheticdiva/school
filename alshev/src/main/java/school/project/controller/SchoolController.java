package school.project.controller;



import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.project.dto.SchoolEntityDTO;
import school.project.util.HandlerResult;
import school.project.util.SchoolHandler;
import school.project.util.SchoolModelHelper;

import static school.project.constants.Constants.*;
@Controller
@RequestMapping("/schools")
@Slf4j
public class SchoolController {
    private final SchoolHandler schoolHandler;
    private final SchoolModelHelper modelHelper;

    public SchoolController(SchoolHandler schoolHandler, SchoolModelHelper modelHelper) {
        this.schoolHandler = schoolHandler;
        this.modelHelper = modelHelper;
    }

    @GetMapping
    public String listSchools(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<SchoolEntityDTO> schoolPage = schoolHandler.handleListRequest(page, 10);
        if (schoolPage.isEmpty() && page > 0) {
            return "redirect:/schools?page=" + (page - 1);
        }
        modelHelper.addSchoolListAttributes(model, schoolPage);
        return "all-schools";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        modelHelper.addSchoolFormAttributes(model, schoolHandler.handleCreateRequest());
        return CREATE_VIEW;
    }

    @PostMapping
    public String saveSchool(@Valid @ModelAttribute("school") SchoolEntityDTO schoolDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        HandlerResult handlerResult = schoolHandler.handleSaveRequest(schoolDTO, result);
        modelHelper.addResultAttributes(redirectAttributes, handlerResult);
        return handlerResult.isSuccess() ? "redirect:/" + REDIRECT_SCHOOLS : CREATE_VIEW;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SchoolEntityDTO school = schoolHandler.handleEditRequest(id);
        modelHelper.addSchoolFormAttributes(model, school);
        return EDIT_VIEW;
    }

    @PostMapping("/update/{id}")
    public String updateSchool(@PathVariable Long id,
                               @Valid @ModelAttribute("school") SchoolEntityDTO schoolDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        HandlerResult handlerResult = schoolHandler.handleUpdateRequest(id, schoolDTO, result);
        modelHelper.addResultAttributes(redirectAttributes, handlerResult);
        return handlerResult.isSuccess() ? "redirect:/" + REDIRECT_SCHOOLS : EDIT_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteSchool(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        HandlerResult handlerResult = schoolHandler.handleDeleteRequest(id);
        modelHelper.addResultAttributes(redirectAttributes, handlerResult);
        return "redirect:/" + REDIRECT_SCHOOLS;
    }
}
