package com.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.Dtos.ApiResponseMessage;
import com.store.Dtos.CreateOrderRequest;
import com.store.Dtos.OrderDto;
import com.store.Dtos.OrderUpdateRequest;
import com.store.Dtos.PageableResponse;
import com.store.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	
	@Autowired
	private OrderService orderservce;
	
	
	
	
	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request){
		OrderDto order = orderservce.createOrder(request);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
		}
	
	
	@DeleteMapping("/{orderId}")
		public ResponseEntity<ApiResponseMessage> deleteOrder(@PathVariable String orderId){
		orderservce.removeOrder(orderId);
		ApiResponseMessage apiresponse = ApiResponseMessage.builder().
		message("Order is remove")
		.status(HttpStatus.OK)
		.success(true).build();
		return new ResponseEntity<>(apiresponse, HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("user/{userId}")
	public ResponseEntity<List<OrderDto>> getUser(@PathVariable String userId){
		List<OrderDto> orderOfUser = orderservce.getOrderOfUser(userId);
		return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<PageableResponse> getAlluser(
			 @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
	            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
	            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir )
			{
		PageableResponse<OrderDto> pageableResponse = orderservce.getOrders(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);	
	}
	
	
	@PutMapping("{orderId})")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable String orderId, @RequestBody OrderUpdateRequest request){
		OrderDto updateOrder = orderservce.updateOrder(orderId, request);
		return ResponseEntity.ok(updateOrder);	
	}
	
}
