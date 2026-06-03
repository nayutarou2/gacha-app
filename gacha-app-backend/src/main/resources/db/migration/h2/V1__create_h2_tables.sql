-- 📄 db/migration/h2/V1__create_h2_tables.sql (これに丸ごと上書き！)

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gacha_menus (
    id BIGSERIAL PRIMARY KEY,
    kinds_num INT NOT NULL
);

CREATE TABLE IF NOT EXISTS gacha_results (
    id BIGSERIAL PRIMARY KEY,
    s_count INT DEFAULT 0,
    a_count INT DEFAULT 0,
    b_count INT DEFAULT 0,
    c_count INT DEFAULT 0,
    gacha_menu_id BIGINT NOT NULL REFERENCES gacha_menus(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gacha_result_details (
    id BIGSERIAL PRIMARY KEY,
    gacha_result_id BIGINT NOT NULL REFERENCES gacha_results(id) ON DELETE CASCADE,
    turns INT NOT NULL,
    rank VARCHAR(2) NOT NULL
);