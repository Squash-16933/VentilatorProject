package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
	PatientProfile profile;
	VentilatorSetting settings;
	Peep p = new Peep();
	
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
		if (status.equals(Peep.FORWARD)) {
			sc.forward(settings.breathRate, 0.1);
		}else if(status.equals(Peep.BACKWARD)){
			sc.backward(settings.breathRate, 0.1);
		}else{
			System.out.println("waiting");
		}
	}
	
	public int getRespRateAnyTime() {
		//breaths per minute (checks every 30 seconds)
		String status;
		String begStat = p.runPeep(settings.getPeep());
		int numUpDown = 0;
		int numMilliSeconds = 0;
		int numBreaths = 0;
		try {
		while(numMilliSeconds<30000) {
			status = p.runPeep(settings.getPeep());
			if(!(status.equals(begStat))) {
				numUpDown++;
			}
			if(numUpDown%2!=0 && numUpDown!=0) {
				numBreaths ++;
			}
			Thread.sleep(100);
			numMilliSeconds+=100;
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numBreaths*2;
		
	}

}
