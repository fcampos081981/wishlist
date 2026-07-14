package com.cleanarch.wishlist.infrastructore.repository;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.infrastructore.persistence.WhishlistMapper;
import com.cleanarch.wishlist.infrastructore.persistence.WishlistDocument;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WishlistRepositoryMongoImpl implements WishlistRepository {

    private final WhislistMongoSpringData mongoRepo;
    private final WhishlistMapper whishlistMapper;

    public WishlistRepositoryMongoImpl(WhislistMongoSpringData mongoRepo, WhishlistMapper whishlistMapper) {
        this.mongoRepo = mongoRepo;
        this.whishlistMapper = whishlistMapper;
    }

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        return mongoRepo.findByCustomerId(customerId)
                .map(whishlistMapper::toDomain);
    }

    @Override
    public void save(Wishlist wishlist) {
        WishlistDocument doc =  whishlistMapper.toDocument(wishlist);
        mongoRepo.save(doc);
    }

}
