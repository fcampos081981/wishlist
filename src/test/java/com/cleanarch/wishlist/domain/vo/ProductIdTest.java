package com.cleanarch.wishlist.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductIdTest {

    @Test
    void shouldCreateWithValidValue() {
        ProductId productId = new ProductId("product-1");

        assertThat(productId.value()).isEqualTo("product-1");
        assertThat(productId.toString()).isEqualTo("product-1");
    }

    @Test
    void shouldThrowWhenValueIsNull() {
        assertThatThrownBy(() -> new ProductId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ProductId cannot be null or empty");
    }

    @Test
    void shouldThrowWhenValueIsEmpty() {
        assertThatThrownBy(() -> new ProductId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ProductId cannot be null or empty");
    }

    @Test
    void equals_shouldReturnTrueForSameValue() {
        ProductId first = new ProductId("product-1");
        ProductId second = new ProductId("product-1");

        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void equals_shouldReturnFalseForDifferentValue() {
        ProductId first = new ProductId("product-1");
        ProductId second = new ProductId("product-2");

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void equals_shouldReturnTrueForSameReference() {
        ProductId productId = new ProductId("product-1");

        assertThat(productId).isEqualTo(productId);
    }

    @Test
    void equals_shouldReturnFalseForDifferentType() {
        ProductId productId = new ProductId("product-1");

        assertThat(productId.equals("product-1")).isFalse();
    }
}
