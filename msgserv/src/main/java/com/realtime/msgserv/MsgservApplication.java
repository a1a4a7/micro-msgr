package com.realtime.msgserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsgservApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgservApplication.class, args);
	}

}
