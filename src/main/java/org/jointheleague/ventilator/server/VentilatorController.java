package org.jointheleague.ventilator.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.ConcurrentModificationException;
import javax.swing.Timer;
import java.awt.event.*;

public class VentilatorController extends WebSocketServer implements ActionListener {
	private int connNum;
	private ArrayList<SavedMessage> updates; // Requests that must continuously update
	private ArrayList<Client> clients;

	private Timer updateTimer;
	private final int port;

	/**
	 * Starts a new VentilatorController on a specific port.
	 * @param port Port number
	 */
	public VentilatorController(int port) {
		super(new InetSocketAddress(port));
		setReuseAddr(true);

		this.port = port;
		connNum = 0;
		updates = new ArrayList<>();
		clients = new ArrayList<>();

		updateTimer = new Timer(2000, this); // TODO change to 500 (2000 is for testing)
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connNum++;

		// Logs connection
		System.out.println("New connection: client "+connNum+"\n");
		clients.add(new Client(conn, connNum)); // Adds to list of connections

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
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Client "+(clients.indexOf(conn)+1)+" disconnected");
		System.out.println("Reason:\n"+reason+"End\n");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		onMessage(conn, message, true);
	}

	public void onMessage(WebSocket conn, String message, boolean fresh) {
		JSONParser parser = new JSONParser();
		
		try {
			Client c = null;
			for (Client c1 : clients) {
				if (c1.isWebSocket(conn)) {
					c = c1;
					break;
				}
			}

			handleMessage(message, c, fresh);
		} catch (ProtocolException e) {
			System.err.println("Error occurred handling message: "+e.getMessage()+"\n");

			JSONObject response = new JSONObject();
			response.put("status", e.statusCode);
			response.put("timestamp", System.currentTimeMillis() / 1000L);
			response.put("note", e.getMessage());

			conn.send(response.toString());

			System.out.println("Responded to message with HTTP "+e.statusCode+":");
			System.out.println(response.toString());
			System.out.println("End\n");
		} catch (WebsocketNotConnectedException e) {
			System.out.println("Cannot send message, client "+connNum+" disconnected");
		}
	}

	/**
	 * Handles a websocket message from the client (assumes it is a new message).
	 * @param message Message
	 * @param client Client
	 * @throws ParseException
	 */
	public void handleMessage(String message, Client client) throws ProtocolException {
		handleMessage(message, client, true);
	}

	/**
	 * Handles a websocket message from the client.
	 * @param message Message
	 * @param client Client
	 * @param fresh if the message was just recieved, not a continuous update
	 * @throws ParseException
	 */
	public void handleMessage(String message, Client client, boolean fresh) throws ProtocolException {
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
		if (fresh) System.out.println("Logging message "+(reqnum+1)+" of connection "+client.getNum()+":\n"+message+"\nEnd\n");

		// Tests "type" property
		String type;
		try {
			type = (String) msg.get("type");
		} catch (NullPointerException e) {
			throw new ProtocolException("Message has no \"type\" property", 400);
		} catch (ClassCastException e) {
			throw new ProtocolException("Message \"type\" property is not a string", 400);
		}

		Boolean cont;
		try {
			cont = (Boolean) ((JSONObject) msg.get("flags")).get("continuous");
		} catch (NullPointerException e) {
			cont = null;
		}


		if (fresh // If has not been added already
			&& (cont == null || cont == true) // And continuous flag enabled
			) {
			updates.add(new SavedMessage(client, msg)); // Add to list of updating requests
		}

		// TODO make this handle other message types
		switch (type) {
			case "getAll":
			{
			VentilatorService.vs_getAll(msg, client, reqnum);
			} break;

			case "getHumidity":
			{
			VentilatorService.vs_getHumidity(msg, client, reqnum);
			} break;

			case "getPressure":
			{
			VentilatorService.vs_getPressure(msg, client, reqnum);
			} break;

			case "getTemp":
			{
			VentilatorService.vs_getTemp(msg, client, reqnum);
			} break;

			default:
			throw new ProtocolException("Unknown \"type\" property", 400);
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("Unhandlable error:");
		ex.printStackTrace();
		System.err.println("End\n");
	}

	@Override
	public void onStart() {
		System.out.println("Server opened on port "+port);
		System.out.println("This may not be the port you connect to\n");

		// Start Timer for updates
		updateTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		ArrayList<SavedMessage> toDelete = new ArrayList<>();
		for (SavedMessage update : updates) {
			if (update.isConnected()) { // If update is still relevant
				update.log();
				update.runMessage(this);
			} else {
				toDelete.add(update); // Add update to list for deletion
			}
		}

		// Delete irrelevant updates, they have outlived their usefulness.
		// (And their WebSocket connections)
		for (SavedMessage update : toDelete) {
			updates.remove(update);
		}
	}
}

class ProtocolException extends Exception {
	public final int statusCode; // HTTP status code

	/**
	 * Initializes a ProtocolException.
	 * @param errorMessage Error message
	 * @param statusCode HTTP status code
	 */
	public ProtocolException(String errorMessage, int statusCode) {
		super(errorMessage);
		this.statusCode = statusCode;
	}

	/**
	 * Returns the HTTP status code.
	 * @return Code
	 */
	public int getStatus() {
		return statusCode;
	}
}