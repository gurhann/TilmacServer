package com.kayra.tilmac.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class TilmacServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TilmacServerApplication.class, args);
	}
}
