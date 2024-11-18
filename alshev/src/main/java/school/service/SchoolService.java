package school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.dto.SchoolCreateDTO;
import school.dto.SchoolEntityDTO;


public interface SchoolService {

    SchoolEntityDTO create(SchoolCreateDTO schoolCreateDTO);

    SchoolEntityDTO findById(Long id);

    Page<SchoolEntityDTO> getAllSchoolsPaged(Pageable pageable);

    SchoolEntityDTO update(Long id, SchoolEntityDTO schoolEntityDTO);

    void deleteById(Long id);
}