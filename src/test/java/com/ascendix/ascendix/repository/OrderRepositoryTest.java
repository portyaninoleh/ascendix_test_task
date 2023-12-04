package com.ascendix.ascendix.repository;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ascendix.ascendix.model.Order;

@SpringBootTest
@Testcontainers
public class OrderRepositoryTest extends BaseTest {

	@AfterEach
	void tierDown() {
		orderRepository.deleteAll();
	}

	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void createOrderTest() {
		Order order = new Order();
		order.setName("testOrder");
		Order savedOrder = orderRepository.save(order);
		MatcherAssert.assertThat(savedOrder, Matchers.notNullValue());
	}

	@Test
	public void updateOrderTest() {
		Order order = new Order();
		order.setName("testOrder");
		Order savedOrder = orderRepository.save(order);
		String updatedName = "Updated";
		savedOrder.setName(updatedName);
		Order updatedOrder = orderRepository.save(order);
		MatcherAssert.assertThat(updatedOrder.getName(), Matchers.equalTo(updatedName));
	}		
	
	@Test
	public void readOrderTest() {
		Order order = new Order();
		String orderName = "testOrder";
		order.setName(orderName);
		orderRepository.save(order);
		List<Order> orders = orderRepository.findAll();
		MatcherAssert.assertThat(orders.size(), Matchers.equalTo(1));
		MatcherAssert.assertThat(orders.get(0).getName(), Matchers.equalTo(orderName));
	}

	@Test
	public void deleteOrderTest() {
		Order order = new Order();
		String orderName = "testOrder";
		order.setName(orderName);
		Order savedOrder = orderRepository.save(order);
		List<Order> orders = orderRepository.findAll();
		MatcherAssert.assertThat(orders.size(), Matchers.equalTo(1));
		orderRepository.delete(savedOrder);
		List<Order> emptyOrdersList = orderRepository.findAll();
		MatcherAssert.assertThat(emptyOrdersList.size(), Matchers.equalTo(0));
	}
}
