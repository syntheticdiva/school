package school.project.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import school.project.dto.SchoolCreateDTO;
import school.project.dto.SchoolEntityDTO;
import school.project.service.SchoolService;

@Component
@Slf4j
public class SchoolHandler {
    private final SchoolService schoolService;

    public SchoolHandler(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public Page<SchoolEntityDTO> handleListRequest(int page, int pageSize) {
        return schoolService.getAllSchoolsPaged(PageRequest.of(page, pageSize));
    }

    public HandlerResult handleSaveRequest(SchoolCreateDTO schoolDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new HandlerResult(false, "Validation errors occurred");
        }
        try {
            schoolService.create(schoolDTO);
            return new HandlerResult(true, "School created successfully!");
        } catch (Exception e) {
            log.error("Error while saving school", e);
            return new HandlerResult(false, "Error creating school");
        }
    }

    public SchoolEntityDTO handleEditRequest(Long id) {
        return schoolService.findById(id);
    }

    public HandlerResult handleUpdateRequest(Long id, SchoolEntityDTO schoolDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new HandlerResult(false, "Validation errors occurred");
        }
        try {
            schoolService.update(id, schoolDTO);
            return new HandlerResult(true, "School updated successfully!");
        } catch (Exception e) {
            log.error("Error while updating school", e);
            return new HandlerResult(false, "Error updating school");
        }
    }

    public HandlerResult handleDeleteRequest(Long id) {
        try {
            schoolService.deleteById(id);
            return new HandlerResult(true, "School deleted successfully!");
        } catch (Exception e) {
            log.error("Error while deleting school", e);
            return new HandlerResult(false, "Error deleting school");
        }
    }
}
