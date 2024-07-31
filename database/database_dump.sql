-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: bi_db
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `user_id` bigint NOT NULL,
  `address_type` varchar(20) NOT NULL,
  `address_line_1` varchar(50) NOT NULL,
  `address_line_2` varchar(50) DEFAULT NULL,
  `city` varchar(40) NOT NULL,
  `state` varchar(40) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  PRIMARY KEY (`address_type`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (2,'DELIVERY','75, 4th cross,','Sapthagiri Residency, Mutharayana Nagar','Bengaluru','Karnataka','560056','9739237458');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backup_duration`
--

DROP TABLE IF EXISTS `backup_duration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `backup_duration` (
  `backup_duration_id` int NOT NULL AUTO_INCREMENT,
  `backup_duration` decimal(5,2) NOT NULL,
  PRIMARY KEY (`backup_duration_id`),
  UNIQUE KEY `backup_duration` (`backup_duration`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_duration`
--

LOCK TABLES `backup_duration` WRITE;
/*!40000 ALTER TABLE `backup_duration` DISABLE KEYS */;
INSERT INTO `backup_duration` VALUES (1,4.00),(2,4.50),(3,5.00),(4,5.50),(5,6.00);
/*!40000 ALTER TABLE `backup_duration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT,
  `total` decimal(10,4) NOT NULL,
  `delivery_cost` decimal(10,4) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `pincode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,0.0000,0.0000,1,NULL),(2,0.0000,0.0000,2,'560059');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_id` bigint NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `is_rebate` tinyint NOT NULL,
  PRIMARY KEY (`cart_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE,
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`) ON DELETE CASCADE,
  CONSTRAINT `chk_quantity` CHECK ((`quantity` >= 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_battery`
--

DROP TABLE IF EXISTS `commercial_v_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_battery` (
  `product_id` int NOT NULL,
  `guarantee` int NOT NULL,
  `warranty` int DEFAULT NULL,
  `series` varchar(40) DEFAULT NULL,
  `ah` int DEFAULT NULL,
  `terminal_layout` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `commercial_v_battery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_battery`
--

LOCK TABLES `commercial_v_battery` WRITE;
/*!40000 ALTER TABLE `commercial_v_battery` DISABLE KEYS */;
INSERT INTO `commercial_v_battery` VALUES (12,24,18,'HARVEST',75,'RIGHT'),(13,24,0,'BLACK',80,'RIGHT');
/*!40000 ALTER TABLE `commercial_v_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_brand`
--

DROP TABLE IF EXISTS `commercial_v_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(40) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_brand`
--

LOCK TABLES `commercial_v_brand` WRITE;
/*!40000 ALTER TABLE `commercial_v_brand` DISABLE KEYS */;
INSERT INTO `commercial_v_brand` VALUES (1,'EICHER');
/*!40000 ALTER TABLE `commercial_v_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_brand_segment`
--

DROP TABLE IF EXISTS `commercial_v_brand_segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_brand_segment` (
  `brand_id` int NOT NULL,
  `segment_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`segment_id`),
  KEY `segment_id` (`segment_id`),
  CONSTRAINT `commercial_v_brand_segment_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `commercial_v_brand` (`brand_id`) ON DELETE CASCADE,
  CONSTRAINT `commercial_v_brand_segment_ibfk_2` FOREIGN KEY (`segment_id`) REFERENCES `commercial_v_segment` (`segment_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_brand_segment`
--

LOCK TABLES `commercial_v_brand_segment` WRITE;
/*!40000 ALTER TABLE `commercial_v_brand_segment` DISABLE KEYS */;
INSERT INTO `commercial_v_brand_segment` VALUES (1,1);
/*!40000 ALTER TABLE `commercial_v_brand_segment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_model`
--

DROP TABLE IF EXISTS `commercial_v_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) NOT NULL,
  `brand_id` int DEFAULT NULL,
  `segment_id` int DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `model_name` (`model_name`),
  KEY `brand_id` (`brand_id`),
  KEY `segment_id` (`segment_id`),
  CONSTRAINT `commercial_v_model_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `commercial_v_brand` (`brand_id`) ON DELETE CASCADE,
  CONSTRAINT `commercial_v_model_ibfk_2` FOREIGN KEY (`segment_id`) REFERENCES `commercial_v_segment` (`segment_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_model`
--

LOCK TABLES `commercial_v_model` WRITE;
/*!40000 ALTER TABLE `commercial_v_model` DISABLE KEYS */;
INSERT INTO `commercial_v_model` VALUES (1,'Eicher 242 (25 HP)',1,1);
/*!40000 ALTER TABLE `commercial_v_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_model_battery`
--

DROP TABLE IF EXISTS `commercial_v_model_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_model_battery` (
  `model_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `commercial_v_model_battery_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `commercial_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `commercial_v_model_battery_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `commercial_v_battery` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_model_battery`
--

LOCK TABLES `commercial_v_model_battery` WRITE;
/*!40000 ALTER TABLE `commercial_v_model_battery` DISABLE KEYS */;
INSERT INTO `commercial_v_model_battery` VALUES (1,12),(1,13);
/*!40000 ALTER TABLE `commercial_v_model_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial_v_segment`
--

DROP TABLE IF EXISTS `commercial_v_segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commercial_v_segment` (
  `segment_id` int NOT NULL AUTO_INCREMENT,
  `segment_name` varchar(100) NOT NULL,
  PRIMARY KEY (`segment_id`),
  UNIQUE KEY `segment_name` (`segment_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial_v_segment`
--

LOCK TABLES `commercial_v_segment` WRITE;
/*!40000 ALTER TABLE `commercial_v_segment` DISABLE KEYS */;
INSERT INTO `commercial_v_segment` VALUES (1,'FARM VEHICLES');
/*!40000 ALTER TABLE `commercial_v_segment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `four_v_battery`
--

DROP TABLE IF EXISTS `four_v_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `four_v_battery` (
  `product_id` int NOT NULL,
  `guarantee` int NOT NULL,
  `warranty` int DEFAULT NULL,
  `series` varchar(40) DEFAULT NULL,
  `ah` int DEFAULT NULL,
  `terminal_layout` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `four_v_battery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `four_v_battery`
--

LOCK TABLES `four_v_battery` WRITE;
/*!40000 ALTER TABLE `four_v_battery` DISABLE KEYS */;
INSERT INTO `four_v_battery` VALUES (9,36,36,'FLO',35,'RIGHT'),(10,36,30,'PRO',74,'LEFT'),(11,36,24,'MILEAGE',60,'LEFT'),(19,30,30,'FLO',55,'LEFT'),(20,30,30,'FLO',66,'LEFT'),(21,30,30,'FLO',45,'LEFT'),(22,30,30,'FLO',50,'LEFT');
/*!40000 ALTER TABLE `four_v_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `four_v_brand`
--

DROP TABLE IF EXISTS `four_v_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `four_v_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(40) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `four_v_brand`
--

LOCK TABLES `four_v_brand` WRITE;
/*!40000 ALTER TABLE `four_v_brand` DISABLE KEYS */;
INSERT INTO `four_v_brand` VALUES (3,'FORD'),(4,'KIA'),(1,'TATA'),(2,'VOLKSWAGEN');
/*!40000 ALTER TABLE `four_v_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `four_v_model`
--

DROP TABLE IF EXISTS `four_v_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `four_v_model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) NOT NULL,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `model_name` (`model_name`),
  KEY `brand_id` (`brand_id`),
  CONSTRAINT `four_v_model_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `four_v_brand` (`brand_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `four_v_model`
--

LOCK TABLES `four_v_model` WRITE;
/*!40000 ALTER TABLE `four_v_model` DISABLE KEYS */;
INSERT INTO `four_v_model` VALUES (1,'Altroz - XE, XM BS6',1),(2,'Passat 2.0L',2),(3,'Harrier - BSIV (2019)',1),(4,'Nexon (2018)',1),(5,'Fiesta',3),(6,'Eco Sport (2020 onwards)',3),(7,'Sonet Xline 1.0L',4),(8,'Seltos (2019)',4);
/*!40000 ALTER TABLE `four_v_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `four_v_model_fuel`
--

DROP TABLE IF EXISTS `four_v_model_fuel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `four_v_model_fuel` (
  `model_id` int NOT NULL,
  `fuel_type_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`fuel_type_id`),
  KEY `fuel_type_id` (`fuel_type_id`),
  CONSTRAINT `four_v_model_fuel_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `four_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `four_v_model_fuel_ibfk_2` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`fuel_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `four_v_model_fuel`
--

LOCK TABLES `four_v_model_fuel` WRITE;
/*!40000 ALTER TABLE `four_v_model_fuel` DISABLE KEYS */;
INSERT INTO `four_v_model_fuel` VALUES (1,1),(5,1),(6,1),(7,1),(8,1),(2,2),(3,2),(4,2),(5,2),(8,2);
/*!40000 ALTER TABLE `four_v_model_fuel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `four_v_model_fuel_battery`
--

DROP TABLE IF EXISTS `four_v_model_fuel_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `four_v_model_fuel_battery` (
  `model_id` int NOT NULL,
  `fuel_type_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`fuel_type_id`,`product_id`),
  KEY `fuel_type_id` (`fuel_type_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `four_v_model_fuel_battery_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `four_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `four_v_model_fuel_battery_ibfk_2` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`fuel_type_id`) ON DELETE CASCADE,
  CONSTRAINT `four_v_model_fuel_battery_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `four_v_battery` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `four_v_model_fuel_battery`
--

LOCK TABLES `four_v_model_fuel_battery` WRITE;
/*!40000 ALTER TABLE `four_v_model_fuel_battery` DISABLE KEYS */;
INSERT INTO `four_v_model_fuel_battery` VALUES (1,1,9),(5,1,21),(6,1,21),(7,1,22),(8,1,22),(2,2,10),(2,2,11),(3,2,10),(4,2,19),(5,2,20),(8,2,20);
/*!40000 ALTER TABLE `four_v_model_fuel_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuel_type`
--

DROP TABLE IF EXISTS `fuel_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fuel_type` (
  `fuel_type_id` int NOT NULL,
  `fuel_type` varchar(10) NOT NULL,
  PRIMARY KEY (`fuel_type_id`),
  UNIQUE KEY `fuel_type` (`fuel_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuel_type`
--

LOCK TABLES `fuel_type` WRITE;
/*!40000 ALTER TABLE `fuel_type` DISABLE KEYS */;
INSERT INTO `fuel_type` VALUES (3,'CNG'),(2,'DIESEL'),(4,'LPG'),(1,'PETROL');
/*!40000 ALTER TABLE `fuel_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inv_backup_warranty_battery`
--

DROP TABLE IF EXISTS `inv_backup_warranty_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inv_backup_warranty_battery` (
  `backup_duration_id` int NOT NULL,
  `warranty_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`backup_duration_id`,`warranty_id`,`product_id`),
  KEY `warranty_id` (`warranty_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `inv_backup_warranty_battery_ibfk_1` FOREIGN KEY (`backup_duration_id`) REFERENCES `backup_duration` (`backup_duration_id`) ON DELETE CASCADE,
  CONSTRAINT `inv_backup_warranty_battery_ibfk_2` FOREIGN KEY (`warranty_id`) REFERENCES `inverter_battery_warranty` (`warranty_id`) ON DELETE CASCADE,
  CONSTRAINT `inv_backup_warranty_battery_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `inverter_battery` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inv_backup_warranty_battery`
--

LOCK TABLES `inv_backup_warranty_battery` WRITE;
/*!40000 ALTER TABLE `inv_backup_warranty_battery` DISABLE KEYS */;
INSERT INTO `inv_backup_warranty_battery` VALUES (1,1,23),(1,1,24),(3,1,14);
/*!40000 ALTER TABLE `inv_backup_warranty_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inverter`
--

DROP TABLE IF EXISTS `inverter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inverter` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `no_of_battery_req` int NOT NULL,
  `warranty` int NOT NULL,
  `inverter_type` varchar(40) NOT NULL,
  `rec_battery_capacity` varchar(40) NOT NULL,
  `capacity_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `capacity_id` (`capacity_id`),
  CONSTRAINT `inverter_ibfk_1` FOREIGN KEY (`capacity_id`) REFERENCES `inverter_capacity` (`capacity_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inverter`
--

LOCK TABLES `inverter` WRITE;
/*!40000 ALTER TABLE `inverter` DISABLE KEYS */;
INSERT INTO `inverter` VALUES (15,1,36,'INVERTER / HOME UPS','100 AH - 200 AH',1);
/*!40000 ALTER TABLE `inverter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inverter_battery`
--

DROP TABLE IF EXISTS `inverter_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inverter_battery` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `guarantee` int DEFAULT NULL,
  `warranty` int NOT NULL,
  `series` varchar(40) DEFAULT NULL,
  `ah` int DEFAULT NULL,
  `terminal_layout` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `inverter_battery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inverter_battery`
--

LOCK TABLES `inverter_battery` WRITE;
/*!40000 ALTER TABLE `inverter_battery` DISABLE KEYS */;
INSERT INTO `inverter_battery` VALUES (14,30,12,'CURRENT',200,'LEFT'),(23,30,12,'CURRENT',150,'LEFT'),(24,30,12,'CURRENT',150,'LEFT');
/*!40000 ALTER TABLE `inverter_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inverter_battery_warranty`
--

DROP TABLE IF EXISTS `inverter_battery_warranty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inverter_battery_warranty` (
  `warranty_id` int NOT NULL AUTO_INCREMENT,
  `warranty` int NOT NULL,
  `guarantee` int DEFAULT NULL,
  PRIMARY KEY (`warranty_id`),
  UNIQUE KEY `warranty` (`warranty`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inverter_battery_warranty`
--

LOCK TABLES `inverter_battery_warranty` WRITE;
/*!40000 ALTER TABLE `inverter_battery_warranty` DISABLE KEYS */;
INSERT INTO `inverter_battery_warranty` VALUES (1,42,30),(2,54,36),(3,66,42);
/*!40000 ALTER TABLE `inverter_battery_warranty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inverter_capacity`
--

DROP TABLE IF EXISTS `inverter_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inverter_capacity` (
  `capacity_id` int NOT NULL AUTO_INCREMENT,
  `capacity` int NOT NULL,
  PRIMARY KEY (`capacity_id`),
  UNIQUE KEY `capacity` (`capacity`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inverter_capacity`
--

LOCK TABLES `inverter_capacity` WRITE;
/*!40000 ALTER TABLE `inverter_capacity` DISABLE KEYS */;
INSERT INTO `inverter_capacity` VALUES (1,675);
/*!40000 ALTER TABLE `inverter_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_id` bigint NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `is_rebate` tinyint(1) NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,2,2,1),(2,9,1,1),(2,11,3,1);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `total` decimal(10,4) NOT NULL,
  `delivery_cost` decimal(10,4) NOT NULL,
  `grand_total` decimal(10,4) NOT NULL,
  `payment_method` varchar(20) NOT NULL,
  `order_date` datetime NOT NULL,
  `order_status` varchar(20) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,4073.1000,50.0000,4123.1000,'CASH_ON_DELIVERY','2024-06-17 17:28:34','PLACED',2,NULL),(2,27148.5500,50.0000,27198.5500,'ONLINE','2024-06-17 21:43:54','PLACED',2,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_detail`
--

DROP TABLE IF EXISTS `payment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_detail` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,4) NOT NULL,
  `payment_method` varchar(20) NOT NULL,
  `payment_status` varchar(20) NOT NULL,
  `payment_date` datetime DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `razorpay_payment_link_id` varchar(255) DEFAULT NULL,
  `razorpay_payment_link_reference_id` varchar(255) DEFAULT NULL,
  `razorpay_payment_link_status` varchar(255) DEFAULT NULL,
  `razorpay_payment_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `payment_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `payment_detail_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_detail`
--

LOCK TABLES `payment_detail` WRITE;
/*!40000 ALTER TABLE `payment_detail` DISABLE KEYS */;
INSERT INTO `payment_detail` VALUES (1,27198.5500,'ONLINE','SUCCESS','2024-06-17 21:45:40',2,2,'plink_ONtEwDVFRlCNhD','','paid','pay_ONtGHQmw7Q2nwc');
/*!40000 ALTER TABLE `payment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `model_name` varchar(40) NOT NULL,
  `brand_name` varchar(40) NOT NULL,
  `voltage` int DEFAULT NULL,
  `image_main_url` varchar(255) DEFAULT NULL,
  `image_one_url` varchar(255) DEFAULT NULL,
  `image_two_url` varchar(255) DEFAULT NULL,
  `image_three_url` varchar(255) DEFAULT NULL,
  `mrp` decimal(10,4) NOT NULL,
  `discount_percentage` decimal(5,2) NOT NULL DEFAULT '0.00',
  `stock` int NOT NULL,
  `rebate` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `product_type` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `model_name` (`model_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'AMARON PRO Bike Rider 2 Wheeler Battery - APBTZ7L (ABR-PR-APBTZ7L)','AP-BTZ7L','AMARON',12,'https://i.postimg.cc/HnYwz1Gs/amaron-two.jpg','https://i.postimg.cc/nzZRy58N/amaron-two-1.jpg','https://i.postimg.cc/NGxqj6p1/amaron-two-2.jpg','https://i.postimg.cc/BbzCn7yS/amaron-two-3.jpg',2022.0000,5.00,4,230.0000,'TWOVBATTERY'),(2,'AMARON PRO Bike Rider 2 Wheeler Battery - APBTZ9R (ABR-PR-APBTZ9R)','AP-BTZ9R','AMARON',12,'https://i.postimg.cc/HnYwz1Gs/amaron-two.jpg','https://i.postimg.cc/nzZRy58N/amaron-two-1.jpg','https://i.postimg.cc/NGxqj6p1/amaron-two-2.jpg','https://i.postimg.cc/BbzCn7yS/amaron-two-3.jpg',2449.0000,5.00,3,290.0000,'TWOVBATTERY'),(3,'EXIDE XPLORE (12XL9-B)','12XL9-B','EXIDE',12,'https://i.postimg.cc/76s3KQq3/exide-two.jpg','https://i.postimg.cc/Dwr19z2y/exide-two-1.jpg','https://i.postimg.cc/T36tLF11/exide-two-2.jpg','https://i.postimg.cc/yYqPttFj/exide-two-3.jpg',2522.0000,5.00,2,300.0000,'TWOVBATTERY'),(4,'EXIDE XPLORE (XLTZ9)','XLTZ9','EXIDE',12,'https://i.postimg.cc/76s3KQq3/exide-two.jpg','https://i.postimg.cc/Dwr19z2y/exide-two-1.jpg','https://i.postimg.cc/T36tLF11/exide-two-2.jpg','https://i.postimg.cc/yYqPttFj/exide-two-3.jpg',2452.0000,5.00,1,280.0000,'TWOVBATTERY'),(5,'AMARON BLACK Automotive Battery-BL300RMF (AAM-BL-BL0300RMF)','BL300R','AMARON',12,'https://i.postimg.cc/7YY6HYMZ/amaron-three.jpg','https://i.postimg.cc/hj1DnHXm/amaron-three-1.jpg','https://i.postimg.cc/Xv06vsSk/amaron-three-2.jpg','https://i.postimg.cc/VsHytfP6/amaron-three-3.jpg',3642.0000,2.00,3,650.0000,'THREEVBATTERY'),(6,'EXIDE EKO (EKO40L)','EKO40L','EXIDE',40,'https://i.postimg.cc/4N9ddc65/exide-three.jpg','https://i.postimg.cc/x1VdbdgS/exide-three-1.jpg','https://i.postimg.cc/13030ZYV/exide-three-2.jpg','https://i.postimg.cc/s2VDw0t6/exide-three-3.jpg',4390.0000,3.00,3,390.0000,'THREEVBATTERY'),(7,'EXIDE EKO (EKO32)','EKO32','EXIDE',12,'https://i.postimg.cc/4N9ddc65/exide-three.jpg','https://i.postimg.cc/x1VdbdgS/exide-three-1.jpg','https://i.postimg.cc/13030ZYV/exide-three-2.jpg','https://i.postimg.cc/s2VDw0t6/exide-three-3.jpg',3712.0000,3.00,3,270.0000,'THREEVBATTERY'),(8,'AMARON BLACK Automotive Battery-BL600RMF (AAM-BL-0BL600LMF)','BL600L','AMARON',12,'https://i.postimg.cc/7YY6HYMZ/amaron-three.jpg','https://i.postimg.cc/hj1DnHXm/amaron-three-1.jpg','https://i.postimg.cc/Xv06vsSk/amaron-three-2.jpg','https://i.postimg.cc/VsHytfP6/amaron-three-3.jpg',6121.0000,1.00,3,600.0000,'THREEVBATTERY'),(9,'AMARON FLO Automotive Battery - 42B20R (AAM-FL-00042B20R)','42B20R','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',5155.0000,3.00,4,800.0000,'FOURVBATTERY'),(10,'AMARON PRO Automotive Battery - 574102069 (AAM-PR-574102069)','574102069','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',12529.0000,3.00,4,1640.0000,'FOURVBATTERY'),(11,'EXIDE MILEAGE (MLDIN60)','MLDIN60','EXIDE',12,'https://i.postimg.cc/2yNZ9pPB/exide-four.jpg','https://i.postimg.cc/Nf4HDzb5/exide-four-1.jpg','https://i.postimg.cc/Kjb1rPMP/exide-four-2.jpg','https://i.postimg.cc/MKdnVjKB/exide-four-3.jpg',9030.0000,2.00,3,1200.0000,'FOURVBATTERY'),(12,'AMARON HARVEST Automotive Battery-TR500D31R (AAM-HR-TR500D31R)','TR500D31R','AMARON',12,'https://i.postimg.cc/FshN2b0d/amaron-commercial.jpg','https://i.postimg.cc/qMQ40tGm/amaron-commercial-1.jpg','https://i.postimg.cc/3xR7wKS3/amaron-commercial-2.jpg','https://i.postimg.cc/Jz28D9vZ/amaron-commercial-3.jpg',7519.0000,4.00,3,1515.0000,'COMMERCIALVBATTERY'),(13,'AMARON BLACK Automotive Battery-BL800RMF (AAM-BL-0BL800RMF)','BL800RMF','AMARON',12,'https://i.postimg.cc/FshN2b0d/amaron-commercial.jpg','https://i.postimg.cc/qMQ40tGm/amaron-commercial-1.jpg','https://i.postimg.cc/3xR7wKS3/amaron-commercial-2.jpg','https://i.postimg.cc/Jz28D9vZ/amaron-commercial-3.jpg',7565.0000,4.00,2,1380.0000,'COMMERCIALVBATTERY'),(14,'AMARON CURRENT Tall Tubular Battery-EA200TT42(AAM_CR_EA200TT42)','EA200TT42','AMARON',12,'https://i.postimg.cc/hGp4w1nS/amaron-inverter-battery.jpg','https://i.postimg.cc/j52qrnqy/amaron-inverter-battery-1.jpg','https://i.postimg.cc/j51xqqCm/amaron-inverter-battery-2.jpg','https://i.postimg.cc/KjgZ1XMz/amaron-inverter-battery-3.jpg',19016.0000,3.00,2,3460.0000,'INVERTERBATTERY'),(15,'AMARON HUPS - HB750A (AAM-HU-HB0000750)','HB750A','AMARON',12,'https://i.postimg.cc/9Q27Hb6T/amaron-inverter.jpg','https://i.postimg.cc/Jnsy52Xw/amaron-inverter-1.jpg','https://i.postimg.cc/TwBK7JjK/amaron-inverter-2.jpg','https://i.postimg.cc/Y0ghJL8b/amaron-inverter-3.jpg',7738.0000,3.00,2,0.0000,'INVERTER'),(16,'AMARON PRO Bike Rider - APBTZ4L (ABR-PR-APBTZ4L)','APBTZ4L','AMARON',12,'https://i.postimg.cc/HnYwz1Gs/amaron-two.jpg','https://i.postimg.cc/nzZRy58N/amaron-two-1.jpg','https://i.postimg.cc/NGxqj6p1/amaron-two-2.jpg','https://i.postimg.cc/BbzCn7yS/amaron-two-3.jpg',1325.0000,3.00,3,130.0000,'TWOVBATTERY'),(17,'AMARON PRO Bike Rider - APBTZ5L (ABR-PR-APBTZ5L)','APBTZ5L','AMARON',12,'https://i.postimg.cc/HnYwz1Gs/amaron-two.jpg','https://i.postimg.cc/nzZRy58N/amaron-two-1.jpg','https://i.postimg.cc/NGxqj6p1/amaron-two-2.jpg','https://i.postimg.cc/BbzCn7yS/amaron-two-3.jpg',1548.0000,3.00,2,165.0000,'TWOVBATTERY'),(18,'AMARON PRO Bike Rider - A48ATZ14R (AAM-BA-A48ATZ14R)','A48ATZ14R','AMARON',12,'https://i.postimg.cc/HnYwz1Gs/amaron-two.jpg','https://i.postimg.cc/nzZRy58N/amaron-two-1.jpg','https://i.postimg.cc/NGxqj6p1/amaron-two-2.jpg','https://i.postimg.cc/BbzCn7yS/amaron-two-3.jpg',3453.0000,2.00,5,360.0000,'TWOVBATTERY'),(19,'AMARON FLO Automotive Battery - 555112054 (AAM-FL-555112054)','555112054','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',8347.0000,2.00,5,1355.0000,'FOURVBATTERY'),(20,'AMARON FLO Automotive Battery - 566112060 (AAM-FL-566112060)','566112060','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',8531.0000,3.00,3,1355.0000,'FOURVBATTERY'),(21,'AMARON FLO Automotive Battery - 545106036 (AAM-FL-545106036)','545106036','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',6589.0000,3.00,1,965.0000,'FOURVBATTERY'),(22,'AMARON FLO Automotive Battery - 550114042 (AAM-FL-550114042)','550114042','AMARON',12,'https://i.postimg.cc/fbbjYNNz/amaron-four.jpg','https://i.postimg.cc/Y9G6yz0T/amaron-four-1.jpg','https://i.postimg.cc/4dS8Y3HR/amaron-four-2.jpg','https://i.postimg.cc/N0Lm5k6L/amaron-four-3.jpg',7291.0000,5.00,2,1120.0000,'FOURVBATTERY'),(23,'AMARON CURRENT Tall Tubular Battery - DP150TT42 (AAM-CR-DP150TT42)','DP150TT42','AMARON',12,'https://i.postimg.cc/hGp4w1nS/amaron-inverter-battery.jpg','https://i.postimg.cc/j52qrnqy/amaron-inverter-battery-1.jpg','https://i.postimg.cc/j51xqqCm/amaron-inverter-battery-2.jpg','https://i.postimg.cc/KjgZ1XMz/amaron-inverter-battery-3.jpg',15583.0000,4.00,2,3460.0000,'INVERTERBATTERY'),(24,'AMARON CURRENT Tall Tubular Battery - EA150TT42 (AAM-CR-EA150TT42)','EA150TT42','AMARON',12,'https://i.postimg.cc/hGp4w1nS/amaron-inverter-battery.jpg','https://i.postimg.cc/j52qrnqy/amaron-inverter-battery-1.jpg','https://i.postimg.cc/j51xqqCm/amaron-inverter-battery-2.jpg','https://i.postimg.cc/KjgZ1XMz/amaron-inverter-battery-3.jpg',15583.0000,4.00,3,3460.0000,'INVERTERBATTERY');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_address`
--

DROP TABLE IF EXISTS `shipping_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_address` (
  `order_id` bigint NOT NULL,
  `address_line_1` varchar(100) NOT NULL,
  `address_line_2` varchar(100) DEFAULT NULL,
  `city` varchar(40) NOT NULL,
  `state` varchar(40) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `shipping_address_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
INSERT INTO `shipping_address` VALUES (1,'75, 4th cross,','Sapthagiri Residency, Mutharayana Nagar','Bengaluru','Karnataka','560056','9739237458'),(2,'75, 4th cross,','Sapthagiri Residency, Mutharayana Nagar','Bengaluru','Karnataka','560056','9739237458');
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `three_v_battery`
--

DROP TABLE IF EXISTS `three_v_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `three_v_battery` (
  `product_id` int NOT NULL,
  `guarantee` int NOT NULL,
  `warranty` int DEFAULT NULL,
  `series` varchar(40) DEFAULT NULL,
  `ah` int DEFAULT NULL,
  `terminal_layout` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `three_v_battery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `three_v_battery`
--

LOCK TABLES `three_v_battery` WRITE;
/*!40000 ALTER TABLE `three_v_battery` DISABLE KEYS */;
INSERT INTO `three_v_battery` VALUES (5,24,24,'BLACK',30,'LEFT'),(6,12,12,'EKO',35,'LEFT'),(7,12,12,'EKO',32,'RIGHT'),(8,24,0,'BLACK',60,'LEFT');
/*!40000 ALTER TABLE `three_v_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `three_v_brand`
--

DROP TABLE IF EXISTS `three_v_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `three_v_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(40) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `three_v_brand`
--

LOCK TABLES `three_v_brand` WRITE;
/*!40000 ALTER TABLE `three_v_brand` DISABLE KEYS */;
INSERT INTO `three_v_brand` VALUES (2,'KRANTI'),(1,'TVS');
/*!40000 ALTER TABLE `three_v_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `three_v_model`
--

DROP TABLE IF EXISTS `three_v_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `three_v_model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) NOT NULL,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `model_name` (`model_name`),
  KEY `brand_id` (`brand_id`),
  CONSTRAINT `three_v_model_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `three_v_brand` (`brand_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `three_v_model`
--

LOCK TABLES `three_v_model` WRITE;
/*!40000 ALTER TABLE `three_v_model` DISABLE KEYS */;
INSERT INTO `three_v_model` VALUES (1,'KING DELUXE',1),(2,'Nandi Jaiho',2),(3,'Nandi Power',2),(4,'Nandi Super',2),(5,'Nandi Super Shakti',2),(6,'KING Kargo',1);
/*!40000 ALTER TABLE `three_v_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `three_v_model_fuel`
--

DROP TABLE IF EXISTS `three_v_model_fuel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `three_v_model_fuel` (
  `model_id` int NOT NULL,
  `fuel_type_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`fuel_type_id`),
  KEY `fuel_type_id` (`fuel_type_id`),
  CONSTRAINT `three_v_model_fuel_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `three_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `three_v_model_fuel_ibfk_2` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`fuel_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `three_v_model_fuel`
--

LOCK TABLES `three_v_model_fuel` WRITE;
/*!40000 ALTER TABLE `three_v_model_fuel` DISABLE KEYS */;
INSERT INTO `three_v_model_fuel` VALUES (1,1),(2,2),(3,2),(4,2),(5,2),(1,3),(6,3),(1,4),(6,4);
/*!40000 ALTER TABLE `three_v_model_fuel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `three_v_model_fuel_battery`
--

DROP TABLE IF EXISTS `three_v_model_fuel_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `three_v_model_fuel_battery` (
  `model_id` int NOT NULL,
  `fuel_type_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`fuel_type_id`,`product_id`),
  KEY `fuel_type_id` (`fuel_type_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `three_v_model_fuel_battery_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `three_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `three_v_model_fuel_battery_ibfk_2` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`fuel_type_id`) ON DELETE CASCADE,
  CONSTRAINT `three_v_model_fuel_battery_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `three_v_battery` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `three_v_model_fuel_battery`
--

LOCK TABLES `three_v_model_fuel_battery` WRITE;
/*!40000 ALTER TABLE `three_v_model_fuel_battery` DISABLE KEYS */;
INSERT INTO `three_v_model_fuel_battery` VALUES (1,1,5),(1,1,7),(2,2,8),(3,2,8),(4,2,8),(5,2,8),(1,3,5),(1,3,6),(1,3,7),(6,3,5),(1,4,5),(1,4,6),(1,4,7),(6,4,5);
/*!40000 ALTER TABLE `three_v_model_fuel_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `user_id` bigint NOT NULL,
  `token` varchar(512) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3OTc1ODE3OTgzIiwiaWF0IjoxNzIwNzg4MjAxLCJleHAiOjE3MjA4NzQ2MDF9.Pzycubgxpj53ZSLCIFJNYmVQRY4S-tDZLD7d8_3-gYw'),(2,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NzM5MjM3NDU4IiwiaWF0IjoxNzIwMTc4MjMzLCJleHAiOjE3MjAyNjQ2MzN9.TGS0lMpgT27qF9Puwz5GY4i6HtQAMp1LcyoHR4WBSuY');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `two_v_battery`
--

DROP TABLE IF EXISTS `two_v_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `two_v_battery` (
  `product_id` int NOT NULL,
  `guarantee` int NOT NULL,
  `warranty` int DEFAULT NULL,
  `series` varchar(40) DEFAULT NULL,
  `ah` int DEFAULT NULL,
  `terminal_layout` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `two_v_battery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `two_v_battery`
--

LOCK TABLES `two_v_battery` WRITE;
/*!40000 ALTER TABLE `two_v_battery` DISABLE KEYS */;
INSERT INTO `two_v_battery` VALUES (1,24,24,'PRO',6,'LEFT'),(2,24,24,'PRO',8,'RIGHT'),(3,24,24,'XPLORE',9,'RIGHT'),(4,24,24,'XPLORE',9,'RIGHT'),(16,24,24,'PRO',3,'LEFT'),(17,24,24,'PRO',4,'LEFT'),(18,24,24,'PRO',12,'RIGHT');
/*!40000 ALTER TABLE `two_v_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `two_v_brand`
--

DROP TABLE IF EXISTS `two_v_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `two_v_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(40) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `two_v_brand`
--

LOCK TABLES `two_v_brand` WRITE;
/*!40000 ALTER TABLE `two_v_brand` DISABLE KEYS */;
INSERT INTO `two_v_brand` VALUES (2,'BAJAJ'),(3,'HERO'),(4,'HONDA'),(5,'ROYAL ENFIELD'),(1,'TVS');
/*!40000 ALTER TABLE `two_v_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `two_v_model`
--

DROP TABLE IF EXISTS `two_v_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `two_v_model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) NOT NULL,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `model_name` (`model_name`),
  KEY `brand_id` (`brand_id`),
  CONSTRAINT `two_v_model_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `two_v_brand` (`brand_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `two_v_model`
--

LOCK TABLES `two_v_model` WRITE;
/*!40000 ALTER TABLE `two_v_model` DISABLE KEYS */;
INSERT INTO `two_v_model` VALUES (1,'Apache - RTR 200 4V (ES)',1),(2,'Dominar 400 (ES)',2),(3,'SPLENDOR (ES)',3),(4,'Maestro BS6 (ES)',3),(5,'X Pulse (ES)',3),(6,'Jupitor Classic (BS6) (ES)',1),(7,'NTORQ 125 (BS6) (ES)',1),(8,'Apache RTR 160 (ES)',1),(9,'Activa 125 (BS6) (ES)',4),(10,'CB Unicorn (BS6) (ES)',4),(11,'Dio (ES)',4),(12,'Shine (BS6) (ES)',4),(13,'Bullet 350 (BS6) (ES)',5),(14,'Classic 500 (ES)',5),(15,'Continental GT (ES)',5);
/*!40000 ALTER TABLE `two_v_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `two_v_model_battery`
--

DROP TABLE IF EXISTS `two_v_model_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `two_v_model_battery` (
  `model_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`model_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `two_v_model_battery_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `two_v_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `two_v_model_battery_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `two_v_battery` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `two_v_model_battery`
--

LOCK TABLES `two_v_model_battery` WRITE;
/*!40000 ALTER TABLE `two_v_model_battery` DISABLE KEYS */;
INSERT INTO `two_v_model_battery` VALUES (1,1),(5,1),(8,1),(2,2),(13,2),(1,3),(2,3),(2,4),(3,16),(4,16),(11,16),(6,17),(7,17),(9,17),(10,17),(12,17),(14,18),(15,18);
/*!40000 ALTER TABLE `two_v_model_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) NOT NULL,
  `password` varchar(256) NOT NULL,
  `is_active` tinyint NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `phone_number` (`phone_number`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin','','mohangowda8055k@gmail.com','7975817983','$2a$10$nMV0GDGuv5KwmwShdl.wUesXdOsJTlwMEh5nJQu/PEIfCOUF8E4PS',1,'ADMIN'),(2,'Gagan','','gagan@gmail.com','9739237458','$2a$10$evMF.BoePgzoTvZAr8P6iuSyVHFe3Nef3AeJ/QJvvKXsH1qC/y/wi',1,'USER');
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

-- Dump completed on 2024-07-12 20:44:00
