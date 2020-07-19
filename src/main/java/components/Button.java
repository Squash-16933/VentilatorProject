package components;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import ventilator.App;

public class Button extends Component {
    static final float DEF_BUTTON_WIDTH = 100;
    static final float DEF_BUTTON_HEIGHT = 50;

    private float x;
    private float y;
    private float width;
    private float height;
    private String text;

    /**
     * Creates a new Button.
     * 
     * @param app      The app to pass in
     * @param text     Text
     * @param x        X position
     * @param y        Y position
     * @param width    Button width
     * @param height   Button height
     * @param listener ActionListener when clicked
     */
    public Button(App app, String text, float x, float y, float width, float height, ActionListener listener) {
        super(app, app.currentState, listener);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a new Button.
     * 
     * @param app      The app to pass in
     * @param text     Text
     * @param x        X position
     * @param y        Y position
     * @param listener ActionListener when clicked
     */
    public Button(App app, String text, float x, float y, ActionListener listener) {
        this(app, text, x, y, DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT, listener);
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

    public void mousePressed(ActionEvent event, int mouseX, int mouseY) {
        if (checkBounds(mouseX, mouseY)) {
            listener.actionPerformed(event);
        }
    }
}