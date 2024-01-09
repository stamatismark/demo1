package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;

public interface ProfessorService {
	public Professor retrieveProfile(String professor);
	public void saveProfile(Professor professor);
	public List<Subject> listProfessorSubjects(String professor);
	public void addSubject(String professor, Subject subject);
	public List<Application> listApplications(String professor, int subject);
	public List<Thesis> listProfessorTheses(String professor);
	public void assignSubject(String professor, int id, String strategy);
}
