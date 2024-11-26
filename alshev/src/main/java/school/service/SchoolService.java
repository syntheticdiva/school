package school.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.dto.SchoolCreateDTO;
import school.dto.SchoolEntityDTO;
import school.entity.SchoolEntity;
import school.exception.ResourceNotFoundException;
import school.mapper.SchoolMapper;
import school.repository.SchoolRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;
    private final NotificationService notificationService;

    public SchoolEntityDTO create(SchoolCreateDTO schoolCreateDTO) {
        SchoolEntity school = schoolMapper.toEntity(schoolCreateDTO);
        school = schoolRepository.save(school);

        SchoolEntityDTO schoolDTO = schoolMapper.toDto(school);
        schoolDTO.setNew(true); // Устанавливаем флаг isNew в true

        // Отправляем уведомление о создании школы
        notificationService.handleSchoolCreationNotification(schoolDTO);

        return schoolDTO;
    }

    public SchoolEntityDTO update(Long id, SchoolEntityDTO schoolEntityDTO) {
        SchoolEntity school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        schoolMapper.updateEntityFromDto(schoolEntityDTO, school);
        school = schoolRepository.save(school);

        SchoolEntityDTO updatedSchoolDTO = schoolMapper.toDto(school);
        updatedSchoolDTO.setNew(false); // Устанавливаем флаг isNew в false

        // Отправляем уведомление об обновлении школы
        notificationService.handleSchoolUpdateNotification(updatedSchoolDTO);

        return updatedSchoolDTO;
    }

    // Остальные методы без изменений
    public void deleteById(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException("School not found with id: " + id);
        }

        schoolRepository.deleteById(id);
    }

    public SchoolEntityDTO findById(Long id) {
        return schoolRepository.findById(id)
                .map(schoolMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    public Page<SchoolEntityDTO> getAllSchoolsPaged(Pageable pageable) {
        Page<SchoolEntity> schoolPage = schoolRepository.findAll(pageable);
        return schoolPage.map(schoolMapper::toDto);
    }
}