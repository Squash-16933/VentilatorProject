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
     * @param id  The Screen ID (must be unique)
     * @param app The parent App
     * @param bg  Lambda that extends Background to draw the background with
     *            Processing
     */
    public Screen(int id, App app, Background bg) throws IllegalArgumentException {
        this.id = id;
        this.app = app;
        this.bg = bg;

        if (app.isUnique(id))
            throw new IllegalArgumentException("Screen ID is not unique");
    }

    /**
     * Initializes a new Screen with no background. If the screen name is not
     * unique, throws an `IllegalArgumentException`.
     * 
     * @param id  The Screen ID (must be unique)
     * @param app The parent App
     */
    public Screen(int id, App app) throws IllegalArgumentException {
        this(id, app, a -> {
        });
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