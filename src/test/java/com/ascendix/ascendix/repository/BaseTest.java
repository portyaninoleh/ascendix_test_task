package com.ascendix.ascendix.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {

	static MongoDBContainer mongodb = new MongoDBContainer("mongo:latest").withExposedPorts(27017);
	static {
		mongodb.start();
	}

	@DynamicPropertySource
	static void mongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongodb::getReplicaSetUrl);
	}

}
