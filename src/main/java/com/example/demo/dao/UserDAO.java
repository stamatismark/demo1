package com.example.demo.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Configuration
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

}
