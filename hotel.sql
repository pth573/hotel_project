-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel_manager
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bed`
--

DROP TABLE IF EXISTS `bed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bed_number` int NOT NULL,
  `bed_type` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed`
--

LOCK TABLES `bed` WRITE;
/*!40000 ALTER TABLE `bed` DISABLE KEYS */;
INSERT INTO `bed` VALUES (1,2,0),(18,1,0),(19,0,1),(20,0,2),(21,0,3),(22,0,0),(23,1,1),(24,0,2),(25,0,3),(26,0,0),(27,0,1),(28,0,2),(29,1,3),(30,2,0),(31,0,1),(32,1,2),(33,1,3),(50,1,0),(51,0,1),(52,0,2),(53,0,3),(54,1,0),(55,0,1),(56,0,2),(57,0,3),(58,1,0),(59,0,1),(60,0,2),(61,0,3),(62,1,0),(63,2,1),(64,0,2),(65,0,3),(66,1,0),(67,0,1),(68,0,2),(69,0,3),(70,1,0),(71,0,1),(72,11,2),(73,0,3),(74,1,0),(75,0,1),(76,0,2),(77,0,3),(78,1,0),(79,0,1),(80,0,2),(81,0,3),(82,1,0),(83,1,1),(84,0,2),(85,0,3),(86,1,0),(87,1,1),(88,2,2),(89,0,3),(90,1,0),(91,0,1),(92,1,2),(93,0,3),(114,0,0),(115,1,1),(116,0,2),(117,0,3),(118,0,0),(119,1,1),(120,0,2),(121,0,3),(122,2,0),(123,0,1),(124,0,2),(125,0,3),(126,2,0),(127,0,1),(128,0,2),(129,1,3),(130,0,0),(131,0,1),(132,2,2),(133,1,3),(134,0,0),(135,0,1),(136,2,2),(137,1,3),(138,0,0),(139,0,1),(140,1,2),(141,1,3),(142,0,0),(143,0,1),(144,1,2),(145,1,3);
/*!40000 ALTER TABLE `bed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `check_in_date` varchar(255) DEFAULT NULL,
  `check_out_date` varchar(255) DEFAULT NULL,
  `status` enum('ACCEPTED','DELETED','PENDING','REJECTED') DEFAULT NULL,
  `total_price` bigint DEFAULT NULL,
  `room_id` bigint NOT NULL,
  `amount_has_paid` bigint DEFAULT NULL,
  `payment_status` enum('DEPOSIT_PAID','NOT_PAID','PAID','PENDING') DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`),
  KEY `FKlnnelfsha11xmo2ndjq66fvro` (`customer_id`),
  CONSTRAINT `FKlnnelfsha11xmo2ndjq66fvro` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (86,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-01 10:00:00','2024-10-03 10:00:00','ACCEPTED',5000000,16,5000000,'DEPOSIT_PAID',3),(87,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-04 12:00:00','2024-10-07 12:00:00','ACCEPTED',7000000,17,7000000,'PAID',3),(88,'2024-11-24 17:09:37.000000','2024-11-24 17:12:31.021735','2024-10-08 14:00:00','2024-10-10 14:00:00','ACCEPTED',3000000,18,3000000,'NOT_PAID',3),(89,'2024-11-24 17:09:37.000000','2024-11-24 17:12:39.863496','2024-10-11 09:00:00','2024-10-15 09:00:00','ACCEPTED',12000000,19,12000000,'DEPOSIT_PAID',3),(90,'2024-11-24 17:09:37.000000','2024-11-24 17:12:45.711876','2024-10-16 08:00:00','2024-10-19 08:00:00','ACCEPTED',4000000,20,4000000,'PENDING',5),(91,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-20 15:00:00','2024-10-22 15:00:00','ACCEPTED',2000000,21,2000000,'PAID',6),(92,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-23 18:00:00','2024-10-26 18:00:00','PENDING',8000000,22,8000000,'DEPOSIT_PAID',7),(93,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-27 11:00:00','2024-10-29 11:00:00','REJECTED',3500000,23,3500000,'NOT_PAID',8),(94,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-10-30 13:00:00','2024-11-01 13:00:00','ACCEPTED',9000000,24,8900000,'PAID',9),(95,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-02 14:00:00','2024-11-05 14:00:00','DELETED',6500000,25,2500000,'PENDING',3),(96,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-06 10:00:00','2024-11-08 10:00:00','PENDING',6000000,26,3000000,'DEPOSIT_PAID',3),(97,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-09 16:00:00','2024-11-11 16:00:00','ACCEPTED',7500000,27,7500000,'PAID',3),(98,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-12 17:00:00','2024-11-14 17:00:00','REJECTED',2500000,28,0,'NOT_PAID',3),(99,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-15 19:00:00','2024-11-17 19:00:00','PENDING',10000000,29,5000000,'DEPOSIT_PAID',3),(100,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-18 20:00:00','2024-11-20 20:00:00','DELETED',3000000,16,0,'PENDING',3),(101,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-21 09:00:00','2024-11-23 09:00:00','ACCEPTED',11000000,17,5500000,'PAID',3),(102,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-24 11:00:00','2024-11-26 11:00:00','PENDING',4000000,18,2000000,'DEPOSIT_PAID',3),(103,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-27 14:00:00','2024-11-29 14:00:00','REJECTED',7500000,19,0,'NOT_PAID',3),(104,'2024-11-24 17:09:37.000000','2024-11-24 17:09:37.000000','2024-11-30 15:00:00','2024-12-02 15:00:00','PENDING',8500000,20,4250000,'DEPOSIT_PAID',3),(105,'2024-11-24 17:36:02.043457','2024-11-24 17:36:02.043457','2024-11-24 14:00:00','2024-11-25 11:00:00','PENDING',1000000,16,NULL,NULL,3);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (3,'2024-11-14 01:00:20.413348',NULL,'2024-11-14 01:00:20.413348','	Thành phố Thái Bình','01/01/2003','user@gmail.com','Phạm Thị Hương Giang','$2a$10$ZFj.R4uM7p1VSLEl/Z4UJOi7iOfYwA8azl0eoJDvA7.MNRoa5kisy','0123456789'),(5,'2024-11-15 17:38:47.828376',NULL,'2024-11-15 17:38:47.828376','Vũ Thư. Thái Bình','02/02/2003','admin@gmail.com','ADMIN','$2a$10$OsE6tWHcoAuBXHUEp8yl..yWrTUvbJMU6h.dMQUseCJYyL95Sv3jS','0123456789'),(6,'2024-11-18 00:04:04.410996',NULL,'2024-11-18 00:04:04.410996','Đông Hưng, Thái Bình','03/02/2003','136662c5@example.com','Trần Lan Hương',NULL,'0950622522'),(7,'2024-11-18 00:17:21.730077',NULL,'2024-11-18 00:17:21.730077','Hưng Hà, Thái Bình','02/04/2003','ae783eb4@example.com','Lê Hoàng Mai',NULL,'0989086658'),(8,'2024-11-18 00:25:23.698779',NULL,'2024-11-18 00:25:23.698779','Thái Thụy, Thái Bình','03/02/2003','c94c29da@example.com','Trần Thị Thanh',NULL,'0193565750'),(9,'2024-11-18 00:27:24.432723',NULL,'2024-11-18 00:27:24.432723','Tiền Hải, Thái Bình','02/02/2002','1ea0aa13@example.com','Nguyễn Anh Phan',NULL,'0600568804'),(10,'2024-11-18 00:32:59.231319',NULL,'2024-11-18 00:32:59.231319','Vũ Thư, Thái Bình','22/12/2003','f8cfd269@example.com','Nguyễn Văn A',NULL,'0522146798'),(11,'2024-11-18 00:36:50.802496',NULL,'2024-11-18 00:36:50.802496','Quỳnh Phụ,Thái Bình','13/12/2003','9691e814@example.com','Mai Đức Dũng',NULL,'0709027608'),(12,'2024-11-18 00:40:56.030481',NULL,'2024-11-18 00:40:56.030481','Hưng Hà, Thái Bình','23/12/2004','3202adb4@example.com','Nguyễn Thanh Mai',NULL,'0978697672'),(13,'2024-11-18 00:43:03.947927',NULL,'2024-11-18 00:43:03.947927','Thái Thụy, Thái Bình','23/12/2002','d6d42d1e@example.com','Phạm Bá Chung',NULL,'0866405564'),(14,'2024-11-18 00:46:27.255603',NULL,'2024-11-18 00:46:27.255603','Tiền Hải, Thái Bình','23/12/2002','ceca87db@example.com','Trần Thanh Hương',NULL,'0395934832'),(15,'2024-11-18 00:52:07.263220',NULL,'2024-11-18 00:52:07.263220','Vũ Thư, Thái Bình','23/12/2001','46ea2f00@example.com','Phạm An Chi',NULL,'0180457496'),(16,'2024-11-18 00:53:33.380950',NULL,'2024-11-18 00:53:33.380950','Quỳnh Phụ,Thái Bình','23/12/2012','0f54e08a@example.com','Đàm Văn Hiển',NULL,'0514483012'),(17,'2024-11-18 00:56:19.852922',NULL,'2024-11-18 00:56:19.852922','Hưng Hà, Thái Bình','23/12/2002','729114dd@example.com','Nguyễn Văn Thắng',NULL,'0131042601'),(18,'2024-11-18 01:00:00.957016',NULL,'2024-11-18 01:00:00.957016','Thái Thụy, Thái Bình','23/12/2002','3fds3423@example.com','Nguyễn Hương Giang',NULL,'0123912992'),(19,'2024-11-18 01:04:43.582128',NULL,'2024-11-18 01:04:43.582128','	Thành phố Thái Bình','23/12/2004','fdg435ys@example.com','Trần Hương Giang',NULL,'0239919321'),(20,'2024-11-24 18:10:53.235774',NULL,'2024-11-24 18:10:53.235774',NULL,NULL,'giang123@gmail.com','Nguyễn Minh Giang',NULL,'0987654321'),(21,'2024-11-24 18:23:06.820783',NULL,'2024-11-24 18:23:06.820783',NULL,NULL,'giang1234@gmail.com','Nguyễn Văn Giang ',NULL,'0987654321');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_role`
--

DROP TABLE IF EXISTS `customer_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_role` (
  `customer_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKhfpoop4wxkxlxtlm6j6pqa827` (`role_id`),
  KEY `FKipr3etk2mjwkv6ahb4x4vwqy3` (`customer_id`),
  CONSTRAINT `FKhfpoop4wxkxlxtlm6j6pqa827` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `FKipr3etk2mjwkv6ahb4x4vwqy3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_role`
--

LOCK TABLES `customer_role` WRITE;
/*!40000 ALTER TABLE `customer_role` DISABLE KEYS */;
INSERT INTO `customer_role` VALUES (3,2),(5,1),(5,1);
/*!40000 ALTER TABLE `customer_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `reply_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `review_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `FKd5ckwt38d4ibe84wlfc3o8jw8` (`review_id`),
  KEY `FK260040758xk1bj0vur54t3js2` (`customer_id`),
  CONSTRAINT `FK260040758xk1bj0vur54t3js2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKd5ckwt38d4ibe84wlfc3o8jw8` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (4,'Dạ bên mình cám ơn bạn rất nhiều, mong sẽ đưược bạn ủng hộ lần tiếp','2024-11-24 18:05:57.292492',8,5);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `rating` int NOT NULL,
  `booking_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FKk4xawqohtguy5yx5nnpba6yf3` (`booking_id`),
  KEY `FKgce54o0p6uugoc2tev4awewly` (`customer_id`),
  CONSTRAINT `FKgce54o0p6uugoc2tev4awewly` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKk4xawqohtguy5yx5nnpba6yf3` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Phòng xịn, rộng đẹp xinh',5,1,'2024-11-15 13:21:10.746931',3),(2,'Tạm được',2,1,'2024-11-15 13:21:31.937383',3),(3,'Đồ ăn sáng không đủ',1,1,'2024-11-15 13:23:44.268981',3),(4,'Phòng xịn, rộng',4,1,'2024-11-15 13:26:31.441068',3),(5,'Xinh, nhân vên nhiệt tình',3,2,'2024-11-15 13:36:31.946836',3),(8,'Phòng đẹp, sạch sẽ',5,89,'2024-11-24 17:29:57.201717',3);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'CUSTOMER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `room_group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FK1oxc28nrsip1bmlgynvt12gca` (`room_group_id`),
  CONSTRAINT `FK1oxc28nrsip1bmlgynvt12gca` FOREIGN KEY (`room_group_id`) REFERENCES `room_group` (`room_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (16,'2024-11-24 16:50:18.294323','2024-11-24 16:50:18.294323','Superior_R1','Superior_R1',32),(17,'2024-11-24 16:50:27.220393','2024-11-24 16:50:27.220393','Superior_R2','Superior_R2',32),(18,'2024-11-24 16:50:38.739198','2024-11-24 16:50:38.739198','Superior_R3','Superior_R3',32),(19,'2024-11-24 16:50:47.944644','2024-11-24 16:50:47.944644','Superior_R4','Superior_R4',32),(20,'2024-11-24 16:51:05.136716','2024-11-24 16:51:05.136716','Deluxe_R1','Deluxe_R1',33),(21,'2024-11-24 16:51:11.257878','2024-11-24 16:51:11.257878','Deluxe_R2','Deluxe_R2',33),(22,'2024-11-24 16:51:18.579479','2024-11-24 16:51:18.579479','Deluxe_R3','Deluxe_R3',33),(23,'2024-11-24 16:51:37.659685','2024-11-24 16:51:37.659685','Premium_R1','Premium_R1',34),(24,'2024-11-24 16:51:45.840205','2024-11-24 16:51:45.840205','Premium_R2','Premium_R2',34),(25,'2024-11-24 16:51:53.857704','2024-11-24 16:51:53.857704','Premium_R3','Premium_R3',34),(26,'2024-11-24 16:52:12.604160','2024-11-24 16:52:12.604160','Suite_R1','Suite_R1',35),(27,'2024-11-24 16:52:19.000950','2024-11-24 16:52:19.000950','Suite_R2','Suite_R2',35),(28,'2024-11-24 16:52:36.688041','2024-11-24 16:52:36.688041','Grand Suite R1','Grand Suite R1',36),(29,'2024-11-24 16:52:44.633068','2024-11-24 16:52:44.633068','Grand Suite R2','Grand Suite R2',36);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_group`
--

DROP TABLE IF EXISTS `room_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_group` (
  `room_group_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `area` float DEFAULT NULL,
  `combo_price2h` bigint DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `extra_hour_price` bigint DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price_per_night` bigint DEFAULT NULL,
  `standard_occupancy` int DEFAULT NULL,
  `extra_adult` bigint DEFAULT NULL,
  `max_occupancy` int NOT NULL,
  `num_children_free` int NOT NULL,
  `available_room_count` bigint DEFAULT NULL,
  `price_date_time` bigint DEFAULT NULL,
  PRIMARY KEY (`room_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_group`
--

LOCK TABLES `room_group` WRITE;
/*!40000 ALTER TABLE `room_group` DISABLE KEYS */;
INSERT INTO `room_group` VALUES (32,'2024-11-24 16:18:40.928565','2024-11-24 16:34:40.123600',30,200000,'Nội thất được thiết kế một cách tinh tế, tạo cảm giác ấm cúng và gần gũi. Là lựa chọn lý tưởng cho du khách có sở thích ngắm nhìn ánh ban mai buổi sớm.',80000,'Phòng Superior','http://localhost:8080/room_groups/anh-phong-4.jpg',1000000,2,100000,3,1,0,0),(33,'2024-11-24 16:37:08.701531','2024-11-24 16:37:08.701531',34,300000,'Mang hơi thở hiện đại, sở hữu tầm nhìn hướng trung tâm thành phố. Du khách có thể ngắm nhìn bao quát cuộc sống yên bình của người dân Thành phố Thái Bình một cách chân thực nhất.\r\n',90000,'Phòng Deluxe','http://localhost:8080/room_groups/anh-phong-6.jpg',1200000,4,100000,6,2,0,0),(34,'2024-11-24 16:40:20.439197','2024-11-24 16:40:20.439197',36,400000,'Kiến trúc tao nhã, tinh tế, hệ thống ban công rộng đáp ứng nhu cầu nghỉ ngơi, thư giãn của du khách sau thời gian làm việc căng thẳng, mệt mỏi. Du khách có thể ngắm nhìn hoàng hôn phía chân trời và hít thở không khí trong lành của vùng quê lúa.\r\n',150000,'Phòng Premium','http://localhost:8080/room_groups/anh-phong-8.jpg',1400000,5,100000,7,2,0,0),(35,'2024-11-24 16:44:00.769470','2024-11-24 16:44:00.769470',48,400000,'Kết hợp hiện đại và tinh tế, không gian của du khách luôn ngập tràn anh sáng thiên nhiên\r\nPhòng khách riêng biệt, phòng ngủ với giường đôi lớn, phòng tắm được trang bị thêm bồn tắm và các thiết bị vệ sinh cao cấp.\r\n',149964,'Phòng Suite','http://localhost:8080/room_groups/anh-phong-15.jpg',1900000,5,100000,7,2,0,0),(36,'2024-11-24 16:49:13.268912','2024-11-24 16:49:41.706008',67,550000,'Là hạng phòng cao cấp nhất tại Selegend Hotel. Hòa quyện tổng thể của kiến trúc hiện đại lấy phong cách chủ đạo vùng quê lúa, các phòng Grand Suite có tầm nhìn bao quát toàn cảnh thành phố và luôn ngập tràn ánh sáng thiên nhiên. \r\nPhòng khách riêng biệt và sang trọng, phòng ngủ với giường đôi lớn, phòng tắm được trang bị thêm bồn tắm và các thiết bị vệ sinh hiện đại.\r\n',150000,'Phòng Grand Suite','http://localhost:8080/room_groups/anh-phong-23.jpg',2500000,5,150000,8,2,0,0);
/*!40000 ALTER TABLE `room_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_group_bed`
--

DROP TABLE IF EXISTS `room_group_bed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_group_bed` (
  `room_group_id` bigint NOT NULL,
  `bed_id` int NOT NULL,
  KEY `FKr50oug5577hmvvgxplateoh1b` (`bed_id`),
  KEY `FKdkx1tdfrorym2yrcv6epidp8p` (`room_group_id`),
  CONSTRAINT `FKdkx1tdfrorym2yrcv6epidp8p` FOREIGN KEY (`room_group_id`) REFERENCES `room_group` (`room_group_id`),
  CONSTRAINT `FKr50oug5577hmvvgxplateoh1b` FOREIGN KEY (`bed_id`) REFERENCES `bed` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_group_bed`
--

LOCK TABLES `room_group_bed` WRITE;
/*!40000 ALTER TABLE `room_group_bed` DISABLE KEYS */;
INSERT INTO `room_group_bed` VALUES (32,118),(32,119),(32,120),(32,121),(33,122),(33,123),(33,124),(33,125),(34,126),(34,127),(34,128),(34,129),(35,130),(35,131),(35,132),(35,133),(36,142),(36,143),(36,144),(36,145);
/*!40000 ALTER TABLE `room_group_bed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_group_service`
--

DROP TABLE IF EXISTS `room_group_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_group_service` (
  `room_group_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  KEY `FKleufvhprvkl5tp3p85d1kh9yd` (`service_id`),
  KEY `FK6eeu0rmxklaiu8es6e9xquih5` (`room_group_id`),
  CONSTRAINT `FK6eeu0rmxklaiu8es6e9xquih5` FOREIGN KEY (`room_group_id`) REFERENCES `room_group` (`room_group_id`),
  CONSTRAINT `FKleufvhprvkl5tp3p85d1kh9yd` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_group_service`
--

LOCK TABLES `room_group_service` WRITE;
/*!40000 ALTER TABLE `room_group_service` DISABLE KEYS */;
INSERT INTO `room_group_service` VALUES (32,12),(32,13),(32,16),(32,20),(33,11),(33,12),(33,13),(33,16),(33,20),(34,11),(34,12),(34,14),(34,16),(34,20),(35,12),(35,13),(35,14),(35,15),(35,16),(35,20),(36,11),(36,12),(36,13),(36,14),(36,15),(36,16),(36,20);
/*!40000 ALTER TABLE `room_group_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_image`
--

DROP TABLE IF EXISTS `room_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_image` (
  `image_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `room_group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKrwk8rm0fwvoaa5nnb5str0ffx` (`room_group_id`),
  CONSTRAINT `FKrwk8rm0fwvoaa5nnb5str0ffx` FOREIGN KEY (`room_group_id`) REFERENCES `room_group` (`room_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_image`
--

LOCK TABLES `room_image` WRITE;
/*!40000 ALTER TABLE `room_image` DISABLE KEYS */;
INSERT INTO `room_image` VALUES (222,'2024-11-24 16:34:40.055461','2024-11-24 16:34:40.055461','http://localhost:8080/room_groups/anh-phong_1.jpg',32),(223,'2024-11-24 16:34:40.074466','2024-11-24 16:34:40.074466','http://localhost:8080/room_groups/anh-phong-2.jpg',32),(224,'2024-11-24 16:34:40.088194','2024-11-24 16:34:40.088194','http://localhost:8080/room_groups/anh-phong-3.jpg',32),(225,'2024-11-24 16:34:40.102329','2024-11-24 16:34:40.102329','http://localhost:8080/room_groups/anh-phong-4.jpg',32),(226,'2024-11-24 16:34:40.116598','2024-11-24 16:34:40.116598','http://localhost:8080/room_groups/anh-phong-5.jpg',32),(227,'2024-11-24 16:37:08.723539','2024-11-24 16:37:08.723539','http://localhost:8080/room_groups/anh-phong-2.jpg',33),(228,'2024-11-24 16:37:08.741541','2024-11-24 16:37:08.741541','http://localhost:8080/room_groups/anh-phong-5.jpg',33),(229,'2024-11-24 16:37:08.757545','2024-11-24 16:37:08.757545','http://localhost:8080/room_groups/anh-phong-6.jpg',33),(230,'2024-11-24 16:37:08.769938','2024-11-24 16:37:08.769938','http://localhost:8080/room_groups/anh-phong-7.jpg',33),(231,'2024-11-24 16:40:20.471204','2024-11-24 16:40:20.471204','http://localhost:8080/room_groups/anh-phong-8.jpg',34),(232,'2024-11-24 16:40:20.491393','2024-11-24 16:40:20.491393','http://localhost:8080/room_groups/anh-phong-9.jpg',34),(233,'2024-11-24 16:40:20.507398','2024-11-24 16:40:20.507398','http://localhost:8080/room_groups/anh-phong-10.jpg',34),(234,'2024-11-24 16:40:20.524401','2024-11-24 16:40:20.524401','http://localhost:8080/room_groups/anh-phong-11.jpg',34),(235,'2024-11-24 16:40:20.538404','2024-11-24 16:40:20.538404','http://localhost:8080/room_groups/anh-phong-12.jpg',34),(236,'2024-11-24 16:44:00.819131','2024-11-24 16:44:00.820131','http://localhost:8080/room_groups/anh-phong-13.jpg',35),(237,'2024-11-24 16:44:00.837978','2024-11-24 16:44:00.837978','http://localhost:8080/room_groups/anh-phong-14.jpg',35),(238,'2024-11-24 16:44:00.853393','2024-11-24 16:44:00.853393','http://localhost:8080/room_groups/anh-phong-15.jpg',35),(239,'2024-11-24 16:44:00.874018','2024-11-24 16:44:00.874018','http://localhost:8080/room_groups/anh-phong-16.jpg',35),(240,'2024-11-24 16:44:00.890860','2024-11-24 16:44:00.890860','http://localhost:8080/room_groups/anh-phong-17.jpg',35),(241,'2024-11-24 16:44:00.904139','2024-11-24 16:44:00.904139','http://localhost:8080/room_groups/anh-phong-18.jpg',35),(242,'2024-11-24 16:44:00.924234','2024-11-24 16:44:00.924234','http://localhost:8080/room_groups/anh-phong-19.jpg',35),(243,'2024-11-24 16:44:00.937234','2024-11-24 16:44:00.937234','http://localhost:8080/room_groups/anh-phong-20.jpg',35),(244,'2024-11-24 16:44:00.951899','2024-11-24 16:44:00.951899','http://localhost:8080/room_groups/anh-phong-21.jpg',35),(246,'2024-11-24 16:49:41.628552','2024-11-24 16:49:41.628552','http://localhost:8080/room_groups/anh-phong-22.jpg',36),(247,'2024-11-24 16:49:41.649722','2024-11-24 16:49:41.649722','http://localhost:8080/room_groups/anh-phong-23.jpg',36),(248,'2024-11-24 16:49:41.663567','2024-11-24 16:49:41.663567','http://localhost:8080/room_groups/anh-phong-24.jpg',36),(249,'2024-11-24 16:49:41.675573','2024-11-24 16:49:41.675573','http://localhost:8080/room_groups/anh-phong-25.jpg',36),(250,'2024-11-24 16:49:41.688575','2024-11-24 16:49:41.688575','http://localhost:8080/room_groups/anh-phong-26.jpg',36),(251,'2024-11-24 16:49:41.700568','2024-11-24 16:49:41.700568','http://localhost:8080/room_groups/anh-phong-27.jpg',36);
/*!40000 ALTER TABLE `room_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_service`
--

DROP TABLE IF EXISTS `room_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_service` (
  `room_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  KEY `FKn5ff8ekvf2ayw3o35ctksyfyp` (`service_id`),
  KEY `FK3jb3uo6oi9pyw63s0ulnlss32` (`room_id`),
  CONSTRAINT `FK3jb3uo6oi9pyw63s0ulnlss32` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `FKn5ff8ekvf2ayw3o35ctksyfyp` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_service`
--

LOCK TABLES `room_service` WRITE;
/*!40000 ALTER TABLE `room_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `service_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (11,'2024-11-07 23:16:19.326591','2024-11-07 23:16:19.326591','Bể bơi chung','Bể bơi',0),(12,'2024-11-07 23:16:58.194619','2024-11-07 23:16:58.194619','Tivi 50 inch','Tivi',0),(13,'2024-11-07 23:17:10.640326','2024-11-07 23:17:10.640326','Tivi kết hợp netflix ','Netflix',0),(14,'2024-11-07 23:18:14.666433','2024-11-07 23:18:14.666433','Buffee ăn sáng ','Ăn sáng',50000),(15,'2024-11-07 23:18:48.171680','2024-11-07 23:18:48.171680','Mỗi booking sẽ free 1 chai rượu','Rượu',0),(16,'2024-11-07 23:19:18.833365','2024-11-07 23:19:18.833365','Khách sạn có phòng gym cho mỗi khách hàng','Gym',0),(20,'2024-11-16 21:10:50.935324','2024-11-16 21:10:50.935324','Spa','Spa',500000);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 22:01:36
