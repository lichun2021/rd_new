-- 2026-10-15: 1.4.111 客户端 1121~1124 英雄 ID 对齐迁移脚本
-- 背景：旧服 1121(艾伦)/1122(莱克莎)/1123(雪拉) 在 1.4.111 客户端整体后移一位为 1122(艾伦)/1123(莱克莎)/1124(雪拉)，1121 新增莱因。
-- 按从大到小顺序 (1123 -> 1124, 1122 -> 1123, 1121 -> 1122) 迁移存量数据，避免主键/唯一索引冲突。

-- 1. 英雄表 (hero)
UPDATE `hero`
SET `heroId` = 1124,
    `passiveSkill` = REPLACE(`passiveSkill`, '11230', '11240'),
    `skillBlank` = REPLACE(`skillBlank`, '11231', '11241')
WHERE `heroId` = 1123;

UPDATE `hero`
SET `heroId` = 1123,
    `passiveSkill` = REPLACE(`passiveSkill`, '11220', '11230'),
    `soulSerialized` = REPLACE(REPLACE(`soulSerialized`, '11220', '11230'), '"heroId":1122', '"heroId":1123')
WHERE `heroId` = 1122;

UPDATE `hero`
SET `heroId` = 1122,
    `passiveSkill` = REPLACE(`passiveSkill`, '11210', '11220'),
    `skillBlank` = REPLACE(`skillBlank`, '11211', '11221')
WHERE `heroId` = 1121;

-- 2. 道具表 (item) 英雄碎片与招募卡随英雄 ID 同步后移（仅在未拥有目标道具时迁移）
UPDATE `item` SET `itemId` = 1001124 WHERE `itemId` = 1001123;
UPDATE `item` SET `itemId` = 1101124 WHERE `itemId` = 1101123;
UPDATE `item` SET `itemId` = 1001123 WHERE `itemId` = 1001122;
UPDATE `item` SET `itemId` = 1101123 WHERE `itemId` = 1101122;
UPDATE `item` SET `itemId` = 1001122 WHERE `itemId` = 1001121;
UPDATE `item` SET `itemId` = 1101122 WHERE `itemId` = 1101121;
