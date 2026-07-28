package com.cleanarch.wishlist.infrastructure.config;

import com.cleanarch.wishlist.domain.repositorie.ConfigPropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistPropertiesProviderImplTest {

    private ConfigPropertyRepository configRepo;
    private WishlistPropertiesProviderImpl propertiesProvider;

    @BeforeEach
    void setUp() {
        configRepo = mock(ConfigPropertyRepository.class);
        propertiesProvider = new WishlistPropertiesProviderImpl(configRepo);
    }

    @Test
    void getMaxProducts_shouldReturnConfiguredValue() {
        when(configRepo.findValueByKey("wishlist.maxProducts")).thenReturn("10");

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(10);
    }

    @Test
    void getMaxProducts_shouldReturnDefaultWhenPropertyNotFound() {
        when(configRepo.findValueByKey("wishlist.maxProducts")).thenReturn(null);

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(5);
    }

    @Test
    void getMaxProducts_shouldReturnDefaultWhenValueIsInvalid() {
        when(configRepo.findValueByKey("wishlist.maxProducts")).thenReturn("invalid");

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(5);
    }
}
