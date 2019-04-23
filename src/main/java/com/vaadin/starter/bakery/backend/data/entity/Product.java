package com.vaadin.starter.bakery.backend.data.entity;

import com.vaadin.starter.bakery.backend.data.ProductStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Entity
public class Product extends AbstractEntity {

	@NotBlank(message = "{bakery.name.required}")
	@Size(max = 255)
	@Column(unique = true)
	private String name;

	// Real price * 100 as an int to avoid rounding errors
	@Min(value = 0, message = "{bakery.price.limits}")
	@Max(value = 100000, message = "{bakery.price.limits}")
	private Integer price;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{bakery.status.required}")
	private ProductStatus status = ProductStatus.IN_STOCK;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return name;
	}
}
