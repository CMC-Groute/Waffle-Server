package com.MakeUs.Waffle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WaffleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaffleApplication.class, args);
	}

}
