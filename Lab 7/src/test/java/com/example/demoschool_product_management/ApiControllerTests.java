package com.example.demoschool_product_management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.example.demoschool_product_management.entity.School;
import com.example.demoschool_product_management.entity.Student;
import com.example.demoschool_product_management.exception.ConflictException;
import com.example.demoschool_product_management.exception.ResourceNotFoundException;
import com.example.demoschool_product_management.service.SchoolService;
import com.example.demoschool_product_management.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(com.example.demoschool_product_management.exception.GlobalExceptionHandler.class)
class ApiControllerTests {
    @Autowired MockMvc mvc;
    @MockBean SchoolService schoolService;
    @MockBean StudentService studentService;

    @Test
    void createSchoolReturns201() throws Exception {
        when(schoolService.saveSchool(any())).thenReturn(new School("Green Valley", "123 Main"));
        mvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Green Valley\",\"address\":\"123 Main\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createStudentReturns201() throws Exception {
        when(studentService.saveStudent(any())).thenReturn(new Student("Alice", 16, "school-1"));
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Alice\",\"age\":16,\"schoolId\":\"school-1\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void missingSchoolReferenceReturns404() throws Exception {
        when(studentService.saveStudent(any())).thenThrow(new ResourceNotFoundException("School not found: missing"));
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Alice\",\"age\":16,\"schoolId\":\"missing\"}"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.path").value("/students"));
    }

    @Test
    void missingSchoolReturns404() throws Exception {
        when(schoolService.getSchoolById("missing")).thenThrow(new ResourceNotFoundException("School not found: missing"));
        mvc.perform(get("/schools/missing")).andExpect(status().isNotFound());
    }

    @Test
    void invalidRequestReturns400() throws Exception {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \",\"age\":150,\"schoolId\":\"\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void deletingReferencedSchoolReturns409() throws Exception {
        doThrow(new ConflictException("Cannot delete school while students reference it")).when(schoolService).deleteSchool("school-1");
        mvc.perform(delete("/schools/school-1")).andExpect(status().isConflict());
    }

    @Test
    void getStudentsBySchoolReturnsList() throws Exception {
        when(studentService.getStudentsBySchool("school-1")).thenReturn(List.of(new Student("Alice", 16, "school-1")));
        mvc.perform(get("/schools/school-1/students")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Alice"));
    }
}
