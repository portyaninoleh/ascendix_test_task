package com.ascendix.ascendix.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ascendix.ascendix.model.Product;

public interface ProductRepository extends MongoRepository<Product, Integer>{

}
