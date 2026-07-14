package com.cleanarch.wishlist.infrastructore.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "wishlists")
@Getter
@Setter
@NoArgsConstructor
public class WishlistDocument {
    @Id
    private String id;
    private String customerId;
    private Set<String> productIds;

    public WishlistDocument(String id, String customerId, Set<String> productIds) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
    }
}
