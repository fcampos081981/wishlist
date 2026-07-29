package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.infrastructure.persistence.mongo.ConfigPropertyDocumentMongo;
import com.cleanarch.wishlist.infrastructure.persistence.mongo.WishlistDocumentMongo;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.ConfigPropertyEntityPostgresql;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceDocumentTest {

    @Test
    void wishlistDocument_defaultConstructor_shouldInitializeProductIds() {
        WishlistDocumentMongo document = new WishlistDocumentMongo();

        assertThat(document.getProductIds()).isNotNull().isEmpty();
        assertThat(document.getProductNotes()).isNotNull().isEmpty();
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldSetProductNotes() {
        WishlistDocumentMongo document = new WishlistDocumentMongo(
                "id-1", "customer-1", Set.of("product-1"), Map.of("product-1", "note-1"));

        assertThat(document.getProductNotes()).containsEntry("product-1", "note-1");
    }

    @Test
    void wishlistDocument_parameterizedConstructor_shouldUseEmptyMapWhenNotesAreNull() {
        WishlistDocumentMongo document = new WishlistDocumentMongo(
                "id-1", "customer-1", Set.of("product-1"), null);

        assertThat(document.getProductNotes()).isNotNull().isEmpty();
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
        document.setProductNotes(Map.of("product-1", "note-1"));

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
        assertThat(document.getProductNotes()).containsEntry("product-1", "note-1");
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

    @Test
    void wishlistEntityPostgresql_defaultConstructor_shouldCreateInstance() {
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getCustomerId()).isNull();
        assertThat(entity.getProductIds()).isNull();
        assertThat(entity.getProductNotes()).isNotNull().isEmpty();
    }

    @Test
    void wishlistEntityPostgresql_setters_shouldUpdateFields() {
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();
        entity.setId(1L);
        entity.setCustomerId("customer-1");
        entity.setProductIds(new HashSet<>(Set.of("product-1")));
        entity.setProductNotes(Map.of("product-1", "note-1"));

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCustomerId()).isEqualTo("customer-1");
        assertThat(entity.getProductIds()).containsExactly("product-1");
        assertThat(entity.getProductNotes()).containsEntry("product-1", "note-1");
    }

    @Test
    void configPropertyEntityPostgresql_defaultConstructor_shouldCreateInstance() {
        ConfigPropertyEntityPostgresql entity = new ConfigPropertyEntityPostgresql();

        assertThat(entity).isNotNull();
        assertThat(entity.getNameKey()).isNull();
        assertThat(entity.getValueKey()).isNull();
    }

    @Test
    void configPropertyEntityPostgresql_setters_shouldUpdateFields() {
        ConfigPropertyEntityPostgresql entity = new ConfigPropertyEntityPostgresql();
        entity.setNameKey("wishlist.maxProducts");
        entity.setValueKey("20");

        assertThat(entity.getNameKey()).isEqualTo("wishlist.maxProducts");
        assertThat(entity.getValueKey()).isEqualTo("20");
    }
}
