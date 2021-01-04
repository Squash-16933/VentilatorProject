package org.jointheleague.ventilator.sensors;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

public class Peep {
	public static final int MAX_PRESSURE = 5; // fix this lol
	public static final String FORWARD = "forward";
	public static final String BACKWARD = "backward";
	public static final String NOT_READY = "not ready";

	public ArrayList<Float> pressures;
	public SensorExamples se;
	public double avgp;
	
	public Peep() {
		pressures = new ArrayList<Float>();
		se = new SensorExamples();
		avgp = 0;
	}

	public String runPeep(double peep) {
			try {
				if (pressures.size() >= 5) {
					pressures.remove(0);
					pressures.add(se.readPressure());
					for (int i = 0; i < pressures.size(); i++) {
						avgp = avgp + pressures.get(i);
					}
					avgp = avgp / 5;

					if (avgp > peep) {
						//sc.backwardStep();
						return BACKWARD;
					} else {
						if (avgp < MAX_PRESSURE) {
							//sc.forwardStep();
							return FORWARD;
						}
					}

				} else {
					pressures.add(se.readPressure());
					return NOT_READY;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}
//}
