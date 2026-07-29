package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.application.dto.ProductsResponse;

public interface WishlistUseCase {
    void addProduct(String customerId, String productId);

    void removeProduct(String customerId, String productId);

    void removeCustomerWishlist(String customerId);

    ProductsResponse getAllProducts(String customerId);

    void addNoteToProduct(String customerId, String productId, String note);

    String getNoteForProduct(String customerId, String productId);
}
