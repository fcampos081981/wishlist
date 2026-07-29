CREATE TABLE IF NOT EXISTS wishlist (
    id          BIGSERIAL PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_wishlist_customer_id ON wishlist (customer_id);

CREATE TABLE IF NOT EXISTS wishlist_product (
    wishlist_id BIGINT       NOT NULL,
    product_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_wishlist_product PRIMARY KEY (wishlist_id, product_id),
    CONSTRAINT fk_wishlist_product_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS wishlist_product_note (
    wishlist_id BIGINT       NOT NULL,
    product_id  VARCHAR(255) NOT NULL,
    note        TEXT,
    CONSTRAINT pk_wishlist_product_note PRIMARY KEY (wishlist_id, product_id),
    CONSTRAINT fk_wishlist_product_note_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS config_properties (
    name_key  VARCHAR(255) PRIMARY KEY,
    value_key VARCHAR(255) NOT NULL
);
