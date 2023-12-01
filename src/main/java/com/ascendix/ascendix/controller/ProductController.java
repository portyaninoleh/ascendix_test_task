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

import com.ascendix.ascendix.dtos.ProductDTO;
import com.ascendix.ascendix.service.ProductService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") String productId) {
		return ok(productService.getProductById(productId));
	}
	
	@PostMapping
	public ResponseEntity<Boolean> createProduct(@RequestBody ProductDTO productDto) {
		productService.createProduct(productDto);
		return ok(true);
	}
	
	@PutMapping
	public ResponseEntity<Boolean> updateProduct(@RequestBody ProductDTO productDTO) {
		productService.updateProduct(productDTO);
		return ok(true);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Boolean> deleteProduct(@PathParam("productId") String productId) {
		productService.deleteProductById(productId);
		return ok(true);
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<List<ProductDTO>> getOrderProducts(@PathVariable("orderId") String orderId) {
		return ok(productService.getOrderProducts(orderId));
	}
}
