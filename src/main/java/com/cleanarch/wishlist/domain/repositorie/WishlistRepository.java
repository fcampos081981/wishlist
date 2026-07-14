package com.cleanarch.wishlist.domain.repositorie;

import com.cleanarch.wishlist.domain.entity.Wishlist;

import java.util.Optional;

public interface WishlistRepository {

   Optional<Wishlist> findByCustomerId(String customerId);

   void save(Wishlist wishlist);
}
