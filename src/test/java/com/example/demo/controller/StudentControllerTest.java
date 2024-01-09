package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.example.demo.service.ApplicationService;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.UserService;

@WebMvcTest(StudentController.class)
@WithMockUser(username = "testStud", roles = "STUDENT")
@AutoConfigureMockMvc(addFilters = false)

class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private StudentService studentService;	
	
	@MockBean
	private SubjectService subjectService;
	
	@MockBean
	private ApplicationService applicationService;
	
	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
        user.setUsername("testProf");
        Professor professor = new Professor();
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
        

		User user1 = new User();
        user1.setUsername("testStud");
		Student student1 = new Student();
		student1.setNumberOfRemainingCourses(5);
		student1.setAverageGrade(7);
		student1.setFullName("testStud");
		
		Student student2 = new Student();
		student2.setNumberOfRemainingCourses(4);
		student2.setAverageGrade(8);
		
		
		Application a1 = new Application();
		a1.setStudent(student1);
		a1.setSubject(subject);
		ArrayList<Application> applications1 = new ArrayList<Application>();
		applications1.add(a1);

		student.setApplications(applications1);
		Application a2 = new Application();
		a2.setStudent(student2);
				
		
		Mockito.when(studentService.retrieveProfile("testStud"))
        .thenReturn(student1);

		
		Mockito.when(subjectService.findById(2))
        .thenReturn(subject2);
        
		Mockito.when(subjectService.findAll())
        .thenReturn(subjects);
		Mockito.when(userService.findById("testStud"))
        .thenReturn(user1);
	}

	@Test
	void testGetStudentMainMenu() {
		try {
			this.mockMvc.perform(get("/stud/dashboard")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Homepage for students")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testRetreiveProfile() {
		try {
			this.mockMvc.perform(get("/stud/reqProf")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Edit profile info")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("student"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testSaveProfile() {
		try {
			Student student = new Student();
			student.setFullName("test full name");
			student.setNumberOfRemainingCourses(5);
			student.setAverageGrade(7);
			student.setYearOfStudies(5);
			this.mockMvc.perform(get("/stud/saveStudentProfile").flashAttr("student", student)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Homepage for students")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("student"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testListProfessorSubjects() {
		try {
			this.mockMvc.perform(get("/stud/reqAvailSubj")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Professor subjects")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("professorSubjects"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testAddSubjectIntModel() {
		try {
			this.mockMvc.perform(get("/stud/description/{id}", 2)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Subject description")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subject"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testAddSubjectSubjectModel() {
		try {
			this.mockMvc.perform(get("/stud/description/{id}", 2)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Subject description")))
			.andExpect(MockMvcResultMatchers.model().attributeExists("subject"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
