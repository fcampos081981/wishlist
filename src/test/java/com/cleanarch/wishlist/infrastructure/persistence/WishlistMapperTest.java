package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.vo.ProductId;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WishlistMapperTest {

    private final WishlistMapper mapper = new WishlistMapperImpl();

    @Test
    void toDocument_shouldMapAllFields() {
        Wishlist wishlist = new Wishlist(
                "id-1",
                "customer-1",
                new HashSet<>(Set.of(new ProductId("product-1")))
        );

        WishlistDocument document = mapper.toDocument(wishlist);

        assertThat(document.getId()).isEqualTo("id-1");
        assertThat(document.getCustomerId()).isEqualTo("customer-1");
        assertThat(document.getProductIds()).containsExactly("product-1");
    }

    @Test
    void toDocument_shouldReturnNullWhenWishlistIsNull() {
        assertThat(mapper.toDocument(null)).isNull();
    }

    @Test
    void toDomain_shouldMapAllFields() {
        WishlistDocument document = new WishlistDocument(
                "id-1",
                "customer-1",
                Set.of("product-1")
        );

        Wishlist wishlist = mapper.toDomain(document);

        assertThat(wishlist.getId()).isEqualTo("id-1");
        assertThat(wishlist.getCustomerId()).isEqualTo("customer-1");
        assertThat(wishlist.getProductIds()).containsExactly(new ProductId("product-1"));
    }

    @Test
    void toDomain_shouldReturnNullWhenDocumentIsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void map_shouldConvertProductIdsToStrings() {
        Set<String> result = mapper.map(Set.of(new ProductId("product-1"), new ProductId("product-2")));

        assertThat(result).containsExactlyInAnyOrder("product-1", "product-2");
    }

    @Test
    void map_shouldReturnNullWhenProductIdsAreNull() {
        assertThat(mapper.map(null)).isNull();
    }

    @Test
    void mapToProductId_shouldConvertStringsToProductIds() {
        Set<ProductId> result = mapper.mapToProductId(Set.of("product-1", "product-2"));

        assertThat(result).containsExactlyInAnyOrder(
                new ProductId("product-1"),
                new ProductId("product-2")
        );
    }

    @Test
    void mapToProductId_shouldReturnNullWhenStringsAreNull() {
        assertThat(mapper.mapToProductId(null)).isNull();
    }
}
