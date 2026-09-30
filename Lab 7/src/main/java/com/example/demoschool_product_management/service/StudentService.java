package com.example.demoschool_product_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demoschool_product_management.entity.Student;
import com.example.demoschool_product_management.repository.StudentRepository;
import com.example.demoschool_product_management.repository.SchoolRepository;
import com.example.demoschool_product_management.exception.ResourceNotFoundException;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    public StudentService(StudentRepository studentRepository, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    public Student saveStudent(Student student) {
        if (!schoolRepository.existsById(student.getSchoolId())) {
            throw new ResourceNotFoundException("School not found: " + student.getSchoolId());
        }
        return studentRepository.save(student);
    }

    public List<Student> getStudentsBySchool(String schoolId) {
        if (!schoolRepository.existsById(schoolId)) {
            throw new ResourceNotFoundException("School not found: " + schoolId);
        }
        return studentRepository.findBySchoolId(schoolId);
    }

    public void deleteStudent(String id) {
        getStudentById(id);
        studentRepository.deleteById(id);
    }
}
