package com.cleanarch.wishlist.interfaces.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ProductsResponseDTO {
    @JsonProperty("product_ids")
    private Set<String> productIds;

    @JsonProperty("product_notes")
    private Map<String, String> productNotes;

    public ProductsResponseDTO(Set<String> productIds, Map<String, String> productNotes) {
        this.productIds = productIds;
        this.productNotes = productNotes;
    }
}
