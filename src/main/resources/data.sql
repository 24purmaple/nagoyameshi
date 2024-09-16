-- rolesテーブル
INSERT IGNORE INTO roles (id, role_name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, role_name) VALUES (2, 'ROLE_MEMBER');
INSERT IGNORE INTO roles (id, role_name) VALUES (3, 'ROLE_ADMIN');

-- usersテーブル
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (1, 3, '管理 者', 'かんり しゃ', 'admin@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (2, 1, '無料 会員', 'むりょう かいいん', 'general@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (3, 2, '有料 会員', 'ゆうりょう かいいん', 'member@example.com', '090-1234-5678', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (4, 2, '侍 正保', 'サムライ マサヤス', 'masayasu.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (5, 2, '侍 真由美', 'サムライ マユミ', 'mayumi.samurai@example.com','090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (6, 2, '侍 修一', 'サムライ シュウイチ', 'shuichi.samurai@sample.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (7, 2, '侍 佐藤', 'サムライ サトウ', 'sato.samurai@sample.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (8, 2, '侍 真司', 'サムライ シンジ', 'shinji.samurai@sample.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (9, 2, '侍 義夫', 'サムライ ヨシオ', 'yoshio.samurai@sample.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (6, 2, '侍 正保', 'サムライ マサヤス', 'masayasu.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (7, 2, '侍 真由美', 'サムライ マユミ', 'mayumi.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (8, 2, '侍 安民', 'サムライ ヤスタミ', '090-1234-5678', 'yasutami.samurai@example.com', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (9, 2, '侍 章緒', 'サムライ アキオ', 'akio.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (10, 2, '侍 祐子', 'サムライ ユウコ', 'yuko.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (11, 2, '侍 秋美', 'サムライ アキミ', 'akimi.samurai@example.com', '090-1234-5678', 'password', false);
INSERT IGNORE INTO users (id, role_id, user_name, furigana, email, phone_number, password, enabled) VALUES (12, 2, '侍 信平', 'サムライ シンペイ', 'shinpei.samurai@example.com', '090-1234-5678', 'password', false);


-- restaurantsテーブル
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (1, 'SAMURAIの宿', 'restaurant01.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 500, 2000, 10, '08:00', '20:00', '["月曜日", "火曜日"]', '073-0145', '北海道砂川市西五条南X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (2, 'ペンション SAMURAI', 'restaurant02.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 1000, 3000, 20, '10:00', '22:00', '["日曜日"]', '030-0945', '青森県青森市桜川X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (3, 'SAMURAI荘', 'restaurant03.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 800, 2000, 30, '11:00', '22:00', '["水曜日"]', '029-5618',  '岩手県和賀郡西和賀町沢内両沢X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (4, 'ゲストハウス SAMURAI', 'restaurant04.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 1200, 5000, 50,'15:00', '23:00', '["月曜日","火曜日"]', '989-0555', '宮城県刈田郡七ヶ宿町滝ノ上X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (5, 'SAMURAI屋', 'restaurant05.jpg', '最寄り駅から徒歩10分。自然豊かで閑静な場所にあります。長期滞在も可能です。', 600, 6000, 50, '09:00', '23:00', '["金曜日"]', '018-2661', '秋田県山本郡八峰町八森浜田X-XX-XX', '012-345-678');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (6, '味噌煮込み 一刻', 'restaurant01.jpg', 'こだわりの味噌で煮込んだ絶品の味噌煮込みを提供。アットホームな雰囲気でお待ちしております。', 2000, 5000, 30, '11:00', '21:00', '["土曜日", "日曜日", "祝日"]', '460-0001', '愛知県名古屋市中区栄1-1-1', '052-1111-1111');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (7, '台湾ラーメン 蓮', 'restaurant02.jpg', '本場の台湾ラーメンを提供するお店。こだわりのスープと自家製麺が自慢です。', 2000, 5000, '11:30', '22:00', 25, '["火曜日", "日曜日", "祝日"]', '460-0002', '愛知県名古屋市中区栄2-2-2', '052-2222-2222');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (8, '味噌カツ 三春', 'restaurant03.jpg', 'サクサクの衣とジューシーな味噌カツが自慢のお店。地元の人に愛される味をご堪能ください。', 2000, 5000, 40, '11:00', '20:30', '["水曜日"]', '460-0003', '愛知県名古屋市中区栄3-3-3', '052-3333-3333');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (9, 'あんかけスパゲッティ 茜', 'restaurant04.jpg', '濃厚なあんかけソースが特徴のスパゲッティ専門店。リーズナブルで美味しい一品をご提供します。', 1000, 5000, 35, '11:30', '21:30', '["全日営業"]', '460-0004', '愛知県名古屋市中区栄4-4-4', '052-4444-4444');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (10, '手羽先の名店 かもの屋', 'restaurant05.jpg', '名古屋風手羽先の専門店。ジューシーで香ばしい手羽先を心ゆくまでお楽しみください。', 1000, 4000, 28, '17:00', '23:00', '["月曜日"]', '460-0005', '愛知県名古屋市中区栄5-5-5', '052-5555-5555');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (11, '味噌炭火焼肉 こはな', 'restaurant06.jpg', 'こだわりの味噌ダレで炭火焼肉を楽しむお店。新鮮な肉と絶妙な調理法が自慢です。', 3000, 6000, 20, '18:00', '00:00', '["火曜日","水曜日"]', '460-0006', '愛知県名古屋市中区栄6-6-6', '052-6666-6666');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (12, '手作りうどん 蓮華', 'restaurant07.jpg', '自家製の手打ちうどんが自慢のお店。優しい出汁とコシのある麺をお楽しみください。', 1000, 4000, 45, '11:00', '20:00', '["木曜日"]', '460-0007', '愛知県名古屋市中区栄7-7-7', '052-7777-7777');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (13, '味噌串カツ 串匠', 'restaurant08.jpg', '名古屋名物の味噌串カツを提供するお店。サクサクの衣と味噌の相性が抜群です。', 2000, 5000, 30, '17:30', '22:30', '["土曜日", "日曜日", "祝日"]', '460-0008', '愛知県名古屋市中区栄8-8-8', '052-8888-8888');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (14, 'きしめん専門店 はるや', 'restaurant09.jpg', '名古屋の郷土料理、きしめんを存分に味わえるお店。シンプルながらも風味豊かな逸品です。', 1000, 4000, 30, '12:00', '19:30', '["全日営業"]', '460-0009', '愛知県名古屋市中区栄9-9-9', '052-9999-9999');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (15, '味噌カレー こふく', 'restaurant10.jpg', 'こだわりの味噌とカレーが融合した絶品の味噌カレーを提供。スパイスの香りが食欲をそそります。', 2000, 5000, 35, '11:30', '20:30', '["土曜日"]', '460-0010', '愛知県名古屋市中区栄10-10-10', '052-1010-1010');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (16, 'みそかつ亭', 'restaurant10.jpg', '名古屋の味噌かつをリーズナブルに楽しめるお店。サクサクの衣と濃厚な味噌ソースが自慢です。', 2000, 5000, 35, '11:30', '20:30', '["金曜日"]', '460-0011', '愛知県名古屋市中区栄11-11-11', '052-1111-1111');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (17, '台湾小吃屋 台湾小', 'restaurant01.jpg', '台湾の風味豊かな小吃（点心）を楽しめるアットホームなお店。本場の味をご堪能ください。', 2000, 5000, 30, '11:00', '21:00', '["火曜日","木曜日"]', '460-0012', '愛知県名古屋市中区栄12-12-12', '052-1212-1212');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (18, '味噌串焼き 串屋', 'restaurant03.jpg', '名古屋名物の味噌串焼きが味わえるおしゃれな串焼き屋。リーズナブルでボリューム満点です。', 1000, 4000, 25, '18:00', '00:00', '["土曜日", "日曜日", "祝日"]', '460-0013', '愛知県名古屋市中区栄13-13-13', '052-1313-1313');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (19, '手打ちうどん 一滴', 'restaurant04.jpg', 'こだわりの手打ちうどんが楽しめるお店。優しい出汁と自家製の麺が自慢です。', 1000, 4000, 40, '11:00', '20:00', '["土曜日"]', '460-0014', '愛知県名古屋市中区栄14-14-14', '052-1414-1414');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (20, '味噌カツカレー かつ善', 'restaurant05.jpg', '味噌カツとカレーの絶妙なマリアージュを楽しめるお店。ボリューム満点で大満足の一皿です。', 2000, 5000, 35, '11:30', '20:30', '["火曜日"]', '460-0015', '愛知県名古屋市中区栄15-15-15', '052-1515-1515');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (21, '名古屋味噌ラーメン みそや', 'restaurant06.jpg', '名古屋の風味豊かな味噌ラーメンが楽しめる老舗のお店。こだわりの味をご賞味ください。', 2000, 5000, 30, '11:30', '21:00', '["木曜日"]', '460-0016', '愛知県名古屋市中区栄16-16-16', '052-1616-1616');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (22, 'あんかけシチュー ことぶき', 'restaurant07.jpg', '濃厚なあんかけソースが特徴のシチュー専門店。ホッとする味わいを提供しております。', 1000, 4000, 28, '11:00', '21:00', '["日曜日", "祝日"]', '460-0017', '愛知県名古屋市中区栄17-17-17', '052-1717-1717');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (23, '手羽先BAR やまと', 'restaurant08.jpg', '地元で愛される手羽先の専門店。こだわりのソースで味わう極上の手羽先をご提供します。', 1000, 4000, 28, '17:00', '23:00', '["火曜日", "金曜日"]', '460-0018', '愛知県名古屋市中区栄18-18-18', '052-1818-1818');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (24, '味噌焼きそば 風花', 'restaurant09.jpg', '名古屋風の味噌焼きそばが楽しめるお店。モチモチの麺とコクのある味噌が絶妙なバランスです。', 1000, 4000, 35, '11:30', '20:30', '["月曜日", "水曜日", "金曜日"]', '460-0019', '愛知県名古屋市中区栄19-19-19', '052-1919-1919');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (25, '味噌寿司専門店 こはる', 'restaurant02.jpg', '新しい味噌の楽しみ方、味噌寿司を提供するお店。新鮮なネタと味噌の風味が絶品です。', 3000, 6000, 20, '18:00', '00:00', '["日曜日"]', '460-0020', '愛知県名古屋市中区栄20-20-20', '052-2020-2020');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (26, 'しゃぶしゃぶ温野菜', 'restaurant01.jpg', '新鮮な野菜とこだわりの肉で楽しむしゃぶしゃぶが自慢のお店。健康を意識したメニューも豊富です。', 3000, 6000, 40, '17:00', '22:00', '["金曜日"]', '460-0021', '愛知県名古屋市中区栄21-21-21', '052-2121-2121');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (27, 'イタリアンダイニング ベッラ', 'restaurant02.jpg', '本場イタリアの味を味わえるイタリアンダイニング。厳選された素材とシェフの腕前が光ります。', 4000, 8000, 30, '11:30', '21:30', '["火曜日"]', '460-0022', '愛知県名古屋市中区栄22-22-22', '052-2222-2222');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (28, '和風バル 花鳥風月', 'restaurant03.jpg', '和風とモダンが融合した雰囲気のバル。季節感あふれる料理とこだわりの日本酒が楽しめます。', 4000, 7000, 25, '18:00', '00:00', '["土曜日", "日曜日"]', '460-0023', '愛知県名古屋市中区栄23-23-23', '052-2323-2323');
INSERT IGNORE INTO restaurants (id, restaurant_name, image_name, description, min_price, max_price, capacity, opening_time, closing_time, closed_days, postal_code, address, phone_number) VALUES (29, 'カフェ ラ・パンディ', 'restaurant04.jpg', 'ここでしか味わえない特製のパンケーキが自慢のカフェ。アートな雰囲気でゆったりとお過ごしください。', 2000, 4000, 20, '10:00', '18:00', '["木曜日", "金曜日"]', '460-0024', '愛知県名古屋市中区栄24-24-24', '052-2424-2424');

-- categoriesテーブルにデータを挿入
INSERT IGNORE INTO categories (id, category_name) VALUES (1, '和食');
INSERT IGNORE INTO categories (id, category_name) VALUES (2, '洋食');
INSERT IGNORE INTO categories (id, category_name) VALUES (3, '中華');
INSERT IGNORE INTO categories (id, category_name) VALUES (4, 'イタリアン');

-- restaurantCategoryテーブルにデータを挿入
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (1, 1, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (2, 2, 2);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (3, 3, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (4, 4, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (5, 5, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (6, 6, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (7, 7, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (8, 8, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (9, 9, 4);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (10, 10, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (11, 11, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (12, 12, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (13, 13, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (14, 14, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (15, 15, 2);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (16, 16, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (17, 17, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (18, 18, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (19, 19, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (20, 20, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (21, 21, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (22, 22, 2);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (23, 23, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (24, 24, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (25, 25, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (26, 26, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (27, 27, 4);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (28, 28, 1);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (29, 29, 3);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (30, 29, 4);
INSERT IGNORE INTO restaurant_categories (id, restaurant_id, category_id) VALUES (31, 1, 2);


-- reviewテーブルにデータを挿入
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (2, 3, 1, 3, '普通の味でした。');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (3, 4, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (4, 5, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (5, 6, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (6, 7, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (7, 8, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (8, 9, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (9, 10, 1, 5, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (10, 11, 1, 3, '素晴らしい寿司でした！');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, score, comment) VALUES (11, 12, 1, 4, '素晴らしい寿司でした！');
-- favoriteテーブルにデータを挿入
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(1, 3, 1 );
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(2, 3, 2 );
INSERT IGNORE INTO favorites (id, user_id, restaurant_id) VALUES(3, 3, 3 );

-- reservationテーブルにデータを挿入
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (1, 3, 1, '2024-08-01', '19:00', 2 );
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (2, 3, 2, '2024-08-02', '18:00', 4 );
INSERT IGNORE INTO reservations (id, user_id, restaurant_id, reservation_date, reservation_time, number_of_people) VALUES (3, 3, 3, '2024-08-03', '20:00', 3 );

/*-- historyテーブルにデータを挿入
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (1, 3, 1 );
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (2, 3, 2 );
INSERT IGNORE INTO histories (id, user_id, restaurant_id) VALUES (3, 3, 3 );*/

-- company_infoテーブルにデータを挿入
INSERT IGNORE INTO company (id, company_name, postal_code, address, managing_director, established, capital, service, employees, phone_number, email) VALUES (1, 'NAGOYAMESHI株式会社', '101-0022', '東京都千代田区神田練塀町300番地 住友不動産秋葉原駅前ビル5F', '侍 太郎', '2015-03-19', 11000000, '飲食店等の情報提供サービス', 81, '123-4567-8901', 'company@example.com');

/*-- policyテーブルにデータを挿入
INSERT IGNORE INTO policies (id, policy_text, effective_date) VALUES (1, '利用規約1', '2024-01-01');*/
