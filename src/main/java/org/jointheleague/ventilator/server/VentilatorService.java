package org.jointheleague.ventilator.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.jointheleague.ventilator.sensors.SensorExamples;
import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;
import java.util.Random;

public class VentilatorService {
	private static final boolean MOCK_SENSORS = true; // if true, will generate random values
											           // instead of checking sensors

    public static void vs_getAll(JSONObject message, WebSocket conn, long reqnum) throws ProtocolException, WebsocketNotConnectedException {
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
				SensorExamples se = new SensorExamples();

				float hum = se.readHumidity();
				float pre = se.readPressure();
				float tem = se.readTemperature();

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
		conn.send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
    }
}