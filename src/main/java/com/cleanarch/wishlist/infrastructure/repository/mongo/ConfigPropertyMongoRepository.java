package com.cleanarch.wishlist.infrastructure.repository.mongo;

import com.cleanarch.wishlist.domain.repositorie.ConfigPropertyRepository;
import com.cleanarch.wishlist.infrastructure.persistence.ConfigPropertyDocument;
import com.cleanarch.wishlist.infrastructure.repository.ConfigPropertyMongoSpringData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;


@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "mongo")
public class ConfigPropertyMongoRepository implements ConfigPropertyRepository {

    private final ConfigPropertyMongoSpringData mongoRepo;

    public ConfigPropertyMongoRepository(ConfigPropertyMongoSpringData mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Override
    public String findValueByKey(String key) {
        ConfigPropertyDocument doc = mongoRepo.findByKey(key);
        return doc != null ? doc.getValue() : null;
    }
}
