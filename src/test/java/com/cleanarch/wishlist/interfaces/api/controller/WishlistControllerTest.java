package com.cleanarch.wishlist.interfaces.api.controller;

import com.cleanarch.wishlist.application.dto.ProductIdsResponse;
import com.cleanarch.wishlist.application.usecase.WishlistUseCaseImpl;
import com.cleanarch.wishlist.domain.exception.BusinesException;
import com.cleanarch.wishlist.domain.exception.NotFoundException;
import com.cleanarch.wishlist.interfaces.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
@Import(GlobalExceptionHandler.class)
@org.springframework.test.context.TestPropertySource(properties = {
        "spring.mvc.throw-exception-if-no-handler-found=true",
        "spring.web.resources.add-mappings=false"
})
class WishlistControllerTest {

    private static final String CUSTOMER_ID = "customer-1";
    private static final String PRODUCT_ID = "product-1";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistUseCaseImpl wishlistUseCase;

    @Test
    void addProduct_shouldReturnCreated() throws Exception {
        doNothing().when(wishlistUseCase).addProduct(CUSTOMER_ID, PRODUCT_ID);

        mockMvc.perform(post("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
                .andExpect(status().isCreated());

        verify(wishlistUseCase).addProduct(CUSTOMER_ID, PRODUCT_ID);
    }

    @Test
    void addProduct_shouldReturnBadRequestWhenBusinessException() throws Exception {
        doThrow(new BusinesException("Product already in wishlist!"))
                .when(wishlistUseCase).addProduct(CUSTOMER_ID, PRODUCT_ID);

        mockMvc.perform(post("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Product already in wishlist!"));
    }

    @Test
    void removeProduct_shouldReturnNoContent() throws Exception {
        doNothing().when(wishlistUseCase).removeProduct(CUSTOMER_ID, PRODUCT_ID);

        mockMvc.perform(delete("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
                .andExpect(status().isNoContent());

        verify(wishlistUseCase).removeProduct(CUSTOMER_ID, PRODUCT_ID);
    }

    @Test
    void removeProduct_shouldReturnNotFoundWhenProductMissing() throws Exception {
        doThrow(new NotFoundException("Product not in wishlist!"))
                .when(wishlistUseCase).removeProduct(CUSTOMER_ID, PRODUCT_ID);

        mockMvc.perform(delete("/api/wishlists/{customerId}/products/{productId}", CUSTOMER_ID, PRODUCT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not in wishlist!"));
    }

    @Test
    void removeCustomerWishlist_shouldReturnNoContent() throws Exception {
        doNothing().when(wishlistUseCase).removeCustomerWishlist(CUSTOMER_ID);

        mockMvc.perform(delete("/api/wishlists/{customerId}", CUSTOMER_ID))
                .andExpect(status().isNoContent());

        verify(wishlistUseCase).removeCustomerWishlist(CUSTOMER_ID);
    }

    @Test
    void removeCustomerWishlist_shouldReturnNotFoundWhenWishlistMissing() throws Exception {
        doThrow(new NotFoundException("Wishlist not found!"))
                .when(wishlistUseCase).removeCustomerWishlist(CUSTOMER_ID);

        mockMvc.perform(delete("/api/wishlists/{customerId}", CUSTOMER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Wishlist not found!"));
    }

    @Test
    void getAllProducts_shouldReturnProducts() throws Exception {
        when(wishlistUseCase.getAllProducts(CUSTOMER_ID))
                .thenReturn(new ProductIdsResponse(Set.of(PRODUCT_ID)));

        mockMvc.perform(get("/api/wishlists/{customerId}/products", CUSTOMER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.product_ids[0]").value(PRODUCT_ID));
    }

    @Test
    void getAllProducts_shouldReturnEmptyListWhenNoProducts() throws Exception {
        when(wishlistUseCase.getAllProducts(CUSTOMER_ID))
                .thenReturn(new ProductIdsResponse(Set.of()));

        mockMvc.perform(get("/api/wishlists/{customerId}/products", CUSTOMER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.product_ids").isEmpty());
    }

    @Test
    void unknownEndpoint_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The required parameter is not in the path."));
    }
}
