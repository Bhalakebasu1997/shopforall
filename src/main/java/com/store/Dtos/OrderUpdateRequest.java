package com.store.Dtos;

import java.util.Date;

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
public class OrderUpdateRequest {

	private String orderStatus;
	private String paymentStatus;
	private String billingName;
	private String billingPhone;
	private String billingAddress;
	private Date deliveredDate;
	
	
}
