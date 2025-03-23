-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: fashion_e_commerce
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.24.10.1

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
-- Table structure for table `bill_status`
--

DROP TABLE IF EXISTS `bill_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `bill_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKae9hdgju6u10vl0jaj3yiom7u` (`bill_id`),
  CONSTRAINT `FKae9hdgju6u10vl0jaj3yiom7u` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_status`
--

LOCK TABLES `bill_status` WRITE;
/*!40000 ALTER TABLE `bill_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name_location` varchar(255) DEFAULT NULL,
  `pay_type` enum('CASH','TRANSFORM') DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk8vs7ac9xknv5xp18pdiehpp1` (`user_id`),
  CONSTRAINT `FKk8vs7ac9xknv5xp18pdiehpp1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `color_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_of_product`
--

DROP TABLE IF EXISTS `material_of_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_of_product` (
  `material_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  KEY `FKav3ce4ovos9cxs8s2eklpbg3c` (`product_id`),
  KEY `FKaan5wymh9ki6b0v8qlvl8xibi` (`material_id`),
  CONSTRAINT `FKaan5wymh9ki6b0v8qlvl8xibi` FOREIGN KEY (`material_id`) REFERENCES `product_materials` (`id`),
  CONSTRAINT `FKav3ce4ovos9cxs8s2eklpbg3c` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_of_product`
--

LOCK TABLES `material_of_product` WRITE;
/*!40000 ALTER TABLE `material_of_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_of_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_of_role`
--

DROP TABLE IF EXISTS `permission_of_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_of_role` (
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FK27vqypo00xvqxi1wlu27yd4u1` (`role_id`),
  KEY `FK7gah02c7pey20f506b9ytxeqp` (`permission_id`),
  CONSTRAINT `FK27vqypo00xvqxi1wlu27yd4u1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK7gah02c7pey20f506b9ytxeqp` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_of_role`
--

LOCK TABLES `permission_of_role` WRITE;
/*!40000 ALTER TABLE `permission_of_role` DISABLE KEYS */;
INSERT INTO `permission_of_role` VALUES (1,1),(2,5),(3,5);
/*!40000 ALTER TABLE `permission_of_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpnvtwliis6p05pn6i3ndjrqt2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'2025-03-19 16:29:33.000000',NULL,_binary '\0','2025-03-19 16:29:37.000000',NULL,'Test api authentication','TEST_API'),(2,'2025-03-19 16:29:31.000000',NULL,_binary '\0','2025-03-19 16:29:36.000000',NULL,'Get all role','GET_ALL_ROLE'),(3,'2025-03-19 16:29:34.000000',NULL,_binary '\0','2025-03-19 16:29:35.000000',NULL,'Create new role','CREATE_ROLE'),(4,'2025-03-19 16:29:25.000000',NULL,_binary '\0','2025-03-19 16:29:21.000000',NULL,'Add some new role for user','ADD_ROLE_FOR_USER'),(5,'2025-03-19 16:30:46.000000',NULL,_binary '\0','2025-03-19 16:30:42.000000',NULL,'Remove some role of user','REMOVE_ROLE_OF_USER');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `promotion` double NOT NULL,
  `quantity` int NOT NULL,
  `bill_id` bigint DEFAULT NULL,
  `product_option_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9b9s4todwjbvfye1xhx1s5uvd` (`bill_id`),
  KEY `FK75oy8wnk0323xj00locwchih0` (`product_option_id`),
  CONSTRAINT `FK75oy8wnk0323xj00locwchih0` FOREIGN KEY (`product_option_id`) REFERENCES `product_options` (`id`),
  CONSTRAINT `FK9b9s4todwjbvfye1xhx1s5uvd` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `is_default` bit(1) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_materials`
--

DROP TABLE IF EXISTS `product_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_materials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_materials`
--

LOCK TABLES `product_materials` WRITE;
/*!40000 ALTER TABLE `product_materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_options`
--

DROP TABLE IF EXISTS `product_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_options` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `is_default` bit(1) NOT NULL,
  `price` double NOT NULL,
  `color_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `promotion_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs4jucklnr9t3ntii94qarr80n` (`color_id`),
  KEY `FK8vv4f8fru80wxocwgxwsrow61` (`product_id`),
  KEY `FKj00g9ruj7hy2qtodw2kgr7tra` (`promotion_id`),
  KEY `FK1xhgd1dfag3mtn9a5y4to46g4` (`size_id`),
  CONSTRAINT `FK1xhgd1dfag3mtn9a5y4to46g4` FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`),
  CONSTRAINT `FK8vv4f8fru80wxocwgxwsrow61` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKj00g9ruj7hy2qtodw2kgr7tra` FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`),
  CONSTRAINT `FKs4jucklnr9t3ntii94qarr80n` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_options`
--

LOCK TABLES `product_options` WRITE;
/*!40000 ALTER TABLE `product_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_types`
--

DROP TABLE IF EXISTS `product_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_types`
--

LOCK TABLES `product_types` WRITE;
/*!40000 ALTER TABLE `product_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `end` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent` int NOT NULL,
  `start` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `star` double NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_of_user`
--

DROP TABLE IF EXISTS `role_of_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_of_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT b'0',
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4f5v893r5jqd3iqe12nk8dt1q` (`user_id`,`role_id`),
  KEY `FK80a5iqvcvkxkd3skc9v8he8o7` (`role_id`),
  CONSTRAINT `FK5txlioy962qm7my7w6chkoj3h` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK80a5iqvcvkxkd3skc9v8he8o7` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_of_user`
--

LOCK TABLES `role_of_user` WRITE;
/*!40000 ALTER TABLE `role_of_user` DISABLE KEYS */;
INSERT INTO `role_of_user` VALUES (8,'2025-03-19 21:25:01.430348',NULL,_binary '\0','2025-03-19 21:25:01.430348',NULL,1,2),(10,'2025-03-19 22:11:09.192559',NULL,_binary '\0','2025-03-19 22:11:09.192559',NULL,1,3),(11,'2025-03-19 22:11:09.287097',NULL,_binary '\0','2025-03-19 22:11:09.287097',NULL,7,3),(12,'2025-03-23 07:35:58.000000',NULL,_binary '\0','2025-03-23 07:35:52.000000',NULL,5,1);
/*!40000 ALTER TABLE `role_of_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2025-03-19 15:48:52.000000',NULL,_binary '\0','2025-03-19 15:48:56.000000',NULL,'BASE'),(5,'2025-03-19 16:41:52.000000',NULL,_binary '\0','2025-03-19 16:41:24.435162',NULL,'ADMIN'),(7,'2025-03-16 16:17:15.582000',NULL,_binary '\0','2025-03-16 16:17:15.582568',NULL,'MANAGE');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sizes`
--

DROP TABLE IF EXISTS `sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sizes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` enum('L','M','S','XL','XXL','XXXL','XXXXL','XXXXXL') DEFAULT NULL,
  `size_number` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sizes`
--

LOCK TABLES `sizes` WRITE;
/*!40000 ALTER TABLE `sizes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_of_product`
--

DROP TABLE IF EXISTS `type_of_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_of_product` (
  `type_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  KEY `FK81lu0exbe29e3oy5qcnb7sely` (`product_id`),
  KEY `FK8xo5rsql6d3t6ggmhkjqev7q0` (`type_id`),
  CONSTRAINT `FK81lu0exbe29e3oy5qcnb7sely` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FK8xo5rsql6d3t6ggmhkjqev7q0` FOREIGN KEY (`type_id`) REFERENCES `product_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_of_product`
--

LOCK TABLES `type_of_product` WRITE;
/*!40000 ALTER TABLE `type_of_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_of_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrmincuqpi8m660j1c57xj7twr` (`user_id`),
  CONSTRAINT `FKrmincuqpi8m660j1c57xj7twr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sex` enum('FEMALE','MALE','OTHER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKf4pdcamta635qqbhgcyqvrg7f` (`user_id`),
  CONSTRAINT `FKicouhgavvmiiohc28mgk0kuj5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details`
--

LOCK TABLES `user_details` WRITE;
/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` VALUES (1,'2025-03-16 13:06:59.086965',NULL,_binary '\0','2025-03-16 13:06:59.086965',NULL,NULL,NULL,'Nguyễn Đình Lam','+84855354919',NULL,1);
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT b'0',
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-03-16 13:06:58.907754',NULL,_binary '\0','2025-03-23 07:51:37.333487',NULL,_binary '','kiminonawa1305@gmail.com','$2a$10$.qlelB.ooBW59Wbu4b6W3uCGNVXPsHwg2fcr1yDinAUiQRsxSF9BW'),(2,'2025-03-16 13:06:58.907754',NULL,_binary '\0','2025-03-18 08:16:23.278033',NULL,_binary '','ndlam.dev@gmail.com','$2a$10$CEtDjFC6jhWU7duRPjxoOOB6mRwgr9jgERaMKrTTGDiEaAwIY/Sta'),(3,'2025-03-16 13:06:58.907754',NULL,_binary '\0','2025-03-18 08:16:23.278033',NULL,_binary '','maitien13052003@gmail.com','$2a$10$CEtDjFC6jhWU7duRPjxoOOB6mRwgr9jgERaMKrTTGDiEaAwIY/Sta');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_of_category`
--

DROP TABLE IF EXISTS `voucher_of_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher_of_category` (
  `voucher_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  KEY `FK7n8bguv9xrqfkppwjv14e6kaw` (`category_id`),
  KEY `FK3ftpi5tw2oplkp43u8wmoudl8` (`voucher_id`),
  CONSTRAINT `FK3ftpi5tw2oplkp43u8wmoudl8` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  CONSTRAINT `FK7n8bguv9xrqfkppwjv14e6kaw` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_of_category`
--

LOCK TABLES `voucher_of_category` WRITE;
/*!40000 ALTER TABLE `voucher_of_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher_of_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_of_user`
--

DROP TABLE IF EXISTS `voucher_of_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher_of_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `used` bit(1) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `voucher_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpyrrud4if0lgvpsuhiksak2a4` (`user_id`),
  KEY `FKil1gkp3k5nv2df5vpdlnjlyj7` (`voucher_id`),
  CONSTRAINT `FKil1gkp3k5nv2df5vpdlnjlyj7` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  CONSTRAINT `FKpyrrud4if0lgvpsuhiksak2a4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_of_user`
--

LOCK TABLES `voucher_of_user` WRITE;
/*!40000 ALTER TABLE `voucher_of_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher_of_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_lock` bit(1) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `end` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent` int NOT NULL,
  `quantity` int NOT NULL,
  `start` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-23 12:32:08
