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

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.User;
import com.example.demo.model.Application;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;
import com.example.demo.service.SubjectService;

@Controller
public class StudentController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;	
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/stud/dashboard")
	public String getStudentMainMenu() {
		System.out.println("hello");
		return "stud/dashboard";
	}
	
	@RequestMapping("/stud/reqProf")
	public String retreiveProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Student student = studentService.retrieveProfile(authentication.getName());
		model.addAttribute("student", student);
		return "stud/edit";
	}
	
	@RequestMapping("/stud/saveStudentProfile")
	public String saveProfile(@ModelAttribute("student") Student student, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String errorMessage = "";
		boolean hasErrors = false;
		if(student.getFullName().length() == 0) {
			errorMessage = "Give values for all fields.";
			
		}
		if(student.getAverageGrade() < 5 || student.getAverageGrade() > 10) {
			errorMessage = errorMessage + "Give valid value for average.";
			
			hasErrors = true;
		}

		if(student.getYearOfStudies() < 5) {
			errorMessage = errorMessage + "Give valid value for year of studies.";	
			hasErrors = true;
		}
		if(student.getNumberOfRemainingCourses() < 0 || student.getNumberOfRemainingCourses() > 52) {
			errorMessage = errorMessage + "Give valid value for remaining courses.";	
			hasErrors = true;
		}
		if(hasErrors) {
			model.addAttribute("errorMessage", errorMessage);
			return "stud/edit";
		}
			
		User user = userService.findById(authentication.getName());

		student.setUser(user);
		studentService.saveStudent(student);
		return "stud/dashboard";
	}
	
	@RequestMapping("/stud/reqAvailSubj")
	public String listProfessorSubjects(Model model){
		
		List<Subject> professorSubjects = subjectService.findAll();
		model.addAttribute("professorSubjects", professorSubjects);
		
		return "stud/subjects";
	}
	

	@RequestMapping("/stud/description/{id}")
	public String addSubject(@PathVariable("id")int id, Model model){
		Subject subject = subjectService.findById(id);
		model.addAttribute("subject", subject);
		return "stud/subjectDescription";
	}
	
	@RequestMapping("/stud/apply")
	public String addSubject(@ModelAttribute("subject") Subject subject, Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Student student = studentService.retrieveProfile(authentication.getName());
		if(student.getFullName() == null || student.getFullName().length() == 0) {
			model.addAttribute("errorMessage", "Complete your profile first");
			subject = subjectService.findById(subject.getId());
			model.addAttribute("subject", subject);
			return "stud/subjectDescription";
		}
		subject = subjectService.findById(subject.getId());
		
		List<Application> applications = student.getApplications();
		for(int i = 0; i < applications.size(); i++) {
			Subject searchSubj = applications.get(i).getSubject();
			if(searchSubj.getId() == subject.getId()) {
				model.addAttribute("errorMessage", "You have a pending application for this subject");
				subject = subjectService.findById(subject.getId());
				model.addAttribute("subject", subject);
				return "stud/subjectDescription";
			}
		}
		Application application = new Application();
		application.setStudent(student);
		application.setSubject(subject);
		
		applicationService.save(application);
		model.addAttribute("subject", subject);
		model.addAttribute("errorMessage", "You have applied for this subject");

		return "stud/subjectDescription";
	}
	
}
