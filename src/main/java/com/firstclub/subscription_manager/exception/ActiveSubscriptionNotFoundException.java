package com.firstclub.subscription_manager.exception;

public class ActiveSubscriptionNotFoundException extends RuntimeException {
    public ActiveSubscriptionNotFoundException(String message) {
        super(message);
    }
}
