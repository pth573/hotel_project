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
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed`
--

LOCK TABLES `bed` WRITE;
/*!40000 ALTER TABLE `bed` DISABLE KEYS */;
INSERT INTO `bed` VALUES (1,2,0),(18,1,0),(19,0,1),(20,0,2),(21,0,3),(22,0,0),(23,1,1),(24,0,2),(25,0,3),(26,0,0),(27,0,1),(28,0,2),(29,1,3),(30,2,0),(31,0,1),(32,1,2),(33,1,3),(50,1,0),(51,0,1),(52,0,2),(53,0,3),(54,1,0),(55,0,1),(56,0,2),(57,0,3),(58,1,0),(59,0,1),(60,0,2),(61,0,3),(62,1,0),(63,2,1),(64,0,2),(65,0,3),(66,1,0),(67,0,1),(68,0,2),(69,0,3),(70,1,0),(71,0,1),(72,11,2),(73,0,3),(74,1,0),(75,0,1),(76,0,2),(77,0,3),(78,1,0),(79,0,1),(80,0,2),(81,0,3),(82,1,0),(83,1,1),(84,0,2),(85,0,3),(86,1,0),(87,1,1),(88,2,2),(89,0,3),(90,1,0),(91,0,1),(92,1,2),(93,0,3),(98,1,0),(99,0,1),(100,0,2),(101,0,3),(102,0,0),(103,1,1),(104,0,2),(105,0,3),(106,0,0),(107,0,1),(108,1,2),(109,0,3),(110,2,0),(111,0,1),(112,1,2),(113,1,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (21,'2024-11-18 01:04:56.766245','2024-11-18 10:43:29.297513','2024-11-17 14:00:00','2024-11-18 11:00:00','PENDING',1500000,3,1800000,NULL,19),(22,'2024-11-18 01:07:39.157745','2024-11-18 01:07:39.157745','2024-11-18 14:00:00','2024-11-19 11:00:00','PENDING',500000,1,500000,NULL,5),(23,'2024-11-18 01:14:32.012509','2024-11-18 01:14:32.012509','2024-11-17 14:00:00','2024-11-18 11:00:00','ACCEPTED',1500000,4,1800000,NULL,19),(24,'2024-11-18 01:31:22.948881','2024-11-18 01:31:22.948881','2024-11-18 14:00:00','2024-11-19 11:00:00','PENDING',800000,2,800000,NULL,3),(25,'2024-11-18 11:38:17.680611','2024-11-18 21:55:19.405248','2024-11-18 14:00:00','2024-11-19 11:00:00','ACCEPTED',1500000,3,1500000,NULL,3),(26,'2024-11-18 13:58:44.648116','2024-11-18 13:58:44.648116','2024-11-19 14:00:00','2024-11-20 11:00:00','PENDING',500000,1,500000,NULL,3),(27,'2024-11-18 13:59:46.888699','2024-11-18 13:59:46.888699','2024-11-22 14:00:00','2024-11-23 11:00:00','PENDING',500000,1,500000,NULL,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (3,'2024-11-14 01:00:20.413348',NULL,'2024-11-14 01:00:20.413348','	Thành phố Thái Bình','01/01/2003','user@gmail.com','Phạm Thị Hương Giang','$2a$10$ZFj.R4uM7p1VSLEl/Z4UJOi7iOfYwA8azl0eoJDvA7.MNRoa5kisy','0123456789'),(5,'2024-11-15 17:38:47.828376',NULL,'2024-11-15 17:38:47.828376','Vũ Thư. Thái Bình','02/02/2003','admin@gmail.com','ADMIN','$2a$10$OsE6tWHcoAuBXHUEp8yl..yWrTUvbJMU6h.dMQUseCJYyL95Sv3jS','0123456789'),(6,'2024-11-18 00:04:04.410996',NULL,'2024-11-18 00:04:04.410996','Đông Hưng, Thái Bình','03/02/2003','136662c5@example.com','Lê Hoàng C',NULL,'0950622522'),(7,'2024-11-18 00:17:21.730077',NULL,'2024-11-18 00:17:21.730077','Hưng Hà, Thái Bình','02/04/2003','ae783eb4@example.com','Lê Hoàng C',NULL,'0989086658'),(8,'2024-11-18 00:25:23.698779',NULL,'2024-11-18 00:25:23.698779','Thái Thụy, Thái Bình','03/02/2003','c94c29da@example.com','Trần Thị B',NULL,'0193565750'),(9,'2024-11-18 00:27:24.432723',NULL,'2024-11-18 00:27:24.432723','Tiền Hải, Thái Bình','02/02/2002','1ea0aa13@example.com','Trần Thị B',NULL,'0600568804'),(10,'2024-11-18 00:32:59.231319',NULL,'2024-11-18 00:32:59.231319','Vũ Thư, Thái Bình','22/12/2003','f8cfd269@example.com','Nguyễn Văn A',NULL,'0522146798'),(11,'2024-11-18 00:36:50.802496',NULL,'2024-11-18 00:36:50.802496','Quỳnh Phụ,Thái Bình','13/12/2003','9691e814@example.com','Trần Thị B',NULL,'0709027608'),(12,'2024-11-18 00:40:56.030481',NULL,'2024-11-18 00:40:56.030481','Hưng Hà, Thái Bình','23/12/2004','3202adb4@example.com','Lê Hoàng C',NULL,'0978697672'),(13,'2024-11-18 00:43:03.947927',NULL,'2024-11-18 00:43:03.947927','Thái Thụy, Thái Bình','23/12/2002','d6d42d1e@example.com','Lê Hoàng C',NULL,'0866405564'),(14,'2024-11-18 00:46:27.255603',NULL,'2024-11-18 00:46:27.255603','Tiền Hải, Thái Bình','23/12/2002','ceca87db@example.com','Nguyễn Văn A',NULL,'0395934832'),(15,'2024-11-18 00:52:07.263220',NULL,'2024-11-18 00:52:07.263220','Vũ Thư, Thái Bình','23/12/2001','46ea2f00@example.com','Lê Hoàng C',NULL,'0180457496'),(16,'2024-11-18 00:53:33.380950',NULL,'2024-11-18 00:53:33.380950','Quỳnh Phụ,Thái Bình','23/12/2012','0f54e08a@example.com','Trần Thị B',NULL,'0514483012'),(17,'2024-11-18 00:56:19.852922',NULL,'2024-11-18 00:56:19.852922','Hưng Hà, Thái Bình','23/12/2002','729114dd@example.com','Trần Thị B',NULL,'0131042601'),(18,'2024-11-18 01:00:00.957016',NULL,'2024-11-18 01:00:00.957016','Thái Thụy, Thái Bình','23/12/2002','3fds3423@example.com','Nguyễn Hương Giang',NULL,'0123912992'),(19,'2024-11-18 01:04:43.582128',NULL,'2024-11-18 01:04:43.582128','	Thành phố Thái Bình','23/12/2004','fdg435ys@example.com','Trần Hương Giang',NULL,'0239919321');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Phòng xịn, rộng đẹp xinh',5,1,'2024-11-15 13:21:10.746931',3),(2,'Tạm được',2,1,'2024-11-15 13:21:31.937383',3),(3,'Đồ ăn sáng không đủ',1,1,'2024-11-15 13:23:44.268981',3),(4,'Phòng xịn, rộng',4,1,'2024-11-15 13:26:31.441068',3),(5,'Xinh, nhân vên nhiệt tình',3,2,'2024-11-15 13:36:31.946836',3),(6,'Phòng đẹp',3,24,'2024-11-18 01:31:50.908841',3),(7,'Phòng đẹp, gọn gàng',3,27,'2024-11-18 14:00:47.205143',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,NULL,NULL,'1','R1',22),(2,NULL,NULL,'2','R2',23),(3,NULL,NULL,'3','R3',24),(4,NULL,'2024-11-16 20:41:59.594907','6','R6',24);
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
                              `description` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_group`
