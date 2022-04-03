package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.SensorReader;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.stepper.StepperInterface;

public class PositionCheck {
	//checkPosition() and moveToTop() to run before EVERY START!!
	SensorReader sensorReader;
	int distance = 0;
	public PositionCheck(SensorReader r) {
		 sensorReader = r;
	}
	
	public String checkPosition() {
		String setting = "";
		distance = sensorReader.readLidar();
		
		if(distance >=100 && distance<=140) { // 100-140 range given by Vic, guess and check to fix 
			setting = "SET";
		}else if(distance > 140) {
				setting = "TOO HIGH";
		}else {
				setting = "TOO LOW";
		}
		
		System.out.println(setting);
		return setting;
	}
	
	public void moveToTop(StepperInterface sc) {
		if(checkPosition().equals("TOO HIGH")){
			while(distance > 140) {
				sc.downStep();
				distance = sensorReader.readLidar();
			}
			System.out.println("CALIBRATED");
		}else if(checkPosition().equals("TOO LOW")){
			while(distance <100) {
				sc.upStep();
				distance = sensorReader.readLidar();
			}
			System.out.println("CALIBRATED");
		}else {
			System.out.println("CALIBRATED");
		}
	}

}
