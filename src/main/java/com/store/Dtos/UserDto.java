package com.store.Dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.store.validation.ImageNameValid;

import io.swagger.annotations.ApiModelProperty;
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
public class UserDto {
	
	private String UserId;
	
	@Size(min=3, max=15, message="Invallied Users!!")
	@ApiModelProperty(value= "user_name", name="username", required= true, notes="user name of user")
	private String name;
	
	//@Email(message="Invallid user email!!")
	//@Pattern(regexp ="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\\\.)+[a-z]{2,5}$", message="Invalid User Email!!")
	@Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email !!")
	@NotBlank(message="email is required!!")
	private String email;
	
	@NotBlank(message = "write Something about yourself !!")
	private String about;
	
	@NotBlank(message="Password is required!!")
	private String password;
	
	@Size(min=4, max=15, message="Invalid gender!!")
	private String gender;
	
	@ImageNameValid()
	private String imageName;


}
