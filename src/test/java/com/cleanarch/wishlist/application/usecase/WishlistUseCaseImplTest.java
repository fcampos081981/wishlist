package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.application.dto.ProductsResponse;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.exception.NotFoundException;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistUseCaseImplTest {

    private static final String CUSTOMER_ID = "customer-1";
    private static final String PRODUCT_ID = "product-1";

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistPropertiesProvider propertiesProvider;

    @InjectMocks
    private WishlistUseCaseImpl wishlistUseCase;

    @Test
    void addProduct_shouldCreateWishlistWhenNotExists() {
        when(propertiesProvider.getMaxProducts()).thenReturn(2);
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID);

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepository).save(captor.capture());
        Wishlist saved = captor.getValue();
        assertThat(saved.getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(saved.getProductIds()).containsExactly(new ProductId(PRODUCT_ID));
    }

    @Test
    void addProduct_shouldAddProductToExistingWishlist() {
        when(propertiesProvider.getMaxProducts()).thenReturn(2);
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId("product-0"))));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID);

        verify(wishlistRepository).save(existing);
        assertThat(existing.getProductIds()).containsExactlyInAnyOrder(
                new ProductId("product-0"),
                new ProductId(PRODUCT_ID)
        );
    }

    @Test
    void addProduct_shouldThrowWhenProductAlreadyExists() {
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID))));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> wishlistUseCase.addProduct(CUSTOMER_ID, PRODUCT_ID))
                .isInstanceOf(BusinesException.class)
                .hasMessage("Product already in wishlist!");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowWhenSizeLimitExceeded() {
        when(propertiesProvider.getMaxProducts()).thenReturn(2);
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(
                new ProductId("product-0"),
                new ProductId("product-1")
        )));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> wishlistUseCase.addProduct(CUSTOMER_ID, "product-2"))
                .isInstanceOf(BusinesException.class)
                .hasMessage("Wishlist size limit exceeded! Max size: 2");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowWhenCustomerIdIsNull() {
        assertThatThrownBy(() -> wishlistUseCase.addProduct(null, PRODUCT_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("customerId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowWhenCustomerIdIsEmpty() {
        assertThatThrownBy(() -> wishlistUseCase.addProduct("", PRODUCT_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("customerId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowWhenProductIdIsNull() {
        assertThatThrownBy(() -> wishlistUseCase.addProduct(CUSTOMER_ID, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("productId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowWhenProductIdIsEmpty() {
        assertThatThrownBy(() -> wishlistUseCase.addProduct(CUSTOMER_ID, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("productId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void removeProduct_shouldRemoveProductFromWishlist() {
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID))));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID);

        verify(wishlistRepository).save(existing);
        assertThat(existing.getProductIds()).isEmpty();
    }

    @Test
    void removeProduct_shouldThrowWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wishlist not found!");
    }

    @Test
    void removeProduct_shouldThrowWhenProductNotInWishlist() {
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>());
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> wishlistUseCase.removeProduct(CUSTOMER_ID, PRODUCT_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product not in wishlist!");
    }

    @Test
    void removeCustomerWishlist_shouldDeleteWishlist() {
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID))));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        wishlistUseCase.removeCustomerWishlist(CUSTOMER_ID);

        verify(wishlistRepository).deleteByCustomerId(CUSTOMER_ID);
    }

    @Test
    void removeCustomerWishlist_shouldThrowWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wishlistUseCase.removeCustomerWishlist(CUSTOMER_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wishlist not found!");
    }

    @Test
    void getAllProducts_shouldReturnProductIdsWhenWishlistExists() {
        Wishlist existing = new Wishlist("id-1", CUSTOMER_ID, new HashSet<>(Set.of(new ProductId(PRODUCT_ID))));
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existing));

        ProductsResponse response = wishlistUseCase.getAllProducts(CUSTOMER_ID);

        assertThat(response.getProductIds()).containsExactly(PRODUCT_ID);
    }

    @Test
    void getAllProducts_shouldReturnEmptySetWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        ProductsResponse response = wishlistUseCase.getAllProducts(CUSTOMER_ID);

        assertThat(response.getProductIds()).isEmpty();
    }

    @Test
    void addNoteProduct_shouldAddNotAdnSaveWishlist() {
        String customerId = "customer-id";
        String productId = "prod-id-2";
        String note = "I want to buy this product on Black Friday";

        HashSet<ProductId> products = new HashSet<>(Collections.singleton(new ProductId(productId)));
        Wishlist wishlist = spy(new Wishlist("id", customerId, products));

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        wishlistUseCase.addNoteToProduct(customerId, productId, note);

        verify(wishlist).addNote(new ProductId(productId), note);
        verify(wishlistRepository).save(wishlist);

    }

    @Test
    void addNoteToProduct_shouldThrowWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wishlistUseCase.addNoteToProduct(CUSTOMER_ID, PRODUCT_ID, "note"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wishlist not found!");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addNoteToProduct_shouldThrowWhenCustomerIdIsNull() {
        assertThatThrownBy(() -> wishlistUseCase.addNoteToProduct(null, PRODUCT_ID, "note"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("customerId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void addNoteToProduct_shouldThrowWhenProductIdIsEmpty() {
        assertThatThrownBy(() -> wishlistUseCase.addNoteToProduct(CUSTOMER_ID, "", "note"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("productId cannot be a null or empty");

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void getNoteForProduct_shouldReturnNote(){
        String customerId = "customer-id";
        String productId = "prod-id-2";
        String note = "I want to buy this product on Black Friday";
        Wishlist wishlist = mock(Wishlist.class);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));
        when(wishlist.getNote(new ProductId(productId))).thenReturn(note);
        String result = wishlistUseCase.getNoteForProduct(customerId, productId);
        assertEquals(note, result);
        verify(wishlist).getNote(new ProductId(productId));
    }

    @Test
    void getNoteForProduct_shouldNThrow_When_wishlist_not_found(){
        String customerId = "customer-id";
        String productId = "prod-id-2";

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> wishlistUseCase.getNoteForProduct(customerId, productId));
    }
}
