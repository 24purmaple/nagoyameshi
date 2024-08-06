-- rolesテーブル
INSERT IGNORE INTO roles (id, role_name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, role_name) VALUES (2, 'ROLE_MEMBER');
INSERT IGNORE INTO roles (id, role_name) VALUES (3, 'ROLE_ADMIN');

-- usersテーブル
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (1, 3, '管理 者', 'かんり しゃ', 'admin@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true );
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (2, 1, '無料 会員', 'むりょう かいいん', 'general@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true );
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (3, 1, '有料 会員', 'ゆうりょう かいいん', 'member@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true );
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (4, 1, '侍 正保', 'サムライ マサヤス', 'masayasu.samurai@example.com', '090-1234-5678', 'password', false );
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (5, 1, '侍 真由美', 'mayumi.samurai@example.com', 'サムライ マユミ','090-1234-5678', 'password', false );

-- restaurantsテーブル
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (1, 'SAMURAIの宿', 'restaurant01.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 500, 2000, 10, '08:00', '20:00', '月曜日', '073-0145', '北海道砂川市西五条南X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (2, 'ペンション SAMURAI', 'restaurant02.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 1000, 3000, 20, '10:00', '22:00', '日曜日', '030-0945', '青森県青森市桜川X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (3, 'SAMURAI荘', 'restaurant03.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 800, 2000, 30, '11:00', '22:00', '水曜日', '029-5618',  '岩手県和賀郡西和賀町沢内両沢X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (4, 'ゲストハウス SAMURAI', 'restaurant04.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 1200, 5000, 50,'15:00', '23:00', '月曜日、火曜日', '989-0555', '宮城県刈田郡七ヶ宿町滝ノ上X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (5, 'SAMURAI屋', 'restaurant05.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 600, 6000, 50, '09:00', '23:00', '018-2661', '金', '秋田県山本郡八峰町八森浜田X-XX-XX', '012-345-678');

-- categoriesテーブルにデータを挿入
INSERT IGNORE INTO categories (id, category_name) VALUES (1, '和食');
INSERT IGNORE INTO categories (id, category_name) VALUES (2, '洋食');
INSERT IGNORE INTO categories (id, category_name) VALUES (3, '中華');
INSERT IGNORE INTO categories (id, category_name) VALUES (4, 'イタリアン');

-- restaurantCategoryテーブルにデータを挿入
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (1, 1, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (2, 2, 2);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (3, 3, 3);

-- reviewテーブルにデータを挿入
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (1, 1, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (2, 2, 1, 4, 'とても美味しかったです。');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (3, 3, 1, 3, '普通の味でした。');

-- favoriteテーブルにデータを挿入
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(1, 1, 1 );
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(2, 1, 2 );
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(3, 1, 3 );

-- reservationテーブルにデータを挿入
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (1, 1, 1, '2024-08-01', '19:00', 2 );
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (2, 1, 2, '2024-08-02', '18:00', 4 );
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (3, 1, 3, '2024-08-03', '20:00', 3 );

-- historyテーブルにデータを挿入
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (1, 1, 1 );
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (2, 1, 2 );
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (3, 1, 3 );

-- policyテーブルにデータを挿入
INSERT IGNORE INTO policies (id, policy_text, effective_date) VALUES (1, '利用規約1', '2024-01-01');
INSERT IGNORE INTO policies (id, policy_text, effective_date) VALUES (2, '利用規約2', '2024-02-01');
INSERT IGNORE INTO policies (id, policy_text, effective_date) VALUES (3, '利用規約3', '2024-03-01');

-- company_infoテーブルにデータを挿入
INSERT IGNORE INTO company_info (id, company_name, description, address, phone_number, email) VALUES (1, '株式会社A', '飲食店情報サービス', '東京都渋谷区1-1-1', '03-1111-2222', 'info@companyA.co.jp');
INSERT IGNORE INTO company_info (id, company_name, description, address, phone_number, email) VALUES (2, '株式会社B', 'オンライン予約サービス', '東京都新宿区2-2-2', '03-2222-3333', 'info@companyB.co.jp');
INSERT IGNORE INTO company_info (id, company_name, description, address, phone_number, email) VALUES (3, '株式会社C', 'レストランレビューサイト', '東京都品川区3-3-3', '03-3333-4444', 'info@companyC.co.jp');

-- edit_historyテーブルにデータを挿入
INSERT IGNORE INTO edit_histories (id, user_id, edited_table, record_id, action_type) VALUES (1, 1, 'restaurants', 1, 'update');
INSERT IGNORE INTO edit_histories (id, user_id, edited_table, record_id, action_type) VALUES (2, 2, 'review', 2, 'insert');
INSERT IGNORE INTO edit_histories (id, user_id, edited_table, record_id, action_type) VALUES (3, 3, 'favorite', 3, 'delete');

-- subscriptionテーブル
INSERT IGNORE INTO subscriptions (subscription_start_date, subscription_end_date, stripe_customer_id, stripe_subscription_id) VALUES ('2023-01-01', '2023-01-31', 'cus_test1', 'sub_test1');
INSERT IGNORE INTO subscriptions (subscription_start_date, subscription_end_date, stripe_customer_id, stripe_subscription_id) VALUES ('2023-02-01', '2024-01-31', 'cus_test2', 'sub_test2');
