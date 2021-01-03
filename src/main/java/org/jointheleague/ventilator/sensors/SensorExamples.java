package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

public class SensorExamples {
	BME280Driver bm280;

	public SensorExamples() {
		bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
			bme280.open();
	}

	public static void main(String[] args) {
		try {
			SensorExamples se = new SensorExamples();
			System.out.println("humidity:"+se.readHumidity());
			System.out.println("pressure:"+se.readPressure());
			System.out.println("temperature:"+se.readTemperature());
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

	/**
	 * Gets value of pressure sensor.
	 * @return (Pressure in Pa)*256
	 * @throws IOException
	 */
	public float readPressure() throws IOException {
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
	
	/**
	 * Gets value of temperature sensor.
	 * @return (Temperature in °C)*100
	 * @throws IOException
	 */
	public float readTemperature() throws IOException {
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

	/**
	 * Gets value of humidity sensor.
	 * @return (Humidity in %RH)*1024 (e.g. relative humidity of 34% would be 34816)
	 * @throws IOException
	 */
	public float readHumidity() throws IOException {
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
