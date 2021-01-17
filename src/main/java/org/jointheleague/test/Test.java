package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;

public class Test {
	
	public Test() {
		simpleStepperTest();
	}
	
	void simpleStepperTest() {
		StepperController sc = new StepperController();
		sc.forward(100, 5);
		sc.backward(100, 5);
	}
}
