package com.cleanarch.wishlist.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionTest {

    @Test
    void businesException_shouldExposeMessage() {
        BusinesException exception = new BusinesException("Business rule violated");

        assertThat(exception.getMessage()).isEqualTo("Business rule violated");
    }

    @Test
    void notFoundException_shouldExposeMessage() {
        NotFoundException exception = new NotFoundException("Resource not found");

        assertThat(exception.getMessage()).isEqualTo("Resource not found");
    }
}
