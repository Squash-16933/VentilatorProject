package org.jointheleague.ventilator.sensors;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.jointheleague.ventilator.stepper.MockStepperController;
import org.jointheleague.ventilator.stepper.StepperController;

public class Peep {
	public static final String FORWARD = "forward"; //inhalation
	public static final String BACKWARD = "backward"; //exhalation
	public static final String NOT_READY = "not ready"; //none
	public ArrayList<Float> pressures = new ArrayList<Float>();
	public SensorExamples se = new SensorExamples();
	public double avgp = 0;

	/**
	 * Checks whether the patient should be inhaling, exhaling, or doing nothing.
	 * @param peep PEEP value (default: 40)
	 * @return Peep.FORWARD (inhalation), Peep.BACKWARD (exhalation), or Peep.NOT_READY (none)
	 */
	public String runPeep(double peep) {
		double pip = 40 + peep; //default IP = 40, PIP= IP + PEEP
		if (pressures.size() >= 5) {
			pressures.remove(0);
			pressures.add(se.readPressure());
			for (int i = 0; i < pressures.size(); i++) {
				avgp = avgp + pressures.get(i);
			}
			avgp = avgp / 5;

			if (avgp > peep) {
				// sc.backwardStep();
				return BACKWARD;
			} else {
				if (avgp < pip) {
					// sc.forwardStep();
					return FORWARD;
				}
			}
		} else {
			pressures.add(se.readPressure());
			return NOT_READY;
		}

		return null;
	}

}