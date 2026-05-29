CREATE TABLE IF NOT EXISTS gacha_results (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    s_count int,
    a_count int,
    b_count int,
    c_count int,
    gacha_menu_id bigint NOT NULL REFERENCES gacha_menus(id),
    user_id bigint NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP
);