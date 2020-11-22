package screens;

import components.Component;
import ventilator.App;

public class Screen {
    public final int id;
    private App app;
    private Background bg;

    /**
     * Initializes a new Screen. If the screen name is not unique, throws an
     * `IllegalArgumentException`.
     * 
     * @param app The parent App
     * @param bg  Lambda that extends Background to draw the background with
     *            Processing
     */
    public Screen(App app, Background bg) {
        this.id = app.getNextID();
        this.app = app;
        this.bg = bg;
    }

    /**
     * Initializes a new Screen with no background. If the screen name is not
     * unique, throws an `IllegalArgumentException`.
     * 
     * @param app The parent App
     */
    public Screen(App app) throws IllegalArgumentException {
        this(app, a -> {
        });
    }

    /**
     * Get parent App.
     */
    public App getApp() {
        return app;
    }

    /**
     * Adds a component to the screen.
     * 
     * @param comp The component
     */
    public void addComponent(Component comp) {
        app.addComponent(comp);
    }

    public void drawBG() {
        bg.drawBackground(app);
    }
}