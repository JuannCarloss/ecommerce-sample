package com.shop.ecommerce.enterprise;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
