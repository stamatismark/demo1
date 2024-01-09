package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Application;
import com.example.demo.model.Professor;
import com.example.demo.model.Subject;
import com.example.demo.model.Thesis;
import com.example.demo.model.User;
import com.example.demo.service.ProfessorService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.ThesisService;
import com.example.demo.service.UserService;

@Controller
public class ProfessorController {
	@Autowired
	private UserService userService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ThesisService thesisService;
	
	@RequestMapping("/prof/dashboard")
	public String getProfessorMainMenu() {
		return "prof/dashboard";
	}
	 
	@RequestMapping("/prof/reqProf")
	public String retreiveProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Professor professor = professorService.retrieveProfile(authentication.getName());
		System.out.println(authentication.getName());
		professor.setHelpUserId(professor.getUser().getId());
		model.addAttribute("professor", professor);
		model.addAttribute("user", professor.getUser());
		return "prof/edit";
	}
	
	@RequestMapping("/prof/saveProfessorProfile")
	public String saveProfile(@ModelAttribute("professor") Professor professor, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(professor.getFullName().length() == 0 || professor.getSpecialty().length() == 0) {
			model.addAttribute("errorMessage", "Give values for all fields.");
			return "prof/edit";
		}
		User user = userService.findById(authentication.getName());

		professor.setUser(user);
		professorService.saveProfile(professor);
		return "prof/dashboard";
	}
	

	@RequestMapping("/prof/reqAvailSubj")
	public String listProfessorSubjects(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Subject> professorSubjects = professorService.listProfessorSubjects(authentication.getName());
		model.addAttribute("professorSubjects", professorSubjects);
		model.addAttribute("subjectNew", new Subject());
		return "prof/subjects";
	}
	
	public String showSubjectForm(Model model){
		return null;
	}
	@RequestMapping("/prof/saveSubject")
	public String addSubject(@ModelAttribute("subjectNew")Subject subject, Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Professor professor = professorService.retrieveProfile(authentication.getName());
		
		if(subject.getTitle().length() == 0 || subject.getObjectives().length() == 0) {
			model.addAttribute("errorMessage", "Give values for all fields.");
			List<Subject> professorSubjects = professorService.listProfessorSubjects(authentication.getName());
			model.addAttribute("professorSubjects", professorSubjects);
			model.addAttribute("subjectNew", new Subject());
			return "prof/subjects";
		}
		
		
		
		subject.setProfessor(professor);
		subjectService.save(subject);
		
		List<Subject> professorSubjects = professorService.listProfessorSubjects(authentication.getName());
		model.addAttribute("professorSubjects", professorSubjects);
		model.addAttribute("subjectNew", new Subject());
		
		return "prof/subjects";
	}
	@RequestMapping("/prof/delete/{id}")
	public String deleteSubject(@PathVariable("id")int id, Model model) {
		
		Subject subject = subjectService.findById(id);
		subjectService.delete(subject);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Subject> professorSubjects = professorService.listProfessorSubjects(authentication.getName());

		model.addAttribute("professorSubjects", professorSubjects);
		model.addAttribute("subjectNew", new Subject());
		
		return "prof/subjects";
	}
	@RequestMapping("/prof/applications/{id}")
	public String listApplications(@PathVariable("id")int id, Model model){
		Subject subject = subjectService.findById(id);
		List<Application> applications = subject.getApplications();
		
		model.addAttribute("applications", applications);
		model.addAttribute("subject", subject);
		return "prof/applications";
	}
	
	@RequestMapping("/prof/assign/{strategy}/{id}")
	public String assignSubject(@PathVariable("strategy")String strategy, @PathVariable("id")int id, Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		professorService.assignSubject(authentication.getName(), id, strategy);

		List<Subject> professorSubjects = professorService.listProfessorSubjects(authentication.getName());

		model.addAttribute("professorSubjects", professorSubjects);
		model.addAttribute("subjectNew", new Subject());
		
		return "prof/subjects";
	}
	
	@RequestMapping("/prof/reqAssignSubj")
	public String listProfessorTheses(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Thesis> professorTheses = professorService.listProfessorTheses(authentication.getName());
		model.addAttribute("theses", professorTheses);
		return "prof/theses";
	}
	
	@RequestMapping("/prof/setGrade/{id}")
	public String setThesesGrades(@PathVariable("id")int id, Model model){
		Thesis thesis = thesisService.findById(id);
		model.addAttribute("thesis", thesis);
		return "prof/grades";
	}
	
	private boolean check(float grade) {
		return grade >= 0 && grade <= 10;
	}
	
	@RequestMapping("/prof/saveGrades")
	public String saveGrades(@ModelAttribute("thesis")Thesis thesis, Model model){
		Thesis t = thesisService.findById(thesis.getId());
		if(check(thesis.getImplementationGrade())==false || check(thesis.getPresentationGrade())==false || check(thesis.getReportGrade())==false) {
			model.addAttribute("errorMessage", "Give values for all fields >= 0 and <= 10.");
		}
		else {
			t.copyInfo(thesis);
			thesisService.save(t);		
			model.addAttribute("errorMessage", "Grades saved");

		}
		model.addAttribute("thesis", t);
		return "prof/grades";
	}
	
	@RequestMapping("/prof/calculateGrade/{id}")
	public String calculateGrade(@PathVariable("id")int id, Model model){
		Thesis t = thesisService.findById(id);
		t.setGrade();
		thesisService.save(t);	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Thesis> professorTheses = professorService.listProfessorTheses(authentication.getName());
		model.addAttribute("theses", professorTheses);
		return "prof/theses";
	}
}
