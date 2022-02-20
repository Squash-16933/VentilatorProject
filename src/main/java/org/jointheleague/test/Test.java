package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperInterface;
import org.jointheleague.ventilator.stepper.StepperController;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.PositionCheck;
import org.jointheleague.ventilator.sensors.SensorReader;
import sun.misc.Signal;

/**
 * Tests the stepper controller, moves motor back and forth.
 */

// TODO add javadocs
public class Test {

	public static double startTime = System.currentTimeMillis();

	@SuppressWarnings("restriction")
	public Test(String... args) {

		System.out.println("running simple tests v20220220C");
		// setPin();
		if (args.length > 0) {
			System.out.println("Running ventilator at " + args[0] + " steps per second for " + args[1] + " seconds.");
			simpleStepperTest(args);
		} else {
			// simpleStepperTest();
			lidarTest();
			// pressureTest();
		}

	}

	private void setPin() {
		PositionCheck pc = new PositionCheck(new SensorReader());
		StepperInterface sc = new StepperController();
		sc.setPin15High();
	}

	private void pressureTest() {
		SensorReader sr = new SensorReader();
		while (1 == 1) {
			System.out.println("PRESSURE: " + sr.readPressure());
		}
	}

	private void lidarTest() {
		SensorReader sr = new SensorReader();
		while (1 == 1) {
			System.out.println("LIDAR: " + sr.readLidar());
		}
	}

	private void comprehensiveStepperTest() {
		// TODO Auto-generated method stub
		PositionCheck pc = new PositionCheck(new SensorReader());
		PatientProfile testP = new PatientProfile(16, (double) 64, (double) 120, (double) 20.6, "female", "COVID-19");
		BreathController bc = new BreathController(testP);
		StepperInterface sc = bc.getStepperController();
		pc.moveToTop(sc);
		double[] lidarNumbers = new double[10];
		Timer t = new Timer();
		TimerTask tt = new TimerTask2(lidarNumbers);
		t.scheduleAtFixedRate(tt, new Date(System.currentTimeMillis()), 500);
		while (1 == 1) {
			bc.breathe();
			// use calculateBreathRate() here whenever you need to display or use it?
			// System.out.println("RESP RATE: " + bc.calculateRespRate(((TimerTask2)
			// tt).getLidarVals()));
			((TimerTask2) tt).getLidarVals();

		}
	}

	void simpleStepperTest() {

		// PositionCheck pc = new PositionCheck(new SensorReader());
		StepperInterface sc = new StepperController();
		// pc.moveToTop(sc);

		Signal.handle(new Signal("INT"), // SIGINT
				signal -> {
					System.out.println("ctrl c pressed");
					sc.stop();
					System.exit(0);
				});
		for (int i = 0; i < 5; i++) {
			System.out.println("running forward");
			sc.forward(600, 100);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("running backward");
			sc.backward(600, 10);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sc.stop();
		System.out.println("stopped ventilator");
	}

	void simpleStepperTest(String[] args) {
		String direction = args[0];
		int rate = Integer.parseInt(args[1]);
		int time = Integer.parseInt(args[2]);
		StepperInterface sc = new StepperController();

		Runnable r = () -> lidarTest();

		Thread t = new Thread(r);
		t.start();

		Signal.handle(new Signal("INT"), // SIGINT
				signal -> {
					System.out.println("ctrl c pressed");
					sc.stop();
					System.exit(0);
				});
		if (direction.equals("forward")) {
			for (;;) {
				// down first
				sc.forward(rate, time);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sc.backward(rate, time);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		} else {
			for (;;) {
				// up first
				sc.backward(rate, time);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sc.forward(rate, time);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
	}
}

class TimerTask2 extends TimerTask {
	double[] lidNums;
	SensorReader sr = new SensorReader();

	public TimerTask2(double[] ln) {
		lidNums = new double[ln.length];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 1; i < lidNums.length; i++) {
			lidNums[i - 1] = lidNums[i];
		}
		lidNums[lidNums.length - 1] = sr.readLidar();
		System.out.println("READING LIDAR: " + lidNums[lidNums.length - 1]);
	}

	public double[] getLidarVals() {
		return lidNums;
	}
	// mvn command mvn spring-boot:run -Dspring-boot.run.arguments="forward 500 200"

}
