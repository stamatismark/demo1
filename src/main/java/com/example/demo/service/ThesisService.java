package com.example.demo.service;

import com.example.demo.model.Thesis;

public interface ThesisService {
	public void save(Thesis thesis);
	public Thesis findById(Integer id);
}
