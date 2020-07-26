package components;

import java.awt.Color;

import screens.Screen;

public class Button extends Component {
    static final float DEF_BUTTON_WIDTH = 100;
    static final float DEF_BUTTON_HEIGHT = 50;

    private float x;
    private float y;
    private float width;
    private float height;
    private String text;
    private Listener listener;

    /**
     * Creates a new Button.
     * 
     * @param app      The app to pass in
     * @param screen   State to assign the Component to
     * @param text     Text
     * @param x        X position
     * @param y        Y position
     * @param width    Button width
     * @param height   Button height
     * @param listener ActionListener when clicked
     */
    public Button(Screen screen, String text, float x, float y, float width, float height, Listener listener) {
        super(screen, listener);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.listener = listener;
    }

    /**
     * Creates a new Button.
     * 
     * @param app      The app to pass in
     * @param screen   State to assign the Component to
     * @param text     Text
     * @param x        X position
     * @param y        Y position
     * @param listener Listener for when clicked
     */
    // TODO change Listener to something from Java libraries, e.g. EventListener
    public Button(Screen screen, String text, float x, float y, Listener listener) {
        this(screen, text, x, y, DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT, listener);
    }

    public void display() {
        app.fill(DEF_PCOLOR.getRed(), DEF_PCOLOR.getGreen(), DEF_PCOLOR.getBlue());
        app.noStroke();

        app.rect(x, y, width, height, 20);

        app.fill(Component.contrast(DEF_PCOLOR, Color.WHITE) >= 4.5 ? 255 : 0);
        app.textSize(20);
        app.text(text, x + ((width - app.textWidth(text)) / 2), 230);
    }

    /**
     * Checks whether the mouse position is in bounds.
     * 
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     * 
     * @return If in bounds
     */
    public boolean checkBounds(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= mouseX + width && mouseY >= y && mouseY <= mouseY + height;
    }
    
    /**
     * Runs when the mouse is pressed, if out of bounds does not trigger Listener.
     * 
     * @param e MouseEvent
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    public void mousePressed(int mouseX, int mouseY) {
        if (checkBounds(mouseX, mouseY)) listener.mousePressed(mouseX, mouseY);
    }
}