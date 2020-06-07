package ventilator;
import processing.core.PApplet;
import screens.Menu;

public class App extends PApplet {
	public static final int MENU = 0;
	public static final int CHART = 1;
	public static final int ALARMS = 2;
    
	Menu menu = new Menu();
	
	// The argument passed to main must match the class name
	
	
	public static int currentState = MENU;
    // method used only for setting the size of the window
    public void settings(){
        size(1000,800);
    }

    // identical use to setup in Processing IDE except for size()
    public void setup(){
        
    }

    // identical use to draw in Prcessing IDE
    public void draw(){
    	if(currentState == MENU) {
    		menu.display(this);
    		update();
    	} else if (currentState == CHART) {
    		rect(0,0,width, height);
    		if(mousePressed) {
    			StepperController sc = new  StepperController();
    			sc.blink("forward");
    			
    		}
    	}
    }
    
    public void update() {
    	if(currentState == MENU) {
    		menu.update();
    	}
    }
    
    @Override
    public void mousePressed() {
    	super.mousePressed();
    	if(currentState == MENU) {
    		menu.onClick(mouseX, mouseY);
    	}
    }
    
}