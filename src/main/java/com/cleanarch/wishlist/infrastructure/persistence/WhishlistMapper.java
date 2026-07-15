package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.domain.vo.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cleanarch.wishlist.domain.entity.Wishlist;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WhishlistMapper {
    WhishlistMapper INSTANCE = Mappers.getMapper(WhishlistMapper.class);

    WishlistDocument toDocument(Wishlist wishlist);

    Wishlist toDomain(WishlistDocument wishlistDocument);

    default Set<String> map(Set<ProductId> value){
        if(value == null) return null;

        return value.stream().map(ProductId::value).collect(Collectors.toSet());
    }

    default Set<ProductId> mapToProductId(Set<String> value) {
        if (value == null) return null;
        return value.stream().map(ProductId::new).collect(Collectors.toSet());
    }
}
