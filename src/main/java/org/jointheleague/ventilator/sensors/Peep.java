package org.jointheleague.ventilator.sensors;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

public class Peep {
	public static final int MAX_PRESSURE = 5; // fix this lol

	public static void runPeep(float peep) {
		float avgp = 0;
		boolean stop = false;
		StepperController sc = new StepperController();
		ArrayList<Float> pressures = new ArrayList<Float>();

		while (stop != true) {
			try {
				if (pressures.size() >= 5) {
					for (int i = 0; i < pressures.size(); i++) {
						avgp = avgp + pressures.get(i);
					}
					avgp = avgp / 5;

					if (avgp > peep) {
						sc.backwardStep();
					} else {
						if (avgp < MAX_PRESSURE) {
							sc.forwardStep();
						}
					}

					pressures.remove(0);
					pressures.add(SensorExamples.readPressure());
				} else {
					pressures.add(SensorExamples.readPressure());
				}

				if (1 == 0) { // stop machine or change setting
					stop = true;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
