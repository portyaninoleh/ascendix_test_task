package com.ascendix.ascendix.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascendix.ascendix.dtos.ProductDTO;
import com.ascendix.ascendix.model.Order;
import com.ascendix.ascendix.model.Product;
import com.ascendix.ascendix.repository.OrderRepository;
import com.ascendix.ascendix.repository.ProductRepository;
import com.ascendix.ascendix.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	//TODO: This also could be set from the properties file
	private final static int SIMULTANEOUS_THREADS_LIMIT = 100;
	private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(SIMULTANEOUS_THREADS_LIMIT);
	private ProductRepository productRepository;
	private OrderRepository orderRepository;
	
	public ProductServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public void createProduct(ProductDTO productDto) {
		Order order = Optional.ofNullable(productDto.getOrderId())
			.map(orderId -> getOrder(orderId))
			.orElseThrow(() -> new RuntimeException("Order ID is required"));
		//TODO: Here we could think about how to extract synchronized block to be able to reuse it for all 
		// cases
		synchronized (order) {
			Callable<Product> callable = new Callable<Product>() {

				@Override
				public Product call() throws Exception {
					Product product = new Product();
					product.setName(productDto.getName());
					product.setOrder(order);
					return productRepository.save(product);
				}
			};
			try {
				EXECUTOR_SERVICE.invokeAll(List.of(callable));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Order getOrder(String orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException(String.format("No order with id %s found", orderId)));
	}

	@Override
	public ProductDTO getProductById(String productId) {
		Product product = getProductByIdSafety(productId);
		//TODO: Here we could use ObjectMapper
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productId);
		productDTO.setName(product.getName());
		productDTO.setOrderId(product.getOrder().getId());
		return productDTO;
	}
	
	private Product getProductByIdSafety(String productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException(String.format("No product with id %d found", productId)));
	}

	@Override
	public void updateProduct(ProductDTO productDto) {
		Product product = getProductByIdSafety(productDto.getId());
		Order order = getOrder(product.getOrder().getId());
		//TODO: Here we could think about how to extract synchronized block to be able to reuse it for all 
		// cases
		synchronized (order) {
			Callable<Product> callable = new Callable<Product>() {

				@Override
				public Product call() throws Exception {
					product.setName(productDto.getName());
					productRepository.save(product);
					return product;
				}
			};
			try {
				EXECUTOR_SERVICE.invokeAll(List.of(callable));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteProductById(String productId) {
		Product product = getProductByIdSafety(productId);
		Order order = getOrder(product.getOrder().getId());
		synchronized (order) {
			Callable<Product> callable = new Callable<Product>() {

				@Override
				public Product call() throws Exception {
					productRepository.deleteById(productId);
					return product;
				}
			};
			try {
				EXECUTOR_SERVICE.invokeAll(List.of(callable));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ProductDTO> getOrderProducts(String orderId) {
		List<Product> orderProducts = productRepository.findByOrderId(orderId);
		return orderProducts.stream()
				.map(product -> new ProductDTO(product.getId(), product.getName(), product.getOrder().getId()))
				.collect(Collectors.toUnmodifiableList());
	}
}
