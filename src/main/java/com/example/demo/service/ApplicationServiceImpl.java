package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ApplicationDAO;
import com.example.demo.model.Application;
@Service
public class ApplicationServiceImpl implements ApplicationService{

	@Autowired
	ApplicationDAO applicationDAO;
	
	@Override
	public void save(Application application) {
		// TODO Auto-generated method stub
		applicationDAO.save(application);
	}

	@Override
	public List<Application> findAll() {
		// TODO Auto-generated method stub
		return applicationDAO.findAll();
	}

	@Override
	public Application findById(Integer id) {
		// TODO Auto-generated method stub
		return applicationDAO.getById(id);
	}

}
