package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.BreathState;
import org.jointheleague.ventilator.stepper.StepperInterface;
import org.jointheleague.ventilator.sensors.BreathState;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.stepper.MockStepperController;

public class BreathController {
	StepperInterface sc; // Stepper controller
	VentilatorSetting settings;
	BreathState p;
	
	/**
	 * Creates a BreathController object.
	*/
	public BreathController() {
		if (Launcher.CONNECTED_VENTILATOR) { // If connected to ventilator
			System.out.println("Running real ventilator: will connect to sensors");
			sc = new StepperController();
			p = new BreathState();
		} else { // If is mock run
			System.out.println("Running mock ventilator: will not connect to sensors");
			sc = new MockStepperController();
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
		if (status.equals(BreathState.FORWARD)) {
			sc.forward(settings.breathRate, 0.1);
		}else if(status.equals(BreathState.BACKWARD)){
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
		} catch (InterruptedException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
		
		return numBreaths*2;
		
	}

}
