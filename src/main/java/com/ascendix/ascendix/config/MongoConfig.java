package com.ascendix.ascendix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.ascendix.ascendix.repository.OrderRepository;
import com.ascendix.ascendix.repository.ProductRepository;

@EnableMongoRepositories(basePackageClasses = {OrderRepository.class, ProductRepository.class})
@Configuration
public class MongoConfig {
}
