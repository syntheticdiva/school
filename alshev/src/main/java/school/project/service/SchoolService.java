package school.project.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.project.dto.SchoolEntityDTO;

public interface SchoolService {

    SchoolEntityDTO save(SchoolEntityDTO schoolEntityDTO);

    SchoolEntityDTO findById(Long id);

    Page<SchoolEntityDTO> getAllSchoolsPaged(Pageable pageable);

    void deleteById(Long id);
}
