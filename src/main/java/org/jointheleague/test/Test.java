package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.PositionCheck;

// TODO what does this do
public class Test {
	public Test() {
		//simpleStepperTest();
		comprehensiveStepperTest();
	}
	
	private void comprehensiveStepperTest() {
		// TODO Auto-generated method stub
		PositionCheck pc = new PositionCheck();
		StepperController sc = new StepperController();
		pc.moveToTop(sc);
		PatientProfile testP = new PatientProfile(16,(double)64,(double)120,(double)20.6,"female","COVID-19");
		BreathController bc = new BreathController(testP);
		while (1==1) {
			bc.pressureCheck();
		}
		
		
	}

	void simpleStepperTest() {
		StepperController sc = new StepperController();
		while (true) {
			sc.forward(10, 5);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sc.backward(10, 5);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
