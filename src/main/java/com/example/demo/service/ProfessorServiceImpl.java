package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProfessorDAO;
import com.example.demo.dao.ThesisDAO;
import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
import com.example.demo.model.strategies.BestApplicantStrategy;
import com.example.demo.model.strategies.BestApplicantStrategyFactory;
@Service
public class ProfessorServiceImpl implements ProfessorService {
	
	@Autowired
	private ProfessorDAO professorDAO;

	@Autowired
	private ThesisDAO thesisDAO;
	
	@Override
	public Professor retrieveProfile(String professor) {
		// TODO Auto-generated method stub
		List<Professor> professors = professorDAO.findAll();
		System.out.println(professors.size());
		for(int i = 0; i < professors.size(); i++) {
			Professor professor1 = professors.get(i);
			if(professor1.getUser() == null)
				continue;
			String username = professor1.getUser().getUsername();
			if(username.equals(professor))
				return professors.get(i);
		}
		return null;
	}

	@Override
	public void saveProfile(Professor professor) {
		// TODO Auto-generated method stub
		professorDAO.save(professor);
	}

	@Override
	public List<Subject> listProfessorSubjects(String professor) {
		// TODO Auto-generated method stub

		Professor professor1 = retrieveProfile(professor);
		List<Subject> subjects = professor1.getSubjects();
		List<Thesis> thesis = thesisDAO.findAll();
		
		for(int i = 0; i < thesis.size(); i++) {
			Subject deleteSubj = thesis.get(i).getSubject();
			subjects.remove(deleteSubj);
		}
		return subjects;
	}

	@Override
	public void addSubject(String professor, Subject subject) {
		// TODO Auto-generated method stub
		
		Professor professor1 = retrieveProfile(professor);
		professor1.getSubjects().add(subject);
		professorDAO.save(professor1);
	}

	@Override
	public List<Application> listApplications(String professor, int subject) {
		// TODO Auto-generated method stub
		Professor professor1 = retrieveProfile(professor);
		Subject subject1 = professor1.getSubjects().get(subject);
		
		return subject1.getApplications();
	}

	@Override
	public List<Thesis> listProfessorTheses(String professor) {
		// TODO Auto-generated method stub
		Professor professor1 = retrieveProfile(professor);
		return professor1.getTheses();
	}

	@Override
	public void assignSubject(String professor, int id, String strategy) {
		// TODO Auto-generated method stub
		Professor professor1 = retrieveProfile(professor);
		List<Subject> subjects = professor1.getSubjects();
		
		Subject subject = null;
		for(int i = 0; i < subjects.size(); i++) {
			Subject subj = subjects.get(i);
			if(subj.getId() == id) {
				subject = subj;
				break;
			}
		}
		
		BestApplicantStrategy bestApplicantStrategy = BestApplicantStrategyFactory.createStrategy(strategy);
		Student student = bestApplicantStrategy.findBestApplicant(subject.getApplications());
		
		Thesis thesis = new Thesis(subject, student, professor1);
		professor1.getTheses().add(thesis);
		thesisDAO.save(thesis);
	}

}
