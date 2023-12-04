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
import com.ascendix.ascendix.model.Product;

@SpringBootTest
@Testcontainers
public class ProductRepositoryTest extends BaseTest {

	@Autowired
	private ProductRepository productRepository;
	
	@AfterEach
	void tierDown() {
		productRepository.deleteAll();
	}

	@Test
	public void createProductTest() {
		Order order = new Order();
		Product product = new Product();
		product.setName("ProductName");
		product.setOrder(order);
		Product savedProduct = productRepository.save(product);
		MatcherAssert.assertThat(savedProduct, Matchers.notNullValue());
	}
	
	@Test
	public void readProductTest() {
		Order order = new Order();
		Product product = new Product();
		String productName = "ProductName";
		product.setName(productName);
		product.setOrder(order);
		productRepository.save(product);
		List<Product> productsList = productRepository.findAll();
		MatcherAssert.assertThat(productsList.size(), Matchers.equalTo(1));
		MatcherAssert.assertThat(productsList.get(0).getName(), Matchers.equalTo(productName));
	}
	
	@Test
	public void updateProductTest() {
		Order order = new Order();
		Product product = new Product();
		product.setName("ProductName");
		product.setOrder(order);
		Product savedProduct = productRepository.save(product);
		MatcherAssert.assertThat(savedProduct, Matchers.notNullValue());
		String productName = "Updated";
		savedProduct.setName(productName);
		Product updatedProduct = productRepository.save(savedProduct);
		MatcherAssert.assertThat(updatedProduct.getName(), Matchers.equalTo(productName));
	}
	
	@Test
	public void deleteProductTest() {
		Order order = new Order();
		Product product = new Product();
		product.setName("ProductName");
		product.setOrder(order);
		productRepository.save(product);
		List<Product> allProducts = productRepository.findAll();
		MatcherAssert.assertThat(allProducts.size(), Matchers.equalTo(1));
		productRepository.deleteAll();
		List<Product> deletedProductsList = productRepository.findAll();
		MatcherAssert.assertThat(deletedProductsList.size(), Matchers.equalTo(0));
	}
	
}
