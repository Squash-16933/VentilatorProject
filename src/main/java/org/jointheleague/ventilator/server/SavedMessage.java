package org.jointheleague.ventilator.server;

import org.json.simple.JSONObject;

public class SavedMessage {
    private final Client client;      // Client
    private final JSONObject message; // Original message from client

    /**
     * Creates a new SavedMessage object.
     * @param client Client
     * @param message Message from client
     */
    public SavedMessage(Client client, JSONObject message) {
        this.client = client;
        this.message = message;
    }

    /**
     * Returns whether the client is still connected to the server, and therefore the SavedMessage is still needed.
     * @return If connected
     */
    public boolean isConnected() {
        return client.getWebSocket().isOpen();
    }

    /**
     * Logs that it is responding to the message.
     */
    public void log() {
        long reqnum = (long) message.get("request");
        System.out.println("Responding to continuous message "+(reqnum+1)+" of connection "+client.getNum()+":\n"+message+"\n<END>\n");
    }

    /**
     * Sends the saved message from a VentilatorController.
     * @param controller The VentilatorController
     */
    public void runMessage(VentilatorController controller) {
        controller.onMessage(client.getWebSocket(), message.toString(), false);
    }
}