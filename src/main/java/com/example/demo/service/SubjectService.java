package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Subject;

public interface SubjectService {
	public void save(Subject subject);
	public List<Subject> findAll();
	public Subject findById(Integer id);
	public void delete(Subject subject);
}
