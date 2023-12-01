package com.ascendix.ascendix.service;

import java.util.List;

import com.ascendix.ascendix.dtos.ProductDTO;

public interface ProductService {

	void createProduct(ProductDTO productDto);
	ProductDTO getProductById(String productId);
	void updateProduct(ProductDTO productDto);
	void deleteProductById(String productId);
	List<ProductDTO> getOrderProducts(String orderId);
}
