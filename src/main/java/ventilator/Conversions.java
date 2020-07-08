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
}
