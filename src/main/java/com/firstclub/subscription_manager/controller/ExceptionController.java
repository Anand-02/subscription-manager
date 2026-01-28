package com.firstclub.subscription_manager.controller;

import com.firstclub.subscription_manager.exception.ActiveSubscriptionNotFoundException;
import com.firstclub.subscription_manager.exception.PlanNotFoundException;
import com.firstclub.subscription_manager.exception.SubscriptionExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Object> handlePlanNotFoundException(PlanNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(ActiveSubscriptionNotFoundException.class)
    public ResponseEntity<Object> handleActiveSubscriptionNotFoundException(ActiveSubscriptionNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(SubscriptionExpiredException.class)
    public ResponseEntity<Object> handleSubscriptionExpiredException(SubscriptionExpiredException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
