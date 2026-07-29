INSERT INTO config_properties (name_key, value_key)
VALUES ('wishlist.maxProducts', '20')
ON CONFLICT (name_key) DO NOTHING;
