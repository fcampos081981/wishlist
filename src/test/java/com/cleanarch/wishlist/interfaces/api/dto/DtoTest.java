package com.cleanarch.wishlist.interfaces.api.dto;

import com.cleanarch.wishlist.application.dto.ProductsResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DtoTest {

    @Test
    void productIdsResponse_shouldExposeProductIds() {
        ProductsResponse response = new ProductsResponse(Set.of("product-1"), Map.of("product-1", "note-1"));
        response.setProductIds(Set.of("product-2"));

        assertThat(response.getProductIds()).containsExactly("product-2");
    }

    @Test
    void productIdsResponse_shouldExposeProductNotes() {
        ProductsResponse response = new ProductsResponse(Set.of("product-1"), Map.of("product-1", "note-1"));

        assertThat(response.getProductNotes()).containsEntry("product-1", "note-1");

        response.setProductNotes(Map.of("product-2", "note-2"));

        assertThat(response.getProductNotes()).containsEntry("product-2", "note-2");
    }

    @Test
    void productIdsResponseDto_shouldExposeProductIds() {
        ProductsResponseDTO dto = new ProductsResponseDTO(Set.of("product-1"), Map.of("product-1", "note-1"));
        dto.setProductIds(Set.of("product-2"));

        assertThat(dto.getProductIds()).containsExactly("product-2");
    }

    @Test
    void productIdsResponseDto_shouldExposeProductNotes() {
        ProductsResponseDTO dto = new ProductsResponseDTO(Set.of("product-1"), Map.of("product-1", "note-1"));

        assertThat(dto.getProductNotes()).containsEntry("product-1", "note-1");

        dto.setProductNotes(Map.of("product-2", "note-2"));

        assertThat(dto.getProductNotes()).containsEntry("product-2", "note-2");
    }

    @Test
    void responseDto_shouldExposeFields() {
        ProductsResponseDTO data = new ProductsResponseDTO(Set.of("product-1"), Map.of());
        ResponseDTO<ProductsResponseDTO> response = new ResponseDTO<>(data, "Success", 200);

        response.setMessage("Updated");
        response.setStatusCode(201);
        response.setData(new ProductsResponseDTO(Set.of("product-2"), Map.of()));

        assertThat(response.getMessage()).isEqualTo("Updated");
        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getData().getProductIds()).containsExactly("product-2");
    }

    @Test
    void erroResponseDto_shouldExposeMessage() {
        ErroResponseDTO errorResponse = new ErroResponseDTO("Something went wrong");

        assertThat(errorResponse.getMessage()).isEqualTo("Something went wrong");

        errorResponse.setMessage("Updated message");

        assertThat(errorResponse.getMessage()).isEqualTo("Updated message");
    }
}
