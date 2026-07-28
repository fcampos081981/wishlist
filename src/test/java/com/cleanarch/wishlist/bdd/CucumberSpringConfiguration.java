package com.cleanarch.wishlist.bdd;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.infrastructure.config.MongoCollectionInitializer;
import com.cleanarch.wishlist.infrastructure.repository.ConfigPropertyMongoSpringData;
import com.cleanarch.wishlist.infrastructure.repository.WishlistMongoSpringData;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class, DataMongoAutoConfiguration.class})
public class CucumberSpringConfiguration {
    @MockitoBean
    private MongoTemplate mongoTemplate;

    @MockitoBean
    private MongoCollectionInitializer mongoCollectionInitializer;

    @MockitoBean
    private WishlistRepository wishlistRepository;

    @MockitoBean
    private WishlistPropertiesProvider wishlistPropertiesProvider;

    @MockitoBean
    private WishlistMongoSpringData wishlistMongoSpringData;

    @MockitoBean
    private ConfigPropertyMongoSpringData configPropertyMongoSpringData;
}
