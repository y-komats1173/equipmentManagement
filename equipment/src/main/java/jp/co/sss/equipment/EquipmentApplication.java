package jp.co.sss.equipment;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class EquipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentApplication.class, args);
		System.out.println("システム起動");
		
		Random rand = new Random();
		
		int i = rand.nextInt(900000) + 100000;
		System.out.println(i);
	}

}
