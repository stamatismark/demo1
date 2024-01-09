package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="fullName")
	private String fullName;
	
	@Column(name="yearOfStudies")
	private int yearOfStudies;
	
	@Column(name="averageGrade")
	private double averageGrade;
	
	@Column(name="numberOfRemainingCourses")
	private int numberOfRemainingCourses;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(mappedBy="student")
	private Thesis thesis;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="student",cascade = CascadeType.REFRESH)
	private List<Application> applications;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getYearOfStudies() {
		return yearOfStudies;
	}

	public void setYearOfStudies(int yearOfStudies) {
		this.yearOfStudies = yearOfStudies;
	}

	public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public int getNumberOfRemainingCourses() {
		return numberOfRemainingCourses;
	}

	public void setNumberOfRemainingCourses(int numberOfRemainingCourses) {
		this.numberOfRemainingCourses = numberOfRemainingCourses;
	}

	public Thesis getThesis() {
		return thesis;
	}

	public void setThesis(Thesis thesis) {
		this.thesis = thesis;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
