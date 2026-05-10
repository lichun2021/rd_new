-- 修复：星能探索 JSON 序列化长度超过 varchar(512) 导致 Data truncation
-- 在已存在的库上执行一次即可（与 game.sql 中 commander.starExplore 定义对齐）
ALTER TABLE `commander` MODIFY COLUMN `starExplore` text COLLATE utf8mb4_unicode_ci NOT NULL;
