package com.example.demo.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;
@Configuration
@Repository
public interface StudentDAO extends JpaRepository<Student, Integer>{

}
