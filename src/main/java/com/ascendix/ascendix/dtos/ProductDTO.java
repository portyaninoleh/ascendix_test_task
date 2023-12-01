package com.ascendix.ascendix.dtos;

public class ProductDTO {

	private String id;
	private String name;
	private String orderId;
	
	public ProductDTO(String id, String name, String orderId) {
		this.id = id;
		this.name = name;
		this.orderId = orderId;
	}
	
	public ProductDTO() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
