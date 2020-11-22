package components;

public interface Listener {
    
    public default void mouseClicked(int mouseX, int mouseY) {}
    
    public default void mousePressed(int mouseX, int mouseY) {}
    
    public default void mouseReleased(int mouseX, int mouseY) {}

    public default void mouseEntered(int mouseX, int mouseY) {}
    
    public default void mouseExited(int mouseX, int mouseY) {}
    
    public default void mouseDragged(int mouseX, int mouseY) {}
    
    public default void mouseMoved(int mouseX, int mouseY) {}

}