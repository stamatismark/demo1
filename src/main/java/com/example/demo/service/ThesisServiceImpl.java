package com.example.demo.service;

import com.example.demo.model.Thesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ThesisDAO;

@Service
public class ThesisServiceImpl implements ThesisService{
	@Autowired
	ThesisDAO thesisDAO;
	
	@Override
	public void save(Thesis thesis) {
		// TODO Auto-generated method stub
		thesisDAO.save(thesis);
	}

	@Override
	public Thesis findById(Integer id) {
		// TODO Auto-generated method stub
		return thesisDAO.getById(id);
	}

}
