package com.cleanarch.wishlist.interfaces.api.controller;

import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.interfaces.api.dto.WishlistResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlists")
public class WhislistController {

    private final WishlistUseCase wishlistUseCase;

    public WhislistController(WishlistUseCase wishlistUseCase) {
        this.wishlistUseCase = wishlistUseCase;
    }

    @PostMapping("/{customerId}/products/{productsId}")
    public ResponseEntity<WishlistResponse> addProduct(
            @PathVariable String customerId,
            @PathVariable String productsId) {
        Wishlist wishlist = wishlistUseCase.addProduct(customerId, productsId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(wishlist));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<WishlistResponse> getWishlist(@PathVariable String customerId) {
        Wishlist wishlist = wishlistUseCase.getByCustomerId(customerId);
        return ResponseEntity.ok(toResponse(wishlist));
    }

    private static WishlistResponse toResponse(Wishlist wishlist) {
        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getCustomerId(),
                wishlist.getProductIds().stream()
                        .map(ProductId::value)
                        .collect(Collectors.toSet())
        );
    }
}
