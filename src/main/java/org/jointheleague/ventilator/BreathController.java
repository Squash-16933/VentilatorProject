package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
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
     * Sets the client's patient settings.
     * @param profile Patient profile
    */
	public void init(PatientProfile profile) {
		settings = SettingsFactory.getProfile(profile);
	}
	 
	/**
	 * Gets the client's patient settings.
	 * @return Patient profile
	*/
	
	public PatientProfile getProfile() {
		return profile;
	}

	/**
	 * Makes the patient breathe.
	 */
	public void breathe() {
		String status = p.runPeep(settings.getPeep());
		if (status.equals(Peep.FORWARD)) {
			sc.forward(settings.breathRate, 0.1);
		} else if (status.equals(Peep.BACKWARD)){
			sc.backward(settings.breathRate, 0.1);
		} else {
			System.out.println("waiting");
		}
	}
}
