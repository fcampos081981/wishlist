package com.cleanarch.wishlist.interfaces.handler;

import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.exception.NotFoundException;
import com.cleanarch.wishlist.interfaces.api.dto.ErroResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleBusinesException_shouldReturnBadRequest() {
        ResponseEntity<ErroResponseDTO> response = handler.handleBusinesException(
                new BusinesException("Invalid operation"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid operation");
    }

    @Test
    void handleNotFoundException_shouldReturnNotFound() {
        ResponseEntity<ErroResponseDTO> response = handler.handleNotFoundException(
                new NotFoundException("Resource not found"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Resource not found");
    }

    @Test
    void handleIllegalArgumentException_shouldReturnBadRequest() {
        ResponseEntity<ErroResponseDTO> response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("customerId cannot be a null or empty"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage())
                .isEqualTo("Invalid argument: customerId cannot be a null or empty");
    }

    @Test
    void handleNoHandlerFoundException_shouldReturnBadRequest() {
        ResponseEntity<ErroResponseDTO> response = handler.handleNoHandlerFoundException(
                new NoHandlerFoundException("GET", "/api/invalid", null));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("The required parameter is not in the path.");
    }
}
