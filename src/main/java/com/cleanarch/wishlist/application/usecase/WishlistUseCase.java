package com.cleanarch.wishlist.application.usecase;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.application.dto.ProductIdsResponse;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.exception.NotFoundException;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WishlistUseCase {

    private final WishlistRepository wishlistRepository;

    private final WishlistPropertiesProvider prop;

    public WishlistUseCase(WishlistRepository wishlistRepository, WishlistPropertiesProvider wishlistPropertiesProvider) {
        this.wishlistRepository = wishlistRepository;
        this.prop = wishlistPropertiesProvider;
    }

    public void addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist(null, customerId, new HashSet<>()));

        if (wishlist.containsProduct(new ProductId(productId))) {
            throw new BusinesException("Product already in wishlist!");
        }

        int maxProducts = prop.getMaxProducts();
        if (wishlist.canAddProduct(maxProducts)) {
            throw new BusinesException("Wishlist size limit exceeded! Max size: " + maxProducts);
        }

        wishlist.getProductIds().add(new ProductId(productId));

        wishlistRepository.save(wishlist);
    }

    public void removeProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Wishlist not found!"));

        if (!wishlist.getProductIds().contains(new ProductId(productId))) {
            throw new NotFoundException("Product not in wishlist!");
        }

        wishlist.getProductIds().remove(new ProductId(productId));

        wishlistRepository.save(wishlist);
    }

    public void removeCustomerWishlist(String customerId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Wishlist not found!"));

        wishlistRepository.deleteByCustomerId(wishlist.getCustomerId());
    }

    public ProductIdsResponse getAllProducts(String customerId) {
        Optional<Wishlist> allProductsByCosyumer = wishlistRepository.findByCustomerId(customerId);

        Set<ProductId> ids = allProductsByCosyumer
                .map(Wishlist::getProductIds)
                .orElse(Collections.emptySet());

        Set<String> idsAsString = ids
                .stream()
                .map(ProductId::toString)
                .collect(Collectors.toSet());

        return new ProductIdsResponse(idsAsString);
    }
}
