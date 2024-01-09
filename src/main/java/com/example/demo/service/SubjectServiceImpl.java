package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SubjectDAO;
import com.example.demo.dao.ThesisDAO;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
@Service
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectDAO subjectDAO;
	@Autowired
	private ThesisDAO thesisDAO;
	
	@Override
	public void save(Subject subject) {
		// TODO Auto-generated method stub
		subjectDAO.save(subject);
	}

	@Override
	public List<Subject> findAll() {
		// TODO Auto-generated method stub
		List<Subject> subjects = subjectDAO.findAll();
		List<Thesis> thesis = thesisDAO.findAll();
		
		for(int i = 0; i < thesis.size(); i++) {
			Subject deleteSubj = thesis.get(i).getSubject();
			subjects.remove(deleteSubj);
		}
		return subjects;
	}

	@Override
	public Subject findById(Integer id) {
		// TODO Auto-generated method stub
		return subjectDAO.getById(id);
	}

	@Override
	public void delete(Subject subject) {
		// TODO Auto-generated method stub
		subjectDAO.delete(subject);
	}

}
