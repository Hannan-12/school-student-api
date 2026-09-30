package com.example.demoschool_product_management.controller;

import java.net.URI;
import java.util.List;

import com.example.demoschool_product_management.dto.SchoolRequest;
import com.example.demoschool_product_management.entity.School;
import com.example.demoschool_product_management.entity.Student;
import com.example.demoschool_product_management.service.SchoolService;
import com.example.demoschool_product_management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    private final SchoolService schoolService;
    private final StudentService studentService;

    public SchoolController(SchoolService schoolService, StudentService studentService) {
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping
    public List<School> getAllSchools() { return schoolService.getAllSchools(); }

    @GetMapping("/{id}")
    public School getSchoolById(@PathVariable String id) { return schoolService.getSchoolById(id); }

    @GetMapping("/{schoolId}/students")
    public List<Student> getStudentsBySchool(@PathVariable String schoolId) {
        return studentService.getStudentsBySchool(schoolId);
    }

    @PostMapping
    public ResponseEntity<School> createSchool(@Valid @RequestBody SchoolRequest request) {
        School school = schoolService.saveSchool(new School(request.name(), request.address()));
        return ResponseEntity.created(URI.create("/schools/" + school.getId())).body(school);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable String id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.noContent().build();
    }
}
