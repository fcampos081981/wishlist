package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.infrastructure.persistence.postgresql.ConfigPropertyEntityPostgresql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigPropertyPostgresqlSpringDate extends JpaRepository<ConfigPropertyEntityPostgresql, Long> {
    ConfigPropertyEntityPostgresql findByNameKey(String nameKey);
}
