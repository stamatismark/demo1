package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dao.ProfessorDAO;
import com.example.demo.dao.ThesisDAO;
import com.example.demo.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfessorServiceImplTest {
	@Autowired
    private ProfessorService professorService;

    @MockBean
    private ProfessorDAO professorDAO;

    @MockBean
    private ThesisDAO thesisDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

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
        
        ArrayList<Thesis> thesesP = new ArrayList<Thesis>();
        thesesP.add(thesis);
        
        professor.setTheses(thesesP);
        
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
		
        Mockito.when(professorDAO.findAll())
        .thenReturn(professors);
        
        Mockito.when(thesisDAO.findAll())
        .thenReturn(theses);
        
        Mockito.when(professorDAO.save(professor))
        .thenReturn(professor);
        
        Mockito.when(thesisDAO.save(thesis))
        .thenReturn(thesis);
	} 

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRetrieveProfile() {
		Professor professor = professorService.retrieveProfile("testProf");
		assertEquals("testProf", professor.getUser().getUsername());
	}

	@Test
	void testSaveProfile() {
		Professor professor = professorService.retrieveProfile("testProf");
		professor.setFullName("test name");
		professorService.saveProfile(professor);
		professor = professorService.retrieveProfile("testProf");
		assertEquals("test name", professor.getFullName());
	}

	@Test
	void testListProfessorSubjects() {
		List<Subject> subjects = professorService.listProfessorSubjects("testProf");
		
		assertEquals(1, subjects.size());
		assertEquals("subject2", subjects.get(0).getTitle());
	}

	@Test
	void testAddSubject() {
		Subject subject = new Subject();
		professorService.addSubject("testProf", subject);
		Professor professor = professorService.retrieveProfile("testProf");
		assertEquals(3, professor.getSubjects().size());
	}

	

	@Test
	void testListProfessorTheses() {
		List<Thesis> thesis = professorService.listProfessorTheses("testProf");
		
		assertEquals(1, thesis.size());
		assertEquals("subject1", thesis.get(0).getSubject().getTitle());
	}

	@Test
	void testAssignSubject() {
		professorService.assignSubject("testProf", 2, "courses");
		Professor professor = professorService.retrieveProfile("testProf");

		assertEquals(2, professor.getTheses().size());
	}

}
