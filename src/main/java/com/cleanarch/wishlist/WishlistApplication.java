package com.cleanarch.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.cleanarch.wishlist.infrastructure.repository.mongo")
@EnableJpaRepositories(basePackages = "com.cleanarch.wishlist.infrastructure.repository.postgresql")

@SpringBootApplication
public class WishlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}

}
