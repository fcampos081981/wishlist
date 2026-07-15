package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.infrastructure.persistence.ConfigPropertyDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongoCollectionInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MongoCollectionInitializer.class);
    private static final String WISHLISTS_COLLECTION = "wishlists";
    private static final String CONFIG_COLLECTION = "config_properties";
    private static final String MAX_PRODUCTS_KEY = "wishlist.maxProducts";
    private static final String DEFAULT_MAX_PRODUCTS = "20";

    private final MongoTemplate mongoTemplate;

    public MongoCollectionInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        String database = mongoTemplate.getDb().getName();
        ensureCollection(database, WISHLISTS_COLLECTION);
        ensureCollection(database, CONFIG_COLLECTION);
        seedMaxProductsIfAbsent();

        long count = mongoTemplate.getCollection(WISHLISTS_COLLECTION).countDocuments();
        log.warn(
                ">>> MongoDB ativo: database='{}', collection='{}', documentos={}",
                database,
                WISHLISTS_COLLECTION,
                count);
    }

    private void ensureCollection(String database, String collection) {
        if (!mongoTemplate.collectionExists(collection)) {
            mongoTemplate.createCollection(collection);
            log.warn("MongoDB collection CRIADA: {}.{}", database, collection);
        }
    }

    private void seedMaxProductsIfAbsent() {
        Query query = Query.query(Criteria.where("key").is(MAX_PRODUCTS_KEY));
        if (!mongoTemplate.exists(query, ConfigPropertyDocument.class)) {
            mongoTemplate.save(new ConfigPropertyDocument(MAX_PRODUCTS_KEY, DEFAULT_MAX_PRODUCTS));
            log.warn("Config property seed: {}={}", MAX_PRODUCTS_KEY, DEFAULT_MAX_PRODUCTS);
        }
    }
}
