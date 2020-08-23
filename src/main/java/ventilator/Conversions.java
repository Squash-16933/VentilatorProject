package ventilator;

public class Conversions {
	
	/**
	 * NEMA 17-size hybrid bipolar stepping motor
	 * (for compressionTotal)
	 * 
	 * Full compression of ambu bag: ~ 4 in - 2 in = 2 in
	 * 1.6 mil : 1 step
	 * 200 steps : 1 revolution
	 * 320 mil : 1 revolution
	 * 320 mil = .32 in
	 * 1250 steps = 2 in
	 * <strong>1250 steps = 1 breath</strong>
	 * 
	 */
	
	public static int MAX_COMPRESSION_STEPS = 1250;
	
	public static int convertBPMtoSPS(int bpm) {
// (x brths)/(1 min) * (1 min)/(60 sec) = (x brths)/(1 sec) * (y steps)/(1 brth) = (y steps)/(1 sec)
		return (int)((bpm / 60f) * MAX_COMPRESSION_STEPS);
	}
	
	public static int getRespRate(int age) {
		// returns average breath rate for an age group given age of individual
		// source: https://www.healthline.com/health/normal-respiratory-rate#normal-rate-in-adults
		if(age<1) {
			return 45;
		}else if(age>=1 && age<3) {
			return 32;
		}else if(age>=3 && age<6) {
			return 28;
		}else if(age>=6 && age<12) {
			return 24;
		}else{
			return 14;
		}
	}
	public static double getIBW(double height, String sex, int range) {
		//Target tidal volume ranges from 6 to 8 mL/kg IBW
		//go to https://mpog.org/files/quality/toolkit/ibw_tv_chart1.pdf for reference to see tidal volume ranges
		if(sex.equalsIgnoreCase("female")) {
			//IBW female = 45.5kg + 2.3 x (Height in inches – 60)
			double IBW =  45.5 + 2.3 * (height - 60); //in kg
			double TDV = IBW * range; 
			return TDV;
		}
		else {
			//IBW male = 50kg + 2.3 x (Height in inches – 60)
			double IBW =  50 + 2.3 * (height -60);
			double TDV = IBW * range;
			return TDV;
		}
	}
	
	public static double getETTposition(double height) {
		//ETT depth from front teeth (cm) (Chula formula) = 0.1 x Height in cm + 4
		double ETT = 0.1 * height + 4;
		return ETT;
	}
	
}
