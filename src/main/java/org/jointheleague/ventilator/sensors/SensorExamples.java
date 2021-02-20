package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

public class SensorExamples {
	static int previousDist = 0;
	static VL53L0XDevice sensor = null;
	public static void main(String[] args) {
		//print out lidar reading
		int previousDist = -1;
		readLidar();

		try {
			//prints out readPressure reading
			readPressure();
			readTemperature();
			readHumidity();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int readLidar() {
		// Using Lidar
		try {
			if(sensor == null) {
				sensor = new VL53L0XDevice(0x29, 30);
			}
			int mm = sensor.range();
			if (previousDist != mm) {
				System.out.println(String.format("Distance: %d mm", mm));        
				previousDist = mm;
				return mm;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	static float readPressure() throws IOException {
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
	
	static float readTemperature() throws IOException {
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
	
	static float readHumidity() throws IOException {
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
