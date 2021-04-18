package org.jointheleague.ventilator.stepper;

/**
 * Interface for stepper controllers, mock or otherwise.
 */
public interface StepperInterface {
	/**
	 * TODO explain what does
	 * @param rate Rate in steps per second
	 * @param time Time in seconds
	 */
	void forward(double rate, double time);

	/**
	 * TODO explain what does
	 * @param rate Rate in steps per second
	 * @param time Time in seconds
	 */
	void backward(double rate, double time);

	// TODO add docs
	void forwardStep();

	// TODO add docs
	void backwardStep();
}
