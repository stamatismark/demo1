package com.example.demo.model.strategies;

import java.util.List;

import com.example.demo.model.Application;
import com.example.demo.model.Student;

public interface BestApplicantStrategy {
	public Student findBestApplicant(List<Application> applications);
}
