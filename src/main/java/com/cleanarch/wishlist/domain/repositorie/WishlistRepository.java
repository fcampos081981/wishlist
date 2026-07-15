package com.cleanarch.wishlist.domain.repositorie;

import com.cleanarch.wishlist.domain.entity.Wishlist;

import java.util.Optional;

public interface WishlistRepository {

   Optional<Wishlist> findByCustomerId(String customerId);

   Wishlist save(Wishlist wishlist);

   void deleteByCustomerId(String customerId);
}
