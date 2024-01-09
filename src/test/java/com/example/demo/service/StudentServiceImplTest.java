package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dao.StudentDAO;
import com.example.demo.dao.SubjectDAO;
import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
import com.example.demo.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceImplTest {
	@Autowired
	private StudentService studentService;
	
	@MockBean
	private StudentDAO studentDAO;
	
	@MockBean
	private SubjectDAO subjectDAO;
	
	@BeforeEach
	void setUp() throws Exception {

		Student student1 = new Student();
		User user1 = new User();
		user1.setUsername("testStud");
		student1.setUser(user1);
		student1.setNumberOfRemainingCourses(5);
		student1.setAverageGrade(7);
		
		Student student2 = new Student();
		User user3 = new User();
		user3.setUsername("testStud2");
		student2.setUser(user3);
		student2.setNumberOfRemainingCourses(4);
		student2.setAverageGrade(8);
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(student1);
		students.add(student2);
		

		Mockito.when(studentDAO.findAll())
        .thenReturn(students);
		
		Mockito.when(studentDAO.save(student1))
        .thenReturn(student1);
	}

	@Test
	void testSaveStudent() {
		Student student = studentService.retrieveProfile("testStud");
		student.setFullName("test name");
		studentService.saveStudent(student);
		student = studentService.retrieveProfile("testStud");
		assertEquals("test name", student.getFullName());
	}

	@Test
	void testRetrieveProfile() {
		Student student = studentService.retrieveProfile("testStud");
		assertEquals("testStud", student.getUser().getUsername());
	}

}
