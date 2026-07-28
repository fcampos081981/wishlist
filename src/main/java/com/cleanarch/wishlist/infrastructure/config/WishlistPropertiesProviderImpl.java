package com.cleanarch.wishlist.infrastructure.config;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.domain.repositorie.ConfigPropertyRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WishlistPropertiesProviderImpl implements WishlistPropertiesProvider {

    private static final Logger LOGGER = LogManager.getLogger(WishlistPropertiesProviderImpl.class);
    private final ConfigPropertyRepository configRepo;

    public WishlistPropertiesProviderImpl(ConfigPropertyRepository configRepo) {
        this.configRepo = configRepo;
    }

    @Override
    public int getMaxProducts() {
        LOGGER.info("Finding max maxProducts in Config Repository");
        String value = configRepo.findValueByKey("wishlist.maxProducts");

        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                LOGGER.warn("Value found for maxProducts {}", value);
            }
        }

        return 5;
    }
}
