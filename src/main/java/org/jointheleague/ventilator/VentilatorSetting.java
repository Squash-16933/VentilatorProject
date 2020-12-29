package org.jointheleague.ventilator;

public class VentilatorSetting {
	//volume in ml
	double tidalVolume;
	//breaths/minute
	double breathRate;
	//I:E ratio
	double ieRatio;
	//
	double inpiritoryPressure;
	//
	double expiratoryPressure;
	 
	double peep;

	String ventMode;
	
	public VentilatorSetting(double tidalVolume, double breathRate, double ieRatio, double inpiritoryPressure,
			double expiratoryPressure, String ventMode) {
		super();
		this.tidalVolume = tidalVolume;
		this.breathRate = breathRate;
		this.ieRatio = ieRatio;
		this.inpiritoryPressure = inpiritoryPressure;
		this.expiratoryPressure = expiratoryPressure;
		this.ventMode = ventMode;
	}
	
	public double getTidalVolume() {
		return tidalVolume;
	}
	public void setTidalVolume(double tidalVolume) {
		this.tidalVolume = tidalVolume;
	}
	public double getBreathRate() {
		return breathRate;
	}
	public void setBreathRate(double breathRate) {
		this.breathRate = breathRate;
	}
	public double getIeRatio() {
		return ieRatio;
	}
	public void setIeRatio(double ieRatio) {
		this.ieRatio = ieRatio;
	}
	public double getInpiritoryPressure() {
		return inpiritoryPressure;
	}
	public void setInpiritoryPressure(double inpiritoryPressure) {
		this.inpiritoryPressure = inpiritoryPressure;
	}
	public double getExpiratoryPressure() {
		return expiratoryPressure;
	}
	public void setExpiratoryPressure(double expiratoryPressure) {
		this.expiratoryPressure = expiratoryPressure;
	}
	public double getPeep() {
		return peep;
	}
	public String getVentmode() {
		return ventMode;
	}
	public void setVentMode(String ventMode) {
		this.ventMode = ventMode;
	}
	
}
