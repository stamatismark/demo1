package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;

public interface StudentService {

	public void saveStudent(Student student);
	public Student retrieveProfile(String student);
	public List<Subject> listStudentSubjects();
	public void applyToSubject(String student, int subject);
}
