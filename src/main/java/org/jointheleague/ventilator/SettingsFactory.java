package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.BreathState;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.VentilatorSetting;
import org.jointheleague.ventilator.calculations.Conversions;

public class SettingsFactory {
	// http://www.meddean.luc.edu/lumen/MedEd/MEDICINE/PULMONAR/lecture/mvent.htm

	public static VentilatorSetting getProfile(PatientProfile patientProfile) {
		//TODO tbd factor in disease (maybe make use of a database??)
		VentilatorSetting vs;
		
		double minTidalVolume = (double) Conversions.getIBW(patientProfile.getHeight(), patientProfile.getGender(), 1).get(0);
		double maxTidalVolume = (double) Conversions.getIBW(patientProfile.getHeight(), patientProfile.getGender(), 1).get(1);
		double breathRate = Conversions.getRespRate(patientProfile.getAge());
		double ieRatio = 0.5; // 1:2 simulates normal breathing pattern
		
		//double inspiratoryPressure = 30; // TODO couldnt find anything for I or E pressure being set (TBD)
		//double expiratoryPressure = 30; // TODO couldnt find anything for I or E pressure being set (TBD)
		
		String ventMode = "FULL"; // TODO ask abt vent mode (wasnt here)
		vs = new VentilatorSetting(minTidalVolume, maxTidalVolume, breathRate, ieRatio, ventMode);
		return vs;
	}

}
