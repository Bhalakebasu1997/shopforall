package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.Dtos.AddItemToCartRequest;
import com.store.Dtos.ApiResponseMessage;
import com.store.Dtos.CartDto;
import com.store.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartservice;
	
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){
		CartDto addItemtoCart = cartservice.addItemtoCart(userId, request);
		return new ResponseEntity<>(addItemtoCart, HttpStatus.OK);		
}
	
	@DeleteMapping("{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId){
		cartservice.removeItemFromCart(userId,itemId);
		ApiResponseMessage apiresponse =  ApiResponseMessage.builder().message("Item is removed !!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<>(apiresponse, HttpStatus.OK);
	}
	
	// clear chart
	@DeleteMapping("{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
		 cartservice.clearCart(userId);
		ApiResponseMessage responce = ApiResponseMessage.builder().message("Now cart is Black !!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<>(responce, HttpStatus.OK);
		
	}

	// add items to cart
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId){
		CartDto cartDto = cartservice.getCartByUser(userId);
		return new ResponseEntity<>(cartDto,  HttpStatus.OK);
		
	}
	
	

}
