package com.cleanarch.wishlist.domain.entity;

import com.cleanarch.wishlist.domain.vo.ProductId;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void canAddProduct_shouldReturnTrueWhenBelowLimit() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>(Set.of(new ProductId("product-1"))));

        assertThat(wishlist.canAddProduct(2)).isTrue();
    }

    @Test
    void canAddProduct_shouldReturnFalseWhenAtLimit() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>(Set.of(
                new ProductId("product-1"),
                new ProductId("product-2")
        )));

        assertThat(wishlist.canAddProduct(2)).isFalse();
    }

    @Test
    void containsProduct_shouldReturnTrueWhenProductExists() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>(Set.of(new ProductId("product-1"))));

        assertThat(wishlist.containsProduct(new ProductId("product-1"))).isTrue();
    }

    @Test
    void containsProduct_shouldReturnFalseWhenProductDoesNotExist() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>());

        assertThat(wishlist.containsProduct(new ProductId("product-1"))).isFalse();
    }

    @Test
    void addNote_shouldStoreNoteWhenProductExists() {
        ProductId productId = new ProductId("product-1");
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>(Set.of(productId)));

        wishlist.addNote(productId, "buy on Black Friday");

        assertThat(wishlist.getProductNotes()).containsEntry(productId, "buy on Black Friday");
    }

    @Test
    void addNote_shouldThrowWhenProductDoesNotExist() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>());

        assertThatThrownBy(() -> wishlist.addNote(new ProductId("product-1"), "note"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not in wishlist");
    }

    @Test
    void getNote_shouldReturnStoredNote() {
        ProductId productId = new ProductId("product-1");
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>(Set.of(productId)));
        wishlist.addNote(productId, "buy on Black Friday");

        assertThat(wishlist.getNote(productId)).isEqualTo("buy on Black Friday");
    }

    @Test
    void getNote_shouldThrowWhenProductDoesNotExist() {
        Wishlist wishlist = new Wishlist("id-1", "customer-1", new HashSet<>());

        assertThatThrownBy(() -> wishlist.getNote(new ProductId("product-1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not in wishlist");
    }

    @Test
    void productNotes_settersAndGetters_shouldUpdateFields() {
        Wishlist wishlist = new Wishlist();
        Map<ProductId, String> notes = new HashMap<>();
        notes.put(new ProductId("product-1"), "note-1");

        wishlist.setProductNotes(notes);

        assertThat(wishlist.getProductNotes()).isEqualTo(notes);
    }
}
