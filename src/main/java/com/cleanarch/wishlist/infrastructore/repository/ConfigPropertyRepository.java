package com.cleanarch.wishlist.infrastructore.repository;

import com.cleanarch.wishlist.infrastructore.persistence.ConfigPropertyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigPropertyRepository extends MongoRepository<ConfigPropertyDocument, String> {

    ConfigPropertyDocument findByKey(String key);
}
