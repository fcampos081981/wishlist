package com.cleanarch.wishlist.infrastructure.repository;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistMapper;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistDocument;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistRepositoryMongoImplTest {

    private static final String CUSTOMER_ID = "customer-1";

    @Mock
    private WishlistMongoSpringData mongoRepo;

    @Mock
    private WishlistMapper wishlistMapper;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoDatabase mongoDatabase;

    @InjectMocks
    private WishlistRepositoryMongoImpl repository;

    @Test
    void findByCustomerId_shouldReturnDomainWhenDocumentExists() {
        WishlistDocument document = new WishlistDocument("id-1", CUSTOMER_ID, Set.of("product-1"));
        Wishlist wishlist = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));

        when(mongoRepo.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(document));
        when(wishlistMapper.toDomain(document)).thenReturn(wishlist);

        Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

        assertThat(result).contains(wishlist);
    }

    @Test
    void findByCustomerId_shouldReturnEmptyWhenDocumentNotFound() {
        when(mongoRepo.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        Optional<Wishlist> result = repository.findByCustomerId(CUSTOMER_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void save_shouldUpsertAndReturnDomain() {
        Wishlist wishlist = new Wishlist(null, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));
        WishlistDocument incoming = new WishlistDocument(null, CUSTOMER_ID, Set.of("product-1"));
        WishlistDocument savedDocument = new WishlistDocument("id-1", CUSTOMER_ID, Set.of("product-1"));
        Wishlist savedWishlist = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));

        when(wishlistMapper.toDocument(wishlist)).thenReturn(incoming);
        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(WishlistDocument.class)
        )).thenReturn(savedDocument);
        when(wishlistMapper.toDomain(savedDocument)).thenReturn(savedWishlist);
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("wishlist-db");

        Wishlist result = repository.save(wishlist);

        assertThat(result).isEqualTo(savedWishlist);
        verify(mongoTemplate).findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(WishlistDocument.class)
        );
    }

    @Test
    void save_shouldHandleNullSavedDocument() {
        Wishlist wishlist = new Wishlist(null, CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-1"))));
        WishlistDocument incoming = new WishlistDocument(null, CUSTOMER_ID, Set.of("product-1"));

        when(wishlistMapper.toDocument(wishlist)).thenReturn(incoming);
        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(WishlistDocument.class)
        )).thenReturn(null);
        when(wishlistMapper.toDomain(null)).thenReturn(null);
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("wishlist-db");

        Wishlist result = repository.save(wishlist);

        assertThat(result).isNull();
    }

    @Test
    void deleteByCustomerId_shouldDelegateToMongoRepo() {
        repository.deleteByCustomerId(CUSTOMER_ID);

        verify(mongoRepo).deleteByCustomerId(CUSTOMER_ID);
    }
}
