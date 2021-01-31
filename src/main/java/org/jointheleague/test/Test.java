package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;

public class Test {
	
	public Test() {
		//simpleStepperTest();
		comprehensiveStepperTest();
	}
	
	private void comprehensiveStepperTest() {
		// TODO Auto-generated method stub
		StepperController sc = new StepperController();
		PatientProfile testP = new PatientProfile(16,(double)64,(double)120,(double)20.6,"female","COVID-19");
		BreathController bc = new BreathController(testP);
		while (1==1) {
			bc.pressureCheck();
		}
		
		
	}

	void simpleStepperTest() {
		StepperController sc = new StepperController();
		while (1==1) {
		sc.forward(10, 5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.backward(10, 5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
