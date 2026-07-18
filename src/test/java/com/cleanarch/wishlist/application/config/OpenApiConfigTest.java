package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.infrastructure.config.OpenApiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final OpenApiConfig config = new OpenApiConfig();

    @Test
    void wishlistOpenAPI_shouldConfigureApiMetadata() {
        OpenAPI openAPI = config.wishlistOpenAPI();

        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Wishlist API");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("API de lista de desejos");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("v1");
    }
}
