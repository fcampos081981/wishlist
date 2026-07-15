package com.cleanarch.wishlist.interfaces.handler;

import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.interfaces.api.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinesException.class)
    public ResponseEntity<ErrorResponse> handleBusinesException(BusinesException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
