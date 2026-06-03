CREATE TABLE IF NOT EXISTS gacha_menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kinds_num INT
);

CREATE TABLE IF NOT EXISTS users(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE IF NOT EXISTS gacha_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- H2ではPRIMARY KEYにNOT NULLが含まれるため省略可
    s_count INT,
    a_count INT,
    b_count INT,
    c_count INT,
    gacha_menu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 外部キー制約
    FOREIGN KEY (gacha_menu_id) REFERENCES gacha_menus(id)
);

CREATE TABLE IF NOT EXISTS gacha_result_details(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gacha_result_id BIGINT NOT NULL, -- 親テーブルへの紐付け
    num INT NOT NULL,
    rank VARCHAR(2) NOT NUll,
    FOREIGN KEY (gacha_result_details) REFERENCES gacha_results(id) ON DELETE CASCADE
);