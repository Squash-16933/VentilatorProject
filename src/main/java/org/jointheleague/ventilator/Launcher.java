package org.jointheleague.ventilator;

import org.jointheleague.test.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jointheleague.ventilator.server.VentilatorController;

@SpringBootApplication
public class Launcher {
	public static boolean RUN_TEST = true; // Flag for if test run of stepper motor
	public static boolean CONNECTED_VENTILATOR = true; // Flag for if connected to actual ventilator
	public static final int LOCAL_PORT = 5001; // Port to connect on
	private static long LAUNCH_TIME;

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);

		LAUNCH_TIME = System.currentTimeMillis();
		if (RUN_TEST) {
			System.out.println("running tests");
			Test test = new Test();
		} else {
			VentilatorController e = new VentilatorController(LOCAL_PORT);
			e.start();
		}
	}

	/**
	 * Gets amount of time since launched.
	 * @return Time (seconds)
	 */
	public static int getTime() {
		return (int) ((System.currentTimeMillis() - LAUNCH_TIME)/1000L);
	}
}
