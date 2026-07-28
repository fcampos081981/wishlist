package com.cleanarch.wishlist.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "postgresql")
@EnableJpaRepositories(basePackages = "com.cleanarch.wishlist.infrastructure.repository.postgresql")
public class PostgresqlRepositoryConfig {
}
