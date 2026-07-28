package com.cleanarch.wishlist.infrastructure.repository.mongo;


import com.cleanarch.wishlist.infrastructure.persistence.mongo.WishlistDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistMongoSpringData extends MongoRepository<WishlistDocumentMongo, String> {
   Optional<WishlistDocumentMongo> findByCustomerId(String customerId);

   void deleteByCustomerId(String customerId);
}
