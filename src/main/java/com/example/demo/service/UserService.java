package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
	public boolean isUserPresent(User user);
	public User findById(String id);
}
