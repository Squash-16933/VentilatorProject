package org.jointheleague.ventilator;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jointheleague.ventilator.sensors.pressure.*;
import org.jointheleague.ventilator.stepper.*;

import com.pi4j.io.i2c.I2CBus;

public class VentilatorController extends WebSocketServer {
	public static final boolean DEBUG = true;

	VentilatorController(int port) {
		super(new InetSocketAddress(port));
		setReuseAddr(true);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("new Connection");

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		if (message.equals("start")) {
			StepperInterface step = null;
			if (DEBUG) {
				step = new MockStepperController();
			} else {
				step = new StepperController();
			}
			step.forward(Conversions.convertBPMtoSPS(Conversions.getRespRate(18)), 2);
			conn.send("Hello from server");
		}
		if (message.equals("pressure")) {
			BME280Driver bme280 = null;
			try {
				bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
				bme280.open();
				while (true) {
					float[] values = bme280.getSensorValues();
					System.out.println("pressure:" + values[2]);
				}
			} catch (IOException e) {
			} finally {
				if (bme280 != null) {
					try {
						bme280.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
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
