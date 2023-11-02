package com.store.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@Table(name="cart_Items")
public class CartItem {
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY )
	private int cartItemId;
	
	
	@OneToOne
	@JoinColumn(name="productId")
	private Product product;
	
	private int quantity;
	
	private int totalPrice;
	
	
	// mapping Cart
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="cart_id")
	private Cart cart;

	
	
}
