package school.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.dto.SchoolCreateDTO;
import school.dto.SchoolEntityDTO;
import school.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping(SchoolRestController.BASE_URL)
@Slf4j
public class SchoolRestController {
    private final SchoolService schoolService;

    public static final String BASE_URL = "/api/schools";

    public SchoolRestController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public ResponseEntity<List<SchoolEntityDTO>> listSchools(@RequestParam(defaultValue = "0") int page) {
        Page<SchoolEntityDTO> schoolPage = schoolService.getAllSchoolsPaged(PageRequest.of(page, 10));
        return ResponseEntity.ok(schoolPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolEntityDTO> getSchoolById(@PathVariable Long id) {
        SchoolEntityDTO school = schoolService.findById(id);
        return ResponseEntity.ok(school);
    }

    @PostMapping
    public ResponseEntity<SchoolEntityDTO> createSchool(@Valid @RequestBody SchoolCreateDTO schoolCreateDTO) {
        SchoolEntityDTO createdSchool = schoolService.create(schoolCreateDTO);
        return ResponseEntity.status(201).body(createdSchool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolEntityDTO> updateSchool(@PathVariable Long id, @Valid @RequestBody SchoolEntityDTO schoolDTO) {
        SchoolEntityDTO updatedSchool = schoolService.update(id, schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


//package school.controller;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import school.dto.SchoolCreateDTO;
//import school.dto.SchoolEntityDTO;
//import school.service.SchoolService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(SchoolRestController.BASE_URL)
//@Slf4j
//public class SchoolRestController {
//    private final SchoolService schoolService;
//
//    public static final String BASE_URL = "/api/schools"; // URL для REST API
//
//    public SchoolRestController(SchoolService schoolService) {
//        this.schoolService = schoolService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SchoolEntityDTO>> listSchools(@RequestParam(defaultValue = "0") int page) {
//        Page<SchoolEntityDTO> schoolPage = schoolService.getAllSchoolsPaged(PageRequest.of(page, 10));
//        return ResponseEntity.ok(schoolPage.getContent());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SchoolEntityDTO> getSchoolById(@PathVariable Long id) {
//        SchoolEntityDTO school = schoolService.findById(id);
//        return ResponseEntity.ok(school);
//    }
//
//    @PostMapping
//    public ResponseEntity<SchoolEntityDTO> createSchool(@Valid @RequestBody SchoolCreateDTO schoolCreateDTO) {
//        SchoolEntityDTO createdSchool = schoolService.create(schoolCreateDTO);
//        return ResponseEntity.status(201).body(createdSchool);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<SchoolEntityDTO> updateSchool(@PathVariable Long id, @Valid @RequestBody SchoolEntityDTO schoolDTO) {
//        SchoolEntityDTO updatedSchool = schoolService.update(id, schoolDTO);
//        return ResponseEntity.ok(updatedSchool);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
//        schoolService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}