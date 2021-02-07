package org.jointheleague.ventilator.stepper;

public interface StepperInterface {
	void forward(int rate, int time);
	void backward(int rate, int time);
}
