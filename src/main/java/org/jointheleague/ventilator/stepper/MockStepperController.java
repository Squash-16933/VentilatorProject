package org.jointheleague.ventilator.stepper;

/**
 * Mock Stepper Controller for replacing stepper controller when testing, see StepperInterface.
 * @author keithgroves
 *
 */
public class MockStepperController implements StepperInterface {
	
	public void setPin15High() {
		System.out.println("Set pin 15 high");
	}
	
	@Override
	public void forward(double rate, double time) {
		for (int k = 0; k < rate * time; k++) { // rate*time = #steps in total
			try {
				Thread.sleep((int) (1.0 / (rate * 2d) * 1000));// rate = steps per second; 1/rate = seconds per step
				Thread.sleep((int) (1.0 / (rate * 2d) * 1000));
				System.out.println("STEP FORWARD");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void backward(double rate, double time) {
		for (int k = 0; k < rate * time; k++) {
			try {
				Thread.sleep((int) (1.0 / (rate * 2d) * 1000));// rate = steps per second; 1/rate = seconds per step
				Thread.sleep((int) (1.0 / (rate * 2d) * 1000));
				System.out.println("STEP BACKWARD");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void forwardStep() {
		try {
			Thread.sleep(1000);
			System.out.println("STEP FORWARD");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // arbitrary aka this method sucks
	}
	
	public void backwardStep() {
		try {
			Thread.sleep(1000);
			System.out.println("STEP BACKWARD");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // arbitrary aka this method sucks
	}
	public void stop(){

	}
}
