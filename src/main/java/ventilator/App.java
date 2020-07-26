package ventilator;

import processing.core.PApplet;
import java.util.ArrayList;

import components.Component;
import screens.Screen;

public class App extends PApplet {
	public final int MENU = 0;
	public final int CHART = 1;
	public final int ALARMS = 2;

	ArrayList<Screen> screens;
	ArrayList<Component> components;

	// The argument passed to main must match the class name

	protected int currentState = MENU;

	public App() {
		components = new ArrayList<>();
	}

	/**
	 * Returns the next screen ID to be assigned.
	 */
	public int getNextID() {
		return screens.size();
	}

	public void addScreen(Screen screen) {
		screens.add(screen);
	}

	public void updateState(int state) {
		currentState = state;
	}

	public void addComponent(Component comp) {
		components.add(comp);
	}

	// method used only for setting the size of the window
	public void settings() {
		size(1000, 800);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {

	}

	// identical use to draw in Prcessing IDE
	public void draw() {
		screens.get(currentState).drawBG();

		for (Component c : components) {
			if (c.getScreenID() == currentState)
				c.display();
		}
	}

	@Override
	public void mousePressed() {
		super.mousePressed();
		for (Component c : components) {
			if (c.getScreenID() == currentState) c.mousePressed(mouseX, mouseY);
		}
	}
}