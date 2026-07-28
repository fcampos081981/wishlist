package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistMapper;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistPostgresqlImplTest {

    private static final String CUSTOMER_ID = "customer-1";

    @Mock
    private WishlistPostgresqlSprinData postgresqlRepo;

    @Mock
    private WishlistMapper wishlistMapper;

    @InjectMocks
    private WishlistPostgresqlImpl repository;

    @Test
    void findByCustomerId_shouldReturnDomainWhenEntityExists() {
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();
        entity.setId(1L);
        entity.setCustomerId(CUSTOMER_ID);
        entity.setProductIds(Set.of("product-1"));
        Wishlist wishlist = new Wishlist("1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));

        when(postgresqlRepo.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(entity));
        when(wishlistMapper.toDomainPostgresql(entity)).thenReturn(wishlist);

        Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

        assertThat(result).contains(wishlist);
    }

    @Test
    void findByCustomerId_shouldReturnEmptyWhenEntityNotFound() {
        when(postgresqlRepo.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void save_shouldMapAndPersistEntity() {
        Wishlist wishlist = new Wishlist("1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));
        WishlistEntityPostgresql entity = new WishlistEntityPostgresql();
        entity.setId(1L);
        entity.setCustomerId(CUSTOMER_ID);
        entity.setProductIds(Set.of("product-1"));

        when(wishlistMapper.toPostgresqlEntity(wishlist)).thenReturn(entity);

        repository.save(wishlist);

        verify(postgresqlRepo).save(entity);
    }

    @Test
    void deleteByCustomerId_shouldDelegateToPostgresqlRepo() {
        repository.deleteByCustomerId(CUSTOMER_ID);

        verify(postgresqlRepo).deleteByCustomerId(CUSTOMER_ID);
    }
}
