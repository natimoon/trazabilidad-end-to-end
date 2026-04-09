package com.aluracursos.ecomart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class EcomartApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcomartApplication.class, args);
	}
}