package com.example.demo.model.strategies;

import java.util.List;

import com.example.demo.model.Application;
import com.example.demo.model.Student;

public abstract class TemplateStrategyAlgorithm implements BestApplicantStrategy {
	
	public TemplateStrategyAlgorithm() {
		
	}
	@Override
	public Student findBestApplicant(List<Application> applications) {
		// TODO Auto-generated method stub
		int bestPos = 0;
		for (int i = 1; i < applications.size(); i++) {
			Application a1 = applications.get(bestPos);
			Application a2 = applications.get(i);
			if(compareApplications(a2, a1) < 0) {
				bestPos = i;
			}
			
		}
		
		return applications.get(bestPos).getStudent();
	}
	
	protected abstract int compareApplications(Application a1, Application a2);
}
