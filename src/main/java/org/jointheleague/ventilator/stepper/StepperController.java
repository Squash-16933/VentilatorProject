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

	
	public void setPin15High() {
		while(1==1) {
			try {
			pin15.low();
			System.out.println("Set pin 15 low");
			Thread.sleep(500);
			pin15.high();
			System.out.println("Set pin 15 high");
			Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void forward(double rate, double time) {
		// TODO TBD converting breaths per min to steps per sec
		
		pin15.low();// EN invalid, motor under control
		
			pin11.high();// DIR forward
		System.out.println("forward");
		long nanos = (long)(1.0 / (rate * 2d) * 1000*1000);
		System.out.println("Sleep time:" + nanos);
			for (int k = 0; k < rate * time; k++) { // rate*time = #steps in total
					//System.out.println("FORWARD");
					pin13.high();// step
					nanoSleep(nanos);
					pin13.low();
					nanoSleep(nanos);
			}
		 // 1500 steps = max inflation (TODO TBD factoring that in)
		//pin15.high();

	}

	public void backward(double rate, double time) {
		pin15.low();// EN invalid, motor under control
		
			pin11.low();// DIR backward
		System.out.println((int) (1.0 / (rate * 2d) * 1000));
		System.out.println("backward");
			for (int k = 0; k < rate * time; k++) {
				try {
					//System.out.println("BACKWARD");
					pin13.high();// step
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000)); //LOSSY??
					pin13.low();
					Thread.sleep((int) (1.0 / (rate * 2d) * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		//pin15.high();
		
	}
	
	public void nanoSleep(long nanos){
		long start = System.nanoTime();
		long end = System.nanoTime();
		
		while(end-start<nanos){
			end = System.nanoTime();
		}
	}

	@Override
	public void forwardStep() {
		pin15.low();// EN invalid, motor under control
		{
			pin11.high();// DIR forward
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
	public void backwardStep() {
		pin15.low();// EN invalid, motor under control
		
			pin11.low();// DIR backward
			try {
				pin13.high();// step
				Thread.sleep(500);// arbitrary aka this method sucks
				pin13.low();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		
	}
	public void stop(){

		pin15.low();




	}
}
