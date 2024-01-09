package com.example.demo.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Application;
@Configuration
@Repository
public interface ApplicationDAO extends JpaRepository<Application, Integer>{

}
