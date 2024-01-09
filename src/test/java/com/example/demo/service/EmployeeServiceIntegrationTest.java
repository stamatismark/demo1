package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.ProfessorDAO;
import com.example.demo.model.Professor;

@RunWith(SpringRunner.class)
public class EmployeeServiceIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
 
        @Bean
        public ProfessorService professorService() {
            return new ProfessorServiceImpl();
        }
    }

    @Autowired
    private ProfessorService professorService;

    @MockBean
    private ProfessorDAO professorRepository;

    // write test cases here
    
    @Before
    public void setUp() {
        Professor alex = new Professor();
        ArrayList<Professor> p = new ArrayList<>();
        p.add(alex);
        Mockito.when(professorRepository.findAll())
          .thenReturn(p);
    }
    
    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Professor found = professorService.retrieveProfile("1");
     
         assertThat(found.getFullName())
          .isEqualTo(name);
     }
}

