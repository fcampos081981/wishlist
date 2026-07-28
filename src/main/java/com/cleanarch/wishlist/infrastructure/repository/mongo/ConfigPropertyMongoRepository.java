package com.cleanarch.wishlist.infrastructure.repository.mongo;

import com.cleanarch.wishlist.domain.repositorie.ConfigPropertyRepository;
import com.cleanarch.wishlist.infrastructure.persistence.mongo.ConfigPropertyDocumentMongo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;


@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "mongo")
@Repository
public class ConfigPropertyMongoRepository implements ConfigPropertyRepository {

    private final ConfigPropertyMongoSpringData mongoRepo;

    public ConfigPropertyMongoRepository(ConfigPropertyMongoSpringData mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Override
    public String findValueByKey(String key) {
        ConfigPropertyDocumentMongo doc = mongoRepo.findByNameKey(key);
        return doc != null ? doc.getValeuKey() : null;
    }


}
