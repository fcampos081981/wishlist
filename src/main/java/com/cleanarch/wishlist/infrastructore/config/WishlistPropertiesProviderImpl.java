package com.cleanarch.wishlist.infrastructore.config;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.infrastructore.persistence.ConfigPropertyDocument;
import com.cleanarch.wishlist.infrastructore.repository.ConfigPropertyRepository;
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
        ConfigPropertyDocument prop = configRepo.findByKey("wishlist.maxProducts");

        if (prop != null && prop.getValue() != null) {
            try {
                return Integer.parseInt(prop.getValue());
            } catch (NumberFormatException e) {
                LOGGER.warn("Value found for maxProducts {}", prop.getValue());
            }
        }

        return 20;
    }
}
