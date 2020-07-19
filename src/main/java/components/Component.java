package components;

import processing.core.PApplet;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import ventilator.App;

public abstract class Component {
    protected PApplet app;

    public final int currentState;
    protected ActionListener listener;

    /* Defaults */
    public static final Color DEF_PCOLOR = new Color(98, 0, 238); // Default primary color

    /**
     * Creates a new component.
     * 
     * @param app          The parent App
     * @param currentState State to assign the Component to
     * @param listener     ActionListener when event happens
     */
    public Component(App app, int currentState, ActionListener listener) {
        this.app = app;
        this.currentState = currentState;
        this.listener = listener;
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
     * Trigger this event whenever the mouse is pressed.
     * 
     * @param event  ActionEvent
     * @param mouseX Current mouse X
     * @param mouseY Current mouse Y
     */
    public abstract void mousePressed(ActionEvent event, int mouseX, int mouseY);
}