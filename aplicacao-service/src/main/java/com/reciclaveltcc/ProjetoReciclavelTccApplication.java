package com.reciclaveltcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProjetoReciclavelTccApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoReciclavelTccApplication.class, args);
	}

}
