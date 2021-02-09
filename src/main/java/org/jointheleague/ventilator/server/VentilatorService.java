package org.jointheleague.ventilator.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.jointheleague.ventilator.sensors.SensorReader;
import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;
import java.util.Random;

public class VentilatorService {
	private static final boolean MOCK_SENSORS = true; // if true, will generate random values
											           // instead of checking sensors

	/**
	 * Responds to a getAll request message.
	 * @param message Message
	 * @param client Client
	 * @param reqnum Request number
	 * @throws ProtocolException
	 * @throws WebsocketNotConnectedException
	 */
    public static void vs_getAll(JSONObject message, Client client, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);
		
		JSONObject data = new JSONObject(); // "data" property of response

		try {
			if (MOCK_SENSORS) {
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

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
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
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		try {
			if (MOCK_SENSORS) {
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

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
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
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		try {
			if (MOCK_SENSORS) {
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

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
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
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);

		try {
			if (MOCK_SENSORS) {
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

		// Sends response
		client.getWebSocket().send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
    }
}