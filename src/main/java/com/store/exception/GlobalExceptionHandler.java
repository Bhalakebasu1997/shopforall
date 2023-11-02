package com.store.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.store.Dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	// //handler resource not found

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resoucrNotFoundExceptionHandler(ResourceNotFoundException ex){
		logger.info("Exception handler Invokked!!");
	 ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidExcepiton(MethodArgumentNotValidException ex){
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String, Object> responnse = new HashMap();
		
		allErrors.stream().forEach(ObjectError -> {
			String message = ObjectError.getDefaultMessage();// value 
	  String field =   ((FieldError) ObjectError).getField();
	  responnse.put(field, message);
		});
		
		return new ResponseEntity<>(responnse, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(BadApiRequestExcepiton.class)
	public ResponseEntity<ApiResponseMessage> undleBadApiRequestExcepiton(BadApiRequestExcepiton ex){
		logger.info("Bad Request Handler!!");
	 ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		
	}
	
	
}