--

LOCK TABLES `room_group` WRITE;
/*!40000 ALTER TABLE `room_group` DISABLE KEYS */;
INSERT INTO `room_group` VALUES (22,'2024-11-08 00:37:11.316355','2024-11-18 13:53:22.502198',25,200000,'Phòng đơn tiện nghi, thích hợp cho 1 người ở',100000,'Phòng Đơn','http://localhost:8080/room_groups/18.png',500000,1,100000,2,1,NULL,NULL),(23,'2024-11-08 00:38:05.177320','2024-11-18 13:54:12.878488',30,300000,'Phòng đôi rộng rãi, phù hợp cho cặp đôi hoặc gia đình nhỏ',150000,'Phòng Đôi ','http://localhost:8080/room_groups/52.png',800000,2,150000,3,1,NULL,NULL),(24,'2024-11-08 00:38:59.700005','2024-11-18 13:54:59.473055',45,500000,'Phòng King rộng rãi với giường cỡ lớn, thích hợp cho gia đình hoặc nhóm bạn',250000,'Phòng King','http://localhost:8080/room_groups/13.png',1500000,2,250000,5,2,NULL,NULL),(25,'2024-11-08 00:40:04.829314','2024-11-18 13:55:52.917898',60,700000,'Phòng gia đình lớn, có nhiều giường và không gian thoải mái cho các gia đình.',300000,'Phòng Gia Đình','http://localhost:8080/room_groups/5.png',2000000,4,300000,6,2,NULL,NULL);
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
INSERT INTO `room_group_bed` VALUES (22,98),(22,99),(22,100),(22,101),(23,102),(23,103),(23,104),(23,105),(24,106),(24,107),(24,108),(24,109),(25,110),(25,111),(25,112),(25,113);
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
INSERT INTO `room_group_service` VALUES (22,11),(22,12),(22,13),(23,13),(23,14),(23,16),(24,11),(24,12),(24,13),(24,14),(24,15),(24,16),(24,20),(25,11),(25,12),(25,13),(25,14),(25,15),(25,16),(25,20);
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
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_image`
--

LOCK TABLES `room_image` WRITE;
/*!40000 ALTER TABLE `room_image` DISABLE KEYS */;
INSERT INTO `room_image` VALUES (201,'2024-11-18 13:53:22.466190','2024-11-18 13:53:22.466190','http://localhost:8080/room_groups/18.png',22),(202,'2024-11-18 13:53:22.482194','2024-11-18 13:53:22.482194','http://localhost:8080/room_groups/19.png',22),(203,'2024-11-18 13:53:22.496199','2024-11-18 13:53:22.496199','http://localhost:8080/room_groups/50.png',22),(204,'2024-11-18 13:54:12.838468','2024-11-18 13:54:12.838468','http://localhost:8080/room_groups/9.png',23),(205,'2024-11-18 13:54:12.850471','2024-11-18 13:54:12.850471','http://localhost:8080/room_groups/52.png',23),(206,'2024-11-18 13:54:12.873487','2024-11-18 13:54:12.873487','http://localhost:8080/room_groups/54.png',23),(207,'2024-11-18 13:54:59.424041','2024-11-18 13:54:59.424041','http://localhost:8080/room_groups/8.png',24),(208,'2024-11-18 13:54:59.437041','2024-11-18 13:54:59.437041','http://localhost:8080/room_groups/13.png',24),(209,'2024-11-18 13:54:59.450046','2024-11-18 13:54:59.450046','http://localhost:8080/room_groups/18.png',24),(210,'2024-11-18 13:54:59.466051','2024-11-18 13:54:59.466051','http://localhost:8080/room_groups/19.png',24),(211,'2024-11-18 13:55:52.850882','2024-11-18 13:55:52.850882','http://localhost:8080/room_groups/5.png',25),(212,'2024-11-18 13:55:52.864885','2024-11-18 13:55:52.864885','http://localhost:8080/room_groups/11.png',25),(213,'2024-11-18 13:55:52.875887','2024-11-18 13:55:52.875887','http://localhost:8080/room_groups/17.png',25),(214,'2024-11-18 13:55:52.886892','2024-11-18 13:55:52.886892','http://localhost:8080/room_groups/18.png',25),(215,'2024-11-18 13:55:52.899893','2024-11-18 13:55:52.899893','http://localhost:8080/room_groups/28.png',25),(216,'2024-11-18 13:55:52.911898','2024-11-18 13:55:52.911898','http://localhost:8080/room_groups/52.png',25);
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

-- Dump completed on 2024-11-18 22:09:20
