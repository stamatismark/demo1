package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
import com.example.demo.model.User;
import com.example.demo.service.ProfessorService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.ThesisService;
import com.example.demo.service.UserService;

@WebMvcTest(ProfessorController.class)
@WithMockUser(username = "testProf", roles = "PROFESSOR")
@AutoConfigureMockMvc(addFilters = false)

class ProfessorControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private ProfessorService professorService;

	@MockBean
	private SubjectService subjectService;

	@MockBean
	private ThesisService thesisService;
	
	private Professor professor;
	
	@BeforeEach
	void setUp() throws Exception {

		User user = new User();
        user.setUsername("testProf");
        professor = new Professor();
        professor.setUser(user);
        professor.setFullName("");
        professor.setSpecialty("");
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
        
        ArrayList<Subject> subjectsAvailable = new ArrayList<Subject>();
        subjectsAvailable.add(subject2);
        
        Student student = new Student();
        Thesis thesis = new Thesis();
        thesis.setStudent(student);
        thesis.setProfessor(professor);
        thesis.setSubject(subject);
        thesis.setId(1);
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
		
		
		Mockito.when(professorService.retrieveProfile("testProf"))
        .thenReturn(professor);
        
		Mockito.when(professorService.listProfessorSubjects("testProf"))
        .thenReturn(subjectsAvailable);
		
		Mockito.when(subjectService.findById(2))
        .thenReturn(subject2);
        
		Mockito.when(professorService.listProfessorTheses("testProf"))
        .thenReturn(thesesP);
		Mockito.when(userService.findById("testProf"))
        .thenReturn(user);
		Mockito.when(thesisService.findById(1))
        .thenReturn(thesis);
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetProfessorMainMenu() {

		try {
			this.mockMvc.perform(get("/prof/dashboard")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Homepage for professor")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@WithMockUser(value = "spring")
	@Test
	void testRetreiveProfile() {
		try {
			this.mockMvc.perform(get("/prof/reqProf")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Edit profile info")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("professor"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@WithMockUser(value = "spring")
	@Test
	void testSaveProfile() {
		try {
			professor.setFullName("Test name");
			professor.setSpecialty("Test specialty");
			this.mockMvc.perform(post("/prof/saveProfessorProfile").flashAttr("professor", professor)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Homepage for professor")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testListProfessorSubjects() {
		try {
			this.mockMvc.perform(post("/prof/reqAvailSubj")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of subjects")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("professorSubjects"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subjectNew"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddSubject() {
		try {
			Subject subject = new Subject();
			subject.setTitle("subject3");
			subject.setObjectives("objectives3");
			this.mockMvc.perform(post("/prof/saveSubject").flashAttr("subjectNew", subject)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of subjects")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("professorSubjects"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subjectNew"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	@Test
	void testDeleteSubject() {
		fail("Not yet implemented");
	}
	*/
	@WithMockUser(value = "spring")
	@Test
	void testListApplications() {
		try {
			this.mockMvc.perform(post("/prof/applications/{id}", 2)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of applications")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("applications"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subject"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAssignSubject() {
		try {
			
			this.mockMvc.perform(post("/prof/assign/{strategy}/{id}", "courses",2)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of subjects")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("professorSubjects"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subjectNew"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListProfessorTheses() {
		try {
			
			this.mockMvc.perform(post("/prof/reqAssignSubj")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of assigned theses")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("theses"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@WithMockUser(value = "spring")
	@Test
	void testSetThesesGrades() {
		try {
			
			this.mockMvc.perform(post("/prof/setGrade/{id}", 1)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Set grades")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("thesis"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testSaveGrades() {
		try {
			Thesis thesis = new Thesis();
			thesis.setId(1);
			this.mockMvc.perform(post("/prof/saveGrades").flashAttr("thesis", thesis)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Set grades")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("thesis"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testCalculateGrade() {
		try {
			
			this.mockMvc.perform(post("/prof/calculateGrade/{id}", 1)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("List of assigned theses")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("theses"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
