package com.cleanarch.wishlist.application.dto;



import java.util.Map;
import java.util.Set;

public class ProductsResponse {
    private Set<String> productIds;
    private Map<String, String> productNotes;

    public ProductsResponse(Set<String> productIds, Map<String, String> productNotes) {
        this.productIds = productIds;
        this.productNotes = productNotes;
    }

    public Set<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<String> productIds) {
        this.productIds = productIds;
    }

    public Map<String, String> getProductNotes() {
        return productNotes;
    }

    public void setProductNotes(Map<String, String> productNotes) {
        this.productNotes = productNotes;
    }
}
