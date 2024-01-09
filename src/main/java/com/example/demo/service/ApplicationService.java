package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Application;

public interface ApplicationService {
	public void save(Application application);
	public List<Application> findAll();
	public Application findById(Integer id);
}
