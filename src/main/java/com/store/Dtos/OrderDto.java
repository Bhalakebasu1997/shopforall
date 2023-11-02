package com.store.Dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.store.entities.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {
	
	private String orderId;
	private String orderStatus= "PENDING";
	private String paymentStatus ="NOTPAID";
	private int orderAmount;
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderedDate= new Date();
	private Date deliveredyDate;
	private UserDto user;
	private List<OrderItem> orderItems = new ArrayList<>();
	
}
