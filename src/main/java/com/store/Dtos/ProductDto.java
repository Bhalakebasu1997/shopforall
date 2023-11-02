package com.store.Dtos;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

	
	
	private String productId;
	private String title;
	@Column(length=10000)
	private String description;
	private int price;
	private int discoutedprice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	private String productImageName;
	
	
}
