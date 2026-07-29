package com.cleanarch.wishlist.infrastructure.web.controller;

import com.cleanarch.wishlist.application.dto.ProductsResponse;
import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.interfaces.api.dto.ProductsResponseDTO;
import com.cleanarch.wishlist.interfaces.api.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistUseCase wishlistUseCase;

    public WishlistController(WishlistUseCase wishlistUseCase) {
        this.wishlistUseCase = wishlistUseCase;
    }

    @PostMapping("/{customerId}/products/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable String customerId, @PathVariable String productId) {
        wishlistUseCase.addProduct(customerId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{customerId}/products/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable String customerId, @PathVariable String productId) {
        wishlistUseCase.removeProduct(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> removeCustomerWishlist(@PathVariable String customerId) {
        wishlistUseCase.removeCustomerWishlist(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<ResponseDTO<ProductsResponseDTO>> getAllProducts(@PathVariable String customerId) {
        ProductsResponse productsResponse = wishlistUseCase.getAllProducts(customerId);
        ProductsResponseDTO dto = new ProductsResponseDTO(productsResponse.getProductIds(), productsResponse.getProductNotes());
        ResponseDTO<ProductsResponseDTO> response =
                new ResponseDTO<>(dto, "Success", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{customerId}/products/{productId}/notes")
    public ResponseEntity<Void> addNote(@PathVariable String customerId, @PathVariable String productId, @RequestBody String note) {
        wishlistUseCase.addNoteToProduct(customerId, productId, note);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
