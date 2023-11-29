package com.ascendix.ascendix.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ascendix.ascendix.model.Order;

public interface OrderRepository extends MongoRepository<Order, Integer>{

}
