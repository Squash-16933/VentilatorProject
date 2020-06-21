package ventilator;

public class MockStepperController extends StepperController{
@Override
	 public void forward(int rate, int time){
	   for (int k = 0; k < rate*time; k++) { // rate*time = #steps in total
           try {
               Thread.sleep((1/(rate*2))*1000);// rate = steps per second; 1/rate = seconds per step
               Thread.sleep((1/(rate*2))*1000);
               System.out.println("STEP FORWARD");
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           
       }
	
	}
@Override
	public void backward(int rate, int time) {
	 for (int k = 0; k < rate*time; k++) {
	try {
        Thread.sleep((1/(rate*2))*1000);// rate = steps per second; 1/rate = seconds per step
        Thread.sleep((1/(rate*2))*1000);
        System.out.println("STEP BACKWARD");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
	 }
}

}
