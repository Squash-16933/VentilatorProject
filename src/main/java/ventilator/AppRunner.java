package ventilator;

import components.*;
import screens.Screen;

public class AppRunner {
    public static void main(String[] args) {
        App app = new App();

        Screen main = new Screen(app);
        app.addScreen(main);

        main.addComponent(new Button(main, "Go!", 0, 0));
    }
}