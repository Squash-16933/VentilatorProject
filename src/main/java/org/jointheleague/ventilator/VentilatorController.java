package org.jointheleague.ventilator;

import java.io.IOException;
import java.net.InetSocketAddress;
import javax.swing.Timer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jointheleague.ventilator.sensors.SensorExamples;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.ArrayList;
public class VentilatorController extends WebSocketServer {

	public static final boolean DEBUG = true;
	private int connNum;
	private ArrayList<JSONObject> updates; // Requests that must continuously update
								           // Contains a list of messages to refer to
								           // TODO Add functionality to continuously update

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
		System.out.println("Responded to connection with HTTP 200\n");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {}

	@Override
	public void onMessage(WebSocket conn, String message) {
		JSONParser parser = new JSONParser();
		
		try {
			handleMessage(message, conn);
		} catch (ProtocolException e) {
			System.err.println("Error occurred handling message:\n"+e.getMessage()+"\nEnd\n");

			JSONObject response = new JSONObject();
			response.put("status", 400);
			response.put("timestamp", System.currentTimeMillis() / 1000L);
			response.put("data", e.getMessage());
		}
	}

	/**
	 * Handles a websocket message from the client (assumes it is a new message).
	 * @param message Message
	 * @param conn WebSocket connection
	 * @throws ParseException
	 */
	public void handleMessage(String message, WebSocket conn) throws ProtocolException {
		handleMessage(message, conn, true);
	}

	/**
	 * Handles a websocket message from the client.
	 * @param message Message
	 * @param conn WebSocket connection
	 * @param fresh true if the message is new, false if it's just another update to the message
	 * @throws ParseException
	 */
	public void handleMessage(String message, WebSocket conn, boolean fresh) throws ProtocolException {
		JSONParser parser = new JSONParser();
		JSONObject msg;

		try {
			msg = (JSONObject) parser.parse(message);
		} catch (org.json.simple.parser.ParseException e) {
			throw new ProtocolException("Invalid JSON", 400);
		}


		// Tests "request" property
		long reqnum;
		try {
			reqnum = (long) msg.get("request");
		} catch (NullPointerException e) {
			throw new ProtocolException("Message has no \"request\" property", 400);
		} catch (ClassCastException e) {
			throw new ProtocolException("Message \"request\" property is not in the correct number format", 400);
		}

		// Logs message
		System.out.println("Logging message "+(reqnum+1)+" of connection "+connNum+":\n"+message+"\nEnd\n");

		// Tests "type" property
		String type;
		try {
			type = (String) msg.get("type");
		} catch (NullPointerException e) {
			throw new ProtocolException("Message has no \"type\" property", 400);
		} catch (ClassCastException e) {
			throw new ProtocolException("Message \"type\" property is not a string", 400);
		}

		// TODO make this handle other message types
		// TODO move this functionality to another class (VentilatorService)
		switch (type) {
			case "getAll":
			{
			if (fresh // If has not been added already
				&& ((Boolean) msg.get("continuous") == null || (Boolean) msg.get("continuous") == true) // And continuous flag enabled
				) {
				updates.add(msg); // Add to list of updating requests
			}

			// Creates a response
			JSONObject response = new JSONObject();
			response.put("request", reqnum);
			response.put("status", 200);
			response.put("timestamp", System.currentTimeMillis() / 1000L);
			
			JSONObject data = new JSONObject(); // "data" property of response

			try {
				data.put("humidity", SensorExamples.readHumidity());
				data.put("pressure", SensorExamples.readPressure());
				data.put("temperature", SensorExamples.readTemperature());
			} catch (IOException e) {
				throw new ProtocolException("Unable to read sensors", 500);
			}

			response.put("data", data); // Add "data" property to response

			// Sends response
			conn.send(response.toString());

			// Logs
			System.out.println("Responded to message with HTTP 200\n");
		}
			break;

			case "powerOn": // TODO make this turn power on
			{
			// Creates a response
			JSONObject response = new JSONObject();
			response.put("request", reqnum);
			response.put("status", 200);
			response.put("timestamp", System.currentTimeMillis() / 1000L);

			// Sends response
			conn.send(response.toString());

			// Logs
			System.out.println("Responded to message with HTTP 200\n");
			}
			break;

			default:
			throw new ProtocolException("Unknown \"type\" property", 400);
		}
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

class ProtocolException extends Exception {
	int statusCode; // HTTP status code
	public ProtocolException(String errorMessage, int status) {
		super(errorMessage);
	}

	/**
	 * Returns the HTTP status code.
	 * @return Code
	 */
	public int getStatus() {
		return statusCode;
	}
}