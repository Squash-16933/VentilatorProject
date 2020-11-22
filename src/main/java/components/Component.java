package components;

import screens.Screen;
import ventilator.App;

import java.awt.Color;

public abstract class Component {
    protected App app;
    protected Screen screen;

    /* Defaults */
    public static final Color DEF_PCOLOR = new Color(98, 0, 238); // Default primary color

    /**
     * Creates a new component.
     * 
     * @param app      The parent App
     * @param state    State to assign the Component to
     * @param listener Listener for when event happens
     */
    public Component(Screen screen, Listener listener) {
        this.app = screen.getApp();
        this.screen = screen;
    }

    /**
     * Returns the parent screen's ID.
     */
    public int getScreenID() {
        return screen.id;
    }

    /**
     * Gets contrast ratio between two colors.
     * 
     * @param c1 Color 1
     * @param c2 Color 2
     * @return The contrast ratio
     */
    public static float contrast(Color c1, Color c2) {
        float lum1 = luminance(c1);
        float lum2 = luminance(c2);

        float brightest = Math.max(lum1, lum2);
        float darkest = Math.min(lum1, lum2);

        return (brightest + 0.05F) / (darkest + 0.05F);
    }

    /**
     * Get luminance of a color.
     * 
     * @param c The color
     * @return The luminance
     */
    public static float luminance(Color c) {
        return 0.2126F * c.getRed() + 0.7152F * c.getGreen() + 0.0722F * c.getBlue();
    }

    /**
     * Displays the Component.
     */
    public abstract void display();

    /**
     * Runs when the mouse is pressed.
     * 
     * @param e MouseEvent
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    public abstract void mousePressed(int mouseX, int mouseY);
}