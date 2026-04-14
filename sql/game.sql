-- MySQL dump 10.13  Distrib 5.7.44, for Win64 (x86_64)
--
-- Host: localhost    Database: game_10001
-- ------------------------------------------------------
-- Server version	5.7.44-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accumulate_online`
--

DROP TABLE IF EXISTS `accumulate_online`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accumulate_online` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayCount` int(11) NOT NULL,
  `receivedId` int(11) NOT NULL,
  `receivedTime` bigint(20) NOT NULL,
  `onlineTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accumulate_online`
--

LOCK TABLES `accumulate_online` WRITE;
/*!40000 ALTER TABLE `accumulate_online` DISABLE KEYS */;
/*!40000 ALTER TABLE `accumulate_online` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `activityId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `newlyTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES ('7pt-4eerka-1',6,2,1,1776096000000,1776068234492,1776072050327,0),('7pt-4eerka-10',66,4,0,0,1776068234569,1776068234569,0),('7pt-4eerka-11',56,4,0,0,1776068234570,1776068234570,0),('7pt-4eerka-12',57,4,0,0,1776068234571,1776068234571,0),('7pt-4eerka-13',71,4,0,0,1776068234572,1776068234572,0),('7pt-4eerka-14',72,2,10,1776096000000,1776068234574,1776072050278,0),('7pt-4eerka-15',73,4,0,0,1776068234575,1776068234575,0),('7pt-4eerka-16',74,4,0,0,1776068234576,1776068234576,0),('7pt-4eerka-17',75,4,0,0,1776068234577,1776068234577,0),('7pt-4eerka-18',76,4,0,0,1776068234578,1776068234578,0),('7pt-4eerka-19',77,4,0,0,1776068234580,1776068234580,0),('7pt-4eerka-1a',78,4,0,0,1776068234581,1776068234581,0),('7pt-4eerka-1b',79,4,0,0,1776068234582,1776068234582,0),('7pt-4eerka-1c',80,4,0,0,1776068234583,1776068234583,0),('7pt-4eerka-1d',81,4,0,0,1776068234584,1776068234584,0),('7pt-4eerka-1e',82,2,24,1776096000000,1776068234585,1776072050274,0),('7pt-4eerka-1f',83,4,0,0,1776068234587,1776068234587,0),('7pt-4eerka-1g',84,4,0,0,1776068234588,1776068234588,0),('7pt-4eerka-1h',85,4,0,0,1776068234589,1776068234589,0),('7pt-4eerka-1i',86,4,0,0,1776068234590,1776068234590,0),('7pt-4eerka-1j',87,4,0,0,1776068234593,1776068234593,0),('7pt-4eerka-1k',88,4,0,0,1776068234594,1776068234594,0),('7pt-4eerka-1l',89,4,0,0,1776068234595,1776068234595,0),('7pt-4eerka-1m',91,4,0,0,1776068234601,1776068234601,0),('7pt-4eerka-1n',92,4,0,0,1776068234602,1776068234602,0),('7pt-4eerka-1o',93,4,0,0,1776068234603,1776068234603,0),('7pt-4eerka-1p',95,4,0,0,1776068234604,1776068234604,0),('7pt-4eerka-1q',96,4,0,0,1776068234606,1776068234606,0),('7pt-4eerka-1r',97,4,0,0,1776068234607,1776068234607,0),('7pt-4eerka-1s',98,4,0,0,1776068234609,1776068234609,0),('7pt-4eerka-1t',99,4,0,0,1776068234611,1776068234611,0),('7pt-4eerka-1u',100,4,0,0,1776068234612,1776068234612,0),('7pt-4eerka-1v',101,4,0,0,1776068234613,1776068234613,0),('7pt-4eerka-1w',102,4,0,0,1776068234614,1776068234614,0),('7pt-4eerka-1x',103,4,0,0,1776068234615,1776068234615,0),('7pt-4eerka-1y',104,4,0,0,1776068234617,1776068234617,0),('7pt-4eerka-1z',105,4,0,0,1776068234618,1776068234618,0),('7pt-4eerka-2',7,4,0,0,1776068234508,1776068234508,0),('7pt-4eerka-20',106,4,0,0,1776068234619,1776068234619,0),('7pt-4eerka-21',107,4,0,0,1776068234620,1776068234620,0),('7pt-4eerka-22',108,4,0,0,1776068234622,1776068234622,0),('7pt-4eerka-23',109,4,0,0,1776068234624,1776068234624,0),('7pt-4eerka-24',110,4,0,0,1776068234626,1776068234626,0),('7pt-4eerka-25',112,4,0,0,1776068234627,1776068234627,0),('7pt-4eerka-26',114,4,0,0,1776068234628,1776068234628,0),('7pt-4eerka-27',111,4,0,0,1776068234630,1776068234630,0),('7pt-4eerka-28',113,4,0,0,1776068234632,1776068234632,0),('7pt-4eerka-29',116,4,0,0,1776068234637,1776068234637,0),('7pt-4eerka-2a',117,4,0,0,1776068234640,1776068234640,0),('7pt-4eerka-2b',115,4,0,0,1776068234641,1776068234641,0),('7pt-4eerka-2c',118,4,0,0,1776068234642,1776068234642,0),('7pt-4eerka-2d',120,4,0,0,1776068234644,1776068234644,0),('7pt-4eerka-2e',121,4,0,0,1776068234646,1776068234646,0),('7pt-4eerka-2f',123,4,0,0,1776068234647,1776068234647,0),('7pt-4eerka-2g',70,4,0,0,1776068234649,1776068234649,0),('7pt-4eerka-2h',122,4,0,0,1776068234650,1776068234650,0),('7pt-4eerka-2i',124,2,1,1776096000000,1776068234651,1776072050335,0),('7pt-4eerka-2j',130,4,0,0,1776068234652,1776068234652,0),('7pt-4eerka-2k',126,4,0,0,1776068234654,1776068234654,0),('7pt-4eerka-2l',128,4,0,0,1776068234655,1776068234655,0),('7pt-4eerka-2m',127,4,0,0,1776068234657,1776068234657,0),('7pt-4eerka-2n',125,4,0,0,1776068234658,1776068234658,0),('7pt-4eerka-2o',131,4,0,0,1776068234660,1776068234660,0),('7pt-4eerka-2p',132,4,0,0,1776068234661,1776068234661,0),('7pt-4eerka-2q',134,4,0,0,1776068234663,1776068234663,0),('7pt-4eerka-2r',133,4,0,0,1776068234664,1776068234664,0),('7pt-4eerka-2s',135,4,0,0,1776068234665,1776068234665,0),('7pt-4eerka-2t',136,4,0,0,1776068234666,1776068234666,0),('7pt-4eerka-2u',137,4,0,0,1776068234668,1776068234668,0),('7pt-4eerka-2v',138,4,0,0,1776068234669,1776068234669,0),('7pt-4eerka-2w',140,2,1,1776096000000,1776068234670,1776072050325,0),('7pt-4eerka-2x',139,4,0,0,1776068234672,1776068234672,0),('7pt-4eerka-2y',142,4,0,0,1776068234673,1776068234673,0),('7pt-4eerka-2z',144,4,0,0,1776068234675,1776068234675,0),('7pt-4eerka-3',10,4,0,0,1776068234511,1776068234511,0),('7pt-4eerka-30',143,4,0,0,1776068234676,1776068234676,0),('7pt-4eerka-31',58,4,0,0,1776068234678,1776068234678,0),('7pt-4eerka-32',147,4,0,0,1776068234679,1776068234679,0),('7pt-4eerka-33',148,4,0,0,1776068234680,1776068234680,0),('7pt-4eerka-34',149,4,0,0,1776068234686,1776068234686,0),('7pt-4eerka-35',151,4,0,0,1776068234687,1776068234687,0),('7pt-4eerka-36',146,4,0,0,1776068234688,1776068234688,0),('7pt-4eerka-37',150,4,0,0,1776068234690,1776068234690,0),('7pt-4eerka-38',155,4,0,0,1776068234691,1776068234691,0),('7pt-4eerka-39',153,4,0,0,1776068234692,1776068234692,0),('7pt-4eerka-3a',154,4,0,0,1776068234694,1776068234694,0),('7pt-4eerka-3b',152,4,0,0,1776068234695,1776068234695,0),('7pt-4eerka-3c',156,4,0,0,1776068234697,1776068234697,0),('7pt-4eerka-3d',157,4,0,0,1776068234698,1776068234698,0),('7pt-4eerka-3e',159,4,0,0,1776068234700,1776068234700,0),('7pt-4eerka-3f',160,4,0,0,1776068234702,1776068234702,0),('7pt-4eerka-3g',161,4,0,0,1776068234703,1776068234703,0),('7pt-4eerka-3h',165,4,0,0,1776068234705,1776068234705,0),('7pt-4eerka-3i',163,4,0,0,1776068234707,1776068234707,0),('7pt-4eerka-3j',164,4,0,0,1776068234711,1776068234711,0),('7pt-4eerka-3k',166,4,0,0,1776068234715,1776068234715,0),('7pt-4eerka-3l',167,4,0,0,1776068234719,1776068234719,0),('7pt-4eerka-3m',168,4,0,0,1776068234727,1776068234727,0),('7pt-4eerka-3n',169,4,0,0,1776068234728,1776068234728,0),('7pt-4eerka-3o',170,4,0,0,1776068234732,1776068234732,0),('7pt-4eerka-3p',171,4,0,0,1776068234736,1776068234736,0),('7pt-4eerka-3q',172,4,0,0,1776068234739,1776068234739,0),('7pt-4eerka-3r',173,2,13,1776096000000,1776068234743,1776072050274,0),('7pt-4eerka-3s',174,2,13,1776096000000,1776068234746,1776072050325,0),('7pt-4eerka-3t',176,2,13,1776096000000,1776068234749,1776072050277,0),('7pt-4eerka-3u',178,2,13,1776096000000,1776068234752,1776072050320,0),('7pt-4eerka-3v',179,4,0,0,1776068234755,1776068234755,0),('7pt-4eerka-3w',180,4,0,0,1776068234761,1776068234761,0),('7pt-4eerka-3x',188,4,0,0,1776068234765,1776068234765,0),('7pt-4eerka-3y',184,4,0,0,1776068234769,1776068234769,0),('7pt-4eerka-3z',185,4,0,0,1776068234773,1776068234773,0),('7pt-4eerka-4',13,4,0,0,1776068234512,1776068234512,0),('7pt-4eerka-40',189,4,0,0,1776068234777,1776068234777,0),('7pt-4eerka-41',190,4,0,0,1776068234781,1776068234781,0),('7pt-4eerka-42',191,4,0,0,1776068234785,1776068234785,0),('7pt-4eerka-43',198,4,0,0,1776068234788,1776068234788,0),('7pt-4eerka-44',192,4,0,0,1776068234791,1776068234791,0),('7pt-4eerka-45',193,4,0,0,1776068234796,1776068234796,0),('7pt-4eerka-46',194,4,0,0,1776068234800,1776068234800,0),('7pt-4eerka-47',195,4,0,0,1776068234804,1776068234804,0),('7pt-4eerka-48',196,4,0,0,1776068234808,1776068234808,0),('7pt-4eerka-49',197,4,0,0,1776068234812,1776068234812,0),('7pt-4eerka-4a',201,4,0,0,1776068234816,1776068234816,0),('7pt-4eerka-4b',204,4,0,0,1776068234821,1776068234821,0),('7pt-4eerka-4c',207,4,0,0,1776068234824,1776068234824,0),('7pt-4eerka-4d',208,4,0,0,1776068234829,1776068234829,0),('7pt-4eerka-4e',206,4,0,0,1776068234832,1776068234832,0),('7pt-4eerka-4f',205,4,0,0,1776068234835,1776068234835,0),('7pt-4eerka-4g',210,4,0,0,1776068234839,1776068234839,0),('7pt-4eerka-4h',209,4,0,0,1776068234842,1776068234842,0),('7pt-4eerka-4i',211,2,1,1776096000000,1776068234845,1776072050323,0),('7pt-4eerka-4j',215,4,0,0,1776068234847,1776068234847,0),('7pt-4eerka-4k',225,4,0,0,1776068234852,1776068234852,0),('7pt-4eerka-4l',227,4,0,0,1776068234858,1776068234858,0),('7pt-4eerka-4m',220,4,0,0,1776068234863,1776068234863,0),('7pt-4eerka-4n',230,4,0,0,1776068234869,1776068234869,0),('7pt-4eerka-4o',236,4,0,0,1776068234874,1776068234874,0),('7pt-4eerka-4p',243,4,0,0,1776068234877,1776068234877,0),('7pt-4eerka-4q',244,4,0,0,1776068234881,1776068234881,0),('7pt-4eerka-4r',216,4,0,0,1776068234884,1776068234884,0),('7pt-4eerka-4s',245,4,0,0,1776068234889,1776068234889,0),('7pt-4eerka-4t',241,4,0,0,1776068234893,1776068234893,0),('7pt-4eerka-4u',239,4,0,0,1776068234896,1776068234896,0),('7pt-4eerka-4v',238,4,0,0,1776068234899,1776068234899,0),('7pt-4eerka-4w',240,4,0,0,1776068234905,1776068234905,0),('7pt-4eerka-4x',242,4,0,0,1776068234907,1776068234907,0),('7pt-4eerka-4y',219,4,0,0,1776068234910,1776068234910,0),('7pt-4eerka-4z',253,2,0,0,1776068234915,1776072050344,0),('7pt-4eerka-5',15,4,0,0,1776068234516,1776068234516,0),('7pt-4eerka-50',260,4,0,0,1776068234919,1776068234919,0),('7pt-4eerka-51',261,4,0,0,1776068234923,1776068234923,0),('7pt-4eerka-52',262,2,1,1776096000000,1776068234927,1776072050327,0),('7pt-4eerka-53',263,2,1,1776096000000,1776068234930,1776072050274,0),('7pt-4eerka-54',267,4,0,0,1776068234933,1776068234933,0),('7pt-4eerka-55',264,4,0,0,1776068234936,1776068234936,0),('7pt-4eerka-56',265,4,0,0,1776068234938,1776068234938,0),('7pt-4eerka-57',266,4,0,0,1776068234940,1776068234940,0),('7pt-4eerka-58',268,4,0,0,1776068234942,1776068234942,0),('7pt-4eerka-59',270,4,0,0,1776068234944,1776068234944,0),('7pt-4eerka-5a',271,4,0,0,1776068234946,1776068234946,0),('7pt-4eerka-5b',272,4,0,0,1776068234948,1776068234948,0),('7pt-4eerka-5c',273,4,0,0,1776068234950,1776068234950,0),('7pt-4eerka-5d',274,4,0,0,1776068234952,1776068234952,0),('7pt-4eerka-5e',275,4,0,0,1776068234954,1776068234954,0),('7pt-4eerka-5f',269,4,0,0,1776068234956,1776068234956,0),('7pt-4eerka-5g',276,4,0,0,1776068234959,1776068234959,0),('7pt-4eerka-5h',277,4,0,0,1776068234961,1776068234961,0),('7pt-4eerka-5i',278,4,0,0,1776068234963,1776068234963,0),('7pt-4eerka-5j',279,4,0,0,1776068234965,1776068234965,0),('7pt-4eerka-5k',280,4,0,0,1776068234968,1776068234968,0),('7pt-4eerka-5l',281,2,11,1776096000000,1776068234970,1776072050278,0),('7pt-4eerka-5m',282,4,0,0,1776068234972,1776068234972,0),('7pt-4eerka-5n',284,2,1,1776096000000,1776068234974,1776072050275,0),('7pt-4eerka-5o',285,2,1,1776096000000,1776068234978,1776072050335,0),('7pt-4eerka-5p',283,4,0,0,1776068234979,1776068234979,0),('7pt-4eerka-5q',286,4,0,0,1776068234982,1776068234982,0),('7pt-4eerka-5r',287,4,0,0,1776068234983,1776068234983,0),('7pt-4eerka-5s',288,4,0,0,1776068234986,1776068234986,0),('7pt-4eerka-5t',289,4,0,0,1776068234989,1776068234989,0),('7pt-4eerka-5u',290,2,1,1776096000000,1776068234991,1776072050327,0),('7pt-4eerka-5v',293,4,0,0,1776068234993,1776068234993,0),('7pt-4eerka-5w',294,4,0,0,1776068234995,1776068234995,0),('7pt-4eerka-5x',295,4,0,0,1776068234998,1776068234998,0),('7pt-4eerka-6',16,4,0,0,1776068234518,1776068234518,0),('7pt-4eerka-7',17,4,0,0,1776068234520,1776068234520,0),('7pt-4eerka-8',18,4,0,0,1776068234523,1776068234523,0),('7pt-4eerka-9',19,2,1,1776096000000,1776068234524,1776072050275,0),('7pt-4eerka-a',20,4,0,0,1776068234527,1776068234527,0),('7pt-4eerka-b',21,2,1,1776096000000,1776068234529,1776072050363,0),('7pt-4eerka-c',22,2,1,1776096000000,1776068234531,1776072050335,0),('7pt-4eerka-d',25,4,0,0,1776068234533,1776068234533,0),('7pt-4eerka-e',26,4,0,0,1776068234537,1776068234537,0),('7pt-4eerka-f',29,2,1,1776096000000,1776068234538,1776072050371,0),('7pt-4eerka-g',30,2,1,1776096000000,1776068234539,1776072050325,0),('7pt-4eerka-h',1,4,0,0,1776068234541,1776068234541,0),('7pt-4eerka-i',31,4,0,0,1776068234542,1776068234542,0),('7pt-4eerka-j',36,2,1,1776096000000,1776068234544,1776072050274,0),('7pt-4eerka-k',35,2,1,1776096000000,1776068234545,1776072050335,0),('7pt-4eerka-l',38,2,0,0,1776068234547,1776072050277,0),('7pt-4eerka-m',37,4,0,0,1776068234548,1776068234548,0),('7pt-4eerka-n',39,4,0,0,1776068234550,1776068234550,0),('7pt-4eerka-o',42,4,0,0,1776068234551,1776068234551,0),('7pt-4eerka-p',45,4,0,0,1776068234553,1776068234553,0),('7pt-4eerka-q',41,4,0,0,1776068234554,1776068234554,0),('7pt-4eerka-r',40,4,0,0,1776068234555,1776068234555,0),('7pt-4eerka-s',44,4,0,0,1776068234557,1776068234557,0),('7pt-4eerka-t',43,4,0,0,1776068234558,1776068234558,0),('7pt-4eerka-u',3,4,0,0,1776068234560,1776068234560,0),('7pt-4eerka-v',53,4,0,0,1776068234562,1776068234562,0),('7pt-4eerka-w',61,4,0,0,1776068234563,1776068234563,0),('7pt-4eerka-x',62,4,0,0,1776068234565,1776068234565,0),('7pt-4eerka-y',63,4,0,0,1776068234566,1776068234566,0),('7pt-4eerka-z',64,4,0,0,1776068234567,1776068234567,0),('7pt-4eerkb-1',296,4,0,0,1776068235000,1776068235000,0),('7pt-4eerkb-10',330,4,0,0,1776068235083,1776068235083,0),('7pt-4eerkb-11',331,4,0,0,1776068235084,1776068235084,0),('7pt-4eerkb-12',332,4,0,0,1776068235085,1776068235085,0),('7pt-4eerkb-13',333,4,0,0,1776068235086,1776068235086,0),('7pt-4eerkb-14',334,4,0,0,1776068235088,1776068235088,0),('7pt-4eerkb-15',335,2,1,1776096000000,1776068235091,1776072050275,0),('7pt-4eerkb-16',336,4,0,0,1776068235096,1776068235096,0),('7pt-4eerkb-17',337,4,0,0,1776068235098,1776068235098,0),('7pt-4eerkb-18',338,4,0,0,1776068235099,1776068235099,0),('7pt-4eerkb-19',339,2,1,1776096000000,1776068235101,1776072050334,0),('7pt-4eerkb-1a',340,2,1,1776096000000,1776068235103,1776072050327,0),('7pt-4eerkb-1b',341,4,0,0,1776068235104,1776068235104,0),('7pt-4eerkb-1c',342,4,0,0,1776068235106,1776068235106,0),('7pt-4eerkb-1d',343,4,0,0,1776068235107,1776068235107,0),('7pt-4eerkb-1e',344,2,1,1776096000000,1776068235108,1776072050277,0),('7pt-4eerkb-1f',345,4,0,0,1776068235109,1776068235109,0),('7pt-4eerkb-1g',346,2,4,1776096000000,1776068235110,1776072050274,0),('7pt-4eerkb-1h',347,4,0,0,1776068235113,1776068235113,0),('7pt-4eerkb-1i',348,4,0,0,1776068235114,1776068235114,0),('7pt-4eerkb-1j',349,4,0,0,1776068235115,1776068235115,0),('7pt-4eerkb-1k',350,4,0,0,1776068235116,1776068235116,0),('7pt-4eerkb-1l',352,2,1,1776096000000,1776068235117,1776072050275,0),('7pt-4eerkb-1m',354,4,0,0,1776068235118,1776068235118,0),('7pt-4eerkb-1n',353,4,0,0,1776068235119,1776068235119,0),('7pt-4eerkb-1o',355,4,0,0,1776068235121,1776068235121,0),('7pt-4eerkb-1p',356,2,1,1776096000000,1776068235122,1776072050364,0),('7pt-4eerkb-1q',357,4,0,0,1776068235124,1776068235124,0),('7pt-4eerkb-1r',351,4,0,0,1776068235125,1776068235125,0),('7pt-4eerkb-1s',358,4,0,0,1776068235126,1776068235126,0),('7pt-4eerkb-1t',359,4,0,0,1776068235128,1776068235128,0),('7pt-4eerkb-1u',360,4,0,0,1776068235129,1776068235129,0),('7pt-4eerkb-1v',361,4,0,0,1776068235130,1776068235130,0),('7pt-4eerkb-1w',362,4,0,0,1776068235131,1776068235131,0),('7pt-4eerkb-1x',363,4,0,0,1776068235133,1776068235133,0),('7pt-4eerkb-1y',364,4,0,0,1776068235134,1776068235134,0),('7pt-4eerkb-1z',365,4,0,0,1776068235136,1776068235136,0),('7pt-4eerkb-2',292,4,0,0,1776068235002,1776068235002,0),('7pt-4eerkb-20',366,4,0,0,1776068235137,1776068235137,0),('7pt-4eerkb-21',367,2,1,1776096000000,1776068235138,1776072050325,0),('7pt-4eerkb-22',368,4,0,0,1776068235140,1776068235140,0),('7pt-4eerkb-23',369,4,0,0,1776068235141,1776068235141,0),('7pt-4eerkb-24',371,4,0,0,1776068235143,1776068235143,0),('7pt-4eerkb-25',370,2,1,1776096000000,1776068235144,1776072050275,0),('7pt-4eerkb-26',372,4,0,0,1776068235145,1776068235145,0),('7pt-4eerkb-27',374,2,1,1776096000000,1776068235146,1776072050335,0),('7pt-4eerkb-28',373,4,0,0,1776068235147,1776068235147,0),('7pt-4eerkb-29',376,2,1,1776096000000,1776068235149,1776072050321,0),('7pt-4eerkb-2a',375,4,0,0,1776068235151,1776068235151,0),('7pt-4eerkb-2b',377,4,0,0,1776068235152,1776068235152,0),('7pt-4eerkb-2c',378,4,0,0,1776068235155,1776068235155,0),('7pt-4eerkb-2d',379,4,0,0,1776068235156,1776068235156,0),('7pt-4eerkb-2e',380,4,0,0,1776068235157,1776068235157,0),('7pt-4eerkb-2f',381,4,0,0,1776068235158,1776068235158,0),('7pt-4eerkb-2g',382,4,0,0,1776068235160,1776068235160,0),('7pt-4eerkb-2h',383,4,0,0,1776068235161,1776068235161,0),('7pt-4eerkb-2i',384,4,0,0,1776068235162,1776068235162,0),('7pt-4eerkb-2j',385,4,0,0,1776068235163,1776068235163,0),('7pt-4eerkb-2k',387,4,0,0,1776068235164,1776068235164,0),('7pt-4eerkb-2l',386,4,0,0,1776068235166,1776068235166,0),('7pt-4eerkb-2m',389,4,0,0,1776068235167,1776068235167,0),('7pt-4eerkb-2n',388,4,0,0,1776068235168,1776068235168,0),('7pt-4eerkb-2o',390,4,0,0,1776068235169,1776068235169,0),('7pt-4eerkb-2p',391,4,0,0,1776068235170,1776068235170,0),('7pt-4eerkb-3',297,4,0,0,1776068235006,1776068235006,0),('7pt-4eerkb-4',298,4,0,0,1776068235007,1776068235007,0),('7pt-4eerkb-5',299,4,0,0,1776068235010,1776068235010,0),('7pt-4eerkb-6',300,4,0,0,1776068235011,1776068235011,0),('7pt-4eerkb-7',301,4,0,0,1776068235013,1776068235013,0),('7pt-4eerkb-8',302,4,0,0,1776068235016,1776068235016,0),('7pt-4eerkb-9',305,4,0,0,1776068235018,1776068235018,0),('7pt-4eerkb-a',304,4,0,0,1776068235020,1776068235020,0),('7pt-4eerkb-b',303,4,0,0,1776068235023,1776068235023,0),('7pt-4eerkb-c',306,4,0,0,1776068235024,1776068235024,0),('7pt-4eerkb-d',308,4,0,0,1776068235027,1776068235027,0),('7pt-4eerkb-e',310,4,0,0,1776068235030,1776068235030,0),('7pt-4eerkb-f',307,4,0,0,1776068235032,1776068235032,0),('7pt-4eerkb-g',309,4,0,0,1776068235035,1776068235035,0),('7pt-4eerkb-h',312,2,1,1776096000000,1776068235037,1776072050327,0),('7pt-4eerkb-i',311,4,0,0,1776068235039,1776068235039,0),('7pt-4eerkb-j',313,4,0,0,1776068235041,1776068235041,0),('7pt-4eerkb-k',314,4,0,0,1776068235043,1776068235043,0),('7pt-4eerkb-l',315,4,0,0,1776068235045,1776068235045,0),('7pt-4eerkb-m',316,4,0,0,1776068235047,1776068235047,0),('7pt-4eerkb-n',317,4,0,0,1776068235049,1776068235049,0),('7pt-4eerkb-o',318,2,1,1776096000000,1776068235052,1776072050335,0),('7pt-4eerkb-p',319,4,0,0,1776068235053,1776068235053,0),('7pt-4eerkb-q',320,2,6,1776096000000,1776068235056,1776072050335,0),('7pt-4eerkb-r',321,4,0,0,1776068235066,1776068235066,0),('7pt-4eerkb-s',322,2,1,1776096000000,1776068235070,1776072050327,0),('7pt-4eerkb-t',323,2,1,1776096000000,1776068235071,1776072050335,0),('7pt-4eerkb-u',324,4,0,0,1776068235073,1776068235073,0),('7pt-4eerkb-v',325,4,0,0,1776068235075,1776068235075,0),('7pt-4eerkb-w',326,4,0,0,1776068235077,1776068235077,0),('7pt-4eerkb-x',327,4,0,0,1776068235078,1776068235078,0),('7pt-4eerkb-y',328,4,0,0,1776068235080,1776068235080,0),('7pt-4eerkb-z',329,4,0,0,1776068235081,1776068235081,0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_accumulate_consume`
--

