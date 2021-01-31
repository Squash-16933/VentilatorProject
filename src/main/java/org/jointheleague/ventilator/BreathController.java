package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;

public class BreathController {
	StepperController sc = new StepperController();
	PatientProfile patient;
	VentilatorSetting settings;
	Peep p = new Peep();

	public BreathController(PatientProfile patientProfile) {
		settings = SettingsFactory.getProfile(patientProfile);
	}

	public void pressureCheck() {
		String status = p.runPeep(settings.getPeep());
		if (status.equals(Peep.FORWARD)) {
			sc.forward(settings.breathRate, 0.1);
		}else if(status.equals(Peep.BACKWARD)){
			sc.backward(settings.breathRate, 0.1);
		}else{
			System.out.println("waiting");
		}
	}

}
