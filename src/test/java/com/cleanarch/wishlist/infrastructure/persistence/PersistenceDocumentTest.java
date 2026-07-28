package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.infrastructure.persistence.mongo.ConfigPropertyDocumentMongo;
import com.cleanarch.wishlist.infrastructure.persistence.mongo.WishlistDocumentMongo;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceDocumentTest {

    @Test
    void wishlistDocument_defaultConstructor_shouldInitializeProductIds() {
        WishlistDocumentMongo document = new WishlistDocumentMongo();

        assertThat(document.getProductIds()).isNotNull().isEmpty();
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldSetFields() {
        WishlistDocumentMongo document = new WishlistDocumentMongo("id-1", "customer-1", Set.of("product-1"));

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldUseEmptySetWhenNull() {
        WishlistDocumentMongo document = new WishlistDocumentMongo("id-1", "customer-1", null);

        assertThat(document.getProductIds()).isNotNull().isEmpty();
    }

    @Test
    void wishlistDocument_setters_shouldUpdateFields() {
        WishlistDocumentMongo document = new WishlistDocumentMongo();
        document.setId("id-1");
        document.setCustomerId("customer-1");
        document.setProductIds(new HashSet<>(Set.of("product-1")));

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
    }

    @Test
    void configPropertyDocument_shouldExposeFields() {
        ConfigPropertyDocumentMongo document = new ConfigPropertyDocumentMongo("wishlist.maxProducts", "20");

        assertThat(document.getNameKey()).isEqualTo("wishlist.maxProducts");
        assertThat(document.getValeuKey()).isEqualTo("20");

        document.setId("id-1");
        document.setNameKey("other.nameKey");
        document.setValeuKey("30");

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getNameKey()).isEqualTo("other.nameKey");
        assertThat(document.getValeuKey()).isEqualTo("30");
    }

    @Test
    void configPropertyDocument_defaultConstructor_shouldCreateInstance() {
        ConfigPropertyDocumentMongo document = new ConfigPropertyDocumentMongo();

        assertThat(document).isNotNull();
    }
}
