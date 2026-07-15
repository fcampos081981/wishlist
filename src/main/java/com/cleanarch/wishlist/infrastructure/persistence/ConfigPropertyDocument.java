package com.cleanarch.wishlist.infrastructure.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_properties")
@Getter
@Setter
public class ConfigPropertyDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String key;

    private String value;

    public ConfigPropertyDocument() {
    }

    public ConfigPropertyDocument(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

