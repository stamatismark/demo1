package com.example.demo.model.strategies;

import com.example.demo.model.Application;
import java.util.Random;
public class RandomChoiceStrategy extends TemplateStrategyAlgorithm {
	public RandomChoiceStrategy() {
		
	}
	@Override
	protected int compareApplications(Application a1, Application A2) {
		// TODO Auto-generated method stub
		return a1.compare(A2, "random");
	}

}
