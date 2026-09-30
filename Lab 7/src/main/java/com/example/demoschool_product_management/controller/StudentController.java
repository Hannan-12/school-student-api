package com.example.demoschool_product_management.controller;

import java.net.URI;
import java.util.List;

import com.example.demoschool_product_management.dto.StudentRequest;
import com.example.demoschool_product_management.entity.Student;
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
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) { this.studentService = studentService; }

    @GetMapping
    public List<Student> getAllStudents() { return studentService.getAllStudents(); }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) { return studentService.getStudentById(id); }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequest request) {
        Student student = studentService.saveStudent(new Student(request.name(), request.age(), request.schoolId()));
        return ResponseEntity.created(URI.create("/students/" + student.getId())).body(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
