package com.redhat.kubakc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KubakcApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubakcApplication.class, args);
	}

}

