package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
	PatientProfile patient;
	VentilatorSetting settings;
	Peep p = new Peep();
	public BreathController(PatientProfile patientProfile) {
		
	}
	
	public void pressureCheck() {
		String status = p.runPeep(settings.getPeep());
		if(status.equals(Peep.FORWARD)) {
			sc.forwardStep(); //change?
		}
	}
	
}
