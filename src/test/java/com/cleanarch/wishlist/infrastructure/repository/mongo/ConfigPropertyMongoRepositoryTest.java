package com.cleanarch.wishlist.infrastructure.repository.mongo;

import com.cleanarch.wishlist.infrastructure.persistence.ConfigPropertyDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigPropertyMongoRepositoryTest {

    private ConfigPropertyMongoSpringData mongoRepo;
    private ConfigPropertyMongoRepository repository;

    @BeforeEach
    void setUp() {
        mongoRepo = mock(ConfigPropertyMongoSpringData.class);
        repository = new ConfigPropertyMongoRepository(mongoRepo);
    }

    @Test
    void findValueByKey_shouldReturnValueWhenDocumentExists() {
        when(mongoRepo.findByKey("wishlist.maxProducts"))
                .thenReturn(new ConfigPropertyDocument("wishlist.maxProducts", "20"));

        assertThat(repository.findValueByKey("wishlist.maxProducts")).isEqualTo("20");
    }

    @Test
    void findValueByKey_shouldReturnNullWhenDocumentDoesNotExist() {
        when(mongoRepo.findByKey("wishlist.maxProducts")).thenReturn(null);

        assertThat(repository.findValueByKey("wishlist.maxProducts")).isNull();
    }

    @Test
    void findValueByKey_shouldReturnNullWhenDocumentValueIsNull() {
        when(mongoRepo.findByKey("wishlist.maxProducts"))
                .thenReturn(new ConfigPropertyDocument("wishlist.maxProducts", null));

        assertThat(repository.findValueByKey("wishlist.maxProducts")).isNull();
    }
}
