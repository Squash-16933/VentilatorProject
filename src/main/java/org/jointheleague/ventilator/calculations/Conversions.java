package org.jointheleague.ventilator.calculations;

import java.util.ArrayList;

// TODO add more docs
public class Conversions {

	/**
	 * NEMA 17-size hybrid bipolar stepping motor (for compressionTotal)
	 * 
	 * Full compression of ambu bag: ~ 4 in - 2 in = 2 in 1.6 mil : 1 step 200 steps
	 * : 1 revolution 320 mil : 1 revolution 320 mil = .32 in 1250 steps = 2 in
	 * <strong>1250 steps = 1 breath</strong>
	 * 
	 */

	public static int MAX_COMPRESSION_STEPS = 1250;

	public static int convertBPMtoSPS(int bpm) {
// (x brths)/(1 min) * (1 min)/(60 sec) = (x brths)/(1 sec) * (y steps)/(1 brth) = (y steps)/(1 sec)
		return (int) ((bpm / 60f) * MAX_COMPRESSION_STEPS);
	}

	/**
	 * Returns average breath rate for an age group given age of individual.
	 * Source: https://www.healthline.com/health/normal-respiratory-rate#normal-rate-in-adults
	 * @param age Age (months)
	 * @return Breath rate
	 */
	public static int getRespRate(int age) {
		if (age < 12) {
			return 45;
		} else if (age >= 12 && age < 36) {
			return 32;
		} else if (age >= 36 && age < 72) {
			return 28;
		} else if (age >= 72 && age < 144) {
			return 24;
		} else {
			return 14;
		}
	}

	/**
	 * Returns the change in circumference of the tank when deflated by a volume
	 * <code>volumeChange</code> (assuming tank is a perfect cylinder throughout
	 * deflation.)
	 * 
	 * @return Circumference change
	 */
	public static double changeCirc(double diameter, double volumeChange, double height) {
		return (Math.PI * diameter)
				- Math.sqrt(((Math.PI * diameter) * (Math.PI * diameter)) - ((4 * Math.PI * volumeChange) / height));
	}

	public static float toMoleRate(float startp, float startv, float endp, float endv) {
		// all pressures given in atm, all volumes given in L
		float startmoles = (float) ((startp * startv) / (0.0821 * 298)); // Pressure * Volume = #moles * Gas constant *
																			// Temp(K, room temp)
		float endmoles = (float) ((endp * endv) / (0.0821 * 298));
		float difmoles = endmoles - startmoles;
		return (float) (difmoles / .1); // X moles per second
	}

	public static ArrayList getIBW(double height, String sex, int range) {
		// Target tidal volume ranges from 6 to 8 mL/kg IBW
		// go to https://mpog.org/files/quality/toolkit/ibw_tv_chart1.pdf for reference
		// to see tidal volume ranges
		if (sex.equalsIgnoreCase("female")) {
			// IBW female = 45.5kg + 2.3 x (Height in inches – 60)
			double IBW = 45.5 + 2.3 * (height - 60); // in kg
			double minTDV = IBW * 6;
			double maxTDV = IBW * 8;
			ArrayList IBWs = new ArrayList();
			IBWs.add(minTDV);
			IBWs.add(maxTDV);
			return IBWs;
			
		} else {
			// IBW male = 50kg + 2.3 x (Height in inches – 60)
			double IBW = 50 + 2.3 * (height - 60);
			double minTDV = IBW * 6;
			double maxTDV = IBW * 8;
			ArrayList IBWs = new ArrayList();
			IBWs.add(minTDV);
			IBWs.add(maxTDV);
			return IBWs;
		}
	}

	public static double getETTposition(double height) {
		// ETT depth from front teeth (cm) (Chula formula) = 0.1 x Height in cm + 4
		double ETT = 0.1 * height + 4;
		return ETT;
	}

}
