package com.cleanarch.wishlist.infrastructure.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "wishlists")
@Getter
@Setter
public class WishlistDocumentMongo {

    @MongoId(targetType = FieldType.STRING)
    private String id;

    @Indexed(unique = true)
    private String customerId;

    private Set<String> productIds = new HashSet<>();

    private Map<String, String> productNotes = new HashMap<>();


    public WishlistDocumentMongo() {
    }

    public WishlistDocumentMongo(String id, String customerId, Set<String> productIds) {
        this(id, customerId, productIds, new HashMap<>());
    }

    public WishlistDocumentMongo(String id, String customerId, Set<String> productIds, Map<String, String> productNotes) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds != null ? productIds : new HashSet<>();
        this.productNotes = productNotes != null ? productNotes : new HashMap<>();
    }
}
