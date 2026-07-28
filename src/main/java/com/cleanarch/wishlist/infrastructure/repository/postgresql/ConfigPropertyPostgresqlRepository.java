package com.cleanarch.wishlist.infrastructure.repository.postgresql;

import com.cleanarch.wishlist.domain.repositorie.ConfigPropertyRepository;
import com.cleanarch.wishlist.infrastructure.persistence.postgresql.ConfigPropertyEntityPostgresql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "wishlist.repository.type", havingValue = "postgresql")
public class ConfigPropertyPostgresqlRepository implements ConfigPropertyRepository {

    private final ConfigPropertyPostgresqlSpringDate postgreRepo;

    public ConfigPropertyPostgresqlRepository(ConfigPropertyPostgresqlSpringDate postgreRepo) {
        this.postgreRepo = postgreRepo;
    }

    @Override
    public String findValueByKey(String key) {
        ConfigPropertyEntityPostgresql entity = postgreRepo.findByNameKey(key);
        return entity != null ? entity.getValueKey() : "";
    }
}
