package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;

public class Test {
	
	public Test() {
		simpleStepperTest();
	}
	
	void simpleStepperTest() {
		StepperController sc = new StepperController();
		while (1==1) {
		sc.forward(10, 5);
		sc.backward(10, 5);
		}
	}
}
