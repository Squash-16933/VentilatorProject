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
	ArrayList<Integer> screenIDs;
	ArrayList<Component> components;

	// The argument passed to main must match the class name

	public int currentState = MENU;

	public App() {
		components = new ArrayList<>();
		screens.add(new Screen(0, this));
		screens.add(new Screen(1, this));
	}

	/**
	 * Checks if a screen ID is unique. If so, adds to the list of screen IDs and
	 * returns `true`. If not, returns `false`.
	 * 
	 * @param id Screen ID
	 */
	public boolean isUnique(int id) {
		if (screenIDs.contains(id)) {
			return false;
		} else {
			screenIDs.add(id);
			return true;
		}
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
			if (c.currentState == currentState)
				c.display();
		}
	}

	@Override
	public void mousePressed() {
		super.mousePressed();
		for (Component c : components) {
			if (c.currentState == currentState) {} // TODO Trigger action listener
		}
	}

}