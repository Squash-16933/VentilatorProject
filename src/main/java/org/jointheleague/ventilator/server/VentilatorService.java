package org.jointheleague.ventilator.server;

import java.util.Random;

import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.sensors.SensorReader;
import org.jointheleague.ventilator.Launcher;
import org.json.simple.JSONObject;

/**
 * Processes client requests.
 */
public class VentilatorService {
	/**
	 * Responds to a getAll request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getAll(JSONObject message, Client client, BreathController breathCtrl, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONObject data = new JSONObject(); // "data" property of response
		JSONObject flags = new JSONObject();

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				flags.put("fabricated", true);

				data.put("humidity", r.nextInt(95232, 99328));
				data.put("pressure", r.nextInt(25600000, 26368000));
				data.put("temperature", r.nextInt(3610, 3720));
				data.put("time", Launcher.getTime());
				data.put("resp", r.nextInt(12, 20));
				data.put("ie", r.nextDouble(0.25, 1));
			} else {
				SensorReader sr = new SensorReader();
				flags.put("fabricated", false);

				float hum = sr.readHumidity();
				float pre = sr.readPressure();
				float tem = sr.readTemperature();
				double res = Double.NaN;
				double ier = Double.NaN;
				int tim = Launcher.getTime();

				// If patient profile initialized, add these
				try {
					res = breathCtrl.getRespRateAnyTime();
					ier = breathCtrl.getSettings().getIeRatio();
				} catch (NullPointerException e) {
					if (breathCtrl.getSettings() == null) {
						throw new ProtocolException("No settings found, client must setProfile", 400);
					}
				}

				if (hum == 0 || pre == 0 || tem == 0) { // If getting error values
					throw new Exception();
				} else {
					data.put("humidity", hum);
					data.put("pressure", pre);
					data.put("temperature", tem);
					data.put("resp", res);
					data.put("ie", ier);
					data.put("time", tim);
				}
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}

		response.put("data", data); // Add "data" property to response
		response.put("timestamp", System.currentTimeMillis() / 1000L);
		response.put("flags", flags);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
	}

	/**
	 * Responds to a getHumidity request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getHumidity(JSONObject message, Client client, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONObject flags = new JSONObject();

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(1048576));
				flags.put("fabricated", true);
			} else {
				SensorReader sr = new SensorReader();
				float hum = sr.readHumidity();

				flags.put("fabricated", false);

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
		response.put("flags", flags);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
	}

	/**
	 * Responds to a getPressure request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getPressure(JSONObject message, Client client, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONObject flags = new JSONObject();

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(1048576));
				flags.put("fabricated", true);
			} else {
				SensorReader sr = new SensorReader();
				float pre = sr.readPressure();

				if (pre == 0) { // If getting error values
					throw new Exception();
				} else {
					response.put("data", pre);
				}

				flags.put("fabricated", false);
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);
		response.put("flags", flags);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
	}

	/**
	 * Responds to a getTemp request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getTemp(JSONObject message, Client client, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONObject flags = new JSONObject();

		try {
			if (!Launcher.CONNECTED_VENTILATOR) {
				// Generate random replacement for sensor
				Random r = new Random();
				response.put("data", r.nextInt(65536));

				flags.put("fabricated", true);
			} else {
				SensorReader sr = new SensorReader();
				float tem = sr.readTemperature();

				flags.put("fabricated", false);

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
		response.put("flags", flags);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");
	}

	/**
	 * Responds to a setProfile request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_setProfile(JSONObject message, Client client, BreathController breathCtrl, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		JSONObject data = (JSONObject) message.get("data");

		// Set client patient settings
		try {
			breathCtrl.init(new PatientProfile(Integer.valueOf(data.get("age").toString()),
					Double.valueOf(data.get("height").toString()), Double.valueOf(data.get("weight").toString()),
					(String) data.get("gender"), (String) data.get("disease")));
		} catch (NullPointerException e) {
			// Find source of exception
			if (breathCtrl == null)
				throw new ProtocolException("breathCtrl is null on server side", 500);
			if (data == null)
				throw new ProtocolException("Must include \"data\" in request", 400);
			if (data.get("age") == null)
				throw new ProtocolException("Must include \"data\".\"age\" in request", 400);
			if (data.get("height") == null)
				throw new ProtocolException("Must include \"data\".\"height\" in request", 400);
			if (data.get("weight") == null)
				throw new ProtocolException("Must include \"data\".\"weight\" in request", 400);
			if (data.get("gender") == null)
				throw new ProtocolException("Must include \"data\".\"gender\" in request", 400);
			if (data.get("disease") == null)
				breathCtrl.init(new PatientProfile((int) data.get("age"), (double) data.get("height"),
						(double) data.get("weight"), (String) data.get("gender")));
		}

		response.put("timestamp", System.currentTimeMillis() / 1000L);

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("<END>\n");

		System.out.println("respRate: " + breathCtrl.getRespRateAnyTime());
	}

	/**
	 * Responds to a getResp request message.
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getResp(JSONObject message, Client client, BreathController breathCtrl, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		if (Launcher.CONNECTED_VENTILATOR) {

			try {
				response.put("data", breathCtrl.getRespRateAnyTime());
			} catch (NullPointerException e) {
				if (breathCtrl.getSettings() == null) {
					throw new ProtocolException("No settings found, client must setProfile", 400);
				}
			}
		} else {
			response.put("data", new Random().nextInt(12, 20));

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
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getIE(JSONObject message, Client client, BreathController breathCtrl, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
		// Initializes a response
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);

		if (Launcher.CONNECTED_VENTILATOR) {

			try {
				response.put("data", breathCtrl.getSettings().getIeRatio());
			} catch (NullPointerException e) {
				if (breathCtrl.getSettings() == null) {
					throw new ProtocolException("No settings found, client must setProfile", 400);
				}
			}
		} else {
			response.put("data", new Random().nextDouble(0.25, 1));
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
	 * 
	 * @param message Message
	 * @param client  Client
	 * @param reqnum  Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
	public static void vs_getTime(JSONObject message, Client client, long reqnum)
			throws ProtocolException, WebsocketNotConnectedException {
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
}