package com.nhnacademy.miniDooray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NhnAcademyMiniDoorayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NhnAcademyMiniDoorayApplication.class, args);
	}

}
