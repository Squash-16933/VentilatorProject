package org.jointheleague.ventilator.stepper;

/**
 * Mock Stepper Controller for replacing stepper controller when testing, see StepperInterface.
 * @author keithgroves
 *
 */
public class MockStepperController implements StepperInterface {
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
	/**
	 * Placeholder method, doesn't do anything
	 */
	public void forwardStep() {

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

	@Override
	/**
	 * Placeholder method, doesn't do anything
	 */
	public void backwardStep() {

	}
}
