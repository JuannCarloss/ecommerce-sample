package com.shop.ecommerce.enterprise;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
