package com.cleanarch.wishlist.infrastructore.repository;


import com.cleanarch.wishlist.infrastructore.persistence.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WhislistMongoSpringData extends MongoRepository<WishlistDocument, String> {
   Optional<WishlistDocument> findByCustomerId(String customerId);
}
