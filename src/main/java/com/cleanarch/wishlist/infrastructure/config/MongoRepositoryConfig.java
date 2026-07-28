package com.cleanarch.wishlist.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "mongo")
@EnableMongoRepositories(basePackages = "com.cleanarch.wishlist.infrastructure.repository.mongo")
public class MongoRepositoryConfig {
}
