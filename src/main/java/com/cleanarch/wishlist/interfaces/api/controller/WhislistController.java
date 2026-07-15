package com.cleanarch.wishlist.interfaces.api.controller;

import com.cleanarch.wishlist.application.dto.ProductIdsResponse;
import com.cleanarch.wishlist.application.usecase.WishlistUseCase;
import com.cleanarch.wishlist.interfaces.api.dto.ProductIdsResponseDTO;
import com.cleanarch.wishlist.interfaces.api.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WhislistController {

    private final WishlistUseCase wishlistUseCase;

    public WhislistController(WishlistUseCase wishlistUseCase) {
        this.wishlistUseCase = wishlistUseCase;
    }

    @PostMapping("/{customerId}/products/{productsId}")
    public ResponseEntity<Void> addProduct(@PathVariable String customerId, @PathVariable String productsId) {
        wishlistUseCase.addProduct(customerId, productsId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{customerId}/products/{productsId}")
    public ResponseEntity<Void> removeProduct(@PathVariable String customerId, @PathVariable String productsId) {
        wishlistUseCase.removeProduct(customerId, productsId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> removeCustomerWishlist(@PathVariable String customerId) {
        wishlistUseCase.removeCustomerWishlist(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<ResponseDTO<ProductIdsResponseDTO>> getAllProducts(@PathVariable String customerId) {
        ProductIdsResponse productIdsResponse = wishlistUseCase.getAllProducts(customerId);
        ProductIdsResponseDTO dto = new ProductIdsResponseDTO(productIdsResponse.getProductIds());
        ResponseDTO<ProductIdsResponseDTO> response =
                new ResponseDTO<>(dto, "Success", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}
