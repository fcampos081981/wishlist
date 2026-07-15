package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;

import java.util.HashSet;

public class WishlistUseCase {

    private final WishlistRepository wishlistRepository;

    private final WishlistPropertiesProvider prop;

    public WishlistUseCase(WishlistRepository wishlistRepository, WishlistPropertiesProvider wishlistPropertiesProvider) {
        this.wishlistRepository = wishlistRepository;
        this.prop = wishlistPropertiesProvider;
    }

    public Wishlist addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist(null, customerId, new HashSet<>()));

        if (wishlist.getProductIds().contains(new ProductId(productId))) {
            throw new BusinesException("Product already in wishlist!");
        }

        int maxProducts = prop.getMaxProducts();
        if (wishlist.getProductIds().size() >= maxProducts) {
            throw new BusinesException("Wishlist size limit exceeded! Max size: " + maxProducts);
        }

        wishlist.getProductIds().add(new ProductId(productId));

        return wishlistRepository.save(wishlist);
    }

    public Wishlist getByCustomerId(String customerId) {
        return wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new BusinesException("Wishlist not found for customer: " + customerId));
    }
}
