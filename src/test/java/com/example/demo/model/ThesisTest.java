package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThesisTest {

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
	void testCopyInfo() {
		Thesis thesis = new Thesis();
		thesis.setImplementationGrade(9);
		thesis.setPresentationGrade(9);
		thesis.setReportGrade(9);
		
		Thesis thesis2 = new Thesis();
		thesis2.copyInfo(thesis);
		
		assertEquals(9, thesis2.getImplementationGrade());
		assertEquals(9, thesis2.getPresentationGrade());
		assertEquals(9, thesis2.getReportGrade());
	}

	@Test
	void testSetGrade() {

		Thesis thesis = new Thesis();
		thesis.setImplementationGrade(10);
		thesis.setPresentationGrade(8);
		thesis.setReportGrade(8);
		
		thesis.setGrade();
		assertEquals(9.4, thesis.getTotalGrade(), 0.01);
	}

}
