-- MySQL dump 10.13  Distrib 9.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dictionaries`
--

DROP TABLE IF EXISTS `dictionaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dictionaries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `dict_code` varchar(255) NOT NULL,
  `dict_type` varchar(255) NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbf870hiq3cp0j6we16a5hv4y` (`name`),
  UNIQUE KEY `UKcak91hyqqhhimebui7858g7m1` (`dict_type`,`dict_code`),
  UNIQUE KEY `UKiyy9cx2d8nfookadp5w93hyr` (`dict_type`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionaries`
--

LOCK TABLES `dictionaries` WRITE;
/*!40000 ALTER TABLE `dictionaries` DISABLE KEYS */;
INSERT INTO `dictionaries` VALUES (1,'个','ge','unit',NULL,NULL),(2,'克','g','unit',NULL,NULL),(5,'勺','spoon','unit','2024-12-26 14:52:13.916653','2024-12-26 14:52:13.916653'),(9,'开','on','status','2025-01-08 16:04:57.429141','2025-01-08 16:04:57.429141'),(10,'关','off','status','2025-01-08 16:05:34.712613','2025-01-08 16:05:34.712613'),(24,'大荤','big_meat','category','2025-10-10 15:36:19.707106','2025-10-10 15:36:19.707106'),(25,'小荤','small_meat','category','2025-10-11 16:45:51.753193','2025-10-11 16:45:51.753193'),(26,'素菜','vegetable','category','2025-10-11 16:46:17.269666','2025-10-11 16:46:17.269666'),(27,'汤','soup','category','2025-11-13 14:02:32.529452','2025-11-13 14:02:32.529452');
/*!40000 ALTER TABLE `dictionaries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg9v3f8f18je2t2ou8fvwse3kq` (`name`),
  KEY `FKl6khp1r2cjcys9mcicdjmays1` (`category_id`),
  CONSTRAINT `FKl6khp1r2cjcys9mcicdjmays1` FOREIGN KEY (`category_id`) REFERENCES `dictionaries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'2025-10-10 15:37:31.324519','2025-11-14 09:33:24.037493','红烧肉','https://psnine.com/Upload/game/34992.png','简介',24),(2,'2025-10-11 16:29:54.442886','2025-11-13 13:54:26.308578','清蒸鲈鱼','https://origin.picgo.net/2025/10/11/86229112951a0f0f54f49b72.png','测试菜品简介',24),(4,'2025-10-11 16:47:52.227769','2025-11-04 09:49:05.222872','番茄炒蛋','https://origin.picgo.net/2025/11/04/icon-cloudflare98d23c2daa666155.png','番茄炒蛋简介',26),(5,'2025-11-13 13:56:44.744834','2025-11-13 13:56:44.744834','奥尔良鸡翅','','简介',24),(6,'2025-11-13 13:57:12.442191','2025-11-13 13:57:12.442191','回锅肉','','回锅肉',24),(7,'2025-11-13 13:57:50.091587','2025-11-13 13:57:50.091587','麻婆豆腐','','麻婆豆腐',25),(8,'2025-11-13 13:58:10.304742','2025-11-13 13:58:10.304742','红烧大鸡腿','','红烧大鸡腿',24),(9,'2025-11-13 13:58:22.881540','2025-11-13 13:58:22.881540','蚂蚁上树','','蚂蚁上树',25),(10,'2025-11-13 13:58:44.282701','2025-11-13 13:58:44.282701','洋葱炒肉','','洋葱炒肉',25),(11,'2025-11-13 13:59:04.774274','2025-11-13 13:59:04.774274','肉末蒸蛋','','肉末蒸蛋',25),(12,'2025-11-13 13:59:24.431807','2025-11-13 13:59:24.431807','手撕包菜','','手撕包菜',25),(13,'2025-11-13 14:00:11.199632','2025-11-13 15:31:19.055436','炒青菜','','简介',26),(14,'2025-11-13 14:20:37.140800','2025-11-13 14:20:37.140800','蒜苔炒肉','','蒜苔炒肉',25),(15,'2025-11-13 14:22:53.343810','2025-11-13 14:22:53.343810','干锅花菜','','干锅花菜',25),(16,'2025-11-13 15:17:35.330132','2025-11-13 15:17:35.330132','韭菜炒蛋','','简介',26),(17,'2025-11-13 15:19:42.991529','2025-11-13 15:19:42.991529','番茄蛋汤','','简介',27),(18,'2025-11-13 15:20:04.067296','2025-11-13 15:20:04.067296','紫菜蛋汤','','简介',27),(19,'2025-11-13 15:20:18.716531','2025-11-13 15:20:18.716531','豆腐汤','','简介',27),(20,'2025-11-13 15:21:35.641761','2025-11-13 15:21:35.641761','排骨汤','','简介',27),(21,'2025-11-13 15:22:08.703995','2025-11-13 15:22:08.703995','冬瓜海带汤','','冬瓜海带汤',27),(22,'2025-11-13 15:23:06.463445','2025-11-13 15:23:06.463445','菠菜虾皮鸡蛋汤','','简介',27),(23,'2025-11-13 15:24:23.781795','2025-11-13 15:24:23.781795','土豆炖牛肉','','简介',24),(24,'2025-11-13 15:24:38.416528','2025-11-13 15:24:38.416528','梅菜扣肉','','简介',24),(25,'2025-11-13 15:24:57.436577','2025-11-13 15:24:57.436577','西红柿炖牛腩','','简介',24),(26,'2025-11-13 15:25:53.063925','2025-11-13 15:25:53.063925','糖醋排骨','','简介',24),(27,'2025-11-13 15:26:09.873535','2025-11-13 15:26:09.873535','水煮牛肉','','简介',24),(28,'2025-11-13 15:26:59.164774','2025-11-13 15:26:59.164774','蒸蛋','','简介',27),(29,'2025-11-13 15:28:53.690877','2025-11-13 15:28:53.690877','炒土豆丝','','简介',26),(30,'2025-11-13 15:29:06.736125','2025-11-13 15:29:06.736125','清炒西兰花','','简介',26),(31,'2025-11-13 15:29:31.906406','2025-11-13 15:29:41.591603','炒菠菜','','简介',26),(32,'2025-11-13 15:30:24.561996','2025-11-13 15:30:24.561996','凉拌黄瓜','','简介',26),(33,'2025-11-13 15:30:46.956231','2025-11-13 15:30:46.956231','炒豆芽','','简介',26);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `unit_id` bigint DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj6tsl15xx76y4kv41yxr4uxab` (`name`),
  KEY `FKnnycs14he8on0ls7rykhualux` (`unit_id`),
  CONSTRAINT `FKnnycs14he8on0ls7rykhualux` FOREIGN KEY (`unit_id`) REFERENCES `dictionaries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (1,'番茄',1,NULL,NULL),(2,'青菜',2,NULL,NULL),(4,'菠菜',2,'2024-12-26 14:03:54.310665','2024-12-26 14:03:54.310665'),(5,'鸡蛋',1,'2024-12-26 14:41:51.137288','2024-12-26 14:41:51.137288'),(6,'香葱',2,'2024-12-26 14:47:36.792553','2024-12-26 14:47:36.792553'),(8,'青椒',2,'2025-01-10 09:26:16.363024','2025-01-10 09:26:16.363024'),(9,'肉丝',2,'2025-01-10 09:26:23.614266','2025-01-10 09:26:23.614266');
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meal_rule`
--

DROP TABLE IF EXISTS `meal_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meal_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `rule_json` text,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtcd9fi7bkwte1sv3psc66fcdb` (`user_id`),
  CONSTRAINT `FKtcd9fi7bkwte1sv3psc66fcdb` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meal_rule`
--

LOCK TABLES `meal_rule` WRITE;
/*!40000 ALTER TABLE `meal_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `meal_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_ingredients`
--

DROP TABLE IF EXISTS `recipe_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_ingredients` (
  `quantity` double DEFAULT NULL,
  `ingredient_id` bigint NOT NULL,
  `recipe_id` bigint NOT NULL,
  PRIMARY KEY (`ingredient_id`,`recipe_id`),
  KEY `FKcqlw8sor5ut10xsuj3jnttkc` (`recipe_id`),
  CONSTRAINT `FKcqlw8sor5ut10xsuj3jnttkc` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`),
  CONSTRAINT `FKgukrw6na9f61kb8djkkuvyxy8` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_ingredients`
--

LOCK TABLES `recipe_ingredients` WRITE;
/*!40000 ALTER TABLE `recipe_ingredients` DISABLE KEYS */;
INSERT INTO `recipe_ingredients` VALUES (500,1,1),(500,1,5),(500,2,2),(3,5,1),(3,5,5),(10,6,5),(500,8,4),(500,9,4);
/*!40000 ALTER TABLE `recipe_ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `content` text,
  `dish_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnu5hrmymffh3t0hfflblcedrx` (`name`),
  KEY `FKcj53m9x5lckanwckx16ad8tan` (`dish_id`),
  CONSTRAINT `FKcj53m9x5lckanwckx16ad8tan` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (1,'2025-11-04 13:04:50.259378','番茄炒蛋','简单家常菜','2024-12-26 15:09:20.717476','null',4),(2,'2024-12-27 10:29:24.483201','炒青菜','家常菜','2024-12-27 10:29:24.483201',NULL,NULL),(4,'2025-01-10 09:28:02.832517','青椒肉丝','简单家常菜','2025-01-10 09:28:02.832517',NULL,NULL),(5,'2025-11-04 13:04:30.873499','测试-食谱','这个是测试','2025-01-10 15:37:58.056762','[]',4);
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `username` varchar(255) NOT NULL,
  `wechat_open_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UK5xkgfmis4otuj2ca9pbw66erm` (`wechat_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'2025-02-19 11:15:23.680519','2025-02-19 11:15:23.680519','https://img.picgo.net/2025/01/15/206319672c0ab23c4658d4e9.jpg','$2a$10$ZFlRBNG4/FsoPCOBlkggGuQM.6mJopvusCbDG9VsRFzffY2m0suH6','ADMIN','admin',NULL),(5,'2025-05-14 10:28:17.238937','2025-05-14 10:28:17.238937',NULL,'$2a$10$iXXo75DEzkdFc74AP3lul.tIEyr8xHgS0XsdnLUxPVkSJEE3LIXO6','USER','user',NULL),(6,'2025-09-25 16:59:26.880530','2025-09-25 16:59:26.880530',NULL,'$2a$10$rS5q3RTNeM42b22Jv9srC.jTF7lXBL9Z52Wwxj0kucAdl.ZeWq0By','USER','wechat_user_1758790766784','ogcTG5auqythmy7TCej-WT623U6s');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-14 16:09:37
