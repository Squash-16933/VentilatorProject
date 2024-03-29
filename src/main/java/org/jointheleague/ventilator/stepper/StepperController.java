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
	// http://wiringpi.com/pins/
	private final GpioController gpio = GpioFactory.getInstance();
	private final GpioPinDigitalInput pin07 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "Raspi pin 07", PinPullResistance.PULL_UP);//
	private final GpioPinDigitalOutput pin11 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "BCM 17", PinState.LOW);// Dir (low=up=up)
	private final GpioPinDigitalOutput pin13 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "BCM 27", PinState.LOW);// Pul
	private final GpioPinDigitalOutput pin15 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "BCM 22", PinState.LOW);// En (low=enabled)

	
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
	
	public void down(double rate, double time) {
		// TODO TBD converting breaths per min to steps per sec
		
		pin15.low();// EN invalid, motor under control
		
			pin11.high();// DIR down
		System.out.println("down");
		//long nanos = (long)(1.0 / (rate * 2d) * 1000*1000);
		long nanos = 100000;
		//System.out.println("Sleep time:" + nanos);
			for (int k = 0; k < rate * time; k++) { // rate*time = #steps in total
					//System.out.println("DOWN");
					pin13.high();// step
					nanoSleep(nanos);
					pin13.low();
					nanoSleep(nanos);
			}
		 // 1500 steps = max inflation (TODO TBD factoring that in)
		//pin15.high();

	}

	public void up(double rate, double time) {
		pin15.low();// EN invalid, motor under control
		
			pin11.low();// DIR up
		//long nanos = (long)(1.0 / (rate * 2d) * 1000*1000);
		long nanos = 100000;
		//System.out.println("Sleep time:" + nanos);
			for (int k = 0; k < rate * time; k++) { // rate*time = #steps in total
					//System.out.println("DOWN");
					pin13.high();// step
					nanoSleep(nanos);
					pin13.low();
					nanoSleep(nanos);
			}
		
		//pin15.high();
		
	}
	
	public void sendByte(byte byt) {
		long nanos = 100000;
		System.out.println("Sleep time:" + nanos);
		
		String bits = Integer.toBinaryString(byt);
		
		for (int k = 0; k < 8; k++) {
			pin13.low();
			nanoSleep(nanos);
			
			if (bits.charAt(k) == '1') pin13.high();
			nanoSleep(nanos);
		}
	}
	
	public void nanoSleep(long nanos){
		long start = System.nanoTime();
		long end = System.nanoTime();
		
		while(end-start<nanos){
			end = System.nanoTime();
		}
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
	public void stop(){

		pin15.high();
		System.out.println(pin15);
		System.out.println(pin15.getState());

		
		
		
		
		
		
		

	}
}
