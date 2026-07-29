package com.cleanarch.wishlist.interfaces.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductsResponseDTO {
    @JsonProperty("product_ids")
    private Set<String> productIds;

    public ProductsResponseDTO(Set<String> productIds) {
        this.productIds = productIds;
    }
}
