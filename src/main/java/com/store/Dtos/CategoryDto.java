package com.store.Dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class CategoryDto {

	
	
	private String categoryId;
	
	@NotBlank(message= "title is required!!")
	@Size(min=4, message ="title must be minimum 4 characters!!")
	private String title;
	
	@NotBlank(message="Description is required")
	private String description;
	
	
	private String coverImage;

}
