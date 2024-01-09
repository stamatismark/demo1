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

import com.example.demo.dao.ThesisDAO;
import com.example.demo.model.Thesis;
import com.example.demo.model.Professor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ThesisServiceImplTest {
	
	@Autowired
	private ThesisService thesisService;
	
	@MockBean
	private ThesisDAO thesisDAO;
	
	
	@BeforeEach
	void setUp() throws Exception {
		ArrayList<Thesis> theses = new ArrayList<Thesis>();
		Thesis thesis = new Thesis();
		thesis.setId(1);
		Thesis thesis2 = new Thesis();
		thesis2.setId(2);
        theses.add(thesis2);
        
        Mockito.when(thesisDAO.getById(2))
        .thenReturn(thesis2);
		
		Mockito.when(thesisDAO.save(thesis2))
        .thenReturn(thesis2);
	}

	@Test
	void testSave() {
		Thesis thesis = thesisService.findById(2);
		assertNull(thesis.getProfessor());
		Professor professor = new Professor();
		thesis.setProfessor(professor);
		thesisService.save(thesis);
		thesis = thesisService.findById(2);
		assertNotNull(thesis.getProfessor());
		
	}

	@Test
	void testFindById() {
		Thesis thesis = thesisService.findById(2);
		assertEquals(2, thesis.getId());
	}

}
