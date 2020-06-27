package com.dgusev.ab.task1;

import com.dgusev.ab.task1.client.AlphaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Task1Application{

	@Autowired
	private AlphaClient alphaClient;

	public static void main(String[] args) {
		SpringApplication.run(Task1Application.class, args);
	}

}
