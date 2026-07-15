package com.cleanarch.wishlist.application.config;

import com.cleanarch.wishlist.infrastructure.persistence.ConfigPropertyDocument;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoCollectionInitializerTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoDatabase mongoDatabase;

    @Mock
    private MongoCollection<Document> mongoCollection;

    @InjectMocks
    private MongoCollectionInitializer initializer;

    @Test
    void run_shouldCreateCollectionsAndSeedConfigWhenMissing() {
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("wishlist-db");
        when(mongoTemplate.collectionExists("wishlists")).thenReturn(false);
        when(mongoTemplate.collectionExists("config_properties")).thenReturn(false);
        when(mongoTemplate.exists(any(Query.class), eq(ConfigPropertyDocument.class))).thenReturn(false);
        when(mongoTemplate.getCollection("wishlists")).thenReturn(mongoCollection);
        when(mongoCollection.countDocuments()).thenReturn(0L);

        initializer.run(new DefaultApplicationArguments(new String[]{}));

        verify(mongoTemplate).createCollection("wishlists");
        verify(mongoTemplate).createCollection("config_properties");
        verify(mongoTemplate).save(any(ConfigPropertyDocument.class));
    }

    @Test
    void run_shouldSkipCreationAndSeedWhenAlreadyPresent() {
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("wishlist-db");
        when(mongoTemplate.collectionExists("wishlists")).thenReturn(true);
        when(mongoTemplate.collectionExists("config_properties")).thenReturn(true);
        when(mongoTemplate.exists(any(Query.class), eq(ConfigPropertyDocument.class))).thenReturn(true);
        when(mongoTemplate.getCollection("wishlists")).thenReturn(mongoCollection);
        when(mongoCollection.countDocuments()).thenReturn(5L);

        initializer.run(new DefaultApplicationArguments(new String[]{}));

        verify(mongoTemplate, never()).createCollection("wishlists");
        verify(mongoTemplate, never()).createCollection("config_properties");
        verify(mongoTemplate, never()).save(any(ConfigPropertyDocument.class));
    }

    @Test
    void run_shouldSeedMaxProductsWithDefaultValue() {
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.getName()).thenReturn("wishlist-db");
        when(mongoTemplate.collectionExists("wishlists")).thenReturn(true);
        when(mongoTemplate.collectionExists("config_properties")).thenReturn(true);
        when(mongoTemplate.exists(any(Query.class), eq(ConfigPropertyDocument.class))).thenReturn(false);
        when(mongoTemplate.getCollection("wishlists")).thenReturn(mongoCollection);
        when(mongoCollection.countDocuments()).thenReturn(0L);

        initializer.run(new DefaultApplicationArguments(new String[]{}));

        ArgumentCaptor<ConfigPropertyDocument> captor = ArgumentCaptor.forClass(ConfigPropertyDocument.class);
        verify(mongoTemplate).save(captor.capture());
        ConfigPropertyDocument saved = captor.getValue();
        org.assertj.core.api.Assertions.assertThat(saved.getKey()).isEqualTo("wishlist.maxProducts");
        org.assertj.core.api.Assertions.assertThat(saved.getValue()).isEqualTo("20");
    }
}
