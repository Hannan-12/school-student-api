package com.example.demoschool_product_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demoschool_product_management.entity.School;
import com.example.demoschool_product_management.repository.SchoolRepository;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public Optional<School> getSchoolById(String id) {
        return schoolRepository.findById(id);
    }

    public School saveSchool(School school) {
        return schoolRepository.save(school);
    }

    public void deleteSchool(String id) {
        schoolRepository.deleteById(id);
    }
}
