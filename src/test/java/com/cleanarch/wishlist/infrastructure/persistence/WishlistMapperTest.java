package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.infrastructure.persistence.mongo.WishlistDocumentMongo;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
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

        WishlistDocumentMongo document = mapper.toDocument(wishlist);

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
        WishlistDocumentMongo document = new WishlistDocumentMongo(
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
    void toPostgresqlEntity_shouldMapAllFields() {
        Wishlist wishlist = new Wishlist(
                "10",
                "customer-1",
                new HashSet<>(Set.of(new ProductId("product-1")))
        );

        WishlistEntityPostgresql entity = mapper.toPostgresqlEntity(wishlist);

        assertThat(entity.getId()).isEqualTo(10L);
        assertThat(entity.getCustomerId()).isEqualTo("customer-1");
        assertThat(entity.getProductIds()).containsExactly("product-1");
    }

    @Test
    void toPostgresqlEntity_shouldSkipIdWhenWishlistIdIsNull() {
        Wishlist wishlist = new Wishlist(null, "customer-1", new HashSet<>());

        WishlistEntityPostgresql entity = mapper.toPostgresqlEntity(wishlist);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getCustomerId()).isEqualTo("customer-1");
        assertThat(entity.getProductIds()).isEmpty();
    }

    @Test
    void toPostgresqlEntity_shouldReturnNullWhenWishlistIsNull() {
        assertThat(mapper.toPostgresqlEntity(null)).isNull();
    }

    @Test
    void toDomainPostgresql_shouldMapAllFields() {
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();
        entity.setId(10L);
        entity.setCustomerId("customer-1");
        entity.setProductIds(Set.of("product-1"));

        Wishlist wishlist = mapper.toDomainPostgresql(entity);

        assertThat(wishlist.getId()).isEqualTo("10");
        assertThat(wishlist.getCustomerId()).isEqualTo("customer-1");
        assertThat(wishlist.getProductIds()).containsExactly(new ProductId("product-1"));
    }

    @Test
    void toDomainPostgresql_shouldSkipIdWhenEntityIdIsNull() {
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();
        entity.setCustomerId("customer-1");
        entity.setProductIds(Set.of("product-1"));

        Wishlist wishlist = mapper.toDomainPostgresql(entity);

        assertThat(wishlist.getId()).isNull();
        assertThat(wishlist.getCustomerId()).isEqualTo("customer-1");
        assertThat(wishlist.getProductIds()).containsExactly(new ProductId("product-1"));
    }

    @Test
    void toDomainPostgresql_shouldReturnNullWhenEntityIsNull() {
        assertThat(mapper.toDomainPostgresql(null)).isNull();
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
