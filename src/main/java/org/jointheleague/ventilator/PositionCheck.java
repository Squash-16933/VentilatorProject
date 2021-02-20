package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.SensorExamples;
import org.jointheleague.ventilator.stepper.StepperController;

public class PositionCheck {
	//checkPosition() and moveToTop() to run before EVERY START!!
	
	int distance = SensorExamples.readLidar();
	public String checkPosition() {
		String setting = "";
		if(distance >=100 && distance<=140) { // 100-140 range given by Vic, guess and check to fix 
			setting = "SET";
		}else {
			if(distance > 140) {
				setting = "TOO HIGH";
			}else {
				setting = "TOO LOW";
			}
		}
		System.out.println(setting);
		return setting;
	}
	
	public void moveToTop(StepperController sc) {
		if(checkPosition().equals("TOO HIGH")){
			while(distance > 140) {
				sc.forwardStep();
				distance = SensorExamples.readLidar();
			}
			System.out.println("CALIBRATED");
		}else if(checkPosition().equals("TOO LOW")){
			while(distance <100) {
				sc.backwardStep();
				distance = SensorExamples.readLidar();
			}
			System.out.println("CALIBRATED");
		}else {
			System.out.println("CALIBRATED");
		}
	}

}
