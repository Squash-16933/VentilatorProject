package org.jointheleague.ventilator;

import org.jointheleague.ventilator.sensors.Peep;
import org.jointheleague.ventilator.stepper.StepperController;
import org.jointheleague.ventilator.VentilatorSetting;
import org.jointheleague.ventilator.calculations.Conversions;

public class SettingsFactory {
	// http://www.meddean.luc.edu/lumen/MedEd/MEDICINE/PULMONAR/lecture/mvent.htm
	static VentilatorSetting ss;

	public static VentilatorSetting getProfile(PatientProfile patientProfile) {

		double minTidalVolume = (double) Conversions.getIBW(patientProfile.getHeight(), patientProfile.getGender(), 1).get(0);
		double maxTidalVolume = (double) Conversions.getIBW(patientProfile.getHeight(), patientProfile.getGender(), 1).get(1);
		double breathRate = Conversions.getRespRate(patientProfile.getAge());
		double ieRatio = 0.5; // 1:2 simulates normal breathing pattern
		//double inspiratoryPressure = 30; // couldnt find anything for I or E pressure being set (TBD)
		//double expiratoryPressure = 30; // couldnt find anything for I or E pressure being set (TBD)
		String ventMode = "idk"; // ask abt vent mode (wasnt here)
		ss = new VentilatorSetting(minTidalVolume, maxTidalVolume, breathRate, ieRatio, ventMode);
		return ss;
	}

}
