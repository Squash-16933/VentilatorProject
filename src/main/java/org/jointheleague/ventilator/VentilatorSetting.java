package org.jointheleague.ventilator;

public class VentilatorSetting {
	//volume in ml
	double minTidalVolume;
	double maxTidalVolume;
	//breaths/minute
	double breathRate;
	//I:E ratio
	double ieRatio;
	//

	//double inpiritoryPressure;

	//
	//double expiratoryPressure;
	 
	double peep;
	
	double triggersens;
	
	String ventMode;
	

	public VentilatorSetting(double minTidalVolume, double maxTidalVolume, double breathRate, double ieRatio, String ventMode) {
		super();
		this.minTidalVolume = minTidalVolume;
		this.maxTidalVolume = maxTidalVolume;
		this.breathRate = breathRate;
		this.ieRatio = ieRatio;

		//this.inpiritoryPressure = inpiritoryPressure;
		//this.expiratoryPressure = expiratoryPressure;

		this.ventMode = ventMode;
	}
	
	public double getMinTidalVolume() {
		return minTidalVolume;
	}
	public void setMinTidalVolume(double minTidalVolume) {
		this.minTidalVolume = minTidalVolume;
	}
	public double getMaxTidalVolume() {
		return maxTidalVolume;
	}
	public void setMaxTidalVolume(double maxTidalVolume) {
		this.maxTidalVolume = maxTidalVolume;
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

	//public double getInpiritoryPressure() {
		//return inpiritoryPressure;
	//}
	//public void setInpiritoryPressure(double inpiritoryPressure) {
	//	this.inpiritoryPressure = inpiritoryPressure;
	//}
	//public double getExpiratoryPressure() {
	//	return expiratoryPressure;
	//}
	//public void setExpiratoryPressure(double expiratoryPressure) {
	//	this.expiratoryPressure = expiratoryPressure;
	//}

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
