package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Random;

@Entity
@Table(name="applications")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
    @JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
    @JoinColumn(name = "subject_id")
	private Subject subject;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int compare(Application a2, String type) {
		// TODO Auto-generated method stub
		if(type.equals("courses")) {
			return student.getNumberOfRemainingCourses() - a2.student.getNumberOfRemainingCourses();
		}
		else if(type.equals("avg")) {
			if(student.getAverageGrade() < a2.student.getAverageGrade()) {
				return 1;
			}
			else if(student.getAverageGrade() > a2.student.getAverageGrade()) {
				return -1;
			}
			else {
				return 0;
			}
		}
		else if(type.equals("random")) {
			Random random = new Random();
			return random.nextInt(3) - 1;
		}
		else
			return 0;
	}
	
	
}
