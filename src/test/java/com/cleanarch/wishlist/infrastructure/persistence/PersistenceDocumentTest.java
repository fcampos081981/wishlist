package com.cleanarch.wishlist.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceDocumentTest {

    @Test
    void wishlistDocument_defaultConstructor_shouldInitializeProductIds() {
        WishlistDocument document = new WishlistDocument();

        assertThat(document.getProductIds()).isNotNull().isEmpty();
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldSetFields() {
        WishlistDocument document = new WishlistDocument("id-1", "customer-1", Set.of("product-1"));

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldUseEmptySetWhenNull() {
        WishlistDocument document = new WishlistDocument("id-1", "customer-1", null);

        assertThat(document.getProductIds()).isNotNull().isEmpty();
    }

    @Test
    void wishlistDocument_setters_shouldUpdateFields() {
        WishlistDocument document = new WishlistDocument();
        document.setId("id-1");
        document.setCustomerId("customer-1");
        document.setProductIds(new HashSet<>(Set.of("product-1")));

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
    }

    @Test
    void configPropertyDocument_shouldExposeFields() {
        ConfigPropertyDocument document = new ConfigPropertyDocument("wishlist.maxProducts", "20");

        assertThat(document.getKey()).isEqualTo("wishlist.maxProducts");
        assertThat(document.getValue()).isEqualTo("20");

        document.setId("id-1");
        document.setKey("other.key");
        document.setValue("30");

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getKey()).isEqualTo("other.key");
        assertThat(document.getValue()).isEqualTo("30");
    }

    @Test
    void configPropertyDocument_defaultConstructor_shouldCreateInstance() {
        ConfigPropertyDocument document = new ConfigPropertyDocument();

        assertThat(document).isNotNull();
    }
}
