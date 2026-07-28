package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.infrastructure.config.MongoRepositoryConfig;
import com.cleanarch.wishlist.infrastructure.config.PostgresqlRepositoryConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryConfigTest {

    @Test
    void mongoRepositoryConfig_shouldInstantiate() {
        assertThat(new MongoRepositoryConfig()).isNotNull();
    }

    @Test
    void postgresqlRepositoryConfig_shouldInstantiate() {
        assertThat(new PostgresqlRepositoryConfig()).isNotNull();
    }
}
