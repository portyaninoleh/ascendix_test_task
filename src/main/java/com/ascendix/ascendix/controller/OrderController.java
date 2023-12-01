package com.ascendix.ascendix.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ascendix.ascendix.dtos.OrderDTO;
import com.ascendix.ascendix.service.OrderService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<Boolean> createOrder(@RequestBody OrderDTO orderDTO) {
		System.out.println("Create order controller");
		orderService.createOrder(orderDTO);
		return ok(true);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderId") String orderId) {
		return ok(orderService.getOrderById(orderId));
	}
	
	@PutMapping
	public ResponseEntity<Boolean> updateOrder(@RequestBody OrderDTO orderDTO) {
		orderService.updateOrder(orderDTO);
		return ok(true);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Boolean> deleteProduct(@PathParam("orderId") String orderId) {
		orderService.deleteOrderById(orderId);
		return ok(true);
	}

	@GetMapping("/all")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return ok(orderService.getAllOrders());
	}
}
