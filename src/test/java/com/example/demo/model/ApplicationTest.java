package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest {

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
	void testCompare() {
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
		
		
		assertEquals(1, a1.compare(a2, "courses"));
		assertEquals(-1, a2.compare(a1, "courses"));
		
		assertEquals(1, a1.compare(a2, "avg"));
		assertEquals(-1, a2.compare(a1, "avg"));
		
		a2.setStudent(student1);
		assertEquals(0, a1.compare(a2, "courses"));
		assertEquals(0, a1.compare(a2, "avg"));
		
	}

}
