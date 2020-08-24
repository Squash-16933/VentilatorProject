package org.jointheleague.ventilator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
		VentilatorController e = new VentilatorController(3001);
		e.start();
	}
}
