package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.BreathState;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
	PatientProfile profile;
	VentilatorSetting settings;
	BreathState p = new BreathState();
	
	public BreathController() {
		
	}

	/**
	 * Creates a BreathController object.
	 * @param patientProfile Patient settings
	 */
	public BreathController(PatientProfile patientProfile) {
		settings = SettingsFactory.getProfile(patientProfile);
	}
	
	public void initialize() {
		settings = SettingsFactory.getProfile(this.profile);
	}
	
	/**
     * Sets the client's patient settings.
     * @param profile Patient profile
     */
	
	 public void setProfile(PatientProfile profile) {
	        this.profile = profile;
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
		if (status.equals(BreathState.FORWARD)) {
			sc.forward(settings.breathRate, 0.1);
		}else if(status.equals(BreathState.BACKWARD)){
			sc.backward(settings.breathRate, 0.1);
		}else{
			System.out.println("waiting");
		}
	}

}
