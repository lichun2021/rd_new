-- 欧米伽计划：三个模块等级持久化在曼哈顿底座记录中。
ALTER TABLE `manhattan`
  ADD COLUMN `omegaPosLevel` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' AFTER `posLevel`;
