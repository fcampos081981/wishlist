package com.cleanarch.wishlist.domain.entity;

import com.cleanarch.wishlist.domain.vo.ProductId;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Wishlist {

    private String id;
    private String customerId;
    private Set<ProductId> productIds = new HashSet<>();

    public Wishlist(){}

    public Wishlist(String id, String customerId, Set<ProductId> productIds){
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
    }

}
