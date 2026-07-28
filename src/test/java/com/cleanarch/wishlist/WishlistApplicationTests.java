package com.cleanarch.wishlist;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.infrastructure.config.MongoCollectionInitializer;
import com.cleanarch.wishlist.infrastructure.repository.mongo.ConfigPropertyMongoSpringData;
import com.cleanarch.wishlist.infrastructure.repository.mongo.WishlistMongoSpringData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {MongoAutoConfiguration.class, DataMongoAutoConfiguration.class})
class WishlistApplicationTests {

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

	@Test
	void contextLoads() {
	}

}
