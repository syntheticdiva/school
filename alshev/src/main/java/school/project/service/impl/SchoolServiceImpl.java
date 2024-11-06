package school.project.service.impl;

import school.project.service.SchoolService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.project.dto.SchoolEntityDTO;
import school.project.entity.SchoolEntity;
import school.project.exception.ResourceNotFoundException;
import school.project.mapper.SchoolMapper;
import school.project.repository.SchoolRepository;

@Service
@Transactional
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;
    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }
    @Override
    public SchoolEntityDTO save(SchoolEntityDTO schoolEntityDTO) {
        try {
            log.debug("Saving school: {}", schoolEntityDTO);
            SchoolEntity school = schoolMapper.toEntity(schoolEntityDTO);
            school = schoolRepository.save(school);
            return schoolMapper.toDTO(school);
        } catch (Exception e) {
            log.error("Error while saving school: {}", schoolEntityDTO, e);
            throw e;
        }
    }

    @Override
    public SchoolEntityDTO findById(Long id) {
        log.debug("Finding school by id: {}", id);
        return schoolRepository.findById(id)
                .map(schoolMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("School not found with id: {}", id);
                    return new ResourceNotFoundException("School not found with id: " + id);
                });
    }

    @Override
    public Page<SchoolEntityDTO> getAllSchoolsPaged(Pageable pageable) {
        try {
            log.debug("Getting all schools with pageable: {}", pageable);
            Page<SchoolEntity> schoolPage = schoolRepository.findAll(pageable);
            return schoolPage.map(schoolMapper::toDTO);
        } catch (Exception e) {
            log.error("Error while getting all schools", e);
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            log.debug("Deleting school with id: {}", id);
            if (!schoolRepository.existsById(id)) {
                log.error("School not found with id: {}", id);
                throw new ResourceNotFoundException("School not found with id: " + id);
            }
            schoolRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting school with id: {}", id, e);
            throw e;
        }
    }
}
