package com.btreynor.foodappbeta.exception;

public class OrderAlreadyCheckoutException extends Exception {

    public OrderAlreadyCheckoutException(String message) {
        super(message);
    }
}
