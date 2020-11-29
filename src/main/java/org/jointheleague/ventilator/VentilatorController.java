package org.jointheleague.ventilator;

import java.net.InetSocketAddress;
import java.text.ParseException;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.swing.Timer;

public class VentilatorController extends WebSocketServer {
	public static final boolean DEBUG = true;
	private int connNum;
	private Timer[] updates; // Requests that must continuously update

	VentilatorController(int port) {
		super(new InetSocketAddress(port));
		setReuseAddr(true);

		connNum = 0;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connNum++;

		// Logs connection
		System.out.println("New connection: connection "+connNum+"\n");

		// Creates a handshake response
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		conn.send(response.toString());

		// Logs
		System.out.println("Responded to connection with 200 OK\n");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		JSONParser parser = new JSONParser();
		
		try {
			// Parses message
			JSONObject msg = (JSONObject) parser.parse(message);

			// Logs message
			System.out.println("Logging message "+(((long) msg.get("request"))+1)+" of connection "+connNum+":\n"+message+"\nEnd\n");

			handleMessage(msg, conn);
		} catch (org.json.simple.parser.ParseException e) {
			// Logs stack trace
			e.printStackTrace();

			// Logs message
			System.out.println("Error parsing message\nLogging message from connection "+connNum+":\n"+message+"\nEnd\n");
			
			// Creates a response
			JSONObject response = new JSONObject();
			response.put("status", 400);
			response.put("timestamp", System.currentTimeMillis() / 1000L);
			
			// Sends response
			conn.send(response.toString());

			// Logs
			System.out.println("Responded to message with 400 Bad Request\n");
		} // TODO make this handle messages that don't conform to protocol
	      // Possibly custom exception?
	}

	/**
	 * Handles a message as a JSONObject.
	 * @param msg Message
	 * @param conn WebSocket connection
	 */
	public void handleMessage(JSONObject msg, WebSocket conn) {
		// TODO make this method do more than just 200 OK

		// Creates a response
		JSONObject response = new JSONObject();

		response.put("request", (long) msg.get("request"));
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);
		
		// Sends response
		conn.send(response.toString());

		// Logs
		System.out.println("Responded to message with 200 OK\n");
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("Unhandlable error\nLogging error:");
		ex.printStackTrace();
		System.err.println("End\n");
	}

	@Override
	public void onStart() {
		System.out.println("Server started\n");
	}

}
