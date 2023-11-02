package com.store.services;

import com.store.Dtos.AddItemToCartRequest;
import com.store.Dtos.CartDto;

public interface CartService {
	
	// add items to cart:
	// case1 : carts for user is not available: we will create the cart and then add the item
	// case2 : cart available add the items to cart
	
	CartDto addItemtoCart(String userId, AddItemToCartRequest request);
	
	// remove item from cart
	void removeItemFromCart(String userId, int cartItem);
	
	
	// remove all items from cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);

}
