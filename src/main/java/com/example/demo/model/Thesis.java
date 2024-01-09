package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="thesis")
public class Thesis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@OneToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@OneToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
    @JoinColumn(name = "professor_id")
	private Professor professor;

	@Column(name="implementation_grade")
	private float implementationGrade;
	
	@Column(name="presentation_grade")
	private float presentationGrade;
	
	@Column(name="report_grade")
	private float reportGrade;
	
	@Column(name="total_grade")
	private float totalGrade;
	
	public Thesis() {
		
	}
	
	public Thesis(Subject subject, Student student, Professor professor) {
		super();
		this.subject = subject;
		this.student = student;
		this.professor = professor;
	}

	
	public float getImplementationGrade() {
		return implementationGrade;
	}

	public void setImplementationGrade(float implementationGrade) {
		this.implementationGrade = implementationGrade;
	}

	public float getPresentationGrade() {
		return presentationGrade;
	}

	public void setPresentationGrade(float presentationGrade) {
		this.presentationGrade = presentationGrade;
	}

	public float getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(float reportGrade) {
		this.reportGrade = reportGrade;
	}

	public float getTotalGrade() {
		return totalGrade;
	}

	public void setTotalGrade(float totalGrade) {
		this.totalGrade = totalGrade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void copyInfo(Thesis other) {
		// TODO Auto-generated method stub
		implementationGrade = other.implementationGrade;
		presentationGrade = other.presentationGrade;
		reportGrade = other.reportGrade;
	}

	public void setGrade() {
		// TODO Auto-generated method stub
		totalGrade = (float) (0.7*implementationGrade + 0.15*reportGrade + 0.15*presentationGrade);
	}
	
	
}
