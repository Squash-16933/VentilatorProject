package org.jointheleague.ventilator;

import java.net.InetSocketAddress;
import java.text.ParseException;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class VentilatorController extends WebSocketServer {
	public static final boolean DEBUG = true;
	private int connNum;

	VentilatorController(int port) {
		super(new InetSocketAddress(port));
		setReuseAddr(true);

		connNum = 0;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connNum++;

		// Logs connection
		System.out.println("New connection: connection "+connNum);

		// Creates a handshake response
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		conn.send(response.toString());

		// Logs
		System.out.println("Responded with 200 OK");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		JSONParser parser = new JSONParser();
		
		try {
			// Parses message
			JSONObject jobj = (JSONObject) parser.parse(message);
			long reqnum = (long) jobj.get("request");

			// Logs message
			System.out.println("Logging message "+reqnum+" of connection "+connNum+":\n"+message+"\nEnd\n");

			// Creates a response
			JSONObject response = new JSONObject();
			response.put("request", reqnum);
			response.put("status", 200);
			
			// Sends response
			conn.send(response.toString());

			// Logs
			System.out.println("Responded with 200 OK");
		} catch (org.json.simple.parser.ParseException e) {
			// Logs stack trace
			e.printStackTrace();

			// Logs message
			System.out.println("Error parsing message\nLogging message from connection "+connNum+":\n"+message+"\nEnd\n");

			// Creates a response
			JSONObject response = new JSONObject();
			response.put("status", 400);
			
			// Sends response
			conn.send(response.toString());

			// Logs
			System.out.println("Responded with 400 Bad Request");
			System.exit(0); // End program TODO remove this line
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("err");
	}

	@Override
	public void onStart() {
		System.out.println("Start up");
	}

}
