package school.service.impl;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.dto.SchoolCreateDTO;
import school.dto.SchoolEntityDTO;
import school.entity.SchoolEntity;
import school.exception.ResourceNotFoundException;
import school.mapper.SchoolMapper;
import school.repository.SchoolRepository;
import school.service.SchoolService;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public SchoolEntityDTO create(SchoolCreateDTO schoolCreateDTO) {
        SchoolEntity school = schoolMapper.toEntity(schoolCreateDTO);
        school = schoolRepository.save(school);
        return schoolMapper.toDto(school);
    }

    @Override
    public SchoolEntityDTO findById(Long id) {
        return schoolRepository.findById(id)
                .map(schoolMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    @Override
    public Page<SchoolEntityDTO> getAllSchoolsPaged(Pageable pageable) {
        Page<SchoolEntity> schoolPage = schoolRepository.findAll(pageable);
        return schoolPage.map(schoolMapper::toDto);
    }

    @Override
    public SchoolEntityDTO update(Long id, SchoolEntityDTO schoolEntityDTO) {
        SchoolEntity school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        schoolMapper.updateEntityFromDto(schoolEntityDTO, school);
        school = schoolRepository.save(school);
        return schoolMapper.toDto(school);
    }

    @Override
    public void deleteById(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }
}