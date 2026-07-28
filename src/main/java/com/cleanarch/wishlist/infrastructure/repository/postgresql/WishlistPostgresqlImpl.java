package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.infrastructure.persistence.WishlistMapper;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "postgresql")
public class WishlistPostgresqlImpl implements WishlistRepository {

    private final WishlistPostgresqlSprinData postgresqlRepo;
    private final WishlistMapper wishlistMapper;

    public WishlistPostgresqlImpl(WishlistPostgresqlSprinData postgresqlRepo, WishlistMapper wishlistMapper) {
        this.postgresqlRepo = postgresqlRepo;
        this.wishlistMapper = wishlistMapper;
    }


    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {

        return postgresqlRepo.findByCustomerId(customerId)
                .map(wishlistMapper::toDomainPostgresql);
    }

    @Override
    public void save(Wishlist wishlist) {
        WishlistEntityPostgresql entity = wishlistMapper.toPostgresqlEntity(wishlist);
        postgresqlRepo.save(entity);
    }


    @Override
    @Transactional
    public void deleteByCustomerId(String customerId) {
        postgresqlRepo.deleteByCustomerId(customerId);
    }
}
