package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

/**
 * Reads the sensors.
 */
public class SensorReader {
	BME280Driver bme280;
	static int previousDist = 0;
	static VL53L0XDevice sensor = null;

	/**
	 * Creates a SensorReader object.
	 */
	public SensorReader() {
		bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
		previousDist = 1;
		try {
			bme280.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints the sensor values.
	 */
	public void test() {
		System.out.println("lidar:       "+readLidar());
		System.out.println("humidity:    "+readHumidity());
		System.out.println("pressure:    "+readPressure());
		System.out.println("temperature: "+readTemperature());
	}

	/**
	 * Reads the LIDAR distance.
	 * Returns 0 if an error occurs.
	 * @return LIDAR distance (mm)
	 */
	public int readLidar() {
		// Using Lidar
		try {
			if(sensor == null) {
				sensor = new VL53L0XDevice(0x29, 30);
			}
			int mm = sensor.range();
			if (previousDist != mm) {
				previousDist = mm;
				return mm;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * Gets value of pressure sensor.
	 * Returns 0 if an error occurs.
	 * @return (Pressure in Pa)*256
	 */
	public float readPressure() {
		try {
			float[] values = bme280.getSensorValues();
			return values[2];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}
		return 0;
	}
	
	/**
	 * Gets value of temperature sensor.
	 * Returns 0 if an error occurs. 
	 * @return (Temperature in °C)*100
	 */
	public float readTemperature() {
		try {
			
			float[] values = bme280.getSensorValues();
			return values[0];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}
		return 0;
	}

	/**
	 * Gets value of humidity sensor.
	 * Returns 0 if an error occurs.
	 * @return (Humidity in %RH)*1024 (e.g. relative humidity of 34% would be 34816)
	 */
	public float readHumidity() {
		try {
			
			float[] values = bme280.getSensorValues();
			return values[1];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}

		return 0;
	}
}
