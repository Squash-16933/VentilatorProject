package org.jointheleague.ventilator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
	public static final int LOCAL_PORT = 5001;
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
		VentilatorController e = new VentilatorController(LOCAL_PORT);
		e.start();
	}
}
