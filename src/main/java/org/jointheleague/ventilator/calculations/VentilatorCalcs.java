package org.jointheleague.ventilator.calculations;

public class VentilatorCalcs {
	private double volumeChange;
	private double diameter;
	private double height;

	/**
	 * 
	 * @param inspiratoryPress Max40cmH2O
	 * @param expiratoryPress  Max30cmH2O
	 * @param respRate         6-40 Breaths/min -- input: 40.00
	 * @param tidalVol         200-800 mL -- input: 500.00
	 * @param inspir           Inspiratory time
	 * @param expir            Expiratory time
	 */
	private double inspiratoryPress;
	private double expiratroryPress;
	private double respRate;
	private double tidalVol;
	private double volOutput;
	private double inspir;
	private double expir;
	private double percentHold;

	// TODO add docs
	public VentilatorCalcs(double inspir, double expir, double inspiratoryPress, double expiratoryPress,
			double respRate, double tidalVol, double volOutput, double percentHold) {
		this.inspiratoryPress = inspiratoryPress;
		this.expiratroryPress = expiratoryPress;
		this.respRate = respRate;
		this.tidalVol = tidalVol;
		this.volOutput = volOutput;
		this.inspir = inspir;
		this.expir = expir;
		this.percentHold = percentHold;

	}

	/**
	 * Returns the change in circumference of the tank when deflated by a volume
	 * <code>volumeChange</code> (assuming tank is a perfect cylinder throughout
	 * deflation.)
	 * 
	 * @return Circumference change
	 */
	public double changeCirc() {
		return (Math.PI * diameter)
				- Math.sqrt(((Math.PI * diameter) * (Math.PI * diameter)) - ((4 * Math.PI * volumeChange) / height));
	}

	/**
	 * Returns the Inspiratory (I)/Expiratory (E) Time Ratio I:E
	 * 
	 * @return Time Ratio
	 */
	public double timeRatio() {
		return inspir / expir;
	}

	/**
	 * Returns Inhale Time
	 * 
	 * @return THale = 60 sec/RR/(1+ IE) (in seconds)
	 * 
	 */
	public double inhaleTime() {
		return 60 / respRate / ((1 + (inspir * expir)));
	}

	/**
	 * Returns Volumetric Flow Rate
	 * 
	 * @return Vrate= Vout/THale (in cm3/sec)
	 */
	public double volumetricFlowRate() {
		return volOutput / inhaleTime();
	}

	/**
	 * Returns Inhale/Exhale cycle
	 * 
	 * @return T = 60 / RR (in seconds)
	 * 
	 */

	public double inExCycle() {
		return 60 / respRate;
	}

	/**
	 * Returns Length of time in inspiratory phase
	 * 
	 * @return Tin = T/(1+IE) (in seconds)
	 */
	public double inspPhaseTime() {
		return inExCycle() / (1 + (inspir * expir));
	}

	/**
	 * Returns Length of time in expiratory phase
	 * 
	 * @return Tex = T-Tin (in seconds )
	 */

	public double expirPhaseTime() {
		return inExCycle() - inspPhaseTime();
	}

	/**
	 * Sets the default I:E ratio to be 1:2
	 * 
	 * @return Time Default Ratio
	 */

	public double setDefaultIE() {
		return inspir / (2 * inspir);
	}

	/**
	 * Checks if the IE ratio is not between 1:1 and 1:4 If it is not included in
	 * the range, it is set back to the default 1:2
	 * 
	 */

	public void checkIEratio() {
		if (timeRatio() > 1 && timeRatio() < 0.25) {
			setDefaultIE();
		}
	}

	/**
	 * Checks if the Inspiratory pressure reaches 40 and makes it stop until it
	 * reaches 30cmH2O. (Pressure level) The Inspiratory pressure would need to stop
	 * until the Expiratory pressure reaches zero or when the ventilatory period
	 * ends.
	 */
	public void checkInspPress() {
		if (inspiratoryPress >= 40) {
			while (!(inspiratoryPress <= 30)) {
				try {
					// stop inspiratory-intake
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// start expiratory phase and opens valve - patient exhales passively which is
			// controlled by PEEP
		}
	}

	/**
	 * Returns Time to hold compression (inspiratory pause) at end of inhale for
	 * plateau pressure
	 * 
	 * @return Thold = Tin * Percenthold (in seconds)
	 */
	public double compressionTimeHold() {
		return inspPhaseTime() * percentHold;
	}
}
