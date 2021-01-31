package org.jointheleague.ventilator.stepper;

public interface StepperInterface {
	void forward(double rate, double time);
	
	void backward(double rate, double time);
}
