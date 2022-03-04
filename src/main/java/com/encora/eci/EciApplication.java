package com.encora.eci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class })*/
public class EciApplication {

	public static void main(String[] args) {
		SpringApplication.run(EciApplication.class, args);
	}

}
