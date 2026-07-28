package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistPostgresqlSprinData extends JpaRepository<WishlistEntityPostgresql, Long> {

    Optional<WishlistEntityPostgresql> findByCustomerId(String customerId);

    void deleteByCustomerId(String customerId);

}
