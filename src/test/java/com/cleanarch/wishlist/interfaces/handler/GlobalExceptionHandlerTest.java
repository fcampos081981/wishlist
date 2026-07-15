package com.cleanarch.wishlist.interfaces.handler;

import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.exception.NotFoundException;
import com.cleanarch.wishlist.interfaces.api.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleBusinesException_shouldReturnBadRequest() {
        ResponseEntity<ErrorResponse> response = handler.handleBusinesException(
                new BusinesException("Invalid operation"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Invalid operation");
    }

    @Test
    void handleNotFoundException_shouldReturnNotFound() {
        ResponseEntity<ErrorResponse> response = handler.handleNotFoundException(
                new NotFoundException("Resource not found"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Resource not found");
    }
}
