package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperInterface;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.stepper.MockStepperController;

public class BreathController {
	StepperInterface sc; // Stepper controller
	VentilatorSetting settings;
	Peep p;

	/**
	 * Creates a BreathController object.
	*/
	public BreathController() {
		if (!Launcher.CONNECTED_VENTILATOR) { // If is mock run
			sc = new MockStepperController();
		} else { // If connected to ventilator
			sc = new StepperController();
			p = new Peep();
		}
	}

	/**
	 * Creates a BreathController object.
	 * @param patientProfile Patient settings
	 */
	public BreathController(PatientProfile patientProfile) {
		this();
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
	 * @return Patient settings
	*/
	
	public VentilatorSetting getSettings() {
		return settings;
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
