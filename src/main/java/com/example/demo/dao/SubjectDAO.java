package com.example.demo.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Subject;

@Configuration
@Repository
public interface SubjectDAO extends JpaRepository<Subject, Integer>{

}

