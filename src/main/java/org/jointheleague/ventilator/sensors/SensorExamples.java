package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

public class SensorExamples {
	public static void main(String[] args) {
		try {
			System.out.println("humidity:"+readHumidity());
			System.out.println("pressure:"+readPressure());
			System.out.println("temperature:"+readTemperature());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readLidar() {
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
	}

	public static float readPressure() throws IOException {
		BME280Driver bme280 = null;
		try {
			bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[2];
		} catch (IOException e) {
		} finally {
			if (bme280 != null) {
				bme280.close();
			}
		}
		return 0;
	}
	
	public static float readTemperature() throws IOException {
		BME280Driver bme280 = null;
		try {
			bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[0];
		} catch (IOException e) {
		} finally {
			if (bme280 != null) {
				bme280.close();
			}
		}
		return 0;
	}
	
	public static float readHumidity() throws IOException {
		BME280Driver bme280 = null;
		try {
			bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[1];
		} catch (IOException e) {
		} finally {
			if (bme280 != null) {
				bme280.close();
			}
		}
		return 0;
	}
}
