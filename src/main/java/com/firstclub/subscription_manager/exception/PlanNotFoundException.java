package com.firstclub.subscription_manager.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String message) {
        super(message);
    }
}
