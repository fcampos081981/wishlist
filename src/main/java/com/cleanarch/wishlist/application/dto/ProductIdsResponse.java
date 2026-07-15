package com.cleanarch.wishlist.application.dto;

import com.cleanarch.wishlist.domain.vo.ProductId;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductIdsResponse {
    private Set<String> productIds;

    public ProductIdsResponse(Set<String> productIds) {
        this.productIds = productIds;
    }
}
