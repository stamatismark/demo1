package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.Professor;
import com.example.demo.model.User;
import com.example.demo.model.Subject;
import com.example.demo.model.Application;
import com.example.demo.service.ProfessorService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.ThesisService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProfessorController.class)
@WithMockUser(value = "spring")
class ProfessorControllerTest1 {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;

	@MockBean
	private ProfessorService professorService;

	@MockBean
	private SubjectService subjectService;

	@MockBean
	private ThesisService thesisService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Professor alex = new Professor();
        User u = new User();
        
        alex.setUser(u);
        u.setUsername("alex");
        ArrayList<Professor> p = new ArrayList<>();
        p.add(alex);
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        Subject subject = new Subject();
        ArrayList<Application> ap = new ArrayList<Application>();
        ap.add(new Application());
        ap.add(new Application());
        subject.setApplications(ap);
		Mockito.when(professorService.retrieveProfile("spring"))
		.thenReturn(alex);
		Mockito.when(subjectService.findById(1))
		.thenReturn(subject);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetProfessorMainMenu() throws Exception {
		this.mockMvc.perform(get("/prof/dashboard")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Homepage for professor")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testRetreiveProfile() throws Exception {
		this.mockMvc.perform(get("/prof/reqProf")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Edit profile info")))
		.andExpect(MockMvcResultMatchers.model().attributeExists("professor"));
	}

	@Test
	void testSaveProfile() {
		fail("Not yet implemented");
	}

	@Test
	void testListProfessorSubjects() {
		fail("Not yet implemented");
	}

	@Test
	void testShowSubjectForm() {
		fail("Not yet implemented");
	}

	@Test
	void testAddSubject() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteSubject() {
		fail("Not yet implemented");
	}
	@WithMockUser(value = "spring")
	@Test
	void testListApplications() throws Exception {
		this.mockMvc.perform(get("/prof/applications/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Editt profile info")));
	}

	@Test
	void testAssignSubject() {
		fail("Not yet implemented");
	}

	@Test
	void testListProfessorTheses() {
		fail("Not yet implemented");
	}

	@Test
	void testSetThesesGrades() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveGrades() {
		fail("Not yet implemented");
	}

	@Test
	void testCalculateGrade() {
		fail("Not yet implemented");
	}

}
