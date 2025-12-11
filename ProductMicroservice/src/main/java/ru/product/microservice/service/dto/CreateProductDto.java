package ru.product.microservice.service.dto;

import java.math.BigDecimal;

public class CreateProductDto {
	private String title;
	private BigDecimal price;
	private Integer quantity;

	public CreateProductDto(String title, BigDecimal price, Integer quantity) {
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}

	public CreateProductDto() {
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
