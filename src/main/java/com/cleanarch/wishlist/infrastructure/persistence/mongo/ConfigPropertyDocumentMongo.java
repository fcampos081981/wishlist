package com.cleanarch.wishlist.infrastructure.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_properties")
@Getter
@Setter
public class ConfigPropertyDocumentMongo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String nameKey;

    private String valeuKey;

    public ConfigPropertyDocumentMongo() {
    }

    public ConfigPropertyDocumentMongo(String nameKey, String valeuKey) {
        this.nameKey = nameKey;
        this.valeuKey = valeuKey;
    }
}

