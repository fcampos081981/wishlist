package com.cleanarch.wishlist.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;

@Configuration
public class WhislistConfig {

    @Bean
    public WishlistUseCase wishlistUseCase(WishlistRepository wishlistRepository, WishlistPropertiesProvider wishlistPropertiesProvider) {
        return new WishlistUseCase(wishlistRepository, wishlistPropertiesProvider);
    }
}
