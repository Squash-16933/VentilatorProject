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
