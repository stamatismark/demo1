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

import com.example.demo.dao.ApplicationDAO;
import com.example.demo.model.Application;
import com.example.demo.model.Student;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationServiceImplTest {

	@Autowired
	private ApplicationService applicationService;
	
	@MockBean
	ApplicationDAO applicationDAO;
	
	@BeforeEach
	void setUp() throws Exception {
		Student student1 = new Student();
		student1.setNumberOfRemainingCourses(5);
		student1.setAverageGrade(7);
		
		Student student2 = new Student();
		student2.setNumberOfRemainingCourses(4);
		student2.setAverageGrade(8);
		
		
		Application a1 = new Application();
		//a1.setStudent(student1);
		a1.setId(1);
		Application a2 = new Application();
		a2.setStudent(student2);
		a2.setId(2);
		
		ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(a1);
		applications.add(a2);
		
		Mockito.when(applicationDAO.findAll())
        .thenReturn(applications);
		
		Mockito.when(applicationDAO.getById(1))
        .thenReturn(a1);
		
		Mockito.when(applicationDAO.save(a1))
        .thenReturn(a1);
	}

	@Test
	void testSave() {
		Application a1 = applicationService.findById(1);
		assertNull(a1.getStudent());
		Student s = new Student();
		a1.setStudent(s);
		applicationService.save(a1);
		Application a = applicationService.findById(1);
		assertNotNull(a.getStudent());
		
	}

	@Test
	void testFindAll() {
		List<Application> applications = applicationService.findAll();
		assertEquals(2, applications.size());
	}

	@Test
	void testFindById() {
		Application a = applicationService.findById(1);
		assertEquals(1, a.getId());
	}

}
