package com.store.exception;


public class BadApiRequestExcepiton extends RuntimeException {
	
	
	public BadApiRequestExcepiton() {
		super("Bad Request !!");
	}
	
	
	public  BadApiRequestExcepiton(String message) {
		super(message);
}}
