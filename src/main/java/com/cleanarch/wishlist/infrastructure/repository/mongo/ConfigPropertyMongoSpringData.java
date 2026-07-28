package com.cleanarch.wishlist.infrastructure.repository.mongo;

import com.cleanarch.wishlist.infrastructure.persistence.mongo.ConfigPropertyDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigPropertyMongoSpringData extends MongoRepository<ConfigPropertyDocumentMongo, String> {

    ConfigPropertyDocumentMongo findByNameKey(String nameKey);
}
