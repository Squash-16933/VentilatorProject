package org.jointheleague.ventilator;

public class VentilatorSetting {
	//volume in ml
	double tidalVolume;
	//breaths/minute
	double breathRate;
	//I:E ratio
	double ieRatio;
	//
	double inspiratoryPressure;
	//
	double expiratoryPressure;
	 
	double peep;
	
	double triggersens;
	
	String ventMode;
	
	public VentilatorSetting(double tidalVolume, double breathRate, double ieRatio, double inspiratoryPressure,
			double expiratoryPressure,  double triggersens, String ventMode) {
		super();
		this.tidalVolume = tidalVolume;
		this.breathRate = breathRate;
		this.ieRatio = ieRatio;
		this.inspiratoryPressure = inspiratoryPressure;
		this.expiratoryPressure = expiratoryPressure;
		this.triggersens = triggersens;
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
	public double getInspiratoryPressure() {
		return inspiratoryPressure;
	}
	public void setInspiratoryPressure(double inspiratoryPressure) {
		this.inspiratoryPressure = inspiratoryPressure;
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
	public void setTriggersens(double Triggersens) {
		this.triggersens = Triggersens;
	}
	public double getTriggersens() {
		return triggersens;
	}
	public String getVentmode() {
		return ventMode;
	}
	public void setVentMode(String ventMode) {
		this.ventMode = ventMode;
	}
	
}
