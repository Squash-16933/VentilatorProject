package org.jointheleague.ventilator;

import org.jointheleague.ventilator.calculations.VentilatorCalcs;

public class VentilatorModes {
	// Volume Assist Control (ACV)
	// SIMV - irregular breaths and adjusts based on patient
	// Pressure-Controlled Ventilation (PCV)
	// Pressure Support Ventilation (PSV)
	// Dynamic Mode
	
	VentilatorSetting vs;
	VentilatorCalcs vc;
	String VentilatorMode = vs.ventMode;
	double IntraPress;
	// brainstorm
	/*
	 * What I need: volume of breath per interval of time = tidal vol/inspiratory
	 * time, negative value of intrathoracic pressure restarts cycle, functinoal
	 * pressure sensor to read the negative value, changing breath-rate What I don't
	 * have -> pressure sensor functional yet and connected, adaptable volume
	 * delivery
	 * 
	 */

	// Volume Assist Control
	public void VolumeAssist() {
		double ftdv = (vs.getTidalVolume()) / (vc.inspPhaseTime()); //fixed tidal volume given at specific intervals
		double atv = 0; //accumulated tidal volume
		while (VentilatorMode.equals("Volume Assist Control")) {
			try {
				Thread.sleep(Double.valueOf(vc.inhaleTime()).longValue());
				// push the ftdv into patient - how????
				atv = atv + ftdv;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (atv == vs.getTidalVolume()) { // tells us that all the air in a cycle is delivered to the patient
				// go to expiratory which means to pause this code and activate peep
				try {
					//activate expiratory phase and peep
					Thread.sleep(Double.valueOf(vc.expirPhaseTime()).longValue());
					atv = 0;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (IntraPress < vs.getTriggersens()) { // if Intrathoracic pressure is negative, then it restarts the current cycle - could use the BME280Driver to check pressure at all times
				atv = 0; //check pressure class???
			}
		}
	}

}