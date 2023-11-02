package com.store.healpher;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.store.Dtos.PageableResponse;

public class Helper {
	
	public static <U,V>PageableResponse <V>getPageableResponse(Page<U> page, Class<V> type){
	
		List<U> users= page.getContent();
		List<V> dtolist = users.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
	
		PageableResponse<V> response= new PageableResponse<V>();
		response.setContent(dtolist);
		response.setPageNummber(page.getNumber());
		response.setPagesize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());
		
		return response;
	
		
		
	}

}
