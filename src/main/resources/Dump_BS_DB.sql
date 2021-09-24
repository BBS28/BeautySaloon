-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: beauty_saloon_db
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(400) NOT NULL,
  `role` enum('CLIENT','ADMIN','MASTER') NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'vas','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Василий','Иванов','vasya@mail.com'),(2,'liz','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','MASTER','Лиза','Синичкина','liz@mail.com'),(3,'mar','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','MASTER','Марина','Орлова','mar@mail.com'),(4,'mas','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','MASTER','Маша','Кашина','mas@mail.com'),(5,'das','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','MASTER','Даша','Котикова','kva@mail.com'),(6,'sah','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Саша','Бабочкина','pas@mail.com'),(7,'kar','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Мила','Цветочкина','ldvd@mdfhh.tf'),(8,'admin1','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','ADMIN','nike','adidasov','nike@mail.com'),(10,'klmn','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Гриша','Петров','asdfg@mail.com'),(12,'goga','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Гога','Гошин','goga@mail.com'),(13,'valya','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Валя','Веревочкина','valusha@poople.com'),(14,'lily','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Лиля','Миля','lily@mail.com'),(15,'klmn123','5dcccd71a45496969d59af116eadce206f0d138313cc558c6227202ede9bd5b2','CLIENT','Петя','Васин','asdfg@vfr.mnb'),(16,'mnbv987','d078d780204fc3a9bae0fb2e41d93b12d8cda7ae57b7747bf31a9892dc91171e','CLIENT','Юля','Жужина','sdf@sdf.sdf'),(17,'poiu0987','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','CLIENT','Аля','Сиропова','sdcf@xc.sd'),(18,'kari','1c3868af48a0d84a394ff600ac072494d33ecd4087041ece7589bf8d0e8c6af5','MASTER','Карина','Пирожкова','kar@mvrf.of');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master_service`
--

DROP TABLE IF EXISTS `master_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `master_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_master_account_idx` (`account_id`),
  KEY `fk_master_service1_idx` (`service_id`),
  CONSTRAINT `fk_master_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_master_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_service`
--

LOCK TABLES `master_service` WRITE;
/*!40000 ALTER TABLE `master_service` DISABLE KEYS */;
INSERT INTO `master_service` VALUES (1,2,3),(2,2,2),(3,3,1),(4,4,1),(5,4,2),(6,5,7),(7,5,8),(8,18,9),(9,18,10);
/*!40000 ALTER TABLE `master_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `condition` enum('ACTIVE','PAID','DONE') NOT NULL,
  `account_id` bigint NOT NULL,
  `review_id` bigint DEFAULT NULL,
  `master_service_id` bigint NOT NULL,
  `meet_time` timestamp(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_timeTable_account1_idx` (`account_id`),
  KEY `fk_timeTable_rewiew1_idx` (`review_id`),
  KEY `fk_master_service_idx` (`master_service_id`),
  CONSTRAINT `fk_ms` FOREIGN KEY (`master_service_id`) REFERENCES `master_service` (`id`),
  CONSTRAINT `fk_review` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`),
  CONSTRAINT `fk_timeTable_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
INSERT INTO `meeting` VALUES (4,'PAID',6,1,2,'2021-08-30 07:00:00.000000'),(5,'PAID',1,2,4,'2021-08-28 07:00:00.000000'),(6,'PAID',7,3,2,'2021-08-30 12:00:00.000000'),(7,'PAID',6,4,4,'2021-08-31 09:00:00.000000'),(8,'PAID',7,5,2,'2021-09-01 10:00:00.000000'),(9,'PAID',1,NULL,3,'2021-09-15 09:00:00.000000'),(21,'PAID',1,6,3,'2021-09-11 12:00:00.000000'),(23,'PAID',1,7,3,'2021-09-13 11:00:00.000000'),(31,'PAID',1,NULL,3,'2021-09-14 13:00:00.000000'),(36,'PAID',1,NULL,1,'2021-09-15 09:00:00.000000'),(42,'PAID',1,8,3,'2021-09-16 14:00:00.000000'),(44,'PAID',14,NULL,4,'2021-09-16 06:00:00.000000'),(46,'PAID',14,NULL,4,'2021-09-20 07:00:00.000000'),(50,'ACTIVE',13,NULL,3,'2021-09-25 12:00:00.000000'),(51,'ACTIVE',1,NULL,4,'2021-09-30 12:00:00.000000'),(52,'ACTIVE',1,NULL,4,'2021-09-27 09:00:00.000000'),(53,'ACTIVE',16,NULL,4,'2021-09-24 13:00:00.000000'),(54,'PAID',16,NULL,6,'2021-09-23 11:00:00.000000'),(55,'ACTIVE',16,NULL,8,'2021-09-24 06:00:00.000000'),(56,'ACTIVE',16,NULL,9,'2021-09-24 07:00:00.000000'),(57,'ACTIVE',16,NULL,2,'2021-09-27 14:00:00.000000'),(58,'ACTIVE',16,NULL,6,'2021-09-24 10:00:00.000000'),(59,'ACTIVE',15,NULL,3,'2021-09-24 11:00:00.000000'),(60,'ACTIVE',15,NULL,6,'2021-09-24 06:00:00.000000'),(61,'ACTIVE',15,NULL,9,'2021-09-28 09:00:00.000000'),(62,'ACTIVE',13,NULL,5,'2021-09-24 08:00:00.000000'),(63,'ACTIVE',13,NULL,8,'2021-09-27 06:00:00.000000'),(64,'ACTIVE',13,NULL,1,'2021-09-28 12:00:00.000000'),(65,'ACTIVE',17,NULL,7,'2021-09-25 10:00:00.000000'),(66,'ACTIVE',17,NULL,5,'2021-09-27 12:00:00.000000'),(67,'ACTIVE',17,NULL,9,'2021-09-29 08:00:00.000000');
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `text` varchar(1024) DEFAULT NULL,
  `rate` int NOT NULL DEFAULT '5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'wow',5),(2,'so-so',3),(3,'nice',4),(4,'good',4),(5,'cool',5),(6,'здорово\r\nвеликолепно',5),(7,'отрезали ухо, но красиво пришили обратно',4),(8,'sdksdfsdfsadf sdfsvsdfv,ml;vm ;fmv\'ldkmfvb\'dkmfb\'ldk mb\'l;dmf\';ldmfv\';mfv\';mdf\'v;lm d;fmv\';dfm\'dmf\'mdf\'lbm\'dlf;mb\';dmfb;mdfvb;ldmf;vl,mdf;lvb;ldfkvb;ldfb;lmdf\'lb;m\'d;fmb\';dlfmb\';dmfb\';lmdf\'b;lmdf;bmdf ;mb\';dfmb \';dmfb\';mdf\';bmdfb;m d;fmb\';df mb\';dmfb;d mf\'b;mdf ;b\'mdf;lbm df;lm b df',4);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `duration` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'menHaircut',1,200),(2,'womenHaircut',1,400),(3,'hairDyeing',1,500),(7,'manicure',1,200),(8,'pedicure',1,300),(9,'faceMassage',1,450),(10,'massage',1,500);
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

-- Dump completed on 2021-09-24 13:43:29
