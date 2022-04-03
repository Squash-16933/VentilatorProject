package org.jointheleague.ventilator.sensors;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

/**
 * Determines where to inhale or exhale.
 */
public class BreathState {
	public static final String DOWN = "down"; //inhalation
	public static final String UP = "up"; //exhalation
	public static final String NOT_READY = "not ready"; //none
	public ArrayList<Float> pressures;
	public SensorReader sr;
	public double avgp = 0;

	public BreathState() {
		pressures = new ArrayList<Float>();
		sr = new SensorReader();
	}

	/**
	 * Checks whether the patient should be inhaling, exhaling, or doing nothing.
	 * @param peep PEEP value (default: 40)
	 * @return BreathState.DOWN (inhalation), BreathState.UP (exhalation), or BreathState.NOT_READY (none)
	 */
	public String runPeep(double peep) {
		double pip = 40 + peep; //default IP = 40, PIP= IP + PEEP
		if (pressures.size() >= 5) {
			pressures.remove(0);
			pressures.add(sr.readPressure());
			for (int i = 0; i < pressures.size(); i++) {
				avgp = avgp + pressures.get(i);
			}
			avgp = avgp / 5;

			if (avgp > peep) {
				// sc.upStep();
				return UP;
			} else {
				if (avgp < pip) {
					// sc.downStep();
					return DOWN;
				}
			}
		} else {
			pressures.add(sr.readPressure());
			return NOT_READY;
		}

		return null;
	}

}