DROP TABLE IF EXISTS `activity_accumulate_consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_accumulate_consume` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_accumulate_consume`
--

LOCK TABLES `activity_accumulate_consume` WRITE;
/*!40000 ALTER TABLE `activity_accumulate_consume` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_accumulate_consume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_accumulate_recharge`
--

DROP TABLE IF EXISTS `activity_accumulate_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_accumulate_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_accumulate_recharge`
--

LOCK TABLES `activity_accumulate_recharge` WRITE;
/*!40000 ALTER TABLE `activity_accumulate_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_accumulate_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_accumulate_recharge_two`
--

DROP TABLE IF EXISTS `activity_accumulate_recharge_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_accumulate_recharge_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_accumulate_recharge_two`
--

LOCK TABLES `activity_accumulate_recharge_two` WRITE;
/*!40000 ALTER TABLE `activity_accumulate_recharge_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_accumulate_recharge_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_after_competition`
--

DROP TABLE IF EXISTS `activity_after_competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_after_competition` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `homageTime` bigint(20) NOT NULL DEFAULT '0',
  `giftInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_after_competition`
--

LOCK TABLES `activity_after_competition` WRITE;
/*!40000 ALTER TABLE `activity_after_competition` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_after_competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_airdrop_supply`
--

DROP TABLE IF EXISTS `activity_airdrop_supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_airdrop_supply` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `boxNum` int(11) NOT NULL,
  `isBuy` bigint(1) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_airdrop_supply`
--

LOCK TABLES `activity_airdrop_supply` WRITE;
/*!40000 ALTER TABLE `activity_airdrop_supply` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_airdrop_supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_alliance_carnival`
--

DROP TABLE IF EXISTS `activity_alliance_carnival`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_alliance_carnival` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `receiveTimes` int(11) NOT NULL DEFAULT '0',
  `initGuildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `initGuildName` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `initCityLevel` int(11) NOT NULL DEFAULT '0',
  `buyTimes` int(11) NOT NULL DEFAULT '0',
  `buyTime` bigint(20) NOT NULL DEFAULT '0',
  `dayBuyNumber` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiveMissionTime` bigint(20) NOT NULL DEFAULT '0',
  `finishTimes` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `exchangeNumber` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `sendAdvLevel` int(11) NOT NULL DEFAULT '0',
  `payGiftTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_alliance_carnival`
--

LOCK TABLES `activity_alliance_carnival` WRITE;
/*!40000 ALTER TABLE `activity_alliance_carnival` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_alliance_carnival` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_alliance_celebrate`
--

DROP TABLE IF EXISTS `activity_alliance_celebrate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_alliance_celebrate` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `donate` int(11) NOT NULL,
  `rewardInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_alliance_celebrate`
--

LOCK TABLES `activity_alliance_celebrate` WRITE;
/*!40000 ALTER TABLE `activity_alliance_celebrate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_alliance_celebrate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_alliance_rush`
--

DROP TABLE IF EXISTS `activity_alliance_rush`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_alliance_rush` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItemsDaily` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `personalPoints` int(11) NOT NULL DEFAULT '0',
  `luckyValue` int(11) NOT NULL DEFAULT '0',
  `rewardedPersonalLvl` int(11) NOT NULL DEFAULT '0',
  `rewardedAllianceLvl` int(11) NOT NULL DEFAULT '0',
  `rewardedUpgradeLvl` int(11) NOT NULL DEFAULT '0',
  `drawCount` int(11) NOT NULL DEFAULT '0',
  `drawGachaIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_alliance_rush`
--

LOCK TABLES `activity_alliance_rush` WRITE;
/*!40000 ALTER TABLE `activity_alliance_rush` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_alliance_rush` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_alliance_share_glory`
--

DROP TABLE IF EXISTS `activity_alliance_share_glory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_alliance_share_glory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `termId` int(11) NOT NULL DEFAULT '0',
  `rewardInfoA` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardActivityA` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardInfoB` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardActivityB` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardEnergyLevelA` int(11) NOT NULL DEFAULT '0',
  `donateCountA` int(11) NOT NULL DEFAULT '0',
  `donateCountB` int(11) NOT NULL DEFAULT '0',
  `rewardEnergyLevelB` int(11) NOT NULL DEFAULT '0',
  `guildid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rewardEnergyA` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardEnergyB` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_alliance_share_glory`
--

LOCK TABLES `activity_alliance_share_glory` WRITE;
/*!40000 ALTER TABLE `activity_alliance_share_glory` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_alliance_share_glory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_alliance_wish`
--

DROP TABLE IF EXISTS `activity_alliance_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_alliance_wish` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `numbers` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `signDays` int(11) NOT NULL DEFAULT '0',
  `supplySignDays` int(11) NOT NULL DEFAULT '0',
  `lastSignTime` bigint(20) NOT NULL DEFAULT '0',
  `sendGuildCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `wishMembers` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `wishCount` int(11) NOT NULL DEFAULT '0',
  `luxuryWishCount` int(11) NOT NULL DEFAULT '0',
  `achiveWish` bigint(20) NOT NULL DEFAULT '0',
  `buyGift` int(11) NOT NULL DEFAULT '0',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `resetCount` int(11) NOT NULL DEFAULT '0',
  `careIgnore` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_alliance_wish`
--

LOCK TABLES `activity_alliance_wish` WRITE;
/*!40000 ALTER TABLE `activity_alliance_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_alliance_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_ally_beat_back`
--

DROP TABLE IF EXISTS `activity_ally_beat_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_ally_beat_back` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `receivedTime` int(11) NOT NULL DEFAULT '0',
  `wolrdCollectTimes` int(11) DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeTimes` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_ally_beat_back`
--

LOCK TABLES `activity_ally_beat_back` WRITE;
/*!40000 ALTER TABLE `activity_ally_beat_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_ally_beat_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_ann7_party`
--

DROP TABLE IF EXISTS `activity_ann7_party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_ann7_party` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `partyReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createRoomCount` int(11) NOT NULL DEFAULT '0',
  `joinRoomCount` int(11) NOT NULL DEFAULT '0',
  `recAwardTimes` int(11) NOT NULL DEFAULT '0',
  `inviteAwardCount` int(11) NOT NULL DEFAULT '0',
  `masterAwardCount` int(11) NOT NULL DEFAULT '0',
  `memberAwardCount` int(11) NOT NULL DEFAULT '0',
  `applyJoinRooms` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `inviteMeRooms` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `shopInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `hisJoinRooms` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `firstRoomEndTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_ann7_party`
--

LOCK TABLES `activity_ann7_party` WRITE;
/*!40000 ALTER TABLE `activity_ann7_party` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_ann7_party` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_anniversary_gift`
--

DROP TABLE IF EXISTS `activity_anniversary_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_anniversary_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItemsDaily` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_anniversary_gift`
--

LOCK TABLES `activity_anniversary_gift` WRITE;
/*!40000 ALTER TABLE `activity_anniversary_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_anniversary_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_appointget`
--

DROP TABLE IF EXISTS `activity_appointget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_appointget` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `trainCnt` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_appointget`
--

LOCK TABLES `activity_appointget` WRITE;
/*!40000 ALTER TABLE `activity_appointget` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_appointget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_armament_exchange`
--

DROP TABLE IF EXISTS `activity_armament_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_armament_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `exchange` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `isOpen` int(11) DEFAULT '0',
  `isFirst` int(11) DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_armament_exchange`
--

LOCK TABLES `activity_armament_exchange` WRITE;
/*!40000 ALTER TABLE `activity_armament_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_armament_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_armies_mass`
--

DROP TABLE IF EXISTS `activity_armies_mass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_armies_mass` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `stage` int(11) NOT NULL,
  `share` int(11) NOT NULL,
  `sculptureOpenCount` int(11) NOT NULL,
  `sculptures` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `freeAwards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyGifts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_armies_mass`
--

LOCK TABLES `activity_armies_mass` WRITE;
/*!40000 ALTER TABLE `activity_armies_mass` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_armies_mass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_armour_immort`
--

DROP TABLE IF EXISTS `activity_armour_immort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_armour_immort` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `gameInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `gameCount` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_armour_immort`
--

LOCK TABLES `activity_armour_immort` WRITE;
/*!40000 ALTER TABLE `activity_armour_immort` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_armour_immort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_back_gift`
--

DROP TABLE IF EXISTS `activity_back_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_back_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `lotteryCount` int(11) NOT NULL,
  `lotteryTotalCount` int(11) NOT NULL,
  `lotteryTime` bigint(20) NOT NULL,
  `refreshCount` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `awards` text CHARACTER SET utf8mb4 NOT NULL,
  `awardIndex` int(11) NOT NULL,
  `lossDays` int(11) NOT NULL,
  `lossVip` int(11) NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_back_gift`
--

LOCK TABLES `activity_back_gift` WRITE;
/*!40000 ALTER TABLE `activity_back_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_back_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_back_privilege`
--

DROP TABLE IF EXISTS `activity_back_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_back_privilege` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  `reward` int(11) NOT NULL DEFAULT '0',
  `lossDays` int(11) NOT NULL DEFAULT '0',
  `buffStartTime` bigint(20) NOT NULL DEFAULT '0',
  `lossVip` int(11) NOT NULL DEFAULT '0',
  `backType` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_back_privilege`
--

LOCK TABLES `activity_back_privilege` WRITE;
/*!40000 ALTER TABLE `activity_back_privilege` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_back_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_back_soldier_exchange`
--

DROP TABLE IF EXISTS `activity_back_soldier_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_back_soldier_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `backTime` bigint(20) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  `logoutTime` bigint(20) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeType` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `histor` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_back_soldier_exchange`
--

LOCK TABLES `activity_back_soldier_exchange` WRITE;
/*!40000 ALTER TABLE `activity_back_soldier_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_back_soldier_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_back_to_new_fly`
--

DROP TABLE IF EXISTS `activity_back_to_new_fly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_back_to_new_fly` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  `baseLevel` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_back_to_new_fly`
--

LOCK TABLES `activity_back_to_new_fly` WRITE;
/*!40000 ALTER TABLE `activity_back_to_new_fly` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_back_to_new_fly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_back_to_new_fly_old`
--

DROP TABLE IF EXISTS `activity_back_to_new_fly_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_back_to_new_fly_old` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_back_to_new_fly_old`
--

LOCK TABLES `activity_back_to_new_fly_old` WRITE;
/*!40000 ALTER TABLE `activity_back_to_new_fly_old` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_back_to_new_fly_old` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_banner_kill`
--

DROP TABLE IF EXISTS `activity_banner_kill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_banner_kill` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `killEnemyScore` bigint(11) NOT NULL DEFAULT '0',
  `killTargetInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_banner_kill`
--

LOCK TABLES `activity_banner_kill` WRITE;
/*!40000 ALTER TABLE `activity_banner_kill` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_banner_kill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_battlefield_treasure`
--

DROP TABLE IF EXISTS `activity_battlefield_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_battlefield_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `cellId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyTime` bigint(20) NOT NULL DEFAULT '0',
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `receiveAwardDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `fixedRollTimes` int(11) NOT NULL DEFAULT '0',
  `randomRollTimes` int(11) NOT NULL DEFAULT '0',
  `poolAwards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `passedCells` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buyOrdinary` int(11) NOT NULL DEFAULT '0',
  `buyControl` int(11) NOT NULL DEFAULT '0',
  `yijianpaotu` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_battlefield_treasure`
--

LOCK TABLES `activity_battlefield_treasure` WRITE;
/*!40000 ALTER TABLE `activity_battlefield_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_battlefield_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_beauty_contest`
--

DROP TABLE IF EXISTS `activity_beauty_contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_beauty_contest` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_beauty_contest`
--

LOCK TABLES `activity_beauty_contest` WRITE;
/*!40000 ALTER TABLE `activity_beauty_contest` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_beauty_contest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_beauty_final`
--

DROP TABLE IF EXISTS `activity_beauty_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_beauty_final` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_beauty_final`
--

LOCK TABLES `activity_beauty_final` WRITE;
/*!40000 ALTER TABLE `activity_beauty_final` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_beauty_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_best_prize`
--

DROP TABLE IF EXISTS `activity_best_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_best_prize` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `drawConsume` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `bigPoolDrawInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_best_prize`
--

LOCK TABLES `activity_best_prize` WRITE;
/*!40000 ALTER TABLE `activity_best_prize` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_best_prize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_black_tech`
--

DROP TABLE IF EXISTS `activity_black_tech`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_black_tech` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `poolId` int(11) NOT NULL,
  `drawTimes` int(11) NOT NULL,
  `buffId` int(11) NOT NULL,
  `deadline` bigint(20) NOT NULL,
  `activeTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_black_tech`
--

LOCK TABLES `activity_black_tech` WRITE;
/*!40000 ALTER TABLE `activity_black_tech` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_black_tech` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_blood_corps`
--

DROP TABLE IF EXISTS `activity_blood_corps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_blood_corps` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `totalScore` int(11) NOT NULL,
  `buildScore` int(11) NOT NULL,
  `techScore` int(11) NOT NULL,
  `armyScore` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_blood_corps`
--

LOCK TABLES `activity_blood_corps` WRITE;
/*!40000 ALTER TABLE `activity_blood_corps` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_blood_corps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_bounty_hunter`
--

DROP TABLE IF EXISTS `activity_bounty_hunter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_bounty_hunter` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `pool` int(11) NOT NULL DEFAULT '0',
  `hitType` int(11) NOT NULL DEFAULT '0',
  `bossHp` int(11) NOT NULL DEFAULT '0',
  `lefState` int(11) NOT NULL DEFAULT '0',
  `bossBHit` int(11) NOT NULL DEFAULT '0',
  `poolARount` int(11) NOT NULL DEFAULT '0',
  `bossBNotRun` int(11) NOT NULL DEFAULT '0',
  `bossBNotDie` int(11) NOT NULL DEFAULT '0',
  `costMutil` int(11) NOT NULL DEFAULT '0',
  `rewardMutil` int(11) NOT NULL DEFAULT '0',
  `mutilCount` int(11) NOT NULL DEFAULT '0',
  `freeItemDay` int(11) NOT NULL DEFAULT '0',
  `batter` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_bounty_hunter`
--

LOCK TABLES `activity_bounty_hunter` WRITE;
/*!40000 ALTER TABLE `activity_bounty_hunter` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_bounty_hunter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_broken_exchange`
--

DROP TABLE IF EXISTS `activity_broken_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_broken_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `giftCostDiamond` int(11) NOT NULL,
  `exchangeNum` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastOperTime` bigint(11) NOT NULL DEFAULT '0',
  `wolrdCollectTimes` int(11) DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_broken_exchange`
--

LOCK TABLES `activity_broken_exchange` WRITE;
/*!40000 ALTER TABLE `activity_broken_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_broken_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_broken_exchange_three`
--

DROP TABLE IF EXISTS `activity_broken_exchange_three`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_broken_exchange_three` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `giftCostDiamond` int(11) NOT NULL,
  `exchangeNum` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastOperTime` bigint(11) NOT NULL DEFAULT '0',
  `wolrdCollectTimes` int(11) DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_broken_exchange_three`
--

LOCK TABLES `activity_broken_exchange_three` WRITE;
/*!40000 ALTER TABLE `activity_broken_exchange_three` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_broken_exchange_three` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_broken_exchange_two`
--

DROP TABLE IF EXISTS `activity_broken_exchange_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_broken_exchange_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `giftCostDiamond` int(11) NOT NULL,
  `exchangeNum` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastOperTime` bigint(11) NOT NULL DEFAULT '0',
  `wolrdCollectTimes` int(11) DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_broken_exchange_two`
--

LOCK TABLES `activity_broken_exchange_two` WRITE;
/*!40000 ALTER TABLE `activity_broken_exchange_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_broken_exchange_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_build_level`
--

DROP TABLE IF EXISTS `activity_build_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_build_level` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `activityItems` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_build_level`
--

LOCK TABLES `activity_build_level` WRITE;
/*!40000 ALTER TABLE `activity_build_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_build_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_cake_share`
--

DROP TABLE IF EXISTS `activity_cake_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_cake_share` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `cakeGifts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_cake_share`
--

LOCK TABLES `activity_cake_share` WRITE;
/*!40000 ALTER TABLE `activity_cake_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_cake_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_card`
--

DROP TABLE IF EXISTS `activity_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_card` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `cardItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `exchangeRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `customItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `customLatest` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_card`
--

LOCK TABLES `activity_card` WRITE;
/*!40000 ALTER TABLE `activity_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_celebration_course`
--

DROP TABLE IF EXISTS `activity_celebration_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_celebration_course` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `signNumber` int(11) NOT NULL DEFAULT '0',
  `signTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shareIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shareReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shareTime` bigint(20) DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_celebration_course`
--

LOCK TABLES `activity_celebration_course` WRITE;
/*!40000 ALTER TABLE `activity_celebration_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_celebration_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_celebration_food`
--

DROP TABLE IF EXISTS `activity_celebration_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_celebration_food` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `foodLevel` int(11) NOT NULL,
  `buyAdvance` tinyint(1) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `wolrdCollectTimes` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `beatYuriTotalTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `wishTotalTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  `buySuper` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `name_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_celebration_food`
--

LOCK TABLES `activity_celebration_food` WRITE;
/*!40000 ALTER TABLE `activity_celebration_food` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_celebration_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_celebration_fund`
--

DROP TABLE IF EXISTS `activity_celebration_fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_celebration_fund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `fundLevel` int(11) NOT NULL DEFAULT '0',
  `levelScore` int(11) NOT NULL DEFAULT '0',
  `buyOver` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_celebration_fund`
--

LOCK TABLES `activity_celebration_fund` WRITE;
/*!40000 ALTER TABLE `activity_celebration_fund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_celebration_fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_celebration_shop`
--

DROP TABLE IF EXISTS `activity_celebration_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_celebration_shop` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `exchange` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_celebration_shop`
--

LOCK TABLES `activity_celebration_shop` WRITE;
/*!40000 ALTER TABLE `activity_celebration_shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_celebration_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_change_server`
--

DROP TABLE IF EXISTS `activity_change_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_change_server` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `costDiamon` bigint(20) NOT NULL DEFAULT '0',
  `costGold` bigint(20) NOT NULL DEFAULT '0',
  `consumeVit` bigint(20) NOT NULL DEFAULT '0',
  `consumeSpeedTool` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_change_server`
--

LOCK TABLES `activity_change_server` WRITE;
/*!40000 ALTER TABLE `activity_change_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_change_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_chemistry`
--

DROP TABLE IF EXISTS `activity_chemistry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_chemistry` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `backType` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_chemistry`
--

LOCK TABLES `activity_chemistry` WRITE;
/*!40000 ALTER TABLE `activity_chemistry` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_chemistry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_christmas_recharge`
--

DROP TABLE IF EXISTS `activity_christmas_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_christmas_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `rechargeDiamond` int(22) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_christmas_recharge`
--

LOCK TABLES `activity_christmas_recharge` WRITE;
/*!40000 ALTER TABLE `activity_christmas_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_christmas_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_christmas_war`
--

DROP TABLE IF EXISTS `activity_christmas_war`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_christmas_war` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `receivedIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_christmas_war`
--

LOCK TABLES `activity_christmas_war` WRITE;
/*!40000 ALTER TABLE `activity_christmas_war` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_christmas_war` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_chrono_gift`
--

DROP TABLE IF EXISTS `activity_chrono_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_chrono_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyNum` int(11) NOT NULL,
  `chronoDoors` text CHARACTER SET utf8mb4 NOT NULL,
  `achieves` text CHARACTER SET utf8mb4 NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_chrono_gift`
--

LOCK TABLES `activity_chrono_gift` WRITE;
/*!40000 ALTER TABLE `activity_chrono_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_chrono_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_cmmoand_academy`
--

DROP TABLE IF EXISTS `activity_cmmoand_academy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_cmmoand_academy` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `stage` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `giftList` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rankIndex` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stageParam` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_cmmoand_academy`
--

LOCK TABLES `activity_cmmoand_academy` WRITE;
/*!40000 ALTER TABLE `activity_cmmoand_academy` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_cmmoand_academy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_cmmoand_academy_simplify`
--

DROP TABLE IF EXISTS `activity_cmmoand_academy_simplify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_cmmoand_academy_simplify` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `stage` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `giftList` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rankIndex` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stageParam` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_cmmoand_academy_simplify`
--

LOCK TABLES `activity_cmmoand_academy_simplify` WRITE;
/*!40000 ALTER TABLE `activity_cmmoand_academy_simplify` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_cmmoand_academy_simplify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_cny_exam`
--

DROP TABLE IF EXISTS `activity_cny_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_cny_exam` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `takeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `chooseItems1` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `chooseItems2` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE,
  KEY `level_index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_cny_exam`
--

LOCK TABLES `activity_cny_exam` WRITE;
/*!40000 ALTER TABLE `activity_cny_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_cny_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_common_exchange`
--

DROP TABLE IF EXISTS `activity_common_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_common_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_common_exchange`
--

LOCK TABLES `activity_common_exchange` WRITE;
/*!40000 ALTER TABLE `activity_common_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_common_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_common_exchange_two`
--

DROP TABLE IF EXISTS `activity_common_exchange_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_common_exchange_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_common_exchange_two`
--

LOCK TABLES `activity_common_exchange_two` WRITE;
/*!40000 ALTER TABLE `activity_common_exchange_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_common_exchange_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_continuous_recharge`
--

DROP TABLE IF EXISTS `activity_continuous_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_continuous_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `historyRecharge` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `currentRecharge` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_continuous_recharge`
--

LOCK TABLES `activity_continuous_recharge` WRITE;
/*!40000 ALTER TABLE `activity_continuous_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_continuous_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_core_explore`
--

DROP TABLE IF EXISTS `activity_core_explore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_core_explore` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `currLine` int(11) NOT NULL DEFAULT '0',
  `zoneArea` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `areaBox` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `areaStone` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `techInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `freePick` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `pickBuyTimes` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `autoPick` int(11) NOT NULL DEFAULT '0',
  `autoPickRewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `autoPickConsumes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `specialItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `oreItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_core_explore`
--

LOCK TABLES `activity_core_explore` WRITE;
/*!40000 ALTER TABLE `activity_core_explore` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_core_explore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_coreplate`
--

DROP TABLE IF EXISTS `activity_coreplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_coreplate` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `cityLevel` int(11) NOT NULL DEFAULT '0',
  `coreplateTimes` int(11) NOT NULL DEFAULT '0',
  `boxAchieveTimes` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_coreplate`
--

LOCK TABLES `activity_coreplate` WRITE;
/*!40000 ALTER TABLE `activity_coreplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_coreplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_custom_gift`
--

DROP TABLE IF EXISTS `activity_custom_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_custom_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `purchaseItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0',
  `freeGet` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_custom_gift`
--

LOCK TABLES `activity_custom_gift` WRITE;
/*!40000 ALTER TABLE `activity_custom_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_custom_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_daily_recharge`
--

DROP TABLE IF EXISTS `activity_daily_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_daily_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_daily_recharge`
--

LOCK TABLES `activity_daily_recharge` WRITE;
/*!40000 ALTER TABLE `activity_daily_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_daily_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_daily_recharge_new`
--

DROP TABLE IF EXISTS `activity_daily_recharge_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_daily_recharge_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `giftItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_daily_recharge_new`
--

LOCK TABLES `activity_daily_recharge_new` WRITE;
/*!40000 ALTER TABLE `activity_daily_recharge_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_daily_recharge_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_daily_sign`
--

DROP TABLE IF EXISTS `activity_daily_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_daily_sign` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `termRewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `signDays` int(11) NOT NULL DEFAULT '0',
  `signToday` int(11) NOT NULL DEFAULT '0',
  `resignDays` int(11) NOT NULL DEFAULT '0',
  `cfgPoolId` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_daily_sign`
--

LOCK TABLES `activity_daily_sign` WRITE;
/*!40000 ALTER TABLE `activity_daily_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_daily_sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_daiy_buy_gift`
--

DROP TABLE IF EXISTS `activity_daiy_buy_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_daiy_buy_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `itemRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_daiy_buy_gift`
--

LOCK TABLES `activity_daiy_buy_gift` WRITE;
/*!40000 ALTER TABLE `activity_daiy_buy_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_daiy_buy_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_deep_treasure`
--

DROP TABLE IF EXISTS `activity_deep_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_deep_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nineBoxStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nextFree` bigint(20) NOT NULL DEFAULT '0',
  `purchaseItemTimes` bigint(20) NOT NULL DEFAULT '0',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `refreshtimes` bigint(20) NOT NULL DEFAULT '0',
  `lotteryCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `lotteryBuff` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_deep_treasure`
--

LOCK TABLES `activity_deep_treasure` WRITE;
/*!40000 ALTER TABLE `activity_deep_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_deep_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_destiny_revolver`
--

DROP TABLE IF EXISTS `activity_destiny_revolver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_destiny_revolver` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `firstKick` tinyint(1) NOT NULL DEFAULT '0',
  `inTarot` tinyint(1) NOT NULL DEFAULT '0',
  `gridStr` text CHARACTER SET utf8mb4 NOT NULL,
  `nineEndTime` bigint(20) NOT NULL,
  `multiple` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text CHARACTER SET utf8mb4 NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_destiny_revolver`
--

LOCK TABLES `activity_destiny_revolver` WRITE;
/*!40000 ALTER TABLE `activity_destiny_revolver` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_destiny_revolver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_develop_fast`
--

DROP TABLE IF EXISTS `activity_develop_fast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_develop_fast` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `scoreItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_develop_fast`
--

LOCK TABLES `activity_develop_fast` WRITE;
/*!40000 ALTER TABLE `activity_develop_fast` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_develop_fast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_develop_fast_old`
--

DROP TABLE IF EXISTS `activity_develop_fast_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_develop_fast_old` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `scoreItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_develop_fast_old`
--

LOCK TABLES `activity_develop_fast_old` WRITE;
/*!40000 ALTER TABLE `activity_develop_fast_old` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_develop_fast_old` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_develop_spurt`
--

DROP TABLE IF EXISTS `activity_develop_spurt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_develop_spurt` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `loginTime` bigint(20) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `signInDays` int(11) NOT NULL,
  `signInTime` bigint(20) NOT NULL,
  `unlockTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_develop_spurt`
--

LOCK TABLES `activity_develop_spurt` WRITE;
/*!40000 ALTER TABLE `activity_develop_spurt` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_develop_spurt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_diff_info_save`
--

DROP TABLE IF EXISTS `activity_diff_info_save`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_diff_info_save` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `score` int(11) NOT NULL DEFAULT '0',
  `popCnt` int(11) NOT NULL DEFAULT '0',
  `isEnd` int(11) NOT NULL DEFAULT '0',
  `clickTime` bigint(20) NOT NULL DEFAULT '0',
  `dotCnt` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_diff_info_save`
--

LOCK TABLES `activity_diff_info_save` WRITE;
/*!40000 ALTER TABLE `activity_diff_info_save` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_diff_info_save` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_diff_new_server_tech`
--

DROP TABLE IF EXISTS `activity_diff_new_server_tech`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_diff_new_server_tech` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `rewardGet` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buffGet` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_diff_new_server_tech`
--

LOCK TABLES `activity_diff_new_server_tech` WRITE;
/*!40000 ALTER TABLE `activity_diff_new_server_tech` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_diff_new_server_tech` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_direct_gift`
--

DROP TABLE IF EXISTS `activity_direct_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_direct_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyGiftTimes` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_direct_gift`
--

LOCK TABLES `activity_direct_gift` WRITE;
/*!40000 ALTER TABLE `activity_direct_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_direct_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_divide_gold`
--

DROP TABLE IF EXISTS `activity_divide_gold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_divide_gold` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `giveNum` int(11) NOT NULL,
  `winRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `askForTime` bigint(20) NOT NULL,
  `compoundRedNum` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_divide_gold`
--

LOCK TABLES `activity_divide_gold` WRITE;
/*!40000 ALTER TABLE `activity_divide_gold` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_divide_gold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dome_exchange`
--

DROP TABLE IF EXISTS `activity_dome_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dome_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dome_exchange`
--

LOCK TABLES `activity_dome_exchange` WRITE;
/*!40000 ALTER TABLE `activity_dome_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dome_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dome_exchange_two`
--

DROP TABLE IF EXISTS `activity_dome_exchange_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dome_exchange_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  `playerPoint` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dome_exchange_two`
--

LOCK TABLES `activity_dome_exchange_two` WRITE;
/*!40000 ALTER TABLE `activity_dome_exchange_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dome_exchange_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_double_gift`
--

DROP TABLE IF EXISTS `activity_double_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_double_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `doubleGiftItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `accDay` int(11) NOT NULL,
  `latestPurchaseTime` bigint(20) NOT NULL,
  `freeTakenTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_double_gift`
--

LOCK TABLES `activity_double_gift` WRITE;
/*!40000 ALTER TABLE `activity_double_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_double_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_double_recharge`
--

DROP TABLE IF EXISTS `activity_double_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_double_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyGoodsIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_double_recharge`
--

LOCK TABLES `activity_double_recharge` WRITE;
/*!40000 ALTER TABLE `activity_double_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_double_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_benefit`
--

DROP TABLE IF EXISTS `activity_dragon_boat_benefit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_benefit` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_benefit`
--

LOCK TABLES `activity_dragon_boat_benefit` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_benefit` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_benefit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_celebration`
--

DROP TABLE IF EXISTS `activity_dragon_boat_celebration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_celebration` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `wolrdCollectTimes` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `beatYuriTotalTimes` int(11) NOT NULL DEFAULT '0',
  `guildDonateTimes` int(11) NOT NULL,
  `guildDonateTotalTimes` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_celebration`
--

LOCK TABLES `activity_dragon_boat_celebration` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_celebration` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_celebration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_exchange`
--

DROP TABLE IF EXISTS `activity_dragon_boat_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `careItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_exchange`
--

LOCK TABLES `activity_dragon_boat_exchange` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_gift`
--

DROP TABLE IF EXISTS `activity_dragon_boat_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginAward` int(11) NOT NULL,
  `boatGifts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_gift`
--

LOCK TABLES `activity_dragon_boat_gift` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_lucky_bag`
--

DROP TABLE IF EXISTS `activity_dragon_boat_lucky_bag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_lucky_bag` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `openCount` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_lucky_bag`
--

LOCK TABLES `activity_dragon_boat_lucky_bag` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_lucky_bag` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_lucky_bag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dragon_boat_recharge`
--

DROP TABLE IF EXISTS `activity_dragon_boat_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dragon_boat_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `rechargeDays` int(11) NOT NULL,
  `lastRechargeTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItemsDay` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dragon_boat_recharge`
--

LOCK TABLES `activity_dragon_boat_recharge` WRITE;
/*!40000 ALTER TABLE `activity_dragon_boat_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dragon_boat_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dreamy_construction`
--

DROP TABLE IF EXISTS `activity_dreamy_construction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dreamy_construction` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dreamy_construction`
--

LOCK TABLES `activity_dreamy_construction` WRITE;
/*!40000 ALTER TABLE `activity_dreamy_construction` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dreamy_construction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_collection`
--

DROP TABLE IF EXISTS `activity_dress_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_collection` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dressTypes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_collection`
--

LOCK TABLES `activity_dress_collection` WRITE;
/*!40000 ALTER TABLE `activity_dress_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_collection_two`
--

DROP TABLE IF EXISTS `activity_dress_collection_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_collection_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dressTypes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_collection_two`
--

LOCK TABLES `activity_dress_collection_two` WRITE;
/*!40000 ALTER TABLE `activity_dress_collection_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_collection_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_drawing_search`
--

DROP TABLE IF EXISTS `activity_dress_drawing_search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_drawing_search` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `lastOperTime` bigint(20) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `wolrdCollectTimes` int(11) DEFAULT '0',
  `totalDropNum` int(11) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_drawing_search`
--

LOCK TABLES `activity_dress_drawing_search` WRITE;
/*!40000 ALTER TABLE `activity_dress_drawing_search` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_drawing_search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_energy_gather`
--

DROP TABLE IF EXISTS `activity_dress_energy_gather`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_energy_gather` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDays` int(11) NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_energy_gather`
--

LOCK TABLES `activity_dress_energy_gather` WRITE;
/*!40000 ALTER TABLE `activity_dress_energy_gather` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_energy_gather` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_energy_gather_two`
--

DROP TABLE IF EXISTS `activity_dress_energy_gather_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_energy_gather_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDays` int(11) NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_energy_gather_two`
--

LOCK TABLES `activity_dress_energy_gather_two` WRITE;
/*!40000 ALTER TABLE `activity_dress_energy_gather_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_energy_gather_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_fire_reignite`
--

DROP TABLE IF EXISTS `activity_dress_fire_reignite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_fire_reignite` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exp` int(11) NOT NULL,
  `recBoxNum` int(11) NOT NULL,
  `exchangeNum` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_fire_reignite`
--

LOCK TABLES `activity_dress_fire_reignite` WRITE;
/*!40000 ALTER TABLE `activity_dress_fire_reignite` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_fire_reignite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_fire_reignite_two`
--

DROP TABLE IF EXISTS `activity_dress_fire_reignite_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_fire_reignite_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exp` int(11) NOT NULL,
  `recBoxInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeNum` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_fire_reignite_two`
--

LOCK TABLES `activity_dress_fire_reignite_two` WRITE;
/*!40000 ALTER TABLE `activity_dress_fire_reignite_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_fire_reignite_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_gunpowder_rise`
--

DROP TABLE IF EXISTS `activity_dress_gunpowder_rise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_gunpowder_rise` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_gunpowder_rise`
--

LOCK TABLES `activity_dress_gunpowder_rise` WRITE;
/*!40000 ALTER TABLE `activity_dress_gunpowder_rise` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_gunpowder_rise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_gunpowder_rise_two`
--

DROP TABLE IF EXISTS `activity_dress_gunpowder_rise_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_gunpowder_rise_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_gunpowder_rise_two`
--

LOCK TABLES `activity_dress_gunpowder_rise_two` WRITE;
/*!40000 ALTER TABLE `activity_dress_gunpowder_rise_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_gunpowder_rise_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dress_treasure`
--

DROP TABLE IF EXISTS `activity_dress_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dress_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `awards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `randomId` int(11) NOT NULL DEFAULT '0',
  `awardScoreFrom` int(11) NOT NULL DEFAULT '0',
  `awardScoreTo` int(11) NOT NULL DEFAULT '0',
  `resetCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dress_treasure`
--

LOCK TABLES `activity_dress_treasure` WRITE;
/*!40000 ALTER TABLE `activity_dress_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dress_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_dyzz_achieve`
--

DROP TABLE IF EXISTS `activity_dyzz_achieve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_dyzz_achieve` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_dyzz_achieve`
--

LOCK TABLES `activity_dyzz_achieve` WRITE;
/*!40000 ALTER TABLE `activity_dyzz_achieve` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_dyzz_achieve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_energies`
--

DROP TABLE IF EXISTS `activity_energies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_energies` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `dailyScore` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_energies`
--

LOCK TABLES `activity_energies` WRITE;
/*!40000 ALTER TABLE `activity_energies` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_energies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_energy_invest`
--

DROP TABLE IF EXISTS `activity_energy_invest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_energy_invest` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `daliyTask` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_energy_invest`
--

LOCK TABLES `activity_energy_invest` WRITE;
/*!40000 ALTER TABLE `activity_energy_invest` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_energy_invest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_equip_achieve`
--

DROP TABLE IF EXISTS `activity_equip_achieve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_equip_achieve` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_equip_achieve`
--

LOCK TABLES `activity_equip_achieve` WRITE;
/*!40000 ALTER TABLE `activity_equip_achieve` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_equip_achieve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_equip_black_market`
--

DROP TABLE IF EXISTS `activity_equip_black_market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_equip_black_market` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `refines` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyPackageIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastBuyPackage` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_equip_black_market`
--

LOCK TABLES `activity_equip_black_market` WRITE;
/*!40000 ALTER TABLE `activity_equip_black_market` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_equip_black_market` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_equip_carftsman`
--

DROP TABLE IF EXISTS `activity_equip_carftsman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_equip_carftsman` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `attrBox` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `page` int(11) NOT NULL,
  `gachaTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_equip_carftsman`
--

LOCK TABLES `activity_equip_carftsman` WRITE;
/*!40000 ALTER TABLE `activity_equip_carftsman` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_equip_carftsman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_equip_tech`
--

DROP TABLE IF EXISTS `activity_equip_tech`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_equip_tech` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_equip_tech`
--

LOCK TABLES `activity_equip_tech` WRITE;
/*!40000 ALTER TABLE `activity_equip_tech` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_equip_tech` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_evolution`
--

DROP TABLE IF EXISTS `activity_evolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_evolution` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exp` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `taskItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `finishedExchange` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_evolution`
--

LOCK TABLES `activity_evolution` WRITE;
/*!40000 ALTER TABLE `activity_evolution` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_evolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_exchange_decorate`
--

DROP TABLE IF EXISTS `activity_exchange_decorate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_exchange_decorate` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `exp` int(11) DEFAULT NULL,
  `levelReward` text COLLATE utf8mb4_unicode_ci,
  `achieveDayItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveDayRefreshTime` bigint(20) DEFAULT NULL,
  `achieveWeekItems` text COLLATE utf8mb4_unicode_ci,
  `achieveWeekRefreshTime` int(11) DEFAULT NULL,
  `levelOpenExchange` text COLLATE utf8mb4_unicode_ci,
  `decorateExchange` text COLLATE utf8mb4_unicode_ci,
  `loginDays` int(11) DEFAULT NULL,
  `loginRefreshTime` bigint(20) DEFAULT NULL,
  `weekNum` int(11) DEFAULT NULL,
  `weekBuyExpNum` int(11) DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_exchange_decorate`
--

LOCK TABLES `activity_exchange_decorate` WRITE;
/*!40000 ALTER TABLE `activity_exchange_decorate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_exchange_decorate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_exclusive_memory`
--

DROP TABLE IF EXISTS `activity_exclusive_memory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_exclusive_memory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `openState` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_exclusive_memory`
--

LOCK TABLES `activity_exclusive_memory` WRITE;
/*!40000 ALTER TABLE `activity_exclusive_memory` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_exclusive_memory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_festival`
--

DROP TABLE IF EXISTS `activity_festival`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_festival` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDays` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_festival`
--

LOCK TABLES `activity_festival` WRITE;
/*!40000 ALTER TABLE `activity_festival` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_festival` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_festival_two`
--

DROP TABLE IF EXISTS `activity_festival_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_festival_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDays` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_festival_two`
--

LOCK TABLES `activity_festival_two` WRITE;
/*!40000 ALTER TABLE `activity_festival_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_festival_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_fighter_puzzle`
--

DROP TABLE IF EXISTS `activity_fighter_puzzle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_fighter_puzzle` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDay` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_fighter_puzzle`
--

LOCK TABLES `activity_fighter_puzzle` WRITE;
/*!40000 ALTER TABLE `activity_fighter_puzzle` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_fighter_puzzle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_fighter_puzzle_serveropen`
--

DROP TABLE IF EXISTS `activity_fighter_puzzle_serveropen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_fighter_puzzle_serveropen` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDay` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_fighter_puzzle_serveropen`
--

LOCK TABLES `activity_fighter_puzzle_serveropen` WRITE;
/*!40000 ALTER TABLE `activity_fighter_puzzle_serveropen` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_fighter_puzzle_serveropen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_find_store_house`
--

DROP TABLE IF EXISTS `activity_find_store_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_find_store_house` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeCallInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `searchTimes` int(11) NOT NULL DEFAULT '0',
  `takeTreasureTimes` int(11) NOT NULL DEFAULT '0',
  `sendItemTimes` int(11) NOT NULL DEFAULT '0',
  `occupyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `helpTime` int(11) NOT NULL DEFAULT '0',
  `callHelpTime` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_find_store_house`
--

LOCK TABLES `activity_find_store_house` WRITE;
/*!40000 ALTER TABLE `activity_find_store_house` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_find_store_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_fire_work`
--

DROP TABLE IF EXISTS `activity_fire_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_fire_work` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buffInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayFree` tinyint(1) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `player_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_fire_work`
--

LOCK TABLES `activity_fire_work` WRITE;
/*!40000 ALTER TABLE `activity_fire_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_fire_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_first_recharge`
--

DROP TABLE IF EXISTS `activity_first_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_first_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `hasReceiveReward` int(11) NOT NULL DEFAULT '0',
  `hasExtrAward` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_first_recharge`
--

LOCK TABLES `activity_first_recharge` WRITE;
/*!40000 ALTER TABLE `activity_first_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_first_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_first_recharge_three`
--

DROP TABLE IF EXISTS `activity_first_recharge_three`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_first_recharge_three` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `activeTime` bigint(20) NOT NULL DEFAULT '0',
  `payCount` int(11) NOT NULL DEFAULT '0',
  `rewardState` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_first_recharge_three`
--

LOCK TABLES `activity_first_recharge_three` WRITE;
/*!40000 ALTER TABLE `activity_first_recharge_three` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_first_recharge_three` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_flight_plan`
--

DROP TABLE IF EXISTS `activity_flight_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_flight_plan` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `cellId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `goodsExchange` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_flight_plan`
--

LOCK TABLES `activity_flight_plan` WRITE;
/*!40000 ALTER TABLE `activity_flight_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_flight_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_fully_armed`
--

DROP TABLE IF EXISTS `activity_fully_armed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_fully_armed` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `searchId` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_fully_armed`
--

LOCK TABLES `activity_fully_armed` WRITE;
/*!40000 ALTER TABLE `activity_fully_armed` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_fully_armed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_ghost_secret`
--

DROP TABLE IF EXISTS `activity_ghost_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_ghost_secret` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `drewInfo` text COLLATE utf8mb4_unicode_ci,
  `drewNum` int(11) DEFAULT NULL,
  `specAwardGot` tinyint(1) DEFAULT NULL,
  `resetNum` int(11) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_ghost_secret`
--

LOCK TABLES `activity_ghost_secret` WRITE;
/*!40000 ALTER TABLE `activity_ghost_secret` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_ghost_secret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gift_send`
--

DROP TABLE IF EXISTS `activity_gift_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gift_send` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gift_send`
--

LOCK TABLES `activity_gift_send` WRITE;
/*!40000 ALTER TABLE `activity_gift_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gift_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gift_zero`
--

DROP TABLE IF EXISTS `activity_gift_zero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gift_zero` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `purchaseItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gift_zero`
--

LOCK TABLES `activity_gift_zero` WRITE;
/*!40000 ALTER TABLE `activity_gift_zero` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gift_zero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gift_zero_new`
--

DROP TABLE IF EXISTS `activity_gift_zero_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gift_zero_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `purchaseItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `freeTakenTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_name` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gift_zero_new`
--

LOCK TABLES `activity_gift_zero_new` WRITE;
/*!40000 ALTER TABLE `activity_gift_zero_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gift_zero_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_global_sign`
--

DROP TABLE IF EXISTS `activity_global_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_global_sign` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `bullectChatControl` int(11) DEFAULT '0',
  `bulletChatTime` bigint(20) DEFAULT '0',
  `signTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_name` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_global_sign`
--

LOCK TABLES `activity_global_sign` WRITE;
/*!40000 ALTER TABLE `activity_global_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_global_sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gold_baby`
--

DROP TABLE IF EXISTS `activity_gold_baby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gold_baby` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `findTimes` int(11) NOT NULL DEFAULT '0',
  `pools` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `buyTimes` int(11) NOT NULL DEFAULT '0',
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gold_baby`
--

LOCK TABLES `activity_gold_baby` WRITE;
/*!40000 ALTER TABLE `activity_gold_baby` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gold_baby` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gold_baby_new`
--

DROP TABLE IF EXISTS `activity_gold_baby_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gold_baby_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `findTimes` int(11) NOT NULL DEFAULT '0',
  `pools` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `buyTimes` int(11) NOT NULL DEFAULT '0',
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gold_baby_new`
--

LOCK TABLES `activity_gold_baby_new` WRITE;
/*!40000 ALTER TABLE `activity_gold_baby_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gold_baby_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_grateful_benefits`
--

DROP TABLE IF EXISTS `activity_grateful_benefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_grateful_benefits` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `punchCount` int(11) NOT NULL DEFAULT '0',
  `lastPunchTime` bigint(20) NOT NULL DEFAULT '0',
  `shareCount` int(11) NOT NULL DEFAULT '0',
  `shareRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `inviteCDTime` bigint(20) NOT NULL DEFAULT '0',
  `wishMembers` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `award` int(11) NOT NULL DEFAULT '0',
  `first` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_grateful_benefits`
--

LOCK TABLES `activity_grateful_benefits` WRITE;
/*!40000 ALTER TABLE `activity_grateful_benefits` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_grateful_benefits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_gratitude_gift`
--

DROP TABLE IF EXISTS `activity_gratitude_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_gratitude_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `rewardsGet` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_gratitude_gift`
--

LOCK TABLES `activity_gratitude_gift` WRITE;
/*!40000 ALTER TABLE `activity_gratitude_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_gratitude_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_great_gift`
--

DROP TABLE IF EXISTS `activity_great_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_great_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyBag` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `recieveChest` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `outBuyBag` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `finishTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_great_gift`
--

LOCK TABLES `activity_great_gift` WRITE;
/*!40000 ALTER TABLE `activity_great_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_great_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_greetings`
--

DROP TABLE IF EXISTS `activity_greetings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_greetings` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_name` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_greetings`
--

LOCK TABLES `activity_greetings` WRITE;
/*!40000 ALTER TABLE `activity_greetings` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_greetings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_group_buy`
--

DROP TABLE IF EXISTS `activity_group_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_group_buy` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buyScore` int(11) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `hotSellFreeGot` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `topDiscountRewardGot` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `topDiscountGifts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_group_buy`
--

LOCK TABLES `activity_group_buy` WRITE;
/*!40000 ALTER TABLE `activity_group_buy` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_group_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_group_purchase`
--

DROP TABLE IF EXISTS `activity_group_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_group_purchase` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `loginDay` int(11) NOT NULL DEFAULT '0',
  `dailyReward` int(11) NOT NULL DEFAULT '0',
  `scoreState` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_group_purchase`
--

LOCK TABLES `activity_group_purchase` WRITE;
/*!40000 ALTER TABLE `activity_group_purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_group_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_grow_up_boost`
--

DROP TABLE IF EXISTS `activity_grow_up_boost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_grow_up_boost` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `useConfig` int(11) NOT NULL DEFAULT '0',
  `scoreItemDetailString` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `scoreString` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItemsDay` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItemsScore` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buyMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_grow_up_boost`
--

LOCK TABLES `activity_grow_up_boost` WRITE;
/*!40000 ALTER TABLE `activity_grow_up_boost` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_grow_up_boost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_growfund`
--

DROP TABLE IF EXISTS `activity_growfund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_growfund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `isBuy` tinyint(1) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_growfund`
--

LOCK TABLES `activity_growfund` WRITE;
/*!40000 ALTER TABLE `activity_growfund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_growfund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_growfund_new`
--

DROP TABLE IF EXISTS `activity_growfund_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_growfund_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `fundBuyTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_name` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_growfund_new`
--

LOCK TABLES `activity_growfund_new` WRITE;
/*!40000 ALTER TABLE `activity_growfund_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_growfund_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_guild_back`
--

DROP TABLE IF EXISTS `activity_guild_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_guild_back` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `getBox` int(11) NOT NULL DEFAULT '0',
  `useBox` int(11) NOT NULL DEFAULT '0',
  `dayBoxTime` bigint(20) NOT NULL DEFAULT '0',
  `dropCount` int(11) NOT NULL DEFAULT '0',
  `dayPoolCount` int(11) NOT NULL DEFAULT '0',
  `goldNum` int(11) NOT NULL DEFAULT '0',
  `vitNum` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_guild_back`
--

LOCK TABLES `activity_guild_back` WRITE;
/*!40000 ALTER TABLE `activity_guild_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_guild_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_guild_dragon_attack`
--

DROP TABLE IF EXISTS `activity_guild_dragon_attack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_guild_dragon_attack` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `damageMax` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_guild_dragon_attack`
--

LOCK TABLES `activity_guild_dragon_attack` WRITE;
/*!40000 ALTER TABLE `activity_guild_dragon_attack` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_guild_dragon_attack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_happy_gift`
--

DROP TABLE IF EXISTS `activity_happy_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_happy_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_happy_gift`
--

LOCK TABLES `activity_happy_gift` WRITE;
/*!40000 ALTER TABLE `activity_happy_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_happy_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_heal_exchange`
--

DROP TABLE IF EXISTS `activity_heal_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_heal_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `active` bigint(20) NOT NULL DEFAULT '0',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_heal_exchange`
--

LOCK TABLES `activity_heal_exchange` WRITE;
/*!40000 ALTER TABLE `activity_heal_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_heal_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_heaven_blessing`
--

DROP TABLE IF EXISTS `activity_heaven_blessing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_heaven_blessing` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `groupId` int(11) NOT NULL DEFAULT '0',
  `payCount` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `choose` int(11) NOT NULL DEFAULT '0',
  `customState` int(11) NOT NULL DEFAULT '0',
  `activeState` int(11) NOT NULL DEFAULT '0',
  `activeTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_heaven_blessing`
--

LOCK TABLES `activity_heaven_blessing` WRITE;
/*!40000 ALTER TABLE `activity_heaven_blessing` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_heaven_blessing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hell_fire`
--

DROP TABLE IF EXISTS `activity_hell_fire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hell_fire` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `cycleStartTime` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `initBuildingBattlePoint` int(11) NOT NULL,
  `initTechBattlePoint` int(11) NOT NULL,
  `otherSumScore` int(11) NOT NULL,
  `targetIds` text CHARACTER SET utf8mb4 NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hell_fire`
--

LOCK TABLES `activity_hell_fire` WRITE;
/*!40000 ALTER TABLE `activity_hell_fire` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hell_fire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hell_fire_three`
--

DROP TABLE IF EXISTS `activity_hell_fire_three`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hell_fire_three` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `cycleStartTime` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `initBuildingBattlePoint` int(11) NOT NULL,
  `initTechBattlePoint` int(11) NOT NULL,
  `otherSumScore` int(11) NOT NULL,
  `targetIds` text CHARACTER SET utf8mb4 NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hell_fire_three`
--

LOCK TABLES `activity_hell_fire_three` WRITE;
/*!40000 ALTER TABLE `activity_hell_fire_three` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hell_fire_three` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hell_fire_two`
--

DROP TABLE IF EXISTS `activity_hell_fire_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hell_fire_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `cycleStartTime` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `initBuildingBattlePoint` int(11) NOT NULL,
  `initTechBattlePoint` int(11) NOT NULL,
  `otherSumScore` int(11) NOT NULL,
  `targetIds` text CHARACTER SET utf8mb4 NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hell_fire_two`
--

LOCK TABLES `activity_hell_fire_two` WRITE;
/*!40000 ALTER TABLE `activity_hell_fire_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hell_fire_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_achieve`
--

DROP TABLE IF EXISTS `activity_hero_achieve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_achieve` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_achieve`
--

LOCK TABLES `activity_hero_achieve` WRITE;
/*!40000 ALTER TABLE `activity_hero_achieve` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_achieve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_back`
--

DROP TABLE IF EXISTS `activity_hero_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_back` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_back`
--

LOCK TABLES `activity_hero_back` WRITE;
/*!40000 ALTER TABLE `activity_hero_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_back_exchange`
--

DROP TABLE IF EXISTS `activity_hero_back_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_back_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_back_exchange`
--

LOCK TABLES `activity_hero_back_exchange` WRITE;
/*!40000 ALTER TABLE `activity_hero_back_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_back_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_love`
--

DROP TABLE IF EXISTS `activity_hero_love`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_love` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `score` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_love`
--

LOCK TABLES `activity_hero_love` WRITE;
/*!40000 ALTER TABLE `activity_hero_love` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_love` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_skin`
--

DROP TABLE IF EXISTS `activity_hero_skin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_skin` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `pool` int(11) NOT NULL,
  `itemStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `multiple` int(11) NOT NULL,
  `refreshTimes` int(11) NOT NULL,
  `hasFinally` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_skin`
--

LOCK TABLES `activity_hero_skin` WRITE;
/*!40000 ALTER TABLE `activity_hero_skin` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_skin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_theme`
--

DROP TABLE IF EXISTS `activity_hero_theme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_theme` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_theme`
--

LOCK TABLES `activity_hero_theme` WRITE;
/*!40000 ALTER TABLE `activity_hero_theme` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_theme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_trial`
--

DROP TABLE IF EXISTS `activity_hero_trial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_trial` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `mission` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL,
  `acceptTimes` int(11) NOT NULL,
  `refreshTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_trial`
--

LOCK TABLES `activity_hero_trial` WRITE;
/*!40000 ALTER TABLE `activity_hero_trial` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_trial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hero_wish`
--

DROP TABLE IF EXISTS `activity_hero_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hero_wish` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `chooseId` int(11) NOT NULL DEFAULT '0',
  `addCount` int(11) NOT NULL DEFAULT '0',
  `achieveCount` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hero_wish`
--

LOCK TABLES `activity_hero_wish` WRITE;
/*!40000 ALTER TABLE `activity_hero_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hero_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hidden_treasure`
--

DROP TABLE IF EXISTS `activity_hidden_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hidden_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nineBoxStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nextFree` bigint(20) NOT NULL DEFAULT '0',
  `purchaseItemTimes` bigint(20) NOT NULL DEFAULT '0',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `refreshtimes` bigint(20) NOT NULL DEFAULT '0',
  `lottoryCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hidden_treasure`
--

LOCK TABLES `activity_hidden_treasure` WRITE;
/*!40000 ALTER TABLE `activity_hidden_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hidden_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_homeland_puzzle`
--

DROP TABLE IF EXISTS `activity_homeland_puzzle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_homeland_puzzle` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `drawCount` int(11) NOT NULL DEFAULT '0',
  `pCombine` int(11) NOT NULL DEFAULT '0',
  `pGrandPrize` int(11) NOT NULL DEFAULT '0',
  `pItem` int(11) NOT NULL DEFAULT '0',
  `collectedCombinationItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `grandPrizeWon` int(11) NOT NULL DEFAULT '0',
  `freeTimes` int(11) NOT NULL DEFAULT '0',
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `recordItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_homeland_puzzle`
--

LOCK TABLES `activity_homeland_puzzle` WRITE;
/*!40000 ALTER TABLE `activity_homeland_puzzle` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_homeland_puzzle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_homeland_round`
--

DROP TABLE IF EXISTS `activity_homeland_round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_homeland_round` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `drawTimes` int(11) NOT NULL DEFAULT '0',
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `currentFloor` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastFloorChange` int(11) NOT NULL DEFAULT '0',
  `pityCounter` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_homeland_round`
--

LOCK TABLES `activity_homeland_round` WRITE;
/*!40000 ALTER TABLE `activity_homeland_round` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_homeland_round` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hongfu_gift`
--

DROP TABLE IF EXISTS `activity_hongfu_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hongfu_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `hongFuInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hongfu_gift`
--

LOCK TABLES `activity_hongfu_gift` WRITE;
/*!40000 ALTER TABLE `activity_hongfu_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hongfu_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_honor_repay`
--

DROP TABLE IF EXISTS `activity_honor_repay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_honor_repay` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiveReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyTimes` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_honor_repay`
--

LOCK TABLES `activity_honor_repay` WRITE;
/*!40000 ALTER TABLE `activity_honor_repay` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_honor_repay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_honour_hero_befell`
--

DROP TABLE IF EXISTS `activity_honour_hero_befell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_honour_hero_befell` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `freeLotteryCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `oneLotteryCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `tenLotteryCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_honour_hero_befell`
--

LOCK TABLES `activity_honour_hero_befell` WRITE;
/*!40000 ALTER TABLE `activity_honour_hero_befell` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_honour_hero_befell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_honour_hero_return`
--

DROP TABLE IF EXISTS `activity_honour_hero_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_honour_hero_return` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `oneLotteryCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `tenLotteryCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lotteryPage` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_honour_hero_return`
--

LOCK TABLES `activity_honour_hero_return` WRITE;
/*!40000 ALTER TABLE `activity_honour_hero_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_honour_hero_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_honour_mobilize`
--

DROP TABLE IF EXISTS `activity_honour_mobilize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_honour_mobilize` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `chooseId` int(11) NOT NULL DEFAULT '0',
  `freeCount` int(11) NOT NULL DEFAULT '0',
  `lotteryCount` int(11) NOT NULL DEFAULT '0',
  `lotteryTotalCount` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_honour_mobilize`
--

LOCK TABLES `activity_honour_mobilize` WRITE;
/*!40000 ALTER TABLE `activity_honour_mobilize` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_honour_mobilize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_hot_blood_war`
--

DROP TABLE IF EXISTS `activity_hot_blood_war`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_hot_blood_war` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `cureArmyInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `cureFirstType` int(11) NOT NULL DEFAULT '0',
  `cureArmyId` int(11) NOT NULL DEFAULT '0',
  `cureArmyStartTime` bigint(20) NOT NULL DEFAULT '0',
  `cureArmySpeedTime` bigint(20) NOT NULL DEFAULT '0',
  `cureArmyCalTime` bigint(20) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `selfHurtInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `enemyKillInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `enemyKillScore` bigint(20) NOT NULL DEFAULT '0',
  `selfHurtScore` bigint(20) NOT NULL DEFAULT '0',
  `finishCheck` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_hot_blood_war`
--

LOCK TABLES `activity_hot_blood_war` WRITE;
/*!40000 ALTER TABLE `activity_hot_blood_war` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_hot_blood_war` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_inherit`
--

DROP TABLE IF EXISTS `activity_inherit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_inherit` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `totalVipExp` int(11) NOT NULL,
  `totalGold` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_inherit`
--

LOCK TABLES `activity_inherit` WRITE;
/*!40000 ALTER TABLE `activity_inherit` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_inherit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_inherit_new`
--

DROP TABLE IF EXISTS `activity_inherit_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_inherit_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `totalVipExp` int(11) NOT NULL DEFAULT '0',
  `totalGold` int(11) NOT NULL DEFAULT '0',
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `sourcePlayerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_inherit_new`
--

LOCK TABLES `activity_inherit_new` WRITE;
/*!40000 ALTER TABLE `activity_inherit_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_inherit_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_invest`
--

DROP TABLE IF EXISTS `activity_invest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_invest` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `investItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_invest`
--

LOCK TABLES `activity_invest` WRITE;
/*!40000 ALTER TABLE `activity_invest` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_invest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_jigsaw_connect`
--

DROP TABLE IF EXISTS `activity_jigsaw_connect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_jigsaw_connect` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_jigsaw_connect`
--

LOCK TABLES `activity_jigsaw_connect` WRITE;
/*!40000 ALTER TABLE `activity_jigsaw_connect` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_jigsaw_connect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_jijia_skin`
--

DROP TABLE IF EXISTS `activity_jijia_skin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_jijia_skin` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `pool` int(11) NOT NULL DEFAULT '0',
  `itemStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `multiple` int(11) NOT NULL DEFAULT '0',
  `refreshTimes` int(11) NOT NULL DEFAULT '0',
  `hasFinally` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_jijia_skin`
--

LOCK TABLES `activity_jijia_skin` WRITE;
/*!40000 ALTER TABLE `activity_jijia_skin` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_jijia_skin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_joy_buy`
--

DROP TABLE IF EXISTS `activity_joy_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_joy_buy` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exchangeRefreshNum` int(11) NOT NULL,
  `exchangeNextTime` bigint(20) NOT NULL,
  `exchangeList` text COLLATE utf8mb4_unicode_ci,
  `exchangeNumber` int(11) DEFAULT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginRefreshTime` bigint(20) DEFAULT NULL,
  `loginDays` int(11) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_joy_buy`
--

LOCK TABLES `activity_joy_buy` WRITE;
/*!40000 ALTER TABLE `activity_joy_buy` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_joy_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_jxjigsaw_connect`
--

DROP TABLE IF EXISTS `activity_jxjigsaw_connect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_jxjigsaw_connect` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_jxjigsaw_connect`
--

LOCK TABLES `activity_jxjigsaw_connect` WRITE;
/*!40000 ALTER TABLE `activity_jxjigsaw_connect` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_jxjigsaw_connect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_login_day`
--

DROP TABLE IF EXISTS `activity_login_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_login_day` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_login_day`
--

LOCK TABLES `activity_login_day` WRITE;
/*!40000 ALTER TABLE `activity_login_day` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_login_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_login_day_two`
--

DROP TABLE IF EXISTS `activity_login_day_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_login_day_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_login_day_two`
--

LOCK TABLES `activity_login_day_two` WRITE;
/*!40000 ALTER TABLE `activity_login_day_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_login_day_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_login_gift`
--

DROP TABLE IF EXISTS `activity_login_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_login_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyAdvanceTime` bigint(20) NOT NULL DEFAULT '0',
  `receivedCommDays` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `receivedAdvanceDays` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `advanceEndTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_login_gift`
--

LOCK TABLES `activity_login_gift` WRITE;
/*!40000 ALTER TABLE `activity_login_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_login_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_login_sign`
--

DROP TABLE IF EXISTS `activity_login_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_login_sign` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `lastTookTime` bigint(20) NOT NULL,
  `tookItemId` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_login_sign`
--

LOCK TABLES `activity_login_sign` WRITE;
/*!40000 ALTER TABLE `activity_login_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_login_sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_loginfund`
--

DROP TABLE IF EXISTS `activity_loginfund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_loginfund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `isBuy` tinyint(1) NOT NULL,
  `isNew` tinyint(1) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_loginfund`
--

LOCK TABLES `activity_loginfund` WRITE;
/*!40000 ALTER TABLE `activity_loginfund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_loginfund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_loginfund_two`
--

DROP TABLE IF EXISTS `activity_loginfund_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_loginfund_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `facLv` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `loginDaysCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_loginfund_two`
--

LOCK TABLES `activity_loginfund_two` WRITE;
/*!40000 ALTER TABLE `activity_loginfund_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_loginfund_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lottery_draw`
--

DROP TABLE IF EXISTS `activity_lottery_draw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lottery_draw` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `lastFreeDrawTime` bigint(20) NOT NULL,
  `lastCellId` int(11) NOT NULL,
  `tenDrawTimes` int(11) NOT NULL,
  `totalTimes` int(11) NOT NULL,
  `ensureTimes` int(11) NOT NULL,
  `multi` int(11) NOT NULL DEFAULT '1',
  `multiLucky` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lottery_draw`
--

LOCK TABLES `activity_lottery_draw` WRITE;
/*!40000 ALTER TABLE `activity_lottery_draw` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lottery_draw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lottery_ticket`
--

DROP TABLE IF EXISTS `activity_lottery_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lottery_ticket` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `buyMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lottery_ticket`
--

LOCK TABLES `activity_lottery_ticket` WRITE;
/*!40000 ALTER TABLE `activity_lottery_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lottery_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lover_meet`
--

DROP TABLE IF EXISTS `activity_lover_meet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lover_meet` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `questionStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `endingStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lover_meet`
--

LOCK TABLES `activity_lover_meet` WRITE;
/*!40000 ALTER TABLE `activity_lover_meet` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lover_meet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_luck_get_gold`
--

DROP TABLE IF EXISTS `activity_luck_get_gold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_luck_get_gold` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveChoose` int(11) NOT NULL DEFAULT '0',
  `poolChoose` int(11) NOT NULL DEFAULT '0',
  `freeCount` int(11) NOT NULL DEFAULT '0',
  `dailyDrawCount` int(11) NOT NULL DEFAULT '0',
  `totalDrawCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_luck_get_gold`
--

LOCK TABLES `activity_luck_get_gold` WRITE;
/*!40000 ALTER TABLE `activity_luck_get_gold` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_luck_get_gold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lucky_box`
--

DROP TABLE IF EXISTS `activity_lucky_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lucky_box` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `cellMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `mustMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `tipMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buyNeedCount` int(11) NOT NULL DEFAULT '0',
  `randomCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lucky_box`
--

LOCK TABLES `activity_lucky_box` WRITE;
/*!40000 ALTER TABLE `activity_lucky_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lucky_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lucky_discount`
--

DROP TABLE IF EXISTS `activity_lucky_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lucky_discount` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `deadline` bigint(20) NOT NULL,
  `buyRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `poolId` int(11) NOT NULL,
  `freeTimes` int(11) NOT NULL,
  `drawTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lucky_discount`
--

LOCK TABLES `activity_lucky_discount` WRITE;
/*!40000 ALTER TABLE `activity_lucky_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lucky_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lucky_star`
--

DROP TABLE IF EXISTS `activity_lucky_star`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lucky_star` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `leftGiftCnt` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lotCnt` int(11) NOT NULL DEFAULT '0',
  `lastBuyGiftId` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `todayRecieveBag` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lucky_star`
--

LOCK TABLES `activity_lucky_star` WRITE;
/*!40000 ALTER TABLE `activity_lucky_star` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lucky_star` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_lucky_welfare`
--

DROP TABLE IF EXISTS `activity_lucky_welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_lucky_welfare` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_lucky_welfare`
--

LOCK TABLES `activity_lucky_welfare` WRITE;
/*!40000 ALTER TABLE `activity_lucky_welfare` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_lucky_welfare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_luxury_dress`
--

DROP TABLE IF EXISTS `activity_luxury_dress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_luxury_dress` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `drawTimes` int(11) NOT NULL DEFAULT '0',
  `collectDressItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dailyRecieveTime` bigint(20) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_luxury_dress`
--

LOCK TABLES `activity_luxury_dress` WRITE;
/*!40000 ALTER TABLE `activity_luxury_dress` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_luxury_dress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_machine_awake`
--

DROP TABLE IF EXISTS `activity_machine_awake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_machine_awake` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `damage` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_machine_awake`
--

LOCK TABLES `activity_machine_awake` WRITE;
/*!40000 ALTER TABLE `activity_machine_awake` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_machine_awake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_machine_awake_two`
--

DROP TABLE IF EXISTS `activity_machine_awake_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_machine_awake_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `damage` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_machine_awake_two`
--

LOCK TABLES `activity_machine_awake_two` WRITE;
/*!40000 ALTER TABLE `activity_machine_awake_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_machine_awake_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_machine_lab`
--

DROP TABLE IF EXISTS `activity_machine_lab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_machine_lab` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `playerServer` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerExp` int(11) NOT NULL DEFAULT '0',
  `stormingPointTotal` int(11) NOT NULL DEFAULT '0',
  `buyGift` int(11) NOT NULL DEFAULT '0',
  `serverRewardLevel` int(11) NOT NULL DEFAULT '0',
  `playerRewardLevel` int(11) NOT NULL DEFAULT '0',
  `playerAdvRewardLevel` int(11) NOT NULL DEFAULT '0',
  `dropMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `careIgnore` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `supplementTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_machine_lab`
--

LOCK TABLES `activity_machine_lab` WRITE;
/*!40000 ALTER TABLE `activity_machine_lab` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_machine_lab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_machine_sell`
--

DROP TABLE IF EXISTS `activity_machine_sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_machine_sell` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lotteryTimes` int(11) NOT NULL DEFAULT '0',
  `singleTimes` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_machine_sell`
--

LOCK TABLES `activity_machine_sell` WRITE;
/*!40000 ALTER TABLE `activity_machine_sell` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_machine_sell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_material_transport`
--

DROP TABLE IF EXISTS `activity_material_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_material_transport` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `truckNumber` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `trainNumber` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `truckRobNumber` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `trainRobNumber` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `specialTrainNumber` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_material_transport`
--

LOCK TABLES `activity_material_transport` WRITE;
/*!40000 ALTER TABLE `activity_material_transport` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_material_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_medal_action`
--

DROP TABLE IF EXISTS `activity_medal_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_medal_action` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `buyNum` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_medal_action`
--

LOCK TABLES `activity_medal_action` WRITE;
/*!40000 ALTER TABLE `activity_medal_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_medal_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_medal_fund`
--

DROP TABLE IF EXISTS `activity_medal_fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_medal_fund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `daliyTask` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_medal_fund`
--

LOCK TABLES `activity_medal_fund` WRITE;
/*!40000 ALTER TABLE `activity_medal_fund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_medal_fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_medal_fund_two`
--

DROP TABLE IF EXISTS `activity_medal_fund_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_medal_fund_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dailyTask` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_medal_fund_two`
--

LOCK TABLES `activity_medal_fund_two` WRITE;
/*!40000 ALTER TABLE `activity_medal_fund_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_medal_fund_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_merge_competition`
--

DROP TABLE IF EXISTS `activity_merge_competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_merge_competition` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `awardIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildPowerTargetTime` bigint(20) NOT NULL DEFAULT '0',
  `awardIdRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `costVit` int(11) NOT NULL DEFAULT '0',
  `giftScore` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `guildPowerTargetFinish` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_merge_competition`
--

LOCK TABLES `activity_merge_competition` WRITE;
/*!40000 ALTER TABLE `activity_merge_competition` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_merge_competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_mid_autumn`
--

DROP TABLE IF EXISTS `activity_mid_autumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_mid_autumn` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `exchangeNum` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyGiftNum` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginDays` int(11) NOT NULL,
  `playerPoint` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_mid_autumn`
--

LOCK TABLES `activity_mid_autumn` WRITE;
/*!40000 ALTER TABLE `activity_mid_autumn` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_mid_autumn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_military_prepare`
--

DROP TABLE IF EXISTS `activity_military_prepare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_military_prepare` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `advancedBox` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `advanced` int(11) DEFAULT '0',
  `loginDays` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_military_prepare`
--

LOCK TABLES `activity_military_prepare` WRITE;
/*!40000 ALTER TABLE `activity_military_prepare` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_military_prepare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_monster2`
--

DROP TABLE IF EXISTS `activity_monster2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_monster2` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_monster2`
--

LOCK TABLES `activity_monster2` WRITE;
/*!40000 ALTER TABLE `activity_monster2` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_monster2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_new_first_recharge`
--

DROP TABLE IF EXISTS `activity_new_first_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_new_first_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `isActive` int(11) NOT NULL DEFAULT '0',
  `activeTime` bigint(20) NOT NULL DEFAULT '0',
  `payCount` int(11) NOT NULL DEFAULT '0',
  `rewardState` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `popLevel` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_new_first_recharge`
--

LOCK TABLES `activity_new_first_recharge` WRITE;
/*!40000 ALTER TABLE `activity_new_first_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_new_first_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_new_start`
--

DROP TABLE IF EXISTS `activity_new_start`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_new_start` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `isActive` int(11) NOT NULL DEFAULT '0',
  `isBind` int(11) NOT NULL DEFAULT '0',
  `playerLevel` int(11) NOT NULL DEFAULT '0',
  `vipLevel` int(11) NOT NULL DEFAULT '0',
  `baseLevel` int(11) NOT NULL DEFAULT '0',
  `heroCount` int(11) NOT NULL DEFAULT '0',
  `equipTechLevel` int(11) NOT NULL DEFAULT '0',
  `jijiaLevel` int(11) NOT NULL DEFAULT '0',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `icon` int(11) NOT NULL DEFAULT '0',
  `pfIcon` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `oldPlayerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `oldServerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `cfgInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `awardInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_new_start`
--

LOCK TABLES `activity_new_start` WRITE;
/*!40000 ALTER TABLE `activity_new_start` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_new_start` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_new_year_store`
--

DROP TABLE IF EXISTS `activity_new_year_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_new_year_store` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `shoppingCart` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `purchaseHistory` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_new_year_store`
--

LOCK TABLES `activity_new_year_store` WRITE;
/*!40000 ALTER TABLE `activity_new_year_store` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_new_year_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_newbie_train`
--

DROP TABLE IF EXISTS `activity_newbie_train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_newbie_train` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dailyLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `trainInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_newbie_train`
--

LOCK TABLES `activity_newbie_train` WRITE;
/*!40000 ALTER TABLE `activity_newbie_train` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_newbie_train` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_newly_experience`
--

DROP TABLE IF EXISTS `activity_newly_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_newly_experience` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_newly_experience`
--

LOCK TABLES `activity_newly_experience` WRITE;
/*!40000 ALTER TABLE `activity_newly_experience` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_newly_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_newyear_lottery`
--

DROP TABLE IF EXISTS `activity_newyear_lottery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_newyear_lottery` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dayTime` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payGiftInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_newyear_lottery`
--

LOCK TABLES `activity_newyear_lottery` WRITE;
/*!40000 ALTER TABLE `activity_newyear_lottery` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_newyear_lottery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_newyear_treasure`
--

DROP TABLE IF EXISTS `activity_newyear_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_newyear_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_newyear_treasure`
--

LOCK TABLES `activity_newyear_treasure` WRITE;
/*!40000 ALTER TABLE `activity_newyear_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_newyear_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_onermb_purchase`
--

DROP TABLE IF EXISTS `activity_onermb_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_onermb_purchase` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_onermb_purchase`
--

LOCK TABLES `activity_onermb_purchase` WRITE;
/*!40000 ALTER TABLE `activity_onermb_purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_onermb_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_order`
--

DROP TABLE IF EXISTS `activity_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_order` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `weekCycle` int(11) NOT NULL DEFAULT '0',
  `authorityId` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `expBuyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `orderItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `historyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_order`
--

LOCK TABLES `activity_order` WRITE;
/*!40000 ALTER TABLE `activity_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_order_equip`
--

DROP TABLE IF EXISTS `activity_order_equip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_order_equip` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `weekCycle` int(11) NOT NULL DEFAULT '0',
  `authorityId` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `orderItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `historyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `weekNumber` int(11) DEFAULT '0',
  `weekTime` bigint(20) DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_order_equip`
--

LOCK TABLES `activity_order_equip` WRITE;
/*!40000 ALTER TABLE `activity_order_equip` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_order_equip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_order_new`
--

DROP TABLE IF EXISTS `activity_order_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_order_new` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `authorityId` text COLLATE utf8mb4_unicode_ci,
  `exp` int(11) NOT NULL,
  `rewardInfo` text COLLATE utf8mb4_unicode_ci,
  `expBuyInfo` text COLLATE utf8mb4_unicode_ci,
  `orderItems` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_order_new`
--

LOCK TABLES `activity_order_new` WRITE;
/*!40000 ALTER TABLE `activity_order_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_order_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_order_two`
--

DROP TABLE IF EXISTS `activity_order_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_order_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `weekCycle` int(11) NOT NULL DEFAULT '0',
  `authorityId` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `orderItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `historyItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `weekNumber` int(11) NOT NULL DEFAULT '0',
  `weekTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `rewardNormalLevel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardAdvanceLevel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE,
  KEY `level_index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_order_two`
--

LOCK TABLES `activity_order_two` WRITE;
/*!40000 ALTER TABLE `activity_order_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_order_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_ordnance_fortress`
--

DROP TABLE IF EXISTS `activity_ordnance_fortress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_ordnance_fortress` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `stageId` int(11) NOT NULL DEFAULT '0',
  `rewardShow` int(11) NOT NULL DEFAULT '0',
  `bigRewardId` int(11) NOT NULL DEFAULT '0',
  `tickets` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigAwardTimes` int(11) NOT NULL DEFAULT '0',
  `openCount` int(11) NOT NULL DEFAULT '0',
  `rewardChoose` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigRewardCount` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_ordnance_fortress`
--

LOCK TABLES `activity_ordnance_fortress` WRITE;
/*!40000 ALTER TABLE `activity_ordnance_fortress` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_ordnance_fortress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_overlord_blessing`
--

DROP TABLE IF EXISTS `activity_overlord_blessing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_overlord_blessing` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `hasShare` int(11) NOT NULL DEFAULT '0',
  `receiveShare` int(11) NOT NULL DEFAULT '0',
  `hasBless` int(11) NOT NULL DEFAULT '0',
  `receiveBless` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_overlord_blessing`
--

LOCK TABLES `activity_overlord_blessing` WRITE;
/*!40000 ALTER TABLE `activity_overlord_blessing` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_overlord_blessing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_pandora_box`
--

DROP TABLE IF EXISTS `activity_pandora_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_pandora_box` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `freeCount` smallint(3) DEFAULT NULL,
  `lotteryCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `storeInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int(11) DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_pandora_box`
--

LOCK TABLES `activity_pandora_box` WRITE;
/*!40000 ALTER TABLE `activity_pandora_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_pandora_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_pdd`
--

DROP TABLE IF EXISTS `activity_pdd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_pdd` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `failNum` int(11) NOT NULL DEFAULT '0',
  `isFirst` int(11) NOT NULL DEFAULT '0',
  `shareTime` bigint(20) NOT NULL DEFAULT '0',
  `shareCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_pdd`
--

LOCK TABLES `activity_pdd` WRITE;
/*!40000 ALTER TABLE `activity_pdd` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_pdd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_pioneer_gift`
--

DROP TABLE IF EXISTS `activity_pioneer_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_pioneer_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `purchaseItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `accDay` int(11) NOT NULL,
  `latestPurchaseTime` bigint(20) NOT NULL,
  `freeTakenTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_pioneer_gift`
--

LOCK TABLES `activity_pioneer_gift` WRITE;
/*!40000 ALTER TABLE `activity_pioneer_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_pioneer_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plan`
--

DROP TABLE IF EXISTS `activity_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plan` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `score` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plan`
--

LOCK TABLES `activity_plan` WRITE;
/*!40000 ALTER TABLE `activity_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_planet_explore`
--

DROP TABLE IF EXISTS `activity_planet_explore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_planet_explore` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `score` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `collectInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `exploreTimes` int(11) NOT NULL DEFAULT '0',
  `collectCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_planet_explore`
--

LOCK TABLES `activity_planet_explore` WRITE;
/*!40000 ALTER TABLE `activity_planet_explore` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_planet_explore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plant_fortress`
--

DROP TABLE IF EXISTS `activity_plant_fortress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plant_fortress` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `stageId` int(11) NOT NULL DEFAULT '0',
  `rewardShow` int(11) NOT NULL DEFAULT '0',
  `bigRewardId` int(11) NOT NULL DEFAULT '0',
  `tickets` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigAwardTimes` int(11) NOT NULL DEFAULT '0',
  `openCount` int(11) NOT NULL DEFAULT '0',
  `rewardChoose` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigRewardCount` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyCount` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `player_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plant_fortress`
--

LOCK TABLES `activity_plant_fortress` WRITE;
/*!40000 ALTER TABLE `activity_plant_fortress` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plant_fortress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plant_secret`
--

DROP TABLE IF EXISTS `activity_plant_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plant_secret` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `openBoxCount` int(11) NOT NULL DEFAULT '0',
  `openBoxTimes` int(11) NOT NULL DEFAULT '0',
  `buyItemCount` int(11) NOT NULL DEFAULT '0',
  `openedCards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `secret` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  `lastShareTimeWorld` bigint(20) NOT NULL DEFAULT '0',
  `lastShareTimeGuild` bigint(20) NOT NULL DEFAULT '0',
  `worldshare` int(11) NOT NULL DEFAULT '0',
  `allianceshare` int(11) NOT NULL DEFAULT '0',
  `daytime` int(11) NOT NULL DEFAULT '0',
  `dailyopenbox` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plant_secret`
--

LOCK TABLES `activity_plant_secret` WRITE;
/*!40000 ALTER TABLE `activity_plant_secret` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plant_secret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plant_soldier_factory`
--

DROP TABLE IF EXISTS `activity_plant_soldier_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plant_soldier_factory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `awardInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigAwardInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `drawCount` int(11) NOT NULL DEFAULT '0',
  `drawTotalCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plant_soldier_factory`
--

LOCK TABLES `activity_plant_soldier_factory` WRITE;
/*!40000 ALTER TABLE `activity_plant_soldier_factory` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plant_soldier_factory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plant_weapon`
--

DROP TABLE IF EXISTS `activity_plant_weapon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plant_weapon` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `turnCount` int(11) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `continueDraws` int(11) NOT NULL DEFAULT '0',
  `continueGiveups` int(11) NOT NULL DEFAULT '0',
  `cooldownTime` bigint(20) NOT NULL DEFAULT '0',
  `inspireProgress` int(11) NOT NULL DEFAULT '0',
  `consumeItemCount` int(11) NOT NULL DEFAULT '0',
  `awardItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `disCount` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `choosePlantWeapon` int(11) NOT NULL DEFAULT '0',
  `dailyRecieveTime` bigint(20) NOT NULL DEFAULT '0',
  `touchCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plant_weapon`
--

LOCK TABLES `activity_plant_weapon` WRITE;
/*!40000 ALTER TABLE `activity_plant_weapon` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plant_weapon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_plant_weapon_back`
--

DROP TABLE IF EXISTS `activity_plant_weapon_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_plant_weapon_back` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `drawTimes` int(11) NOT NULL DEFAULT '0',
  `freeTimes` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `buff` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_plant_weapon_back`
--

LOCK TABLES `activity_plant_weapon_back` WRITE;
/*!40000 ALTER TABLE `activity_plant_weapon_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_plant_weapon_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_player`
--

DROP TABLE IF EXISTS `activity_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_player` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `activityId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `newlyTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_player`
--

LOCK TABLES `activity_player` WRITE;
/*!40000 ALTER TABLE `activity_player` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_player_comeback`
--

DROP TABLE IF EXISTS `activity_player_comeback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_player_comeback` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `init` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `accountLogoutTime` bigint(20) NOT NULL DEFAULT '0',
  `rewardInfos` text COLLATE utf8mb4_unicode_ci,
  `achieveInfos` text COLLATE utf8mb4_unicode_ci,
  `buyInfos` text COLLATE utf8mb4_unicode_ci,
  `exchangeInfos` text COLLATE utf8mb4_unicode_ci,
  `loginDay` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_player_comeback`
--

LOCK TABLES `activity_player_comeback` WRITE;
/*!40000 ALTER TABLE `activity_player_comeback` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_player_comeback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_playerteam_back`
--

DROP TABLE IF EXISTS `activity_playerteam_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_playerteam_back` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `refreshTime` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `starNum` int(11) NOT NULL,
  `teamId` int(11) NOT NULL,
  `rewardInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `teamMemberInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_playerteam_back`
--

LOCK TABLES `activity_playerteam_back` WRITE;
/*!40000 ALTER TABLE `activity_playerteam_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_playerteam_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_point_sprint`
--

DROP TABLE IF EXISTS `activity_point_sprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_point_sprint` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `round` int(11) NOT NULL DEFAULT '1',
  `awardRound` int(11) NOT NULL DEFAULT '0',
  `scoreInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `awardedInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_point_sprint`
--

LOCK TABLES `activity_point_sprint` WRITE;
/*!40000 ALTER TABLE `activity_point_sprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_point_sprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_power_collect`
--

DROP TABLE IF EXISTS `activity_power_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_power_collect` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectCnt` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_power_collect`
--

LOCK TABLES `activity_power_collect` WRITE;
/*!40000 ALTER TABLE `activity_power_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_power_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_power_send`
--

DROP TABLE IF EXISTS `activity_power_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_power_send` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `sendCount` int(11) NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_power_send`
--

LOCK TABLES `activity_power_send` WRITE;
/*!40000 ALTER TABLE `activity_power_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_power_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_power_up`
--

DROP TABLE IF EXISTS `activity_power_up`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_power_up` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_power_up`
--

LOCK TABLES `activity_power_up` WRITE;
/*!40000 ALTER TABLE `activity_power_up` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_power_up` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_powerfund`
--

DROP TABLE IF EXISTS `activity_powerfund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_powerfund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `isBuy` tinyint(1) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_powerfund`
--

LOCK TABLES `activity_powerfund` WRITE;
/*!40000 ALTER TABLE `activity_powerfund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_powerfund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_preferential_surprise`
--

DROP TABLE IF EXISTS `activity_preferential_surprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_preferential_surprise` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_preferential_surprise`
--

LOCK TABLES `activity_preferential_surprise` WRITE;
/*!40000 ALTER TABLE `activity_preferential_surprise` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_preferential_surprise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_present_rebate`
--

DROP TABLE IF EXISTS `activity_present_rebate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_present_rebate` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_present_rebate`
--

LOCK TABLES `activity_present_rebate` WRITE;
/*!40000 ALTER TABLE `activity_present_rebate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_present_rebate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_prestressing_loss`
--

DROP TABLE IF EXISTS `activity_prestressing_loss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_prestressing_loss` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `openTime` bigint(20) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  `openTerm` int(11) NOT NULL DEFAULT '0',
  `coolTimeVal` bigint(20) NOT NULL DEFAULT '0',
  `vacancyTimeVal` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_prestressing_loss`
--

LOCK TABLES `activity_prestressing_loss` WRITE;
/*!40000 ALTER TABLE `activity_prestressing_loss` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_prestressing_loss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_quest_treasure`
--

DROP TABLE IF EXISTS `activity_quest_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_quest_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `gameInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `gameRefreshCount` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `boxScore` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_quest_treasure`
--

LOCK TABLES `activity_quest_treasure` WRITE;
/*!40000 ALTER TABLE `activity_quest_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_quest_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_question_share`
--

DROP TABLE IF EXISTS `activity_question_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_question_share` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shareAmount` int(11) NOT NULL DEFAULT '0',
  `dailyRewarded` int(11) NOT NULL,
  `rewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayQuestion` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayAnswer` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayShare` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_question_share`
--

LOCK TABLES `activity_question_share` WRITE;
/*!40000 ALTER TABLE `activity_question_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_question_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_radiation_war`
--

DROP TABLE IF EXISTS `activity_radiation_war`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_radiation_war` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `killNum` int(11) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_radiation_war`
--

LOCK TABLES `activity_radiation_war` WRITE;
/*!40000 ALTER TABLE `activity_radiation_war` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_radiation_war` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_radiation_war_two`
--

DROP TABLE IF EXISTS `activity_radiation_war_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_radiation_war_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `killNum` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `guildAchieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_radiation_war_two`
--

LOCK TABLES `activity_radiation_war_two` WRITE;
/*!40000 ALTER TABLE `activity_radiation_war_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_radiation_war_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recall_friend`
--

DROP TABLE IF EXISTS `activity_recall_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recall_friend` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `recallNum` int(11) NOT NULL,
  `lastResetTime` bigint(20) DEFAULT NULL,
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `recallPlayer` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recall_friend`
--

LOCK TABLES `activity_recall_friend` WRITE;
/*!40000 ALTER TABLE `activity_recall_friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recall_friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recharge_fund`
--

DROP TABLE IF EXISTS `activity_recharge_fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recharge_fund` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `rechargeNum` int(11) NOT NULL,
  `investInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `diyReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardedInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recharge_fund`
--

LOCK TABLES `activity_recharge_fund` WRITE;
/*!40000 ALTER TABLE `activity_recharge_fund` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recharge_fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recharge_gift`
--

DROP TABLE IF EXISTS `activity_recharge_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recharge_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `rechargeTotal` int(11) DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recharge_gift`
--

LOCK TABLES `activity_recharge_gift` WRITE;
/*!40000 ALTER TABLE `activity_recharge_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recharge_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recharge_qixi`
--

DROP TABLE IF EXISTS `activity_recharge_qixi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recharge_qixi` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recharge_qixi`
--

LOCK TABLES `activity_recharge_qixi` WRITE;
/*!40000 ALTER TABLE `activity_recharge_qixi` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recharge_qixi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recharge_welfare`
--

DROP TABLE IF EXISTS `activity_recharge_welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recharge_welfare` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci,
  `freeTimes` int(11) NOT NULL,
  `itemset` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `totalCoupon` int(11) NOT NULL,
  `receiveCoupon` int(11) NOT NULL,
  `dailyScore` int(11) NOT NULL,
  `isFreeRec` tinyint(1) NOT NULL,
  `lotteryTimes` int(11) NOT NULL,
  `dailyLotteryTimes` int(11) NOT NULL,
  `receiveDiamond` int(11) NOT NULL,
  `totalDiamond` int(11) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recharge_welfare`
--

LOCK TABLES `activity_recharge_welfare` WRITE;
/*!40000 ALTER TABLE `activity_recharge_welfare` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recharge_welfare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_recovery_exchange`
--

DROP TABLE IF EXISTS `activity_recovery_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_recovery_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `exchangeTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `redTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `redHighTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `recycleItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_recovery_exchange`
--

LOCK TABLES `activity_recovery_exchange` WRITE;
/*!40000 ALTER TABLE `activity_recovery_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_recovery_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_red_envelope`
--

DROP TABLE IF EXISTS `activity_red_envelope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_red_envelope` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `recieveInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_red_envelope`
--

LOCK TABLES `activity_red_envelope` WRITE;
/*!40000 ALTER TABLE `activity_red_envelope` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_red_envelope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_red_package`
--

DROP TABLE IF EXISTS `activity_red_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_red_package` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `recieveInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_red_package`
--

LOCK TABLES `activity_red_package` WRITE;
/*!40000 ALTER TABLE `activity_red_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_red_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_red_recharge`
--

DROP TABLE IF EXISTS `activity_red_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_red_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rechargeItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_red_recharge`
--

LOCK TABLES `activity_red_recharge` WRITE;
/*!40000 ALTER TABLE `activity_red_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_red_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_redblue_ticket`
--

DROP TABLE IF EXISTS `activity_redblue_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_redblue_ticket` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `ticketsA` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `ticketsB` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `poolRefreshTimes` int(11) NOT NULL DEFAULT '0',
  `started` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_redblue_ticket`
--

LOCK TABLES `activity_redblue_ticket` WRITE;
/*!40000 ALTER TABLE `activity_redblue_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_redblue_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_redkoi`
--

DROP TABLE IF EXISTS `activity_redkoi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_redkoi` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `turnId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `freeTimes` int(11) NOT NULL,
  `curChoseAward` int(11) NOT NULL,
  `wishPoints` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_redkoi`
--

LOCK TABLES `activity_redkoi` WRITE;
/*!40000 ALTER TABLE `activity_redkoi` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_redkoi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_resource_defense`
--

DROP TABLE IF EXISTS `activity_resource_defense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_resource_defense` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `exp` int(11) NOT NULL,
  `stationInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `unlcokSuper` int(11) NOT NULL,
  `receivedRewardId` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stealTimes` int(11) NOT NULL,
  `beStealTimes` int(11) NOT NULL,
  `buyExpInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyExpRefreshTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  `canStealTimes` int(11) NOT NULL DEFAULT '0',
  `stealTimesTick` bigint(20) NOT NULL,
  `stealTimesZeroTick` bigint(20) NOT NULL,
  `agentSkill` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `activeSkill` int(11) NOT NULL,
  `skillRefreshTimes` int(11) NOT NULL,
  `freeRefreshTimes` int(11) NOT NULL,
  `agentRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `greatRobotInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stealRobotInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_resource_defense`
--

LOCK TABLES `activity_resource_defense` WRITE;
/*!40000 ALTER TABLE `activity_resource_defense` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_resource_defense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_return_army_exchange`
--

DROP TABLE IF EXISTS `activity_return_army_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_return_army_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `exchangeInfos` text CHARACTER SET utf8mb4 NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_return_army_exchange`
--

LOCK TABLES `activity_return_army_exchange` WRITE;
/*!40000 ALTER TABLE `activity_return_army_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_return_army_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_return_gift`
--

DROP TABLE IF EXISTS `activity_return_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_return_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `backCount` int(11) NOT NULL,
  `buyInfos` text CHARACTER SET utf8mb4 NOT NULL,
  `backType` int(11) NOT NULL,
  `startTime` bigint(20) NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_return_gift`
--

LOCK TABLES `activity_return_gift` WRITE;
/*!40000 ALTER TABLE `activity_return_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_return_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_return_puzzle`
--

DROP TABLE IF EXISTS `activity_return_puzzle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_return_puzzle` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `curDay` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveBoxItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveShareItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nextTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  `loginDay` int(11) NOT NULL DEFAULT '0',
  `lossDays` int(11) NOT NULL DEFAULT '0',
  `lossVip` int(11) NOT NULL DEFAULT '0',
  `backType` int(11) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_return_puzzle`
--

LOCK TABLES `activity_return_puzzle` WRITE;
/*!40000 ALTER TABLE `activity_return_puzzle` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_return_puzzle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_return_upgrade`
--

DROP TABLE IF EXISTS `activity_return_upgrade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_return_upgrade` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `overTime` bigint(20) NOT NULL DEFAULT '0',
  `backCount` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `goldBuyCount` int(11) NOT NULL DEFAULT '0',
  `upgradeInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `baseBeforLevel` int(11) NOT NULL DEFAULT '0',
  `baseAfterLevel` int(11) NOT NULL DEFAULT '0',
  `roleBeforLevel` int(11) NOT NULL DEFAULT '0',
  `roleAfterLevel` int(11) NOT NULL DEFAULT '0',
  `techPower` bigint(20) NOT NULL DEFAULT '0',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_return_upgrade`
--

LOCK TABLES `activity_return_upgrade` WRITE;
/*!40000 ALTER TABLE `activity_return_upgrade` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_return_upgrade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_reward_order`
--

DROP TABLE IF EXISTS `activity_reward_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_reward_order` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `orderInfo` text COLLATE utf8mb4_unicode_ci,
  `refreshCnt` tinyint(1) NOT NULL DEFAULT '0',
  `firstRefresh` tinyint(1) NOT NULL DEFAULT '0',
  `nextFreshTime` bigint(20) NOT NULL DEFAULT '0',
  `finishCnt` smallint(3) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_reward_order`
--

LOCK TABLES `activity_reward_order` WRITE;
/*!40000 ALTER TABLE `activity_reward_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_reward_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_rose_gift`
--

DROP TABLE IF EXISTS `activity_rose_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_rose_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `selfNum` int(11) NOT NULL DEFAULT '0',
  `isPayToday` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `drawInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_rose_gift`
--

LOCK TABLES `activity_rose_gift` WRITE;
/*!40000 ALTER TABLE `activity_rose_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_rose_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_roulette`
--

DROP TABLE IF EXISTS `activity_roulette`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_roulette` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `freeTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `itemset` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `boxReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int(11) DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_roulette`
--

LOCK TABLES `activity_roulette` WRITE;
/*!40000 ALTER TABLE `activity_roulette` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_roulette` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_samurai_blackened`
--

DROP TABLE IF EXISTS `activity_samurai_blackened`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_samurai_blackened` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `initDay` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_samurai_blackened`
--

LOCK TABLES `activity_samurai_blackened` WRITE;
/*!40000 ALTER TABLE `activity_samurai_blackened` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_samurai_blackened` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_scene_share`
--

DROP TABLE IF EXISTS `activity_scene_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_scene_share` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `scene` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_scene_share`
--

LOCK TABLES `activity_scene_share` WRITE;
/*!40000 ALTER TABLE `activity_scene_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_scene_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_sea_treasure`
--

DROP TABLE IF EXISTS `activity_sea_treasure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_sea_treasure` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dayMark` int(11) NOT NULL DEFAULT '0',
  `findTimes` int(11) NOT NULL DEFAULT '0',
  `toolBuyTimes` int(11) NOT NULL DEFAULT '0',
  `receiveTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `boxInfos` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiveRewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiveAdvRewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_sea_treasure`
--

LOCK TABLES `activity_sea_treasure` WRITE;
/*!40000 ALTER TABLE `activity_sea_treasure` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_sea_treasure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_season`
--

DROP TABLE IF EXISTS `activity_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_season` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `orderItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `orderLevel` int(11) NOT NULL DEFAULT '0',
  `orderExp` int(11) NOT NULL DEFAULT '0',
  `authorityId` int(11) NOT NULL DEFAULT '0',
  `orderRewardLevel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `orderRewardAdLevel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchange` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `clientLevel` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_season`
--

LOCK TABLES `activity_season` WRITE;
/*!40000 ALTER TABLE `activity_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_season_guild_grade`
--

DROP TABLE IF EXISTS `activity_season_guild_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_season_guild_grade` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `isReward` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `level_index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_season_guild_grade`
--

LOCK TABLES `activity_season_guild_grade` WRITE;
/*!40000 ALTER TABLE `activity_season_guild_grade` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_season_guild_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_season_puzzle`
--

DROP TABLE IF EXISTS `activity_season_puzzle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_season_puzzle` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `dayTime` bigint(20) NOT NULL DEFAULT '0',
  `itemSendCount` int(11) NOT NULL DEFAULT '0',
  `itemGetCount` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `callHelpInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `itemSetInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_season_puzzle`
--

LOCK TABLES `activity_season_puzzle` WRITE;
/*!40000 ALTER TABLE `activity_season_puzzle` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_season_puzzle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_share_prosperity`
--

DROP TABLE IF EXISTS `activity_share_prosperity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_share_prosperity` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `rebateCount` int(11) NOT NULL DEFAULT '0',
  `bindOldPlayer` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_share_prosperity`
--

LOCK TABLES `activity_share_prosperity` WRITE;
/*!40000 ALTER TABLE `activity_share_prosperity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_share_prosperity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_shooting_practice`
--

DROP TABLE IF EXISTS `activity_shooting_practice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_shooting_practice` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyCount` int(11) NOT NULL DEFAULT '0',
  `buyCountDaily` int(11) NOT NULL DEFAULT '0',
  `freeCount` int(11) NOT NULL DEFAULT '0',
  `scoreMax` int(11) NOT NULL DEFAULT '0',
  `scoreTotal` int(11) NOT NULL DEFAULT '0',
  `lastOverTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItemsDay` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_shooting_practice`
--

LOCK TABLES `activity_shooting_practice` WRITE;
/*!40000 ALTER TABLE `activity_shooting_practice` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_shooting_practice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_skin_plan`
--

DROP TABLE IF EXISTS `activity_skin_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_skin_plan` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `recvTop` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_skin_plan`
--

LOCK TABLES `activity_skin_plan` WRITE;
/*!40000 ALTER TABLE `activity_skin_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_skin_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_snowball`
--

DROP TABLE IF EXISTS `activity_snowball`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_snowball` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `turnId` int(11) NOT NULL,
  `kickScore` int(11) NOT NULL,
  `continueKickScore` int(11) NOT NULL,
  `assisScore` int(11) NOT NULL,
  `goalScore` int(11) NOT NULL,
  `goalAssisScore` int(11) NOT NULL,
  `receive` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_snowball`
--

LOCK TABLES `activity_snowball` WRITE;
/*!40000 ALTER TABLE `activity_snowball` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_snowball` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_soldier_exchange`
--

DROP TABLE IF EXISTS `activity_soldier_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_soldier_exchange` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeType` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `histor` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_soldier_exchange`
--

LOCK TABLES `activity_soldier_exchange` WRITE;
/*!40000 ALTER TABLE `activity_soldier_exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_soldier_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_soldier_exchange_389`
--

DROP TABLE IF EXISTS `activity_soldier_exchange_389`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_soldier_exchange_389` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exchangeType` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `histor` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_soldier_exchange_389`
--

LOCK TABLES `activity_soldier_exchange_389` WRITE;
/*!40000 ALTER TABLE `activity_soldier_exchange_389` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_soldier_exchange_389` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_song_hua`
--

DROP TABLE IF EXISTS `activity_song_hua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_song_hua` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `songHua` int(11) NOT NULL,
  `shouHua` int(11) NOT NULL,
  `laPiao` bigint(20) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_song_hua`
--

LOCK TABLES `activity_song_hua` WRITE;
/*!40000 ALTER TABLE `activity_song_hua` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_song_hua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_space_guard`
--

DROP TABLE IF EXISTS `activity_space_guard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_space_guard` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `points` int(11) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_space_guard`
--

LOCK TABLES `activity_space_guard` WRITE;
/*!40000 ALTER TABLE `activity_space_guard` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_space_guard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_spread`
--

DROP TABLE IF EXISTS `activity_spread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_spread` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `shopItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `hiddenAchieveIds` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `canRewardTimes` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewardedTimes` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `friends` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `dayReward` int(11) NOT NULL DEFAULT '0',
  `isBindCode` int(11) NOT NULL DEFAULT '0',
  `bindCode` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_spread`
--

LOCK TABLES `activity_spread` WRITE;
/*!40000 ALTER TABLE `activity_spread` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_spread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_star_light_sign`
--

DROP TABLE IF EXISTS `activity_star_light_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_star_light_sign` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `signItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `signDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `scoreBox` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `isMultiple` int(11) NOT NULL DEFAULT '0',
  `isAdMultiple` int(11) NOT NULL DEFAULT '0',
  `signRedeemCnt` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_star_light_sign`
--

LOCK TABLES `activity_star_light_sign` WRITE;
/*!40000 ALTER TABLE `activity_star_light_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_star_light_sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_start_invest`
--

DROP TABLE IF EXISTS `activity_start_invest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_start_invest` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `freeInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `daliyTask` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `cells` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rechargeCount` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `speedItemBuyCount` int(11) NOT NULL DEFAULT '0',
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_start_invest`
--

LOCK TABLES `activity_start_invest` WRITE;
/*!40000 ALTER TABLE `activity_start_invest` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_start_invest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_strongest_guild`
--

DROP TABLE IF EXISTS `activity_strongest_guild`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_strongest_guild` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `stageId` int(11) NOT NULL,
  `targetInfo` text COLLATE utf8mb4_unicode_ci,
  `score` bigint(20) NOT NULL,
  `killScore` bigint(20) NOT NULL,
  `hurtScore` bigint(20) NOT NULL,
  `buildBattlePoint` bigint(20) NOT NULL,
  `techBattlePoint` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_strongest_guild`
--

LOCK TABLES `activity_strongest_guild` WRITE;
/*!40000 ALTER TABLE `activity_strongest_guild` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_strongest_guild` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_strongest_leader`
--

DROP TABLE IF EXISTS `activity_strongest_leader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_strongest_leader` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `activityStage` int(11) DEFAULT NULL,
  `stageId` int(11) NOT NULL,
  `score` bigint(11) DEFAULT NULL,
  `initFightPoint` bigint(20) NOT NULL,
  `buildBattlePoint` bigint(20) NOT NULL,
  `techBattlePoint` bigint(20) NOT NULL,
  `historyScore` text COLLATE utf8mb4_unicode_ci,
  `targetIds` text COLLATE utf8mb4_unicode_ci,
  `achieveTargets` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  `hurtScore` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_strongest_leader`
--

LOCK TABLES `activity_strongest_leader` WRITE;
/*!40000 ALTER TABLE `activity_strongest_leader` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_strongest_leader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_strongpoint`
--

DROP TABLE IF EXISTS `activity_strongpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_strongpoint` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_strongpoint`
--

LOCK TABLES `activity_strongpoint` WRITE;
/*!40000 ALTER TABLE `activity_strongpoint` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_strongpoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_submarine_war`
--

DROP TABLE IF EXISTS `activity_submarine_war`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_submarine_war` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `gameInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `gameScore` int(11) NOT NULL DEFAULT '0',
  `gameCount` int(11) NOT NULL DEFAULT '0',
  `buyGameCount` int(11) NOT NULL DEFAULT '0',
  `gameLevelMax` int(11) NOT NULL DEFAULT '0',
  `gameScoreMax` int(11) NOT NULL DEFAULT '0',
  `gameScoreMaxTime` bigint(20) NOT NULL DEFAULT '0',
  `skillItemBuyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `initTime` bigint(20) NOT NULL DEFAULT '0',
  `loginDays` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tips` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `orderInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_submarine_war`
--

LOCK TABLES `activity_submarine_war` WRITE;
/*!40000 ALTER TABLE `activity_submarine_war` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_submarine_war` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_super_discount`
--

DROP TABLE IF EXISTS `activity_super_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_super_discount` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `poolId` int(11) NOT NULL,
  `deadline` bigint(20) NOT NULL,
  `freeTimes` int(11) NOT NULL,
  `drawTimes` int(11) NOT NULL,
  `drawAllTimes` int(11) NOT NULL,
  `buyRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_name` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_super_discount`
--

LOCK TABLES `activity_super_discount` WRITE;
/*!40000 ALTER TABLE `activity_super_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_super_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_super_gold`
--

DROP TABLE IF EXISTS `activity_super_gold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_super_gold` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_super_gold`
--

LOCK TABLES `activity_super_gold` WRITE;
/*!40000 ALTER TABLE `activity_super_gold` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_super_gold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_super_gold_two`
--

DROP TABLE IF EXISTS `activity_super_gold_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_super_gold_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_super_gold_two`
--

LOCK TABLES `activity_super_gold_two` WRITE;
/*!40000 ALTER TABLE `activity_super_gold_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_super_gold_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_supersoldier_invest`
--

DROP TABLE IF EXISTS `activity_supersoldier_invest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_supersoldier_invest` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `daliyTask` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_supersoldier_invest`
--

LOCK TABLES `activity_supersoldier_invest` WRITE;
/*!40000 ALTER TABLE `activity_supersoldier_invest` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_supersoldier_invest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_supply_crate`
--

DROP TABLE IF EXISTS `activity_supply_crate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_supply_crate` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `round` int(11) NOT NULL DEFAULT '0',
  `isCanOPen` int(11) NOT NULL DEFAULT '0',
  `isCanNext` int(11) NOT NULL DEFAULT '0',
  `mult` int(11) NOT NULL DEFAULT '0',
  `crateItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `boxProg` int(11) NOT NULL DEFAULT '0',
  `customIndex` int(11) NOT NULL DEFAULT '0',
  `openItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `isCanDouble` int(11) NOT NULL DEFAULT '0',
  `guildBoxProg` int(11) NOT NULL DEFAULT '0',
  `boxCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_supply_crate`
--

LOCK TABLES `activity_supply_crate` WRITE;
/*!40000 ALTER TABLE `activity_supply_crate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_supply_crate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_supply_station`
--

DROP TABLE IF EXISTS `activity_supply_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_supply_station` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `buyInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_supply_station`
--

LOCK TABLES `activity_supply_station` WRITE;
/*!40000 ALTER TABLE `activity_supply_station` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_supply_station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_supply_station_two`
--

DROP TABLE IF EXISTS `activity_supply_station_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_supply_station_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerPoint` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `exchangeMsg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_supply_station_two`
--

LOCK TABLES `activity_supply_station_two` WRITE;
/*!40000 ALTER TABLE `activity_supply_station_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_supply_station_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_tiberium_guess`
--

DROP TABLE IF EXISTS `activity_tiberium_guess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_tiberium_guess` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_tiberium_guess`
--

LOCK TABLES `activity_tiberium_guess` WRITE;
/*!40000 ALTER TABLE `activity_tiberium_guess` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_tiberium_guess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_time_limit_buy`
--

DROP TABLE IF EXISTS `activity_time_limit_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_time_limit_buy` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `buyStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `closeRemind` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `player_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_time_limit_buy`
--

LOCK TABLES `activity_time_limit_buy` WRITE;
/*!40000 ALTER TABLE `activity_time_limit_buy` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_time_limit_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_time_limit_drop`
--

DROP TABLE IF EXISTS `activity_time_limit_drop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_time_limit_drop` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `collectRemainTime` int(11) NOT NULL,
  `wolrdCollectRemainTime` int(11) NOT NULL,
  `beatYuriTimes` int(11) NOT NULL,
  `wishTimes` int(11) NOT NULL,
  `wolrdCollectTimes` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_time_limit_drop`
--

LOCK TABLES `activity_time_limit_drop` WRITE;
/*!40000 ALTER TABLE `activity_time_limit_drop` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_time_limit_drop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_time_limit_login`
--

DROP TABLE IF EXISTS `activity_time_limit_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_time_limit_login` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginData` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_time_limit_login`
--

LOCK TABLES `activity_time_limit_login` WRITE;
/*!40000 ALTER TABLE `activity_time_limit_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_time_limit_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_travel_shop_assist`
--

DROP TABLE IF EXISTS `activity_travel_shop_assist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_travel_shop_assist` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_travel_shop_assist`
--

LOCK TABLES `activity_travel_shop_assist` WRITE;
/*!40000 ALTER TABLE `activity_travel_shop_assist` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_travel_shop_assist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_treasure_cavalry`
--

DROP TABLE IF EXISTS `activity_treasure_cavalry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_treasure_cavalry` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `pool` int(11) NOT NULL,
  `itemStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `multiple` int(11) NOT NULL,
  `refreshTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_treasure_cavalry`
--

LOCK TABLES `activity_treasure_cavalry` WRITE;
/*!40000 ALTER TABLE `activity_treasure_cavalry` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_treasure_cavalry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_treasury`
--

DROP TABLE IF EXISTS `activity_treasury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_treasury` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `storageInfo` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `receivedInfo` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `costInfo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_playerId` (`playerId`) USING BTREE,
  KEY `playerid_idx` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_treasury`
--

LOCK TABLES `activity_treasury` WRITE;
/*!40000 ALTER TABLE `activity_treasury` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_treasury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_virtual_laboratory`
--

DROP TABLE IF EXISTS `activity_virtual_laboratory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_virtual_laboratory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `cardInfo` text COLLATE utf8mb4_unicode_ci,
  `openCardInfo` text COLLATE utf8mb4_unicode_ci,
  `achieveItems` text COLLATE utf8mb4_unicode_ci,
  `resetNum` int(11) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_virtual_laboratory`
--

LOCK TABLES `activity_virtual_laboratory` WRITE;
/*!40000 ALTER TABLE `activity_virtual_laboratory` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_virtual_laboratory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_war_flag_two`
--

DROP TABLE IF EXISTS `activity_war_flag_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_war_flag_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `playerPoint` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `exchangeMsg` text COLLATE utf8mb4_unicode_ci,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_war_flag_two`
--

LOCK TABLES `activity_war_flag_two` WRITE;
/*!40000 ALTER TABLE `activity_war_flag_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_war_flag_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_warzone_weal`
--

DROP TABLE IF EXISTS `activity_warzone_weal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_warzone_weal` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `loginDays` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_warzone_weal`
--

LOCK TABLES `activity_warzone_weal` WRITE;
/*!40000 ALTER TABLE `activity_warzone_weal` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_warzone_weal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_yuri_achieve_two`
--

DROP TABLE IF EXISTS `activity_yuri_achieve_two`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_yuri_achieve_two` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_yuri_achieve_two`
--

LOCK TABLES `activity_yuri_achieve_two` WRITE;
/*!40000 ALTER TABLE `activity_yuri_achieve_two` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_yuri_achieve_two` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agency`
--

DROP TABLE IF EXISTS `agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agency` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `agencyEventStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `agencyEventPoolStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `itemEventGen` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exp` int(11) NOT NULL DEFAULT '0',
  `currLevel` int(11) NOT NULL DEFAULT '0',
  `box` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `hasKilled` int(11) NOT NULL DEFAULT '0',
  `killCount` int(11) NOT NULL DEFAULT '0',
  `nextRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `playerPos` int(11) NOT NULL DEFAULT '0',
  `finishCount` int(11) NOT NULL DEFAULT '0',
  `boxExtLevel` int(11) NOT NULL DEFAULT '0',
  `specialId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `finishSpecialCount` int(11) NOT NULL DEFAULT '0',
  `finishSpecialDay` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agency`
--

LOCK TABLES `agency` WRITE;
/*!40000 ALTER TABLE `agency` DISABLE KEYS */;
/*!40000 ALTER TABLE `agency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `armour`
--

DROP TABLE IF EXISTS `armour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `armour` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `armourId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `quality` int(11) NOT NULL DEFAULT '0',
  `suit` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `extraAttr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `skillAttr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `locked` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `endTime` bigint(20) NOT NULL DEFAULT '0',
  `isSuper` int(11) NOT NULL DEFAULT '0',
  `star` int(11) NOT NULL DEFAULT '0',
  `starAttr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `starAttrConsume` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `quantum` int(11) NOT NULL DEFAULT '0',
  `immortInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE,
  KEY `level_index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `armour`
--

LOCK TABLES `armour` WRITE;
/*!40000 ALTER TABLE `armour` DISABLE KEYS */;
/*!40000 ALTER TABLE `armour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `army`
--

DROP TABLE IF EXISTS `army`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `army` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `armyId` int(11) NOT NULL DEFAULT '0',
  `free` int(11) NOT NULL DEFAULT '0',
  `trainCount` int(11) NOT NULL DEFAULT '0',
  `trainFinishCount` int(11) NOT NULL DEFAULT '0',
  `march` int(11) NOT NULL DEFAULT '0',
  `woundedCount` int(11) NOT NULL DEFAULT '0',
  `taralabsCount` int(11) NOT NULL DEFAULT '0',
  `cureCount` int(11) NOT NULL DEFAULT '0',
  `cureFinishCount` int(11) NOT NULL DEFAULT '0',
  `trainLatest` int(11) NOT NULL DEFAULT '0',
  `lastTrainTime` bigint(20) NOT NULL DEFAULT '0',
  `advancePower` double NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `nationalHospitalDeadCount` int(11) NOT NULL DEFAULT '0',
  `nationalHospitalRecoveredCount` int(11) NOT NULL DEFAULT '0',
  `tszzDeadCount` int(11) NOT NULL DEFAULT '0',
  `tszzRecoveredCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `army`
--

LOCK TABLES `army` WRITE;
/*!40000 ALTER TABLE `army` DISABLE KEYS */;
/*!40000 ALTER TABLE `army` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battle_mission`
--

DROP TABLE IF EXISTS `battle_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `battle_mission` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chapterId` int(11) NOT NULL DEFAULT '0',
  `chapterState` int(11) NOT NULL DEFAULT '0',
  `missions` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle_mission`
--

LOCK TABLES `battle_mission` WRITE;
/*!40000 ALTER TABLE `battle_mission` DISABLE KEYS */;
/*!40000 ALTER TABLE `battle_mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `buildingCfgId` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `x` int(11) NOT NULL DEFAULT '0',
  `y` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `hp` int(11) NOT NULL DEFAULT '0',
  `resUpdateTime` bigint(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `buildIndex` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '1',
  `lastResCollectTime` bigint(20) NOT NULL DEFAULT '0',
  `lastUpgradeTime` bigint(20) NOT NULL DEFAULT '0',
  `rescueCd` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE,
  KEY `type_index` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college_info`
--

DROP TABLE IF EXISTS `college_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college_info` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `coachId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `collegeName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `expTotal` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `vitality` double(20,6) NOT NULL DEFAULT '0.000000',
  `joinFree` int(11) NOT NULL DEFAULT '0',
  `reNameCount` int(11) NOT NULL DEFAULT '0',
  `statisticsData` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_playerId` (`coachId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college_info`
--

LOCK TABLES `college_info` WRITE;
/*!40000 ALTER TABLE `college_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `college_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college_member`
--

DROP TABLE IF EXISTS `college_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college_member` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `collegeId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `auth` int(11) NOT NULL DEFAULT '0',
  `crossResetTime` bigint(20) NOT NULL DEFAULT '0',
  `quitTime` bigint(20) NOT NULL DEFAULT '0',
  `joinTime` bigint(20) NOT NULL DEFAULT '0',
  `lastNotifyedTime` bigint(20) NOT NULL DEFAULT '0',
  `todayOnlineTime` bigint(20) NOT NULL DEFAULT '0',
  `onlineTookInfo` text COLLATE utf8mb4_unicode_ci,
  `scoreInfo` text COLLATE utf8mb4_unicode_ci,
  `shopInfo` text COLLATE utf8mb4_unicode_ci,
  `vitInfo` text COLLATE utf8mb4_unicode_ci,
  `missionInfo` text COLLATE utf8mb4_unicode_ci,
  `giftInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`),
  KEY `idx_collegeId` (`collegeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college_member`
--

LOCK TABLES `college_member` WRITE;
/*!40000 ALTER TABLE `college_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `college_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commander`
--

DROP TABLE IF EXISTS `commander`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commander` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `equipInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `starExplore` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `starExploreCollect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `soulResetCd` bigint(20) NOT NULL DEFAULT '0',
  `superSoldierSkin` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `shopData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `getDressTime` bigint(20) NOT NULL DEFAULT '0',
  `getDressCount` int(11) NOT NULL DEFAULT '0',
  `fgylData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `mtpremarch` int(11) NOT NULL DEFAULT '0',
  `riseSkillSettings` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `immortEcho` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commander`
--

LOCK TABLES `commander` WRITE;
/*!40000 ALTER TABLE `commander` DISABLE KEYS */;
/*!40000 ALTER TABLE `commander` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cross_tech`
--

DROP TABLE IF EXISTS `cross_tech`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cross_tech` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `techId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `researching` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cross_tech`
--

LOCK TABLES `cross_tech` WRITE;
/*!40000 ALTER TABLE `cross_tech` DISABLE KEYS */;
/*!40000 ALTER TABLE `cross_tech` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_data`
--

DROP TABLE IF EXISTS `custom_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_data` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` int(11) DEFAULT '0',
  `arg` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_data`
--

LOCK TABLES `custom_data` WRITE;
/*!40000 ALTER TABLE `custom_data` DISABLE KEYS */;
INSERT INTO `custom_data` VALUES ('mergeNotChangeIdentify:10001','','',0,'7pt-4eer5p-2',1776067709303,1776067709303,0);
/*!40000 ALTER TABLE `custom_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_data`
--

DROP TABLE IF EXISTS `daily_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_data` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `travelShopRefreshTimes` int(11) NOT NULL DEFAULT '0',
  `dailyFriendBoxTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `guildPushTimes` int(11) NOT NULL DEFAULT '0',
  `deadGuildRefuseRecommendCnt` int(11) NOT NULL DEFAULT '0',
  `lastPushTime` bigint(20) NOT NULL DEFAULT '0',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `travelGiftBuyTimes` int(11) NOT NULL DEFAULT '0',
  `vipTravelGiftBuyTimes` int(11) NOT NULL DEFAULT '0',
  `isMilitaryRankRecieve` int(11) NOT NULL DEFAULT '0',
  `attackFoggyWinTimes` int(11) NOT NULL DEFAULT '0',
  `crRewardTimes` int(11) NOT NULL DEFAULT '0',
  `crHighestScore` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `guardGift` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghostBox` int(11) NOT NULL DEFAULT '0',
  `resCollDropTimes` int(11) NOT NULL DEFAULT '0',
  `travelShopInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `armourStarAttrTimes` int(11) NOT NULL DEFAULT '0',
  `nationMissionDayBuyTimes` int(11) NOT NULL DEFAULT '0',
  `nationShipAssist` int(11) NOT NULL DEFAULT '0',
  `nationTechHelp` int(11) NOT NULL DEFAULT '0',
  `nationTechNotice` int(11) NOT NULL DEFAULT '0',
  `nationSkillDropTimes` int(11) NOT NULL DEFAULT '0',
  `joinAtkFoggyWinTimes` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_data`
--

LOCK TABLES `daily_data` WRITE;
/*!40000 ALTER TABLE `daily_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dress`
--

DROP TABLE IF EXISTS `dress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dress` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dressInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dress`
--

LOCK TABLES `dress` WRITE;
/*!40000 ALTER TABLE `dress` DISABLE KEYS */;
/*!40000 ALTER TABLE `dress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equip`
--

DROP TABLE IF EXISTS `equip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equip` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cfgId` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `isNew` tinyint(1) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equip`
--

LOCK TABLES `equip` WRITE;
/*!40000 ALTER TABLE `equip` DISABLE KEYS */;
/*!40000 ALTER TABLE `equip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equip_research`
--

DROP TABLE IF EXISTS `equip_research`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equip_research` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `researchId` int(11) NOT NULL,
  `researchLevel` int(11) NOT NULL,
  `receiveBox` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equip_research`
--

LOCK TABLES `equip_research` WRITE;
/*!40000 ALTER TABLE `equip_research` DISABLE KEYS */;
/*!40000 ALTER TABLE `equip_research` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `equipId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `point` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gacha`
--

DROP TABLE IF EXISTS `gacha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gacha` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayOfYear` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `gachaType` int(11) NOT NULL,
  `freeTimesUsed` int(11) NOT NULL,
  `firstGachaUsed` int(11) NOT NULL DEFAULT '0',
  `nextFree` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  `dayCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gacha`
--

LOCK TABLES `gacha` WRITE;
/*!40000 ALTER TABLE `gacha` DISABLE KEYS */;
/*!40000 ALTER TABLE `gacha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gm_recharge`
--

DROP TABLE IF EXISTS `gm_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gmUser` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `goodsId` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rechargeGold` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_recharge`
--

LOCK TABLES `gm_recharge` WRITE;
/*!40000 ALTER TABLE `gm_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `gm_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_big_gift`
--

DROP TABLE IF EXISTS `guild_big_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_big_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bigGiftLevelExp` int(11) NOT NULL,
  `bigGiftId` int(11) NOT NULL,
  `bigGiftExp` int(11) NOT NULL,
  `giftSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `guildId_Index` (`guildId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_big_gift`
--

LOCK TABLES `guild_big_gift` WRITE;
/*!40000 ALTER TABLE `guild_big_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_big_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_building`
--

DROP TABLE IF EXISTS `guild_building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_building` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `buildType` int(11) NOT NULL,
  `buildingId` int(11) NOT NULL,
  `buildingStat` int(11) NOT NULL,
  `pos` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0,0',
  `buildTime` bigint(20) DEFAULT '0',
  `buildLife` double DEFAULT '0',
  `level` int(11) DEFAULT '1',
  `buildParam` text COLLATE utf8mb4_unicode_ci,
  `lastTakeBackTime` bigint(20) DEFAULT '0',
  `lastTickTime` bigint(20) DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_building`
--

LOCK TABLES `guild_building` WRITE;
/*!40000 ALTER TABLE `guild_building` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_counterattack`
--

DROP TABLE IF EXISTS `guild_counterattack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_counterattack` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `atkerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lostPower` int(11) NOT NULL,
  `counterPower` int(11) NOT NULL,
  `attackerPointId` int(11) NOT NULL,
  `playerBountySer` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `wipeoutSer` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewards` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bitBackRewards` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `overTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `guildId_index` (`guildId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_counterattack`
--

LOCK TABLES `guild_counterattack` WRITE;
/*!40000 ALTER TABLE `guild_counterattack` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_counterattack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_fgyl`
--

DROP TABLE IF EXISTS `guild_fgyl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_fgyl` (
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `passLevel` int(11) NOT NULL DEFAULT '0',
  `useTime` int(11) NOT NULL DEFAULT '0',
  `passTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`guildId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_fgyl`
--

LOCK TABLES `guild_fgyl` WRITE;
/*!40000 ALTER TABLE `guild_fgyl` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_fgyl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_hospice`
--

DROP TABLE IF EXISTS `guild_hospice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_hospice` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attackerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `maxPower` int(11) NOT NULL DEFAULT '0',
  `lostPower` int(11) NOT NULL DEFAULT '0',
  `state` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `awards` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL,
  `helpQueue` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `helpers` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL,
  `matchStartTime` bigint(20) NOT NULL,
  `matchEndTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  `overwhelming` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_hospice`
--

LOCK TABLES `guild_hospice` WRITE;
/*!40000 ALTER TABLE `guild_hospice` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_hospice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_info`
--

DROP TABLE IF EXISTS `guild_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_info` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tag` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flagId` int(11) NOT NULL DEFAULT '0',
  `langId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `leaderId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `leaderName` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `leaderOfflineTime` bigint(20) NOT NULL DEFAULT '0',
  `coleaderId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `coleaderName` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isNeedPermition` int(11) NOT NULL DEFAULT '0',
  `needBuildingLevel` int(11) NOT NULL DEFAULT '0',
  `needPower` int(11) NOT NULL DEFAULT '0',
  `needCommanderLevel` int(11) NOT NULL DEFAULT '0',
  `announcement` text COLLATE utf8mb4_unicode_ci,
  `notice` text COLLATE utf8mb4_unicode_ci,
  `l1Name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `l2Name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `l3Name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `l4Name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `l5Name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `needLang` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `lastDonateCheckTime` bigint(20) NOT NULL DEFAULT '0',
  `clearResNum` int(11) DEFAULT '0',
  `guildBoundId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authInfo` text COLLATE utf8mb4_unicode_ci,
  `hasChangeTag` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `taskRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `chatRoomModel` int(11) NOT NULL DEFAULT '0',
  `xzqTickets` int(11) DEFAULT '0',
  `spaceMechaInfo` text COLLATE utf8mb4_unicode_ci,
  `formationInfo` text COLLATE utf8mb4_unicode_ci,
  `rewardFlag` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `leaderPlatform` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_info`
--

LOCK TABLES `guild_info` WRITE;
/*!40000 ALTER TABLE `guild_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_manor`
--

DROP TABLE IF EXISTS `guild_manor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_manor` (
  `manorId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `manorIndex` int(64) NOT NULL,
  `manorName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `manorState` int(11) NOT NULL DEFAULT '0',
  `pos` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0,0',
  `level` int(11) DEFAULT '1',
  `buildingLife` double NOT NULL,
  `completeTime` bigint(20) DEFAULT '0',
  `lastTakeBackTime` bigint(20) DEFAULT '0',
  `placeTime` bigint(20) NOT NULL DEFAULT '0',
  `lastTickTime` bigint(20) DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`manorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_manor`
--

LOCK TABLES `guild_manor` WRITE;
/*!40000 ALTER TABLE `guild_manor` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_manor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_member`
--

DROP TABLE IF EXISTS `guild_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_member` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authority` int(11) NOT NULL DEFAULT '0',
  `officeId` int(11) NOT NULL DEFAULT '0',
  `playerName` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `power` int(11) NOT NULL DEFAULT '0',
  `killCount` int(11) NOT NULL DEFAULT '0',
  `quitGuildTime` bigint(20) NOT NULL DEFAULT '0',
  `joinGuildTime` bigint(20) NOT NULL DEFAULT '0',
  `normalDonateTimes` int(11) NOT NULL DEFAULT '0',
  `crystalDonateTimes` int(11) NOT NULL DEFAULT '0',
  `donateResetTimes` int(11) NOT NULL DEFAULT '0',
  `nextDonateAddTime` bigint(20) NOT NULL DEFAULT '0',
  `donateDayOfYear` int(11) NOT NULL DEFAULT '0',
  `lastRefrashBigGift` bigint(20) NOT NULL DEFAULT '0',
  `joinGuildTimes` int(11) NOT NULL DEFAULT '0',
  `manorUnlockTimes` int(11) NOT NULL DEFAULT '0',
  `logoutTime` bigint(20) NOT NULL DEFAULT '0',
  `rewaredTaskIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskResetTime` bigint(20) NOT NULL DEFAULT '0',
  `signTimes` int(11) NOT NULL DEFAULT '0',
  `lastSingTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `noArmyPower` bigint(20) NOT NULL DEFAULT '0',
  `dragonAwardTime` bigint(20) NOT NULL DEFAULT '0',
  `strengthPower` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_member`
--

LOCK TABLES `guild_member` WRITE;
/*!40000 ALTER TABLE `guild_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_science`
--

DROP TABLE IF EXISTS `guild_science`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_science` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `scienceId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `star` int(11) NOT NULL DEFAULT '0',
  `donate` int(11) NOT NULL DEFAULT '0',
  `recommend` int(11) NOT NULL DEFAULT '0',
  `finishTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `openLimitTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_science`
--

LOCK TABLES `guild_science` WRITE;
/*!40000 ALTER TABLE `guild_science` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_science` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_smail_gift`
--

DROP TABLE IF EXISTS `guild_smail_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_smail_gift` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `itemId` int(11) NOT NULL,
  `awardGet` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` int(11) NOT NULL,
  `giftCreateTime` bigint(20) NOT NULL,
  `giftOverTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_smail_gift`
--

LOCK TABLES `guild_smail_gift` WRITE;
/*!40000 ALTER TABLE `guild_smail_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_smail_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hero`
--

DROP TABLE IF EXISTS `hero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hero` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `heroId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `star` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  `skin` int(11) NOT NULL DEFAULT '0',
  `shareCount` int(11) NOT NULL DEFAULT '0',
  `office` int(11) NOT NULL DEFAULT '0',
  `cityDefense` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `skillSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `passiveSkillSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `attrSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `equipSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `talentSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `talentOpen` int(11) NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `skinSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `soulSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `riseSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero`
--

LOCK TABLES `hero` WRITE;
/*!40000 ALTER TABLE `hero` DISABLE KEYS */;
/*!40000 ALTER TABLE `hero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hero_archives`
--

DROP TABLE IF EXISTS `hero_archives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hero_archives` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `archives` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero_archives`
--

LOCK TABLES `hero_archives` WRITE;
/*!40000 ALTER TABLE `hero_archives` DISABLE KEYS */;
/*!40000 ALTER TABLE `hero_archives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `itemId` int(11) NOT NULL DEFAULT '0',
  `itemCount` int(11) NOT NULL DEFAULT '0',
  `isNew` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboratory`
--

DROP TABLE IF EXISTS `laboratory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `laboratory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `pageIndex` int(11) NOT NULL DEFAULT '0',
  `powerCoreStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `powerBlockStr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `pageUnlock` int(11) NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboratory`
--

LOCK TABLES `laboratory` WRITE;
/*!40000 ALTER TABLE `laboratory` DISABLE KEYS */;
/*!40000 ALTER TABLE `laboratory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lifetime_card`
--

DROP TABLE IF EXISTS `lifetime_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lifetime_card` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `commonUnlockTime` bigint(20) NOT NULL DEFAULT '0',
  `advancedEndTime` bigint(20) NOT NULL DEFAULT '0',
  `weekAwardTime` bigint(20) NOT NULL DEFAULT '0',
  `monthAwardTime` bigint(20) NOT NULL DEFAULT '0',
  `freeEndTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `ready` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lifetime_card`
--

LOCK TABLES `lifetime_card` WRITE;
/*!40000 ALTER TABLE `lifetime_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `lifetime_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manhattan`
--

DROP TABLE IF EXISTS `manhattan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manhattan` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `swId` int(11) NOT NULL DEFAULT '0',
  `stage` int(11) NOT NULL DEFAULT '0',
  `posLevel` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `deployed` int(11) NOT NULL DEFAULT '0',
  `cityShow` int(11) NOT NULL DEFAULT '0',
  `base` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manhattan`
--

LOCK TABLES `manhattan` WRITE;
/*!40000 ALTER TABLE `manhattan` DISABLE KEYS */;
/*!40000 ALTER TABLE `manhattan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mecha_core`
--

DROP TABLE IF EXISTS `mecha_core`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mecha_core` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rankLevel` int(11) NOT NULL DEFAULT '0',
  `techInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `slotInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `suitInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `suitCount` int(11) NOT NULL DEFAULT '0',
  `workSuit` int(11) NOT NULL DEFAULT '0',
  `unlockedCityShow` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mecha_core`
--

LOCK TABLES `mecha_core` WRITE;
/*!40000 ALTER TABLE `mecha_core` DISABLE KEYS */;
/*!40000 ALTER TABLE `mecha_core` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mecha_core_module`
--

DROP TABLE IF EXISTS `mecha_core_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mecha_core_module` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `cfgId` int(11) NOT NULL DEFAULT '0',
  `quality` int(11) NOT NULL DEFAULT '0',
  `randomAttr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `locked` int(11) NOT NULL DEFAULT '0',
  `loadSuitInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mecha_core_module`
--

LOCK TABLES `mecha_core_module` WRITE;
/*!40000 ALTER TABLE `mecha_core_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `mecha_core_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medal_factory`
--

DROP TABLE IF EXISTS `medal_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medal_factory` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `exp` int(11) NOT NULL DEFAULT '0',
  `collectStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stealStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `stealTodayStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `canSteal` int(11) NOT NULL DEFAULT '0',
  `enemyStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dailyReward` int(11) NOT NULL DEFAULT '0',
  `dailyRefresh` int(11) NOT NULL DEFAULT '0',
  `refreshCool` bigint(20) NOT NULL DEFAULT '0',
  `lastRefreshDay` int(11) NOT NULL DEFAULT '0',
  `leyuzhuren` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medal_factory`
--

LOCK TABLES `medal_factory` WRITE;
/*!40000 ALTER TABLE `medal_factory` DISABLE KEYS */;
/*!40000 ALTER TABLE `medal_factory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mission`
--

DROP TABLE IF EXISTS `mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mission` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cfgId` int(11) NOT NULL DEFAULT '0',
  `num` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `unfinishPreMission` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mission`
--

LOCK TABLES `mission` WRITE;
/*!40000 ALTER TABLE `mission` DISABLE KEYS */;
/*!40000 ALTER TABLE `mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_reissue`
--

DROP TABLE IF EXISTS `money_reissue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `money_reissue` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `count` int(11) NOT NULL,
  `source` int(11) NOT NULL,
  `reissueParam` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_reissue`
--

LOCK TABLES `money_reissue` WRITE;
/*!40000 ALTER TABLE `money_reissue` DISABLE KEYS */;
/*!40000 ALTER TABLE `money_reissue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monster`
--

DROP TABLE IF EXISTS `monster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monster` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `maxLevel` int(11) NOT NULL DEFAULT '0',
  `currentLevelCount` int(11) NOT NULL DEFAULT '0',
  `newMonsterKileLvl` int(11) NOT NULL DEFAULT '0',
  `attackNewMonsterTimes` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `bosskillInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `bosskillRefreshDay` int(11) NOT NULL DEFAULT '0',
  `dropLimitInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dropLimitRefreshDay` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monster`
--

LOCK TABLES `monster` WRITE;
/*!40000 ALTER TABLE `monster` DISABLE KEYS */;
/*!40000 ALTER TABLE `monster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nation_build_quest`
--

DROP TABLE IF EXISTS `nation_build_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nation_build_quest` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `nationQuestType` int(11) NOT NULL DEFAULT '0',
  `refreshCount` int(11) NOT NULL DEFAULT '0',
  `questTimes` int(11) NOT NULL DEFAULT '0',
  `questInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nation_build_quest`
--

LOCK TABLES `nation_build_quest` WRITE;
/*!40000 ALTER TABLE `nation_build_quest` DISABLE KEYS */;
/*!40000 ALTER TABLE `nation_build_quest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nation_construction`
--

DROP TABLE IF EXISTS `nation_construction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nation_construction` (
  `buildingId` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `buildingStatus` int(11) NOT NULL DEFAULT '0',
  `buildVal` int(11) NOT NULL DEFAULT '0',
  `totalVal` int(11) NOT NULL DEFAULT '0',
  `buildTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`buildingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nation_construction`
--

LOCK TABLES `nation_construction` WRITE;
/*!40000 ALTER TABLE `nation_construction` DISABLE KEYS */;
INSERT INTO `nation_construction` VALUES (1,0,1,0,0,0,1776071656248,1776071656248,0),(2,0,1,0,0,0,1776071656262,1776071656262,0),(3,0,1,0,0,0,1776071656264,1776071656264,0),(4,0,1,0,0,0,1776071656267,1776071656267,0),(5,0,1,0,0,0,1776071656269,1776071656269,0),(6,0,1,0,0,0,1776071656270,1776071656270,0),(7,0,1,0,0,0,1776071656271,1776071656271,0),(8,0,1,0,0,0,1776071656272,1776071656272,0);
/*!40000 ALTER TABLE `nation_construction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nation_military`
--

DROP TABLE IF EXISTS `nation_military`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nation_military` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryExp` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryResetTerm` int(11) NOT NULL DEFAULT '0',
  `nationMilitarLlevel` int(11) NOT NULL DEFAULT '0',
  `crossTermId` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryBattleExp` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryRewardDay` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryReward` int(11) NOT NULL DEFAULT '0',
  `nationMilitaryRankTerm` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nation_military`
--

LOCK TABLES `nation_military` WRITE;
/*!40000 ALTER TABLE `nation_military` DISABLE KEYS */;
/*!40000 ALTER TABLE `nation_military` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nation_mission`
--

DROP TABLE IF EXISTS `nation_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nation_mission` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `missionStr` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `remainTimes` int(11) NOT NULL DEFAULT '0',
  `timeMark` bigint(20) NOT NULL DEFAULT '0',
  `constructionLevelMark` int(11) NOT NULL DEFAULT '0',
  `weekMark` int(11) NOT NULL DEFAULT '0',
  `tech` int(11) NOT NULL DEFAULT '0',
  `giveupTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nation_mission`
--

LOCK TABLES `nation_mission` WRITE;
/*!40000 ALTER TABLE `nation_mission` DISABLE KEYS */;
/*!40000 ALTER TABLE `nation_mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nation_ship_component`
--

DROP TABLE IF EXISTS `nation_ship_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nation_ship_component` (
  `componentId` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `upEndTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`componentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nation_ship_component`
--

LOCK TABLES `nation_ship_component` WRITE;
/*!40000 ALTER TABLE `nation_ship_component` DISABLE KEYS */;
INSERT INTO `nation_ship_component` VALUES (1,0,0,1776071803237,1776071803237,0),(2,0,0,1776071803251,1776071803251,0),(3,0,0,1776071803252,1776071803252,0),(4,0,0,1776071803256,1776071803256,0),(5,0,0,1776071803259,1776071803259,0),(6,0,0,1776071803261,1776071803261,0);
/*!40000 ALTER TABLE `nation_ship_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obelisk`
--

DROP TABLE IF EXISTS `obelisk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `obelisk` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cfgId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `contribution` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obelisk`
--

LOCK TABLES `obelisk` WRITE;
/*!40000 ALTER TABLE `obelisk` DISABLE KEYS */;
/*!40000 ALTER TABLE `obelisk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `officer`
--

DROP TABLE IF EXISTS `officer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `officer` (
  `officerId` int(11) NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `endTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`officerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `officer`
--

LOCK TABLES `officer` WRITE;
/*!40000 ALTER TABLE `officer` DISABLE KEYS */;
INSERT INTO `officer` VALUES (304101,'',0,1776070152637,1776070152637,0),(304102,'',0,1776070152651,1776070152651,0),(304103,'',0,1776070152652,1776070152652,0),(304104,'',0,1776070152656,1776070152656,0),(304105,'',0,1776070152659,1776070152659,0),(304106,'',0,1776070152663,1776070152663,0),(304107,'',0,1776070152665,1776070152665,0),(304108,'',0,1776070152667,1776070152667,0),(304109,'',0,1776070152669,1776070152669,0),(304110,'',0,1776070152671,1776070152671,0),(304111,'',0,1776070152673,1776070152673,0),(304112,'',0,1776070152675,1776070152675,0),(304113,'',0,1776070152678,1776070152678,0),(304114,'',0,1776070152680,1776070152680,0),(304115,'',0,1776070152682,1776070152682,0),(304116,'',0,1776070152683,1776070152683,0),(304117,'',0,1776070152685,1776070152685,0);
/*!40000 ALTER TABLE `officer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_state`
--

DROP TABLE IF EXISTS `pay_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_state` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rechargeGold` int(11) NOT NULL DEFAULT '0',
  `rechargeAwardId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastRechargeTime` bigint(20) NOT NULL DEFAULT '0',
  `rechargeInfo` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_state`
--

LOCK TABLES `pay_state` WRITE;
/*!40000 ALTER TABLE `pay_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_factory`
--

DROP TABLE IF EXISTS `plant_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plant_factory` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `plantCfgId` int(11) NOT NULL,
  `factoryType` int(11) NOT NULL,
  `lastResStoreTime` bigint(20) NOT NULL,
  `resStore` double DEFAULT '0',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_factory`
--

LOCK TABLES `plant_factory` WRITE;
/*!40000 ALTER TABLE `plant_factory` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_factory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_science`
--

DROP TABLE IF EXISTS `plant_science`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plant_science` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `plantScienceSerialized` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_playerId` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_science`
--

LOCK TABLES `plant_science` WRITE;
/*!40000 ALTER TABLE `plant_science` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_science` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_soldier_advance`
--

DROP TABLE IF EXISTS `plant_soldier_advance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plant_soldier_advance` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `advanceArmy` int(11) NOT NULL DEFAULT '0',
  `collectArmy` int(11) NOT NULL DEFAULT '0',
  `lastResStoreTime` bigint(20) NOT NULL DEFAULT '0',
  `resStore` double NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `advanceTotal` int(11) NOT NULL DEFAULT '0',
  `resTotal` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `advanceStart` bigint(20) NOT NULL DEFAULT '0',
  `advanceEnd` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_soldier_advance`
--

LOCK TABLES `plant_soldier_advance` WRITE;
/*!40000 ALTER TABLE `plant_soldier_advance` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_soldier_advance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_soldier_school`
--

DROP TABLE IF EXISTS `plant_soldier_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plant_soldier_school` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `instrumentSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `cracksSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `crystalSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `strengthenSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `militarySerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `militarySerialized3` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `switchInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_soldier_school`
--

LOCK TABLES `plant_soldier_school` WRITE;
/*!40000 ALTER TABLE `plant_soldier_school` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_soldier_school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_tech`
--

DROP TABLE IF EXISTS `plant_tech`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plant_tech` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cfgId` int(11) NOT NULL,
  `buildType` int(11) NOT NULL,
  `chipSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_tech`
--

LOCK TABLES `plant_tech` WRITE;
/*!40000 ALTER TABLE `plant_tech` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_tech` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `puid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `openid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `serverId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `vit` int(11) NOT NULL DEFAULT '0',
  `vitTime` bigint(20) NOT NULL DEFAULT '0',
  `icon` int(11) NOT NULL DEFAULT '0',
  `iconBuy` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `battlePoint` bigint(20) NOT NULL DEFAULT '0',
  `vipFreePoint` int(11) NOT NULL DEFAULT '0',
  `vipExp` int(11) NOT NULL DEFAULT '0',
  `vipLevel` int(11) NOT NULL DEFAULT '0',
  `vipFlag` int(11) NOT NULL DEFAULT '0',
  `militaryExp` int(11) NOT NULL DEFAULT '0',
  `talentType` int(11) NOT NULL DEFAULT '0',
  `platform` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `channel` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `channelId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `country` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `deviceId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `loginWay` int(11) NOT NULL DEFAULT '0',
  `lang` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `version` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `forbidenTime` bigint(20) NOT NULL DEFAULT '0',
  `silentTime` bigint(20) NOT NULL DEFAULT '0',
  `zeroEarningTime` bigint(20) NOT NULL DEFAULT '0',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `loginTime` bigint(20) NOT NULL DEFAULT '0',
  `logoutTime` bigint(20) NOT NULL DEFAULT '0',
  `loginMask` bigint(20) NOT NULL DEFAULT '0',
  `livelyMask` int(11) NOT NULL DEFAULT '0',
  `lastGmailCtime` bigint(20) NOT NULL DEFAULT '0',
  `factoryUpTime` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `oilConsumeTime` bigint(20) NOT NULL DEFAULT '0',
  `onlineTimeHistory` int(11) NOT NULL DEFAULT '0',
  `onlineTimeCurDay` int(11) NOT NULL DEFAULT '0',
  `spyMark` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `pos` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `isActive` int(11) NOT NULL DEFAULT '0',
  `superLab` int(11) NOT NULL DEFAULT '0',
  `actResetTime` bigint(20) NOT NULL DEFAULT '0',
  `beInvited` int(11) NOT NULL DEFAULT '0',
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `armourSuit` int(11) NOT NULL DEFAULT '1',
  `armourSuitCount` int(11) NOT NULL DEFAULT '1',
  `laboratory` int(11) NOT NULL DEFAULT '1',
  `unlockEquipResearch` int(11) NOT NULL DEFAULT '0',
  `maxBattlePoint` bigint(20) NOT NULL DEFAULT '0',
  `openharmony` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_achieve`
--

DROP TABLE IF EXISTS `player_achieve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_achieve` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `missions` text COLLATE utf8mb4_unicode_ci,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_achieve`
--

LOCK TABLES `player_achieve` WRITE;
/*!40000 ALTER TABLE `player_achieve` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_achieve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_base`
--

DROP TABLE IF EXISTS `player_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_base` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `expDec` int(11) NOT NULL DEFAULT '0',
  `gold` int(11) NOT NULL DEFAULT '0',
  `coin` int(11) NOT NULL DEFAULT '0',
  `goldore` bigint(20) NOT NULL DEFAULT '0',
  `oil` bigint(20) NOT NULL DEFAULT '0',
  `steel` bigint(20) NOT NULL DEFAULT '0',
  `steelUnsafe` bigint(20) NOT NULL DEFAULT '0',
  `tombarthite` bigint(20) NOT NULL DEFAULT '0',
  `tombarthiteUnsafe` bigint(20) NOT NULL DEFAULT '0',
  `guildContribution` bigint(20) NOT NULL DEFAULT '0',
  `recharge` int(11) NOT NULL DEFAULT '0',
  `diamonds` int(11) NOT NULL DEFAULT '0',
  `saveAmt` int(11) NOT NULL DEFAULT '0',
  `chargeAmt` int(11) NOT NULL DEFAULT '0',
  `goldoreUnsafe` bigint(20) NOT NULL DEFAULT '0',
  `oilUnsafe` bigint(20) NOT NULL DEFAULT '0',
  `unlockedArea` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `onFireEndTime` bigint(20) NOT NULL DEFAULT '0',
  `cityDefRepairTime` bigint(20) NOT NULL DEFAULT '0',
  `cityDefConsumeTime` bigint(20) NOT NULL DEFAULT '0',
  `cityDefVal` int(11) NOT NULL DEFAULT '0',
  `warFeverEndTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `flag` int(11) NOT NULL DEFAULT '0',
  `guildMilitaryScore` bigint(20) NOT NULL DEFAULT '0',
  `cyborgScore` bigint(20) NOT NULL DEFAULT '0',
  `dyzzScore` bigint(20) NOT NULL DEFAULT '0',
  `saveAmtTotal` int(11) NOT NULL DEFAULT '-1',
  `rechargeTotal` int(11) NOT NULL DEFAULT '-1',
  `levelUpTime` bigint(20) NOT NULL DEFAULT '0',
  `unlockAreaProgress` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`playerId`),
  KEY `level_index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_base`
--

LOCK TABLES `player_base` WRITE;
/*!40000 ALTER TABLE `player_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_daily_gift_buy`
--

DROP TABLE IF EXISTS `player_daily_gift_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_daily_gift_buy` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `termId` int(11) NOT NULL DEFAULT '0',
  `itemRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `refreshTime` bigint(20) NOT NULL DEFAULT '0',
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_daily_gift_buy`
--

LOCK TABLES `player_daily_gift_buy` WRITE;
/*!40000 ALTER TABLE `player_daily_gift_buy` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_daily_gift_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_ghost_tower`
--

DROP TABLE IF EXISTS `player_ghost_tower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_ghost_tower` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `stageId` int(11) NOT NULL,
  `productTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_ghost_tower`
--

LOCK TABLES `player_ghost_tower` WRITE;
/*!40000 ALTER TABLE `player_ghost_tower` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_ghost_tower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_gift`
--

DROP TABLE IF EXISTS `player_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_gift` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `giftGroupIds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `poolResetTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rootGroupIdRefRecords` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `buyLevels` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buyNums` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `giftAdvice` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `weekResetTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_gift`
--

LOCK TABLES `player_gift` WRITE;
/*!40000 ALTER TABLE `player_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_homeland`
--

DROP TABLE IF EXISTS `player_homeland`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_homeland` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `theme_id` int(11) NOT NULL DEFAULT '0',
  `prosperity` bigint(20) NOT NULL DEFAULT '0',
  `buildingData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `warehouseData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `buildingCollect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `likes` int(11) NOT NULL DEFAULT '0',
  `dailyLike` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `lastDailyLikeTime` bigint(20) NOT NULL DEFAULT '0',
  `themes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `activeProsperityAttr` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `shareTime` bigint(20) NOT NULL DEFAULT '0',
  `shopInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `npcInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRankTime` bigint(20) NOT NULL DEFAULT '0',
  `resolve` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_homeland`
--

LOCK TABLES `player_homeland` WRITE;
/*!40000 ALTER TABLE `player_homeland` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_homeland` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_other`
--

DROP TABLE IF EXISTS `player_other`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_other` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `dressItemInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `autoGuildCityMoveCnt` int(11) NOT NULL DEFAULT '0',
  `autoMarchParam` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `strengthPower` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_other`
--

LOCK TABLES `player_other` WRITE;
/*!40000 ALTER TABLE `player_other` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_other` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_recharge`
--

DROP TABLE IF EXISTS `player_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_recharge` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `goodsId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(11) NOT NULL,
  `isFirst` tinyint(1) NOT NULL,
  `todayTimes` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_recharge`
--

LOCK TABLES `player_recharge` WRITE;
/*!40000 ALTER TABLE `player_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_relation`
--

DROP TABLE IF EXISTS `player_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_relation` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `targetPlayerId` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` smallint(6) NOT NULL,
  `love` int(11) NOT NULL,
  `remark` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  `guard` tinyint(1) NOT NULL DEFAULT '0',
  `guardValue` int(11) NOT NULL DEFAULT '0',
  `operationTime` bigint(20) NOT NULL DEFAULT '0',
  `dressId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_relation`
--

LOCK TABLES `player_relation` WRITE;
/*!40000 ALTER TABLE `player_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_resource_gift`
--

DROP TABLE IF EXISTS `player_resource_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_resource_gift` (
  `playerId` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `boughtInfo` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_resource_gift`
--

LOCK TABLES `player_resource_gift` WRITE;
/*!40000 ALTER TABLE `player_resource_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_resource_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_war_college`
--

DROP TABLE IF EXISTS `player_war_college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_war_college` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `instanceInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `maxInstanceId` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `firstReward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `helpRwardCount` int(11) NOT NULL DEFAULT '0',
  `helpRwardDay` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_war_college`
--

LOCK TABLES `player_war_college` WRITE;
/*!40000 ALTER TABLE `player_war_college` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_war_college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_yqzz`
--

DROP TABLE IF EXISTS `player_yqzz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_yqzz` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `termId` int(11) NOT NULL DEFAULT '0',
  `achieveSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `leaveBattleTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `playerGuild` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_yqzz`
--

LOCK TABLES `player_yqzz` WRITE;
/*!40000 ALTER TABLE `player_yqzz` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_yqzz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plot_battle`
--

DROP TABLE IF EXISTS `plot_battle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plot_battle` (
  `playerId` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `levelId` int(11) NOT NULL DEFAULT '0',
  `status` smallint(2) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` tinyint(1) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plot_battle`
--

LOCK TABLES `plot_battle` WRITE;
/*!40000 ALTER TABLE `plot_battle` DISABLE KEYS */;
/*!40000 ALTER TABLE `plot_battle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `president`
--

DROP TABLE IF EXISTS `president`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `president` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `turnCount` int(11) DEFAULT '0',
  `presidentId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `presidentGuildId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `countryModify` int(11) NOT NULL DEFAULT '0',
  `countryName` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `countryIcon` int(11) NOT NULL DEFAULT '0',
  `attackerId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `attackerGuildId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `periodType` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `attackTime` bigint(20) NOT NULL DEFAULT '0',
  `peaceTime` bigint(20) NOT NULL DEFAULT '0',
  `tenureTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `president`
--

LOCK TABLES `president` WRITE;
/*!40000 ALTER TABLE `president` DISABLE KEYS */;
/*!40000 ALTER TABLE `president` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `push_gift`
--

DROP TABLE IF EXISTS `push_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `push_gift` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `giftIdTime` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `groupRefreshCount` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `resetTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `groupRefreshTime` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `groupStatistics` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `plantTechnologyTimes` int(11) NOT NULL DEFAULT '0',
  `plantSoldierCrackTimes` int(11) NOT NULL DEFAULT '0',
  `armourIntensifyTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `armourStarUpTimes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `push_gift`
--

LOCK TABLES `push_gift` WRITE;
/*!40000 ALTER TABLE `push_gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `push_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionnaire`
--

DROP TABLE IF EXISTS `questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionnaire` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pageSurveys` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mailSurveys` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `overdueSurveys` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `finishedSurveys` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastCheckTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionnaire`
--

LOCK TABLES `questionnaire` WRITE;
/*!40000 ALTER TABLE `questionnaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queue`
--

DROP TABLE IF EXISTS `queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queue` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `queueType` int(11) NOT NULL DEFAULT '0',
  `buildingType` int(11) NOT NULL DEFAULT '0',
  `itemId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `endTime` bigint(20) NOT NULL DEFAULT '0',
  `totalQueueTime` bigint(20) NOT NULL DEFAULT '0',
  `totalReduceTime` bigint(20) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `helpTimes` int(11) NOT NULL DEFAULT '0',
  `cancelBackRes` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `reusage` int(11) NOT NULL DEFAULT '-1',
  `enableEndTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `multiply` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queue`
--

LOCK TABLES `queue` WRITE;
/*!40000 ALTER TABLE `queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recharge`
--

DROP TABLE IF EXISTS `recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge` (
  `billno` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` int(11) NOT NULL,
  `diamonds` int(11) NOT NULL DEFAULT '0',
  `time` bigint(20) NOT NULL DEFAULT '0',
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `serverId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `puid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsId` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsPrice` int(11) NOT NULL DEFAULT '0',
  `payMoney` int(11) NOT NULL DEFAULT '0',
  `currency` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `awardItems` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`billno`),
  KEY `playerId_index` (`playerId`) USING BTREE,
  KEY `puid_index` (`puid`) USING BTREE,
  KEY `goodsId_index` (`goodsId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recharge`
--

LOCK TABLES `recharge` WRITE;
/*!40000 ALTER TABLE `recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recharge_back`
--

DROP TABLE IF EXISTS `recharge_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_back` (
  `billno` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` int(11) NOT NULL DEFAULT '0',
  `diamonds` int(11) NOT NULL DEFAULT '0',
  `time` bigint(20) NOT NULL DEFAULT '0',
  `token` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `serverId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `puid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsPrice` int(11) NOT NULL DEFAULT '0',
  `payMoney` int(11) NOT NULL DEFAULT '0',
  `currency` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `awardItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`billno`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recharge_back`
--

LOCK TABLES `recharge_back` WRITE;
/*!40000 ALTER TABLE `recharge_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `recharge_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recharge_daily`
--

DROP TABLE IF EXISTS `recharge_daily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_daily` (
  `billno` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` int(11) NOT NULL DEFAULT '0',
  `diamonds` int(11) NOT NULL DEFAULT '0',
  `time` bigint(20) NOT NULL DEFAULT '0',
  `token` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `serverId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `puid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `goodsPrice` int(11) NOT NULL DEFAULT '0',
  `payMoney` int(11) NOT NULL DEFAULT '0',
  `currency` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `awardItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`billno`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recharge_daily`
--

LOCK TABLES `recharge_daily` WRITE;
/*!40000 ALTER TABLE `recharge_daily` DISABLE KEYS */;
/*!40000 ALTER TABLE `recharge_daily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie_milestone`
--

DROP TABLE IF EXISTS `rookie_milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie_milestone` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `currentChapterId` int(11) NOT NULL DEFAULT '0',
  `missions` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `completeChapters` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie_milestone`
--

LOCK TABLES `rookie_milestone` WRITE;
/*!40000 ALTER TABLE `rookie_milestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `rookie_milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_identify`
--

DROP TABLE IF EXISTS `server_identify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_identify` (
  `serverIdentify` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `serverOpenTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`serverIdentify`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_identify`
--

LOCK TABLES `server_identify` WRITE;
/*!40000 ALTER TABLE `server_identify` DISABLE KEYS */;
INSERT INTO `server_identify` VALUES ('7pt-4eer5p-1',1776045600000,1776067709223,1776067907232,0);
/*!40000 ALTER TABLE `server_identify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `shopId` int(11) NOT NULL DEFAULT '0',
  `term` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `itemData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `extParam` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statistics` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `loginCnt` int(11) NOT NULL DEFAULT '0',
  `loginDay` int(11) NOT NULL DEFAULT '0',
  `warWinCnt` int(11) NOT NULL DEFAULT '0',
  `warLoseCnt` int(11) NOT NULL DEFAULT '0',
  `atkWinCnt` int(11) NOT NULL DEFAULT '0',
  `atkLoseCnt` int(11) NOT NULL DEFAULT '0',
  `atkInProtectCnt` int(11) NOT NULL DEFAULT '0',
  `defWinCnt` int(11) NOT NULL DEFAULT '0',
  `defLoseCnt` int(11) NOT NULL DEFAULT '0',
  `spyCnt` int(11) NOT NULL DEFAULT '0',
  `atkMonsterCnt` int(11) NOT NULL DEFAULT '0',
  `atkMonsterWinCnt` int(11) NOT NULL DEFAULT '0',
  `armyAddCnt` bigint(20) NOT NULL DEFAULT '0',
  `armyKillCnt` bigint(20) NOT NULL DEFAULT '0',
  `armyLoseCnt` bigint(20) NOT NULL DEFAULT '0',
  `armyCureCnt` bigint(20) NOT NULL DEFAULT '0',
  `joinGuildCnt` int(11) NOT NULL DEFAULT '0',
  `loseFightCnt` int(11) NOT NULL DEFAULT '0',
  `buyGoldCount` int(11) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `payAddLevel` int(11) NOT NULL DEFAULT '0',
  `isBeating` int(11) DEFAULT '0',
  `cdkType` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cityMoveRecord` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `commonStatisData` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_data`
--

DROP TABLE IF EXISTS `status_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_data` (
  `uuid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `statusId` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `targetId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `val` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL DEFAULT '0',
  `endTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uuid`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_data`
--

LOCK TABLES `status_data` WRITE;
/*!40000 ALTER TABLE `status_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `status_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_house`
--

DROP TABLE IF EXISTS `story_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_house` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `storeId` int(11) NOT NULL,
  `helpId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `queryHelp` int(11) NOT NULL,
  `openTime` bigint(20) NOT NULL,
  `collect` tinyint(1) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_house`
--

LOCK TABLES `story_house` WRITE;
/*!40000 ALTER TABLE `story_house` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_house_base`
--

DROP TABLE IF EXISTS `story_house_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_house_base` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `exc` int(11) NOT NULL DEFAULT '0',
  `excCount` int(11) NOT NULL DEFAULT '0',
  `nextFreeExc` bigint(20) NOT NULL DEFAULT '0',
  `lastExcRecover` bigint(20) NOT NULL DEFAULT '0',
  `help` int(11) NOT NULL,
  `lastHelpRecover` bigint(20) NOT NULL,
  `lastRefrash` bigint(20) NOT NULL,
  `overDay` bigint(20) NOT NULL,
  `store` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `refrashCount` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_house_base`
--

LOCK TABLES `story_house_base` WRITE;
/*!40000 ALTER TABLE `story_house_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_house_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_house_help`
--

DROP TABLE IF EXISTS `story_house_help`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_house_help` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `targetId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `storehouseId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `storeId` int(11) NOT NULL,
  `openTime` bigint(20) NOT NULL,
  `collect` tinyint(1) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_house_help`
--

LOCK TABLES `story_house_help` WRITE;
/*!40000 ALTER TABLE `story_house_help` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_house_help` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_mission`
--

DROP TABLE IF EXISTS `story_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_mission` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `chapterId` int(11) NOT NULL DEFAULT '0',
  `chapterState` int(11) NOT NULL DEFAULT '0',
  `missions` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `completeChapters` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `paralleledChapterMission` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_mission`
--

LOCK TABLES `story_mission` WRITE;
/*!40000 ALTER TABLE `story_mission` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `super_soldier`
--

DROP TABLE IF EXISTS `super_soldier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `super_soldier` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `soldierId` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `star` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  `skin` int(11) NOT NULL DEFAULT '0',
  `shareCount` int(11) NOT NULL DEFAULT '0',
  `office` int(11) NOT NULL DEFAULT '0',
  `cityDefense` int(11) NOT NULL DEFAULT '0',
  `exp` int(11) NOT NULL DEFAULT '0',
  `skillSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `passiveSkillSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `anyWhereUnlock` int(11) NOT NULL DEFAULT '0',
  `energySerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `skinSerialized` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `super_soldier`
--

LOCK TABLES `super_soldier` WRITE;
/*!40000 ALTER TABLE `super_soldier` DISABLE KEYS */;
/*!40000 ALTER TABLE `super_soldier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `talent`
--

DROP TABLE IF EXISTS `talent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talent` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `talentId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `skillId` bigint(20) NOT NULL DEFAULT '0',
  `skillRefTime` bigint(20) NOT NULL DEFAULT '0',
  `skillState` int(11) NOT NULL DEFAULT '0',
  `castSkillTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `talent`
--

LOCK TABLES `talent` WRITE;
/*!40000 ALTER TABLE `talent` DISABLE KEYS */;
/*!40000 ALTER TABLE `talent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tavern`
--

DROP TABLE IF EXISTS `tavern`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tavern` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL,
  `scoreAchieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveItems` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `achieveFinishCount` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tavern`
--

LOCK TABLES `tavern` WRITE;
/*!40000 ALTER TABLE `tavern` DISABLE KEYS */;
/*!40000 ALTER TABLE `tavern` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technology`
--

DROP TABLE IF EXISTS `technology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technology` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `techId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `researching` int(11) NOT NULL DEFAULT '0',
  `skillCd` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technology`
--

LOCK TABLES `technology` WRITE;
/*!40000 ALTER TABLE `technology` DISABLE KEYS */;
/*!40000 ALTER TABLE `technology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `war_flag`
--

DROP TABLE IF EXISTS `war_flag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `war_flag` (
  `flagId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ownerId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `currentId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `placeTime` bigint(20) NOT NULL DEFAULT '0',
  `life` int(11) NOT NULL,
  `completeTime` bigint(20) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL,
  `speed` double DEFAULT '0',
  `pointId` int(11) NOT NULL,
  `lastBuildTick` bigint(20) NOT NULL DEFAULT '0',
  `lastResourceTick` bigint(20) NOT NULL DEFAULT '0',
  `ownIndex` int(11) NOT NULL DEFAULT '0',
  `occupyLife` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL,
  `centerFlag` int(11) NOT NULL DEFAULT '0',
  `signUp` text CHARACTER SET utf8mb4 NOT NULL,
  `centerNextTickTime` bigint(20) NOT NULL DEFAULT '0',
  `centerActive` int(11) NOT NULL DEFAULT '0',
  `removeTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`flagId`),
  KEY `idx_flagId` (`flagId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `war_flag`
--

LOCK TABLES `war_flag` WRITE;
/*!40000 ALTER TABLE `war_flag` DISABLE KEYS */;
/*!40000 ALTER TABLE `war_flag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wharf`
--

DROP TABLE IF EXISTS `wharf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wharf` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastRefreshTime` bigint(20) NOT NULL,
  `awardTime` int(11) NOT NULL,
  `awardId` int(11) NOT NULL,
  `awardPoolId` int(11) NOT NULL,
  `isTookAward` tinyint(1) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wharf`
--

LOCK TABLES `wharf` WRITE;
/*!40000 ALTER TABLE `wharf` DISABLE KEYS */;
/*!40000 ALTER TABLE `wharf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishing_well`
--

DROP TABLE IF EXISTS `wishing_well`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wishing_well` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastWishTime` bigint(20) NOT NULL,
  `todayWishCounts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `extraWishCount` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishing_well`
--

LOCK TABLES `wishing_well` WRITE;
/*!40000 ALTER TABLE `wishing_well` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishing_well` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `world_march`
--

DROP TABLE IF EXISTS `world_march`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `world_march` (
  `marchId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerName` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `origionId` int(11) NOT NULL,
  `terminalId` int(11) NOT NULL,
  `marchType` int(11) NOT NULL,
  `marchIntention` int(11) NOT NULL DEFAULT '0',
  `marchStatus` int(11) NOT NULL,
  `marchProcMask` int(11) DEFAULT '0',
  `startTime` bigint(20) NOT NULL,
  `endTime` bigint(20) NOT NULL,
  `reachTime` bigint(20) NOT NULL DEFAULT '0',
  `marchJourneyTime` bigint(20) NOT NULL DEFAULT '0',
  `targetId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `targetPointField` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `targetPointType` int(11) NOT NULL DEFAULT '0',
  `leaderPlayerId` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `armyStr` text COLLATE utf8mb4_unicode_ci,
  `assistantStr` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `heroIdStr` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `superSoldierId` int(11) NOT NULL DEFAULT '0',
  `awardStr` text CHARACTER SET utf8mb4,
  `awardExtraStr` text CHARACTER SET utf8mb4,
  `resSpeedAdd` bigint(20) NOT NULL DEFAULT '0',
  `resStartTime` bigint(20) NOT NULL DEFAULT '0',
  `resEndTime` bigint(20) NOT NULL DEFAULT '0',
  `collectSpeed` double(20,6) NOT NULL DEFAULT '0.000000',
  `collectBaseSpeed` double(20,6) NOT NULL DEFAULT '0.000000',
  `massReadyTime` bigint(20) NOT NULL DEFAULT '0',
  `speedUpTimes` int(11) NOT NULL DEFAULT '0',
  `itemUseY` double(20,6) DEFAULT '0.000000',
  `itemUseX` double(20,6) DEFAULT '0.000000',
  `callBackX` double(20,6) NOT NULL DEFAULT '0.000000',
  `callBackY` double(20,6) NOT NULL DEFAULT '0.000000',
  `callBackTime` bigint(20) NOT NULL DEFAULT '0',
  `itemUseTime` bigint(20) NOT NULL DEFAULT '0',
  `attackTimes` int(11) NOT NULL DEFAULT '0',
  `buyItemTimes` int(11) NOT NULL DEFAULT '0',
  `manorMarchReachTime` bigint(20) DEFAULT NULL,
  `lastExploreTime` bigint(20) DEFAULT '0',
  `isOffensive` int(11) NOT NULL DEFAULT '0',
  `towerAttackInfo` text COLLATE utf8mb4_unicode_ci,
  `effect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `vitCost` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `alarmPointId` int(11) NOT NULL DEFAULT '0',
  `extraSpyMarch` int(11) NOT NULL DEFAULT '0',
  `armourSuit` int(11) NOT NULL DEFAULT '0',
  `talentType` int(11) NOT NULL DEFAULT '0',
  `superLab` int(11) NOT NULL DEFAULT '0',
  `emoticon` int(11) NOT NULL DEFAULT '0',
  `emoticonUseTime` bigint(20) NOT NULL DEFAULT '0',
  `formation` int(11) NOT NULL DEFAULT '0',
  `dressStr` text COLLATE utf8mb4_unicode_ci,
  `extraInfo` text COLLATE utf8mb4_unicode_ci,
  `mechacoreSuit` int(11) NOT NULL DEFAULT '0',
  `manhattanAtkSwId` int(11) NOT NULL DEFAULT '0',
  `manhattanDefSwId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`marchId`),
  KEY `index_playerId` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `world_march`
--

LOCK TABLES `world_march` WRITE;
/*!40000 ALTER TABLE `world_march` DISABLE KEYS */;
/*!40000 ALTER TABLE `world_march` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `world_point`
--

DROP TABLE IF EXISTS `world_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `world_point` (
  `id` int(11) NOT NULL DEFAULT '0',
  `x` int(11) NOT NULL DEFAULT '0',
  `y` int(11) NOT NULL DEFAULT '0',
  `areaId` int(11) NOT NULL DEFAULT '0',
  `zoneId` int(11) NOT NULL DEFAULT '0',
  `pointType` int(11) NOT NULL DEFAULT '0',
  `pointStatus` int(11) NOT NULL DEFAULT '0',
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `playerName` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `cityLevel` int(11) NOT NULL DEFAULT '0',
  `playerIcon` int(11) NOT NULL DEFAULT '0',
  `lastActiveTime` bigint(20) NOT NULL DEFAULT '0',
  `resourceId` int(11) NOT NULL DEFAULT '0',
  `monsterId` int(11) NOT NULL DEFAULT '0',
  `lifeStartTime` bigint(20) NOT NULL DEFAULT '0',
  `remainResNum` bigint(20) NOT NULL DEFAULT '0',
  `remainBlood` int(11) NOT NULL DEFAULT '0',
  `marchId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `guildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `guildBuildId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `buildingId` int(11) NOT NULL DEFAULT '0',
  `protectedEndTime` bigint(20) NOT NULL DEFAULT '0',
  `commonHurtEndTime` bigint(20) NOT NULL DEFAULT '0',
  `showEffect` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `ownerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `foggyInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  `emoticon` int(11) NOT NULL DEFAULT '0',
  `emoticonUseTime` bigint(20) NOT NULL DEFAULT '0',
  `personalProtectInfo` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `equipTechLevel` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `plantMilitaryLevel` int(11) NOT NULL DEFAULT '0',
  `atkManhattanSw` int(11) NOT NULL DEFAULT '0',
  `defManhattanSw` int(11) NOT NULL DEFAULT '0',
  `atkSwSkillId` int(11) NOT NULL DEFAULT '0',
  `defSwSkillId` int(11) NOT NULL DEFAULT '0',
  `plantMilitaryShow` int(11) NOT NULL DEFAULT '0',
  `mechaCoreShow` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `protectShowTimeLong` int(11) NOT NULL DEFAULT '0',
  `protectShowPlayerType` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `world_point`
--

LOCK TABLES `world_point` WRITE;
/*!40000 ALTER TABLE `world_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `world_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xqhx`
--

DROP TABLE IF EXISTS `xqhx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xqhx` (
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `season` int(11) NOT NULL DEFAULT '0',
  `usedPoint` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xqhx`
--

LOCK TABLES `xqhx` WRITE;
/*!40000 ALTER TABLE `xqhx` DISABLE KEYS */;
/*!40000 ALTER TABLE `xqhx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xqhx_talent`
--

DROP TABLE IF EXISTS `xqhx_talent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xqhx_talent` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `talentId` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `updateTime` bigint(20) NOT NULL DEFAULT '0',
  `invalid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xqhx_talent`
--

LOCK TABLES `xqhx_talent` WRITE;
/*!40000 ALTER TABLE `xqhx_talent` DISABLE KEYS */;
/*!40000 ALTER TABLE `xqhx_talent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yuri_strike`
--

DROP TABLE IF EXISTS `yuri_strike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuri_strike` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `playerId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cfgId` int(11) NOT NULL,
  `hasReward` int(11) NOT NULL,
  `areaIdLock` int(11) NOT NULL,
  `marchId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cleanQueueId` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `matchTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `invalid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_Index` (`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yuri_strike`
--

LOCK TABLES `yuri_strike` WRITE;
/*!40000 ALTER TABLE `yuri_strike` DISABLE KEYS */;
/*!40000 ALTER TABLE `yuri_strike` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-13 20:40:33
