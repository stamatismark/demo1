package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDAO;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentDAO.save(student);
	}

	@Override
	public Student retrieveProfile(String student) {
		// TODO Auto-generated method stub
		List<Student> students = studentDAO.findAll();
		for(int i = 0; i < students.size(); i++) {
			Student student1 = students.get(i);
			String username = student1.getUser().getUsername();
			if(username.equals(student))
				return students.get(i);
		}
		return null;
	}

	@Override
	public List<Subject> listStudentSubjects() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void applyToSubject(String student, int subject) {
		// TODO Auto-generated method stub
		
	}

}
