package com.cleanarch.wishlist.domain.entity;

import com.cleanarch.wishlist.domain.vo.ProductId;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WishlistTest {

    @Test
    void defaultConstructor_shouldInitializeEmptyProductIds() {
        Wishlist wishlist = new Wishlist();

        assertThat(wishlist.getProductIds()).isNotNull().isEmpty();
    }

    @Test
    void parameterizedConstructor_shouldSetAllFields() {
        Set<ProductId> productIds = new HashSet<>(Set.of(new ProductId("product-1")));

        Wishlist wishlist = new Wishlist("id-1", "customer-1", productIds);

        assertThat(wishlist.getId()).isEqualTo("id-1");
        assertThat(wishlist.getCustomerId()).isEqualTo("customer-1");
        assertThat(wishlist.getProductIds()).containsExactly(new ProductId("product-1"));
    }

    @Test
    void setters_shouldUpdateFields() {
        Wishlist wishlist = new Wishlist();
        Set<ProductId> productIds = new HashSet<>(Set.of(new ProductId("product-1")));

        wishlist.setId("id-1");
        wishlist.setCustomerId("customer-1");
        wishlist.setProductIds(productIds);

        assertThat(wishlist.getId()).isEqualTo("id-1");
        assertThat(wishlist.getCustomerId()).isEqualTo("customer-1");
        assertThat(wishlist.getProductIds()).containsExactly(new ProductId("product-1"));
    }
}
