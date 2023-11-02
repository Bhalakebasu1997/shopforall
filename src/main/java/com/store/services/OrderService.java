package com.store.services;

import java.util.List;

import com.store.Dtos.CreateOrderRequest;
import com.store.Dtos.OrderDto;
import com.store.Dtos.OrderUpdateRequest;
import com.store.Dtos.PageableResponse;

public interface OrderService {

	// create Order 
	OrderDto createOrder(CreateOrderRequest orderDto);
	
	// remove the order
	void removeOrder(String orderId);
	
	// get order by the user 
	List<OrderDto> getOrderOfUser(String userId);
	
	// get orders
	PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	OrderDto updateOrder(String orderId, OrderUpdateRequest request);
	OrderDto updateOrder(String orderId, OrderDto request);
	
	// order methods(logic) related to order;
	
}
