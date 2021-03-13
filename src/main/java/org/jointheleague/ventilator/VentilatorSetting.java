package org.jointheleague.ventilator;

public class VentilatorSetting {
<<<<<<< HEAD
<<<<<<< HEAD
	//volume in ml
	double minTidalVolume;
	double maxTidalVolume;
	//breaths/minute
	double breathRate;
	//I:E ratio
	double ieRatio;
	//
=======
	double minTidalVolume; // Minimum tidal volume (mL)
	double maxTidalVolume; // Maximum tidal volume (mL)
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808

	double breathRate; // breaths/minute
	double ieRatio; // I:E ratio
	//double inpiritoryPressure;
	//double expiratoryPressure;
	 
	double peep; // PEEP

<<<<<<< HEAD
=======
	double minTidalVolume; // Minimum tidal volume (mL)
	double maxTidalVolume; // Maximum tidal volume (mL)

	double breathRate; // breaths/minute
	double ieRatio; // I:E ratio
	//double inpiritoryPressure;
	//double expiratoryPressure;
	 
	double peep; // PEEP

=======
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808
	String ventMode; // Ventilation mode
	
	/**
	 * Creates a new VentilatorSetting object.
	 * @param minTidalVolume Minimum tidal volume (mL)
	 * @param maxTidalVolume Maximum tidal volume (mL)
	 * @param breathRate Breaths per minute
	 * @param ieRatio I:E ratio
	 * @param ventMode Ventilation mode
	 */
<<<<<<< HEAD
>>>>>>> origin/main
=======
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808
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
	
	/**
	 * Returns the minimum tidal volume.
	 * @return Minimum tidal volume
	 */
	public double getMinTidalVolume() {
		return minTidalVolume;
	}

	/**
	 * Sets the minimum tidal volume.
	 */
	public void setMinTidalVolume(double minTidalVolume) {
		this.minTidalVolume = minTidalVolume;
	}

	/**
	 * Gets the maximum tidal volume.
	 * @return Maximum tidal volume
	 */
	public double getMaxTidalVolume() {
		return maxTidalVolume;
	}

	/**
	 * Sets the maximum tidal volume.
	 */
	public void setMaxTidalVolume(double maxTidalVolume) {
		this.maxTidalVolume = maxTidalVolume;
	}

	/**
	 * Gets the breath rate.
	 * @return Breaths per minute
	 */
	public double getBreathRate() {
		return breathRate;
	}

	/**
	 * Sets the breath rate.
	 * @param breathRate Breaths per minute
	 */
	public void setBreathRate(double breathRate) {
		this.breathRate = breathRate;
	}

	/**
	 * Gets the I:E ratio.
	 * @return I:E ratio
	 */
	public double getIeRatio() {
		return ieRatio;
	}

	/**
	 * Sets the I:E ratio.
	 * @param ieRatio I:E ratio
	 */
	public void setIeRatio(double ieRatio) {
		this.ieRatio = ieRatio;
	}

<<<<<<< HEAD
<<<<<<< HEAD
=======
	// TODO add reason for why commented out
>>>>>>> origin/main
=======
	// TODO add reason for why commented out
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808
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

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
	/**
	 * Gets the PEEP.
	 * @return PEEP
	 */
	public double getPeep() {
		return peep;
	}
=======
	/**
	 * Gets the PEEP.
	 * @return PEEP
	 */
	public double getPeep() {
		return peep;
	}
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808

	/**
	 * Gets the ventilator mode.
	 * @return Ventilator mode
	 */
	public String getVentMode() {
<<<<<<< HEAD
>>>>>>> origin/main
=======
>>>>>>> ce606dd97c711f82f2abd7f438728ee35bb13808
		return ventMode;
	}

	/**
	 * Sets the ventilator mode.
	 * @param ventMode Ventilator mode
	 */
	public void setVentMode(String ventMode) {
		this.ventMode = ventMode;
	}
}
