package com.cleanarch.wishlist.integration;

import com.cleanarch.wishlist.WishlistApplication;
import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.infrastructure.config.MongoCollectionInitializer;
import com.cleanarch.wishlist.infrastructure.repository.mongo.ConfigPropertyMongoSpringData;
import com.cleanarch.wishlist.infrastructure.repository.mongo.WishlistMongoSpringData;
import com.cleanarch.wishlist.interfaces.api.dto.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = WishlistApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class, DataMongoAutoConfiguration.class})
class WishlistIntegrationTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private MongoTemplate mongoTemplate;

    @MockitoBean
    private MongoCollectionInitializer mongoCollectionInitializer;

    @MockitoBean
    private WishlistMongoSpringData wishlistMongoSpringData;

    @MockitoBean
    private ConfigPropertyMongoSpringData configPropertyMongoSpringData;

    @MockitoBean
    private WishlistRepository wishlistRepository;

    @MockitoBean
    private WishlistPropertiesProvider wishlistPropertiesProvider;

    private RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void addAndGetProductInWishlist() {
        String customerId = "cust2";
        String productId = "prod1";

        Wishlist wishlist = new Wishlist("id", customerId, new HashSet<>());

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));
        when(wishlistPropertiesProvider.getMaxProducts()).thenReturn(3);

        restClient().post()
                .uri("/api/wishlists/{customerId}/products/{productId}", customerId, productId)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();

        wishlist = new Wishlist("id", customerId, Set.of(new ProductId(productId)));

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        ResponseDTO<?> response =  restClient().get()
                .uri("/api/wishlists/" + customerId + "/products")
                .retrieve()
                .body(ResponseDTO.class);

        assertThat(Optional.of(response)
                .map(ResponseDTO::getData)
                .map(Object::toString)
                .orElse(""))
                .contains(productId);
    }
}
