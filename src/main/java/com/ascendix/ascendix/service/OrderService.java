package com.ascendix.ascendix.service;

import java.util.List;

import com.ascendix.ascendix.dtos.OrderDTO;

public interface OrderService {

	void createOrder(OrderDTO orderDTO);
	OrderDTO getOrderById(String orderId);
	void updateOrder(OrderDTO orderDTO);
	void deleteOrderById(String orderId);
	List<OrderDTO> getAllOrders();
}
