package com.cleanarch.wishlist.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoCollectionInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MongoCollectionInitializer.class);
    private static final String WISHLISTS_COLLECTION = "wishlists";

    private final MongoTemplate mongoTemplate;

    public MongoCollectionInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        String database = mongoTemplate.getDb().getName();
        if (!mongoTemplate.collectionExists(WISHLISTS_COLLECTION)) {
            mongoTemplate.createCollection(WISHLISTS_COLLECTION);
            log.info("MongoDB collection criada: {}.{}", database, WISHLISTS_COLLECTION);
        } else {
            log.info("MongoDB collection já existe: {}.{}", database, WISHLISTS_COLLECTION);
        }
    }
}
