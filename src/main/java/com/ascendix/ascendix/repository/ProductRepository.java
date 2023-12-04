package com.ascendix.ascendix.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ascendix.ascendix.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByOrderId(String orderId);
	
}
