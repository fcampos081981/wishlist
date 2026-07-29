package com.cleanarch.wishlist.domain.entity;

import com.cleanarch.wishlist.domain.vo.ProductId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Wishlist {

    private String id;
    private String customerId;
    private Set<ProductId> productIds = new HashSet<>();
    private Map<ProductId, String> productNotes = new HashMap<>();

    public Wishlist(){}

    public Wishlist(String id, String customerId, Set<ProductId> productIds){
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
    }

    public boolean canAddProduct(int maxProducts){
        return productIds.size() < maxProducts;
    }

    public boolean containsProduct(ProductId productId){
        return productIds.contains(productId);
    }

    public void addNote(ProductId productId, String note) {
        if (!productIds.contains(productId)) {
            throw new IllegalArgumentException("Product not in wishlist");
        }

        productNotes.put(productId, note);
    }

    public String getNote(ProductId productId) {
        if (!productIds.contains(productId)) {
            throw new IllegalArgumentException("Product not in wishlist");
        }
        return productNotes.get(productId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<ProductId> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<ProductId> productIds) {
        this.productIds = productIds;
    }

    public Map<ProductId, String> getProductNotes() {
        return productNotes;
    }

    public void setProductNotes(Map<ProductId, String> productNotes) {
        this.productNotes = productNotes;
    }


}
