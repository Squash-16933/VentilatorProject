package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperInterface;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.PositionCheck;
import org.jointheleague.ventilator.sensors.SensorReader;

/**
 * Tests the stepper controller, moves motor back and forth.
 */

// TODO add javadocs
public class Test {
	public Test() {
		System.out.println("running simple tests");
		simpleStepperTest();
		//comprehensiveStepperTest();
	}
	
	private void comprehensiveStepperTest() {
		// TODO Auto-generated method stub
		PositionCheck pc = new PositionCheck(new SensorReader());
		StepperInterface sc = new StepperController();
		pc.moveToTop(sc);
		PatientProfile testP = new PatientProfile(16,(double)64,(double)120,(double)20.6,"female","COVID-19");
		BreathController bc = new BreathController(testP);
		while (1==1) {
			bc.breathe();
		}
	}

	void simpleStepperTest() {
		StepperInterface sc = new StepperController();
		while (true) {
			sc.forward(200, 15);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sc.backward(100, 30);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
