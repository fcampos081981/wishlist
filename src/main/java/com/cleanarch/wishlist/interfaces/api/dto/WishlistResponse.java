package com.cleanarch.wishlist.interfaces.api.dto;

import java.util.Set;

public record WishlistResponse(
        String id,
        String customerId,
        Set<String> productIds
) {
}
