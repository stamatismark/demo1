package com.example.demo.model.strategies;

public class BestApplicantStrategyFactory {
	public BestApplicantStrategyFactory() {
		
	}
	
	public static BestApplicantStrategy createStrategy(String strategy) {
		if(strategy.equals("random"))
			return new RandomChoiceStrategy();
		if(strategy.equals("avg"))
			return new BestAvgGradeStrategy();
		if(strategy.equals("courses"))
			return new FewestCoursesStrategy();
		return null;
	}
}
