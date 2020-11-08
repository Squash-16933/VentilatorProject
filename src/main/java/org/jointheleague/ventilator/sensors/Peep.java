package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

public class Peep {

	public static void runPeep(float peep) {
		boolean stop = false;
		StepperController sc = new StepperController(); 
		while (stop != true) {
			try {
				while(SensorExamples.readPressure() != peep) {
					sc.backward(1, 1); //think abt how to put rate and time into incremental method
				}
				while(SensorExamples.readPressure() != 0) {
					sc.forward(1, 1);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(1==0){ //stop machine or change setting
				stop = true;
		}
	}
}
