package com.ascendix.ascendix.dtos;

public class OrderDTO {
	private String id;
	private String name;
	
	public OrderDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public OrderDTO() {
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
}
