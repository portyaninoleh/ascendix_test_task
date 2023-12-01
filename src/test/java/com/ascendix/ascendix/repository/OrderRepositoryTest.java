package com.ascendix.ascendix.repository;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import com.ascendix.ascendix.model.Order;

//@DataMongoTest
//@Testcontainers
//@ContextConfiguration(classes = MongoTestConfig.class)
//@ComponentScan(basePackages = {"com.ascendix.ascendix"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application_test.properties")
public class OrderRepositoryTest {

	@LocalServerPort
	private Integer port;
	
	static MongoDBContainer mongodb = new MongoDBContainer("mongo:latest");
	
	  @BeforeAll
	  static void beforeAll() {
	    mongodb.start();
	  }

	  @AfterAll
	  static void afterAll() {
	    mongodb.stop();
	  }

	  @DynamicPropertySource
	  static void configureProperties(DynamicPropertyRegistry registry) {
	    registry.add("spring.datasource.url", mongodb::getConnectionString);
	  }

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void createOrderTest() {
		Order order = new Order();
		order.setName("testOrder");
		orderRepository.save(order);
		List<Order> allOrders = mongoTemplate.findAll(Order.class);
		MatcherAssert.assertThat(allOrders.size(), Matchers.equalTo(1));
	}
}
