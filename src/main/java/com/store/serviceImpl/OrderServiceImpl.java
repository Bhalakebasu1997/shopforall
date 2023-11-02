package com.store.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.store.Dtos.CreateOrderRequest;
import com.store.Dtos.OrderDto;
import com.store.Dtos.OrderUpdateRequest;
import com.store.Dtos.PageableResponse;
import com.store.entities.Cart;
import com.store.entities.CartItem;
import com.store.entities.Order;
import com.store.entities.OrderItem;
import com.store.entities.User;
import com.store.exception.BadApiRequestExcepiton;
import com.store.exception.ResourceNotFoundException;
import com.store.healpher.Helper;
import com.store.repository.CartRepository;
import com.store.repository.OrderRepository;
import com.store.repository.UserRepository;
import com.store.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ModelMapper model;
	
	@Autowired
	private CartRepository cartRepository ;
	
	@Autowired
	private OrderRepository orderrepo;
	
	@Autowired
	private UserRepository userRepo;

	
	
	
	
	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto){
		String userId = orderDto.getUserId();
		String cartId = orderDto.getCartId();
		
		// fetch use
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found with given Id"));
		// fetch cart
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with given Id"));
		
		List<CartItem> cartItems = cart.getItems();
		
		if(cartItems.size()<=0) {
			 throw new BadApiRequestExcepiton("Invalid number of items in carts!!");
		}
		
		//
		
		Order order = Order.builder().billingName(orderDto.getBillingName())
				.billingAddress(orderDto.getBillingAddress())
				.billingPhone(orderDto.getBillingPhone()).orderedDate(new Date())
				.deliveredyDate(null)
				.paymentStatus(orderDto.getPaymentStatus())
				.orderStatus(orderDto.getOrderStatus())
				.orderId(UUID.randomUUID().toString())
				.user(user)
				.build();
		
		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
		 List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//           CartItem->OrderItem
           OrderItem orderItem = OrderItem.builder()
                   .quantity(cartItem.getQuantity())
                   .product(cartItem.getProduct())
                   .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscoutedprice())
                   .order(order)
                   .build();

           orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
           return orderItem;
       }).collect(Collectors.toList());
		 
		 cart.getItems().clear();
		 cartRepository.save(cart);
		 Order saveorder = orderrepo.save(order);
		return model.map(saveorder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Order order = orderrepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found"));
		orderrepo.delete(order);
		
	}

	@Override
	public List<OrderDto> getOrderOfUser(String userId) {
		
		 User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found"));
		List<Order> orders = orderrepo.findByUser(user);
		List<OrderDto> orderDtos = orders.stream().map(order -> model.map(order, OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
	}

	@Override
	public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort =(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
				Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Order> page = orderrepo.findAll(pageable);
		return Helper.getPageableResponse(page, OrderDto.class);
	}

	@Override
	public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {
		Order order = orderrepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order update data"));
		order.setBillingAddress(order.getBillingAddress());
		order.setBillingName(order.getBillingName());
		order.setBillingPhone(order.getBillingPhone());
		order.setDeliveredyDate(order.getDeliveredyDate());
		order.setPaymentStatus(request.getPaymentStatus());
		order.setOrderStatus(order.getOrderStatus());
		return model.map(order, OrderDto.class);
	}

	@Override
	public OrderDto updateOrder(String orderId, OrderDto request) {
		Order order = orderrepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order update data"));
		order.setBillingAddress(order.getBillingAddress());
		order.setBillingName(order.getBillingName());
		order.setBillingPhone(order.getBillingPhone());
		order.setDeliveredyDate(order.getDeliveredyDate());
		order.setPaymentStatus(request.getPaymentStatus());
		order.setOrderStatus(order.getOrderStatus());
		 return model.map(order, OrderDto.class);
	}

}
