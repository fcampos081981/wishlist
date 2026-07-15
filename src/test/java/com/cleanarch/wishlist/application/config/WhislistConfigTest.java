package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.application.usecase.WishlistUseCaseImpl;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WhislistConfigTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistPropertiesProvider wishlistPropertiesProvider;

    private final WhislistConfig config = new WhislistConfig();

    @Test
    void wishlistUseCase_shouldCreateUseCaseBean() {
        WishlistUseCase useCase = config.wishlistUseCase(wishlistRepository, wishlistPropertiesProvider);

        assertThat(useCase).isNotNull().isInstanceOf(WishlistUseCaseImpl.class);
    }
}
