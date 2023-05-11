package ru.clevertec.nms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NmsApplication.class, args);
	}
}
