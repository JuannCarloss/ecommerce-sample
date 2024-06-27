package com.shop.ecommerce.enterprise;

public class TooManyRequestException extends RuntimeException{

    public TooManyRequestException(String message){
        super(message);
    }
}
