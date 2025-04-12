package com.example.jwt.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.model.Student;
import com.example.jwt.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	private List<Student> students = new ArrayList<>(List.of(
			new Student(1, "nitu", 60),
			new Student(2, "vinisha", 70)
		));

	@GetMapping("/students")
	public List<Student> getStudents() {
		logger.info("fetching all students");
		return students;
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student) {
		students.add(student);
		return student;
	}
}
