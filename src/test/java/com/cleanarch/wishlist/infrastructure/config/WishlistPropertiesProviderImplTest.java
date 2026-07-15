package com.cleanarch.wishlist.infrastructure.config;

import com.cleanarch.wishlist.infrastructure.persistence.ConfigPropertyDocument;
import com.cleanarch.wishlist.infrastructure.repository.ConfigPropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistPropertiesProviderImplTest {

    @Mock
    private ConfigPropertyRepository configRepo;

    @InjectMocks
    private WishlistPropertiesProviderImpl propertiesProvider;

    @Test
    void getMaxProducts_shouldReturnConfiguredValue() {
        when(configRepo.findByKey("wishlist.maxProducts"))
                .thenReturn(new ConfigPropertyDocument("wishlist.maxProducts", "10"));

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(10);
    }

    @Test
    void getMaxProducts_shouldReturnDefaultWhenPropertyNotFound() {
        when(configRepo.findByKey("wishlist.maxProducts")).thenReturn(null);

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(20);
    }

    @Test
    void getMaxProducts_shouldReturnDefaultWhenValueIsNull() {
        ConfigPropertyDocument property = new ConfigPropertyDocument("wishlist.maxProducts", null);
        when(configRepo.findByKey("wishlist.maxProducts")).thenReturn(property);

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(20);
    }

    @Test
    void getMaxProducts_shouldReturnDefaultWhenValueIsInvalid() {
        when(configRepo.findByKey("wishlist.maxProducts"))
                .thenReturn(new ConfigPropertyDocument("wishlist.maxProducts", "invalid"));

        assertThat(propertiesProvider.getMaxProducts()).isEqualTo(20);
    }
}
