package com.retailer.error;

import org.springframework.stereotype.Component;

@Component
public class CustomerException extends RuntimeException {

	public CustomerException()
	{
		
	}
    public CustomerException(String message) {
        super(message);
    }
}
