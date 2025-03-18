package com.example.demoschool_product_management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demoschool_product_management.entity.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findBySchoolId(String schoolId);
}
