package com.srs.SpringSecurity.controller;

import com.srs.SpringSecurity.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "std1", 60),
                    new Student(2, "std2", 65)
            ));

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("csrf-token")
    public CsrfToken gerCsrfToken(HttpServletRequest request) {
//        (CsrfToken) - type casting object(returned by getAttribute) to CsrfToken
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }
}
