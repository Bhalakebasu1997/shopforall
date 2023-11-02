package com.store.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {

	@Id
	private String orderId;
	
	//Pending, Dispatched, Delivered
	// enum
	private String orderStatus;
	
	// NotPaid , Paid
	// enum
	// boolean -false=>NOTPAID || true =>PAID
	private String paymentStatus;
	
	private int orderAmount;
	
	@Column(length=100)
	private String billingAddress;
	
	private String billingPhone;
	private String billingName;
	
	private Date orderedDate;
	
	private Date deliveredyDate;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
}
