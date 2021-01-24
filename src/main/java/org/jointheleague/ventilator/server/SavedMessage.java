package org.jointheleague.ventilator.server;

import org.java_websocket.WebSocket;
import org.json.simple.JSONObject;

// TODO add docs
public class SavedMessage {
    private final WebSocket conn;
    private final JSONObject message;
    private final int connNum;

    public SavedMessage(WebSocket conn, JSONObject message, int connNum) {
        this.conn = conn;
        this.message = message;
        this.connNum = connNum;
    }

    public void log() {
        long reqnum = (long) message.get("request");
		System.out.println("Responding to continuous message "+(reqnum+1)+" of connection "+connNum+":\n"+message+"\nEnd\n");
    }

    public void runMessage(VentilatorController controller) {
        controller.onMessage(conn, message.toString(), false);
    }
}