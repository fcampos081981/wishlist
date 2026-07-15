package com.cleanarch.wishlist.infrastructore.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "wishlists")
@Getter
@Setter
public class WishlistDocument {

    @MongoId(targetType = FieldType.STRING)
    private String id;

    @Indexed(unique = true)
    private String customerId;

    private Set<String> productIds = new HashSet<>();

    public WishlistDocument() {
    }

    public WishlistDocument(String id, String customerId, Set<String> productIds) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds != null ? productIds : new HashSet<>();
    }
}
