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
	void down(double rate, double time);

	/**
	 * TODO explain what does
	 * @param rate Rate in steps per second
	 * @param time Time in seconds
	 */
	void up(double rate, double time);

	// TODO add docs
	void downStep();

	// TODO add docs
	void upStep();
	
	void setPin15High();
	void stop();
}
