package org.jointheleague.ventilator;

import org.jointheleague.ventilator.calculations.VentilatorCalcs;
import org.jointheleague.ventilator.sensors.BreathState;
import org.jointheleague.ventilator.sensors.SensorReader;

public class VentilatorModes {
	// Volume Assist Control (ACV)
	// SIMV - irregular breaths and adjusts based on patient, add a psv, must monitor the pressure-support breath (what tidal vol it is generating)
	// Pressure-Controlled Ventilation (PCV) - the selection of inspiratory pressure to control peak pressure (pip) - does not take tidal vol into account
	// Pressure Support Ventilation (PSV) - spontaneous breathing
	// Dynamic Mode
	
	VentilatorSetting vs;
	VentilatorCalcs vc;
	String VentilatorMode;
	double IntraPress;
	BreathController bc;
	String phase = "inspiratory";
	int Triggersens = 0;
	SensorReader sr;
	BreathState p;
	// brainstorm
	/*
	 * What I need: volume of breath per interval of time = tidal vol/inspiratory
	 * time, negative value of intrathoracic pressure restarts cycle, functinoal
	 * pressure sensor to read the negative value, changing breath-rate What I don't
	 * have -> pressure sensor functional yet and connected, adaptable volume
	 * delivery
	 * 
	 */
public VentilatorModes(VentilatorSetting vs, VentilatorCalcs vc, String VentilatorMode, double IntraPress, BreathController bc, SensorReader sr, BreathState p) {
	this.vs = vs;
	this.vc = vc;
	//bc = new BreathController();
//	this.VentilatorMode = vc.ven
	sr = new SensorReader();
	p = new BreathState();
}
	// Volume Assist Control
	public void VolumeAssist() {
		double ftdv = (vs.getMaxTidalVolume()) / (vc.inspPhaseTime()); //fixed tidal volume given at specific intervals
		double atv = 0; //accumulated tidal volume
		if (VentilatorMode.equals("Volume Assist Control")|| phase.equals("inspiratory")){
			try {
				bc.breathe(); //continues to provide air and a check in pressure or volume should lead to the expiratory phase once a threshold is reached 
								//give patient air in a certain time period
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (phase.equals("expiratory")) { //once threshold is reached NOTE: could add pressure checker to see when patient initiates a breath
				try {
					if(p.runPeep(vs.peep).equals("forward")) {
						phase = "inspiratory";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (sr.readPressure() < Triggersens) { // if Intrathoracic pressure is negative, then it restarts the current cycle - could use the BME280Driver to check pressure at all times
				phase = "inspiratory";
			}
		}
	}
	public void SimV() {
		
	}
	public void PCV() {
		
	}
	public void PSV() {
		
	}

}