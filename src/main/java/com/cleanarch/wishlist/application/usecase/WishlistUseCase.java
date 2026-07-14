package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;

import java.util.HashSet;


public class WishlistUseCase {
    private final int WISH_LIST_MAX_SIZE = 10;
    private final WishlistRepository wishlistRepository;

    public WishlistUseCase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public void addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist(null, customerId, new HashSet<>()));

        if(wishlist.getProductIds().contains(new ProductId(productId)))
        {
            throw new BusinesException("Product already in wishlist!");
        }

        if(wishlist.getProductIds().size() >= WISH_LIST_MAX_SIZE)
            throw new BusinesException("Wishlist size limit exceeded! Max size: " + WISH_LIST_MAX_SIZE);


        wishlist.getProductIds().add(new ProductId(productId));

        wishlistRepository.save(wishlist);
    }
}
