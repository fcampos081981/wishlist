package com.cleanarch.wishlist.infrastructure.persistence;

import com.cleanarch.wishlist.domain.vo.ProductId;
import com.cleanarch.wishlist.infrastructure.persistence.mongo.WishlistDocumentMongo;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.WishlistEntityPostgresql;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cleanarch.wishlist.domain.entity.Wishlist;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    //mongo
    WishlistDocumentMongo toDocument(Wishlist wishlist);
    Wishlist toDomain(WishlistDocumentMongo wishlistDocumentMongo);

    //postgresql
    WishlistEntityPostgresql toPostgresqlEntity(Wishlist wishlist);
    Wishlist toDomainPostgresql(WishlistEntityPostgresql entity);

    default Set<String> map(Set<ProductId> value){
        if(value == null) return null;
        return value.stream().map(ProductId::value).collect(Collectors.toSet());
    }

    default Set<ProductId> mapToProductId(Set<String> value) {
        if (value == null) return null;
        return value.stream().map(ProductId::new).collect(Collectors.toSet());
    }
}
