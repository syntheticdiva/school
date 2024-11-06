package school.project.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
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
        Sort sort = Sort.by("id").ascending();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        return schoolService.getAllSchoolsPaged(pageRequest);
    }

    public SchoolEntityDTO handleCreateRequest() {
        return new SchoolEntityDTO();
    }

    public HandlerResult handleSaveRequest(SchoolEntityDTO schoolDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new HandlerResult(false, "Validation errors occurred");
        }
        try {
            schoolService.save(schoolDTO);
            return new HandlerResult(true, "School created successfully!");
        } catch (Exception e) {
            log.error("Error while saving school", e);
            return new HandlerResult(false, "Error creating school");
        }
    }

    public HandlerResult handleUpdateRequest(Long id, SchoolEntityDTO schoolDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new HandlerResult(false, "Validation errors occurred");
        }
        try {
            schoolDTO.setId(id);
            schoolService.save(schoolDTO);
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

    public SchoolEntityDTO handleEditRequest(Long id) {
        return schoolService.findById(id);
    }
}