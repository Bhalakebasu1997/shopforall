package com.store.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dtos.AddItemToCartRequest;
import com.store.Dtos.CartDto;
import com.store.entities.Cart;
import com.store.entities.CartItem;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.exception.BadApiRequestExcepiton;
import com.store.exception.ResourceNotFoundException;
import com.store.repository.CartItemRepository;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import com.store.repository.UserRepository;
import com.store.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	
	@Override
	public CartDto addItemtoCart(String userId, AddItemToCartRequest request) {
		String productId = request.getProductId();
		int quantity = request.getQuantity();
		
		if(quantity <=0) {
			throw new BadApiRequestExcepiton("request quantity is not required");
		}
		
		Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database !!"));
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database !!"));
		
		Cart cart = null;
		try {
			cart = cartRepo.findByUser(user).get();
			
			
		}catch(NoSuchElementException e){
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreateAt(new Date());
		}
		
		
		// perform the cart operations 
		// if Cart items already present : then update
		AtomicReference<Boolean> update = new AtomicReference<>(false);
		List<CartItem> items = cart.getItems();
		 items =items.stream().map(item -> {
			 if(item.getProduct().getProductId().equals(productId)) {
				 //item already present in cart
				 item.setQuantity(quantity);
				 item.setTotalPrice(quantity * product.getDiscoutedprice());
				 update.set(true);
				 }
			return item;
		}).collect(Collectors.toList());
		
		//  cart.setItems(update);
		 
		 // Create items
		 if(!update.get()) {
			 CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getDiscoutedprice()).cart(cart).product(product).build();
			 
			 cart.getItems().add(cartItem);
			 
		 }
		 cart.setUser(user);
		 Cart updateCart = cartRepo.save(cart);
		return mapper.map(updateCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItem) {
		CartItem cartItem2 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found !!"));
		cartItemRepository.delete(cartItem2);
	}

	@Override
	public CartDto getCartByUser(String userId) {
		User user = userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found in database!!"));
		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart is given user not found !!"));
		return mapper.map(cart, CartDto.class);
	}

	@Override
	public void clearCart(String userId) {
		User user = userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found in database!!"));
		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart is given user not found !!"));
		cart.getItems().clear();
		cartRepo.save(cart);
	}

}
