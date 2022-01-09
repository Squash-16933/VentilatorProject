package org.jointheleague.ventilator.stepper;

/**
 * Interface for stepper controllers, mock or otherwise.
 */
public interface StepperInterface {
	/**
	 * Moves screw downwards.
	 * @param rate Rate in steps per second
	 * @param time Time in seconds
	 */
	void down(double rate, double time);

	/**
	 * Moves screw upwards.
	 * @param rate Rate in steps per second
	 * @param time Time in seconds
	 */
	void up(double rate, double time);

	/**
	 * Moves screw downwards a single step.
	 */
	void downStep();

	/**
	 * Moves screw upwards a single step.
	 */
	void upStep();
}
