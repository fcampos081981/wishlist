package com.cleanarch.wishlist.infrastructure.repository;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistMapper;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WishlistRepositoryMongoImpl implements WishlistRepository {

    private static final Logger log = LoggerFactory.getLogger(WishlistRepositoryMongoImpl.class);

    private final WishlistMongoSpringData mongoRepo;
    private final WishlistMapper wishlistMapper;
    private final MongoTemplate mongoTemplate;

    public WishlistRepositoryMongoImpl(
            WishlistMongoSpringData mongoRepo,
            WishlistMapper wishlistMapper,
            MongoTemplate mongoTemplate) {
        this.mongoRepo = mongoRepo;
        this.wishlistMapper = wishlistMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        return mongoRepo.findByCustomerId(customerId)
                .map(wishlistMapper::toDomain);
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        WishlistDocument incoming = wishlistMapper.toDocument(wishlist);

        Query byCustomer = Query.query(Criteria.where("customerId").is(wishlist.getCustomerId()));
        Update update = new Update()
                .set("productIds", incoming.getProductIds())
                .setOnInsert("customerId", wishlist.getCustomerId())
                .setOnInsert("_id", UUID.randomUUID().toString());

        WishlistDocument saved = mongoTemplate.findAndModify(
                byCustomer,
                update,
                FindAndModifyOptions.options().upsert(true).returnNew(true),
                WishlistDocument.class);

        log.info(
                "Wishlist salva em {}.{} -> id={}, customerId={}, productIds={}",
                mongoTemplate.getDb().getName(),
                "wishlists",
                saved != null ? saved.getId() : null,
                wishlist.getCustomerId(),
                incoming.getProductIds());

        return wishlistMapper.toDomain(saved);
    }

    @Override
    public void deleteByCustomerId(String customerId) {
        mongoRepo.deleteByCustomerId(customerId);
    }

}
