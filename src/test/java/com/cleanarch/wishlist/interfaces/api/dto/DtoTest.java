package com.cleanarch.wishlist.interfaces.api.dto;

import com.cleanarch.wishlist.application.dto.ProductIdsResponse;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DtoTest {

    @Test
    void productIdsResponse_shouldExposeProductIds() {
        ProductIdsResponse response = new ProductIdsResponse(Set.of("product-1"));
        response.setProductIds(Set.of("product-2"));

        assertThat(response.getProductIds()).containsExactly("product-2");
    }

    @Test
    void productIdsResponseDto_shouldExposeProductIds() {
        ProductIdsResponseDTO dto = new ProductIdsResponseDTO(Set.of("product-1"));
        dto.setProductIds(Set.of("product-2"));

        assertThat(dto.getProductIds()).containsExactly("product-2");
    }

    @Test
    void responseDto_shouldExposeFields() {
        ProductIdsResponseDTO data = new ProductIdsResponseDTO(Set.of("product-1"));
        ResponseDTO<ProductIdsResponseDTO> response = new ResponseDTO<>(data, "Success", 200);

        response.setMessage("Updated");
        response.setStatusCode(201);
        response.setData(new ProductIdsResponseDTO(Set.of("product-2")));

        assertThat(response.getMessage()).isEqualTo("Updated");
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getData().getProductIds()).containsExactly("product-2");
    }

    @Test
    void errorResponse_shouldExposeMessage() {
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong");

        assertThat(errorResponse.message()).isEqualTo("Something went wrong");
    }
}
