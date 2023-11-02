package com.store.Dtos;

import javax.validation.constraints.NotBlank;

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
public class CreateOrderRequest {
	
	@NotBlank(message="Card Id is required")
	private String cartId;
	
	@NotBlank(message="User Id is required")
	private String userId;
	
	private String orderStatus = "PENDING";
	
	private String paymentStatus="NOTPAID";
	
	@NotBlank(message="Address is required")
	private String billingAddress;
	
	@NotBlank(message="Phone Number is required")
	private String billingPhone;
	
	@NotBlank(message= "Billing Name is required")
	private String billingName;
	

}
