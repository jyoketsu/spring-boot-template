-- MySQL dump 10.13  Distrib 9.1.0, for Linux (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	9.1.0

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionaries`
--

LOCK TABLES `dictionaries` WRITE;
/*!40000 ALTER TABLE `dictionaries` DISABLE KEYS */;
INSERT INTO `dictionaries` VALUES (1,'个','ge','unit',NULL,NULL),(2,'克','g','unit',NULL,NULL),(5,'勺','spoon','unit','2024-12-26 14:52:13.916653','2024-12-26 14:52:13.916653'),(9,'开','on','status','2025-01-08 16:04:57.429141','2025-01-08 16:04:57.429141'),(10,'关','off','status','2025-01-08 16:05:34.712613','2025-01-08 16:05:34.712613');
/*!40000 ALTER TABLE `dictionaries` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnu5hrmymffh3t0hfflblcedrx` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (1,'2025-01-10 09:54:19.619538','番茄炒蛋','简单家常菜','2024-12-26 15:09:20.717476',NULL),(2,'2024-12-27 10:29:24.483201','炒青菜','家常菜','2024-12-27 10:29:24.483201',NULL),(4,'2025-01-10 09:28:02.832517','青椒肉丝','简单家常菜','2025-01-10 09:28:02.832517',NULL),(5,'2025-02-19 13:36:46.428899','测试-食谱','这个是测试','2025-01-10 15:37:58.056762','[{\"imageUrl\":\"\",\"step\":\"这个是步骤1\"},{\"imageUrl\":\"\",\"step\":\"这个是步骤2\"},{\"imageUrl\":\"\",\"step\":\"这个是步骤3\"},{\"imageUrl\":\"https://img.picgo.net/2025/02/19/vue-i18n-logodcc92573cf70ed1f.png\",\"step\":\"步骤4\"},{\"imageUrl\":\"https://img.picgo.net/2025/01/13/20631967ced2e9a5a524975a.jpg\",\"step\":\"步骤5\"},{\"imageUrl\":\"https://img.picgo.net/2025/01/13/4e43181f9d2d25835.png\",\"step\":\"步骤6\"}]');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'2025-02-19 11:15:23.680519','2025-02-19 11:15:23.680519','https://img.picgo.net/2025/01/15/206319672c0ab23c4658d4e9.jpg','$2a$10$ZFlRBNG4/FsoPCOBlkggGuQM.6mJopvusCbDG9VsRFzffY2m0suH6','ADMIN','admin');
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

-- Dump completed on 2025-02-24 16:23:34
