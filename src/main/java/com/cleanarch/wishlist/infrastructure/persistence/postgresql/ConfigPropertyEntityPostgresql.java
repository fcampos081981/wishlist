package com.cleanarch.wishlist.infrastructure.persistence.postgresql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "config_properties")
@Access(AccessType.FIELD)
@Getter
@Setter
public class ConfigPropertyEntityPostgresql {

    @Id
    @Column(name = "name_key", unique = true)
    private String nameKey;

    @Column(name = "value_key", nullable = false)
    private String valueKey;
}
