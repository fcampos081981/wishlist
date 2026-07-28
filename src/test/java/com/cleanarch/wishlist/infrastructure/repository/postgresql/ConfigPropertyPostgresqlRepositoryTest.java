package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.infrastructure.persistence.postgresql.ConfigPropertyEntityPostgresql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigPropertyPostgresqlRepositoryTest {

    private ConfigPropertyPostgresqlSpringDate postgreRepo;
    private ConfigPropertyPostgresqlRepository repository;

    @BeforeEach
    void setUp() {
        postgreRepo = mock(ConfigPropertyPostgresqlSpringDate.class);
        repository = new ConfigPropertyPostgresqlRepository(postgreRepo);
    }

    @Test
    void findValueByKey_shouldReturnValueWhenEntityExists() {
        ConfigPropertyEntityPostgresql entity = new ConfigPropertyEntityPostgresql();
        entity.setNameKey("wishlist.maxProducts");
        entity.setValueKey("20");
        when(postgreRepo.findByNameKey("wishlist.maxProducts")).thenReturn(entity);

        assertThat(repository.findValueByKey("wishlist.maxProducts")).isEqualTo("20");
    }

    @Test
    void findValueByKey_shouldReturnEmptyStringWhenEntityDoesNotExist() {
        when(postgreRepo.findByNameKey("wishlist.maxProducts")).thenReturn(null);

        assertThat(repository.findValueByKey("wishlist.maxProducts")).isEmpty();
    }
}
