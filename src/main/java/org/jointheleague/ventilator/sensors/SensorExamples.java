package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

public class SensorExamples {
	public static void main(String[] args) {
		//print out lidar reading
		readLidar();

		try {
			//prints out readPressure reading
			readPressure();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int readLidar() {
		// Using Lidar
		VL53L0XDevice sensor = null;
		try {
			sensor = new VL53L0XDevice(0x29, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int previousDist = -1;
			while (sensor != null) {
				int mm = sensor.range();
				if (previousDist != mm) {
					System.out.println(String.format("Distance: %d mm", mm));
					return mm;
				}
				previousDist = mm;
				try {
					Thread.sleep(50L);
				} catch (InterruptedException iex) {
					System.out.println("exception");
				}
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
			System.out.println("Fun");
		}
		return 0;
	}

	static void readPressure() throws IOException {
		BME280Driver bme280 = null;
		try {
			bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
			bme280.open();
			while (true) {
				float[] values = bme280.getSensorValues();
				System.out.println("temperature:" + values[0]);
				System.out.println("humidity:" + values[1]);
				System.out.println("pressure:" + values[2]);
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
		} catch (IOException e) {
		} finally {
			if (bme280 != null) {
				bme280.close();
			}
		}
	}
}
