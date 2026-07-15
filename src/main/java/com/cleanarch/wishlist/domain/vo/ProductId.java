package com.cleanarch.wishlist.domain.vo;

import java.util.Objects;

public record ProductId(String value) {
    public ProductId {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("ProductId cannot be null or empty");
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ProductId(String value1))) return false;
        return Objects.equals(value, value1);
    }

    @Override
    public String toString(){
        return value;
    }

}
