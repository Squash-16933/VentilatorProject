package org.jointheleague.ventilator.server;

import org.java_websocket.WebSocket;
import org.json.simple.JSONObject;

public class SavedMessage {
    private final WebSocket conn;     // Original connection from client
    private final JSONObject message; // Original message from client
    private final int connNum;        // Connection number of the client

    /**
     * Creates a new SavedMessage object.
     * @param conn Original connection from client
     * @param message Original message from client
     * @param connNum Connection number of the client
     */
    public SavedMessage(WebSocket conn, JSONObject message, int connNum) {
        this.conn = conn;
        this.message = message;
        this.connNum = connNum;
    }

    /**
     * Returns whether or not the client is still connected to the server, and therefore the SavedMessage is still needed.
     * @return If connected
     */
    public boolean isRelevant() {
        return conn.isOpen();
    }

    /**
     * Logs that it is responding to the message.
     */
    public void log() {
        long reqnum = (long) message.get("request");
        System.out.println("Responding to continuous message "+(reqnum+1)+" of connection "+connNum+":\n"+message+"\nEnd\n");
        System.out.println("Is "+conn.isClosed());
    }

    /**
     * Sends the saved message from a VentilatorController.
     * @param controller The VentilatorController
     */
    public void runMessage(VentilatorController controller) {
        controller.onMessage(conn, message.toString(), false);
    }
}