package org.jointheleague.ventilator.stepper;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Manages the stepper motor, see StepperInterface.
 */
public class StepperController implements StepperInterface {

	private final GpioController gpio = GpioFactory.getInstance();
	private final GpioPinDigitalInput pin07 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "Raspi pin 07",
			PinPullResistance.PULL_UP);//
	private final GpioPinDigitalOutput pin11 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "BCM 17", PinState.LOW);// Dir
	private final GpioPinDigitalOutput pin13 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "BCM 27", PinState.LOW);// Pul
	private final GpioPinDigitalOutput pin15 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "BCM 22", PinState.LOW);// En

	public void down(double rate, double time) {
		// TODO TBD converting breaths per min to steps per sec
		pin15.low();// EN invalid, motor under control
		{
			pin11.high();// DIR down
			for (int k = 0; k < rate * time; k++) { // rate*time = #steps in total
				try {
					System.out.println("DOWN");
					pin13.high();// step
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000));// rate = steps per second; 1/rate = seconds per step
					pin13.low();
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000)); //LOSSY??
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		} // 1500 steps = max inflation (TODO TBD factoring that in)
		pin15.high();

	}

	public void up(double rate, double time) {
		pin15.low();// EN invalid, motor under control
		{
			pin11.low();// DIR up
			for (int k = 0; k < rate * time; k++) {
				try {
					System.out.println("UP");
					pin13.high();// step
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000)); //LOSSY??
					pin13.low();
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		pin15.high();
		
	}

	@Override
	public void downStep() {
		pin15.low();// EN invalid, motor under control
		{
			pin11.high();// DIR down
			try {
				pin13.high();// step
				Thread.sleep(500);// arbitrary aka this method sucks
				pin13.low();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void upStep() {
		pin15.low();// EN invalid, motor under control
		{
			pin11.low();// DIR up
			try {
				pin13.high();// step
				Thread.sleep(500);// arbitrary aka this method sucks
				pin13.low();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
