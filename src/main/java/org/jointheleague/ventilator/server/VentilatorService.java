package org.jointheleague.ventilator.server;

import java.util.Random;

import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.sensors.SensorReader;
import org.jointheleague.ventilator.Launcher;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.json.JsonParser;

/**
 * Processes client requests.
 */
public class VentilatorService {
	/**
	 * Responds to a getAll request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getAll(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		
		JSONObject data = new JSONObject(); // "data" property of response

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				
				data.put("humidity", r.nextInt(1048576));
				data.put("pressure", r.nextInt(1048576));
				data.put("temperature", r.nextInt(65536));
			} else {
				SensorReader sr = new SensorReader();

				float hum = sr.readHumidity();
				float pre = sr.readPressure();
				float tem = sr.readTemperature();

				if (hum == 0 || pre == 0 || tem == 0) { // If getting error values
					throw new Exception();
				} else {
					data.put("humidity", hum);
					data.put("pressure", pre);
					data.put("temperature", tem);
				}
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}

		response.put("data", data); // Add "data" property to response
		
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getHumidity request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getHumidity(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(1048576));
			} else {
				SensorReader sr = new SensorReader();
				float hum = sr.readHumidity();

				if (hum == 0) { // If getting error values
					throw new Exception();
				} else {
					response.put("data", hum);
				}
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}
		
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getPressure request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getPressure(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(1048576));
			} else {
				SensorReader sr = new SensorReader();
				float pre = sr.readPressure();

				if (pre == 0) { // If getting error values
					throw new Exception();
				} else {
					response.put("data", pre);
				}
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}
		
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getTemp request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getTemp(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(65536));
			} else {
				SensorReader sr = new SensorReader();
				float tem = sr.readTemperature();

				if (tem == 0) { // If getting error values
					throw new Exception();
				} else {
					response.put("data", tem);
				}
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a setProfile request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_setProfile(JSONObject message, Client client, BreathController breathController, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONParser parser = new JSONParser();
		JSONObject data = (JSONObject) message.get("data");

		// Set client patient settings
		try {
			breathController.init(new PatientProfile((int) data.get("age"), (double) data.get("height"), (double) data.get("weight"), (String) data.get("gender"), (String) data.get("disease")));
		} catch (NullPointerException e) {
			// Find source of exception
			if (breathController == null) throw new ProtocolException("breathController is null on server side", 500);
			if (data == null) throw new ProtocolException("Must include \"data\" in request", 400);
			if (data.get("age") == null) throw new ProtocolException("Must include \"data\".\"age\" in request", 400);
			if (data.get("height") == null) throw new ProtocolException("Must include \"data\".\"height\" in request", 400);
			if (data.get("weight") == null) throw new ProtocolException("Must include \"data\".\"weight\" in request", 400);
			if (data.get("gender") == null) throw new ProtocolException("Must include \"data\".\"gender\" in request", 400);
			if (data.get("disease") == null) breathController.init(new PatientProfile((int) data.get("age"), (double) data.get("height"), (double) data.get("weight"), (String) data.get("gender")));
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getTime request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getTime(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		response.put("data", Launcher.getTime());
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getResp request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getResp(JSONObject message, Client client, BreathController breathController, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		try {
			response.put("data", breathController.getRespRateAnyTime());
		} catch (NullPointerException e) {
			if (breathController.getSettings() == null) {
				throw new ProtocolException("No settings found, client must setProfile", 400);
			}
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }

	/**
	 * Responds to a getIE request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getIE(JSONObject message, Client client, BreathController breathController, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		try {
			response.put("data", breathController.getSettings().getIeRatio());
		} catch (NullPointerException e) {
			if (breathController.getSettings() == null) {
				throw new ProtocolException("No settings found, client must setProfile", 400);
			}
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
    }
}