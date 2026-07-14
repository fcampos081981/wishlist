package com.cleanarch.wishlist.interfaces.api.controller;

import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WhislistController {

    @Autowired
    private WishlistUseCase wishlistUseCase;

    @PostMapping("/{customerId}/products/{productsId}")
    public ResponseEntity<Void> addProduct(@PathVariable String customerId, @PathVariable String productsId) {
        wishlistUseCase.addProduct(customerId, productsId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Void> getWishlist() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
