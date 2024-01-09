package com.example.demo.model.strategies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.Application;
import com.example.demo.model.Student;

import java.util.*;
class BestAvgGradeStrategyTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCompareApplications() {
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
		
		BestAvgGradeStrategy strategy = (BestAvgGradeStrategy) BestApplicantStrategyFactory.createStrategy("avg");
		assertEquals(1, strategy.compareApplications(a1, a2));
		assertEquals(-1, strategy.compareApplications(a2, a1));
		
		a2.setStudent(student1);
		assertEquals(0, strategy.compareApplications(a1, a2));
		
	}

	@Test
	void testFindBestApplicant() {
		Student student1 = new Student();
		student1.setNumberOfRemainingCourses(5);
		student1.setAverageGrade(7);
		student1.setId(1);
		
		Student student2 = new Student();
		student2.setNumberOfRemainingCourses(4);
		student2.setAverageGrade(8);
		student2.setId(2);
		
		
		Application a1 = new Application();
		a1.setStudent(student1);
		
		Application a2 = new Application();
		a2.setStudent(student2);
		
		ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(a1);
		applications.add(a2);
		
		BestAvgGradeStrategy strategy = (BestAvgGradeStrategy) BestApplicantStrategyFactory.createStrategy("avg");
		Student student3 = strategy.findBestApplicant(applications);
		
		assertEquals(2, student3.getId());
	}

}
