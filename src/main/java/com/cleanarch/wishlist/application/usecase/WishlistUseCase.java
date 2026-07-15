package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.application.dto.ProductIdsResponse;

public interface WishlistUseCase {
    void addProduct(String customerId, String productId);

    void removeProduct(String customerId, String productId);

    void removeCustomerWishlist(String customerId);

    ProductIdsResponse getAllProducts(String customerId);
}
