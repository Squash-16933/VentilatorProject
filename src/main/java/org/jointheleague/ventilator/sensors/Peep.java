package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

public class Peep {
public static final int MAX_PRESSURE = 5; //fix this lol
	public static void runPeep(float peep) {
		boolean stop = false;
		StepperController sc = new StepperController(); 
		while (stop != true) {
			try {
				float p1 = SensorExamples.readPressure();
				Thread.sleep(10);
				float p2 = SensorExamples.readPressure();
				Thread.sleep(10);
				float p3 = SensorExamples.readPressure();
				Thread.sleep(10);
				float p4 = SensorExamples.readPressure();
				Thread.sleep(10);
				float p5 = SensorExamples.readPressure();
				Thread.sleep(10);
				float avgp = (p1 + p2 + p3 + p4 + p5)/5;
				//tweak wait times to increase/decrease breathing rate (figure out later)
				//to think abt: will this increase or decrease precision of pressure readings?
				
				if(avgp > peep) {
					sc.backward(1, 1); //think abt how to put rate and time into incremental method
				}else {
					if(avgp < MAX_PRESSURE) {
						sc.forward(1, 1);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(1==0){ //stop machine or change setting
				stop = true;
		}
	}
}
