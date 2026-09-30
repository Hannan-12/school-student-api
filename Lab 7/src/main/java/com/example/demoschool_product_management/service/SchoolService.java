package com.example.demoschool_product_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demoschool_product_management.entity.School;
import com.example.demoschool_product_management.repository.SchoolRepository;
import com.example.demoschool_product_management.repository.StudentRepository;
import com.example.demoschool_product_management.exception.ConflictException;
import com.example.demoschool_product_management.exception.ResourceNotFoundException;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    public SchoolService(SchoolRepository schoolRepository, StudentRepository studentRepository) {
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public School getSchoolById(String id) {
        return schoolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("School not found: " + id));
    }

    public School saveSchool(School school) {
        return schoolRepository.save(school);
    }

    public void deleteSchool(String id) {
        getSchoolById(id);
        if (studentRepository.existsBySchoolId(id)) {
            throw new ConflictException("Cannot delete school while students reference it");
        }
        schoolRepository.deleteById(id);
    }
}
