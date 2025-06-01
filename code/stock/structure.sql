-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `capital_account`
--

DROP TABLE IF EXISTS `capital_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capital_account` (
  `available_balance` decimal(18,2) NOT NULL,
  `balance` decimal(18,2) NOT NULL,
  `frozen_balance` decimal(18,2) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `securities_account_id` bigint NOT NULL,
  `bank_account_number` varchar(255) NOT NULL,
  `bank_name` varchar(255) NOT NULL,
  `capital_account_number` varchar(255) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('ACTIVE','CLOSED','SUSPENDED') NOT NULL,
  `type` enum('FUND','TRADE') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK35pal3lwt4gd2k89kb55riquo` (`bank_account_number`),
  UNIQUE KEY `UK6ryfwfn9ivtmxidbo4ys2bemo` (`capital_account_number`),
  KEY `idx_capital_account_number` (`capital_account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `capital_account_transaction`
--

DROP TABLE IF EXISTS `capital_account_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capital_account_transaction` (
  `amount` decimal(18,2) NOT NULL,
  `capital_account_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_time` datetime(6) NOT NULL,
  `type` enum('DEPOSIT','FREEZE','UNFREEZE','WITHDRAW') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_capital_account_transaction_capital_account_id` (`capital_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `corporate_securities_account`
--

DROP TABLE IF EXISTS `corporate_securities_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corporate_securities_account` (
  `id` bigint NOT NULL,
  `authorized_trader_address` varchar(255) NOT NULL,
  `authorized_trader_id_card_no` varchar(255) NOT NULL,
  `authorized_trader_name` varchar(255) NOT NULL,
  `authorized_trader_phone` varchar(255) NOT NULL,
  `business_license_number` varchar(255) NOT NULL,
  `corporation_name` varchar(255) NOT NULL,
  `registration_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9a6ilrxi0h5q9xx6lgwn6yxs3` (`authorized_trader_id_card_no`),
  UNIQUE KEY `UKcmn0hbqj49ydcf7k3iije6qju` (`business_license_number`),
  UNIQUE KEY `UK4s5cpvl43tw9e3a072amridl8` (`registration_number`),
  CONSTRAINT `FKof3vo7t6fsrut7ur406961w7p` FOREIGN KEY (`id`) REFERENCES `securities_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `individual_securities_account`
--

DROP TABLE IF EXISTS `individual_securities_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `individual_securities_account` (
  `id` bigint NOT NULL,
  `individual_education` varchar(255) DEFAULT NULL,
  `individual_employer` varchar(255) DEFAULT NULL,
  `individual_gender` varchar(255) NOT NULL,
  `individual_occupation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKpcoc28o9ougkvlh0pkih37mfc` FOREIGN KEY (`id`) REFERENCES `securities_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `average_price` decimal(18,2) NOT NULL,
  `frozen_quantity` int NOT NULL,
  `is_frozen` tinyint(1) NOT NULL DEFAULT '0',
  `quantity` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `securities_account_id` bigint NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `stock_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_position_account_stock` (`securities_account_id`,`stock_code`),
  KEY `idx_position_securities_account_id` (`securities_account_id`),
  KEY `idx_position_stock_code` (`stock_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `position_change_log`
--

DROP TABLE IF EXISTS `position_change_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_change_log` (
  `change_quantity` int NOT NULL,
  `price` decimal(18,2) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `position_id` bigint NOT NULL,
  `securities_account_id` bigint NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `stock_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `securities_account`
--

DROP TABLE IF EXISTS `securities_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securities_account` (
  `registration_date` date NOT NULL,
  `status` tinyint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `id_card_no` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcliqs8ethm07rpr9rhdqw49ct` (`account_number`),
  UNIQUE KEY `UK1s5ux3bcnaybmxtf4narot7bi` (`id_card_no`),
  CONSTRAINT `securities_account_chk_1` CHECK ((`status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `securities_account_capital_accounts`
--

DROP TABLE IF EXISTS `securities_account_capital_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securities_account_capital_accounts` (
  `capital_account_id` bigint DEFAULT NULL,
  `securities_account_id` bigint NOT NULL,
  KEY `FK71dh85qduw7ktvsl8pgxqko2j` (`securities_account_id`),
  CONSTRAINT `FK71dh85qduw7ktvsl8pgxqko2j` FOREIGN KEY (`securities_account_id`) REFERENCES `securities_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `listing_date` date DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exchange` varchar(10) NOT NULL,
  `isin` varchar(20) DEFAULT NULL,
  `company_name` varchar(255) NOT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  `stock_code` varchar(255) NOT NULL,
  `stock_name` varchar(255) NOT NULL,
  `status` enum('ACTIVE','DELISTED','SUSPENDED') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKeymtnhbc6bg0o6q9i4k5a7phr` (`stock_code`),
  UNIQUE KEY `UKcgctum5hons6kk06tliuf5cky` (`isin`),
  KEY `idx_stock_code` (`stock_code`),
  KEY `idx_exchange` (`exchange`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_execution`
--

DROP TABLE IF EXISTS `trade_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_execution` (
  `executed_price` decimal(18,2) NOT NULL,
  `executed_quantity` int NOT NULL,
  `capital_account_id` bigint NOT NULL,
  `execution_time` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `securities_account_id` bigint NOT NULL,
  `trade_order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_trade_execution_trade_order_id` (`trade_order_id`),
  KEY `idx_trade_execution_securities_account_id` (`securities_account_id`),
  KEY `idx_trade_execution_capital_account_id` (`capital_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_order`
--

DROP TABLE IF EXISTS `trade_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_order` (
  `order_price` decimal(18,2) NOT NULL,
  `order_quantity` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_time` datetime(6) NOT NULL,
  `securities_account_id` bigint NOT NULL,
  `stock_code` varchar(10) NOT NULL,
  `order_kind` enum('LIMIT','MARKET','STOP') NOT NULL,
  `order_status` enum('CANCELED','EXECUTED','FAILED','PARTIALLY_EXECUTED','PARTIALLY_TRADING','PENDING') NOT NULL,
  `order_type` enum('BUY','SELL') NOT NULL,
  `order_validity` enum('DAY_ORDER') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_securities_account_id` (`securities_account_id`),
  KEY `idx_stock_code` (`stock_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-21  6:08:58
