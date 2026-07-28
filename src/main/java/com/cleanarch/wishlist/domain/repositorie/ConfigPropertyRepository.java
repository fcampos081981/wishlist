package com.cleanarch.wishlist.domain.repositorie;

public interface ConfigPropertyRepository {

    String findValueByKey(String key);
}
