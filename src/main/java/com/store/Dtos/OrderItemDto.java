package com.store.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {
	
	private String orderItemId;

	private int quantity;
	
	private int totalPrice;
	
	private ProductDto product;
	
}
