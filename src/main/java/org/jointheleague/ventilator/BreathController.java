package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
	PatientProfile patient;
	VentilatorSetting settings;
	Peep p = new Peep();

	/**
	 * Creates a BreathController object.
	 * @param patientProfile Patient settings
	 */
	public BreathController(PatientProfile patientProfile) {
		settings = SettingsFactory.getProfile(patientProfile);
	}

	/**
	 * Makes the patient breathe.
	 */
	public void breathe() {
		String status = p.runPeep(settings.getPeep());
		if (status.equals(Peep.FORWARD)) {
			sc.forwardStep(); // TODO change?
		}
	}

}
