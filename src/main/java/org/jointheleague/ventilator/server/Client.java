package org.jointheleague.ventilator.server;

import org.java_websocket.WebSocket;
import org.jointheleague.ventilator.PatientProfile;

public class Client {
    private final WebSocket conn; // The WebSocket connection
    private final int connNum; // Client's connection number

    private PatientProfile profile; // The client's patient

    /**
     * Creates a new Client object.
     * @param conn The WebSocket connection
     * @param connNum Client's connection number
     */
    public Client(WebSocket conn, int connNum) {
        this.conn = conn;
        this.connNum = connNum;
    }

    /**
     * Returns the client's WebSocket connection.
     * @return WebSocket
     */
    public WebSocket getWebSocket() {
        return this.conn;
    }

    /**
     * Returns whether the client's WebSocket connection matches this connection.
     * @param conn The connection to match
     * @return If it matches
     */
    public boolean isWebSocket(WebSocket conn) {
        return this.conn.equals(conn);
    }

    /**
     * Returns the client's connection number.
     * @return Connection number
     */
    public int getNum() {
        return connNum;
    }

    /**
     * Sets the client's patient settings.
     * @param profile Patient profile
     */
    public void setProfile(PatientProfile profile) {
        this.profile = profile;
    }

    /**
     * Gets the client's patient settings.
     * @return Patient profile
     */
    public PatientProfile getProfile() {
        return profile;
    }
}