package org.jointheleague.ventilator;

import org.jointheleague.test.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
	public static boolean RUN_TEST = true;
	public static final int LOCAL_PORT = 5001;
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
		if(RUN_TEST == false) {
			VentilatorController e = new VentilatorController(LOCAL_PORT);
			e.start();
		}else {
			Test test = new Test();
		}
	}
}
