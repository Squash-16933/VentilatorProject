package ventilator;

public interface StepperInterface {
	void forward(int rate, int time);
	
	void backward(int rate, int time);
}
