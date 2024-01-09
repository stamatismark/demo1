package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dao.SubjectDAO;
import com.example.demo.dao.ThesisDAO;
import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
import com.example.demo.model.User;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubjectServiceImplTest {
	@Autowired
	private SubjectService subjectService;
	
	@MockBean
	private SubjectDAO subjectDAO;
	
	@MockBean
	private ThesisDAO thesisDAO;
	
	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
        user.setUsername("testProf");
        Professor professor = new Professor();
        professor.setUser(user);
        System.out.println(professor.getUser().getUsername());
        User user2 = new User();
        user2.setUsername("testProf2");
        Professor professor2 = new Professor();
        professor2.setUser(user2);
        
        ArrayList<Professor> professors = new ArrayList<>();
        professors.add(professor);
        professors.add(professor2);
        
        
        Subject subject = new Subject();
        subject.setProfessor(professor);
        subject.setTitle("subject1");
        subject.setId(1);
        Subject subject2 = new Subject();
        subject2.setProfessor(professor);
        subject2.setTitle("subject2");
        subject2.setId(2);
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        subjects.add(subject);
        subjects.add(subject2);
        
        professor.setSubjects(subjects);
        
        Student student = new Student();
        Thesis thesis = new Thesis();
        thesis.setStudent(student);
        thesis.setProfessor(professor);
        thesis.setSubject(subject);
        
        
        ArrayList<Thesis> theses = new ArrayList<Thesis>();
        theses.add(thesis);
        
		Student student1 = new Student();
		student1.setNumberOfRemainingCourses(5);
		student1.setAverageGrade(7);
		
		Student student2 = new Student();
		student2.setNumberOfRemainingCourses(4);
		student2.setAverageGrade(8);
		
		
		Application a1 = new Application();
		a1.setStudent(student1);
		
		Application a2 = new Application();
		a2.setStudent(student2);
		
		ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(a1);
		applications.add(a2);
		
		subject2.setApplications(applications);
		
		Mockito.when(thesisDAO.findAll())
	        .thenReturn(theses);
		
		Mockito.when(subjectDAO.findAll())
        .thenReturn(subjects);
		
		Mockito.when(subjectDAO.getById(2))
        .thenReturn(subject2);
		
		Mockito.when(subjectDAO.save(subject2))
        .thenReturn(subject2);
		
	}

	@Test
	void testSave() {
		Subject subject = subjectService.findById(2);
		subject.setTitle("testSave");
		
		subjectService.save(subject);
		subject = subjectService.findById(2);

		assertEquals("testSave", subject.getTitle());
	}

	@Test
	void testFindAll() {
		List<Subject> subjects = subjectService.findAll();
		
		assertEquals(1, subjects.size());
		assertEquals("subject2", subjects.get(0).getTitle());
	}

	@Test
	void testFindById() {
		Subject subject = subjectService.findById(2);
		assertEquals(2, subject.getId());
		assertEquals("subject2", subject.getTitle());
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
