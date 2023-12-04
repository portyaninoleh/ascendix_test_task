package com.ascendix.ascendix.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascendix.ascendix.dtos.OrderDTO;
import com.ascendix.ascendix.model.Order;
import com.ascendix.ascendix.model.Product;
import com.ascendix.ascendix.repository.OrderRepository;
import com.ascendix.ascendix.repository.ProductRepository;
import com.ascendix.ascendix.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void createOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setName(orderDTO.getName());
		orderRepository.save(order);
	}

	@Override
	public OrderDTO getOrderById(String orderId) {
		Order order = getOrderByIdSafety(orderId);
		return new OrderDTO(order.getId(), order.getName());
	}

	private Order getOrderByIdSafety(String orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException(String.format("No order with ID %d found", orderId)));
	}
	
	@Override
	public void updateOrder(OrderDTO orderDTO) {
		Order order = getOrderByIdSafety(orderDTO.getId());
		order.setName(orderDTO.getName());
		orderRepository.save(order);
	}

	@Override
	public void deleteOrderById(String orderId) {
		List<Product> orderProducts = productRepository.findByOrderId(orderId);
		productRepository.deleteAll(orderProducts);
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream()
			.map(order -> new OrderDTO(order.getId(), order.getName()))
			.collect(Collectors.toUnmodifiableList());
	}
	
	
}
