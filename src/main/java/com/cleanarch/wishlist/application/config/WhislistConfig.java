package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.application.usecase.WishlistUseCaseImpl;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhislistConfig {

    @Bean
    public WishlistUseCase wishlistUseCase(WishlistRepository wishlistRepository, WishlistPropertiesProvider wishlistPropertiesProvider) {
        return new WishlistUseCaseImpl(wishlistRepository, wishlistPropertiesProvider);
    }
}
