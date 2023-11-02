package com.store.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cart")
public class Cart {
	
	
	@Id
	private String cartId;
	private Date createAt;
	
	@OneToOne
	private User user;
	// mapping Cart-items 
	@OneToMany(mappedBy = "cart", cascade= CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();
	

}
