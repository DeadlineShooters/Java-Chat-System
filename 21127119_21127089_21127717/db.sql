-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: javachatsystem
-- ------------------------------------------------------
-- Server version	8.0.35

CREATE DATABASE IF NOT EXISTS javachatsystem;
USE javachatsystem;


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
-- Table structure for table `blockeduser`
--

DROP TABLE IF EXISTS `blockeduser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blockeduser` (
  `username` varchar(255) NOT NULL,
  `blockedUser` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`blockedUser`),
  KEY `FK_BLOCKEDUSER_USER_2_idx` (`blockedUser`),
  CONSTRAINT `FK_BLOCKEDUSER_USER_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `FK_BLOCKEDUSER_USER_2` FOREIGN KEY (`blockedUser`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blockeduser`
--

LOCK TABLES `blockeduser` WRITE;
/*!40000 ALTER TABLE `blockeduser` DISABLE KEYS */;
INSERT INTO `blockeduser` VALUES ('a','user1'),('a','user2');
/*!40000 ALTER TABLE `blockeduser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatmember`
--

DROP TABLE IF EXISTS `chatmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatmember` (
  `chatRoomId` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `isAdmin` tinyint DEFAULT '0',
  PRIMARY KEY (`chatRoomId`,`username`),
  KEY `FK_CHATMEMBER_USER_idx` (`username`),
  CONSTRAINT `FK_CHATMEMBER_CHATROOM` FOREIGN KEY (`chatRoomId`) REFERENCES `chatroom` (`chatRoomId`),
  CONSTRAINT `FK_CHATMEMBER_USER` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatmember`
--

LOCK TABLES `chatmember` WRITE;
/*!40000 ALTER TABLE `chatmember` DISABLE KEYS */;
INSERT INTO `chatmember` VALUES ('1a022941-0d2d-43aa-badb-55eec1ba4adc','a',0),('1a022941-0d2d-43aa-badb-55eec1ba4adc','user1',0),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','a',1),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','kiet',0),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','user1',0),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','user2',0),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','user3',0),('47ea2359-75b2-438a-a152-3834cfc67632','a',0),('47ea2359-75b2-438a-a152-3834cfc67632','kiet',1),('47ea2359-75b2-438a-a152-3834cfc67632','user2',1),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','a',1),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','kiet',0),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a',0),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet',0),('74946fbb-d5a3-47ce-8fff-9c2b1187c23b','ngoc',0),('74946fbb-d5a3-47ce-8fff-9c2b1187c23b','vit',0),('7c4fd6f1-6700-42b9-b353-f52f9f3cefe9','kiet',0),('7c4fd6f1-6700-42b9-b353-f52f9f3cefe9','vit',0),('8b2346da-6bae-4d06-a920-b3e5237b3b2d','a',0),('8b2346da-6bae-4d06-a920-b3e5237b3b2d','user1',0),('8b2346da-6bae-4d06-a920-b3e5237b3b2d','user2',0),('a72663ea-8c22-4131-8381-280b1215e520','a',0),('a72663ea-8c22-4131-8381-280b1215e520','vit',0),('b4a58a68-7719-46e6-82d4-59ac8018087c','ngoc',0),('b4a58a68-7719-46e6-82d4-59ac8018087c','user1',0),('c352b89b-8d06-40c2-84d0-14f89b746837','a',0),('c352b89b-8d06-40c2-84d0-14f89b746837','user3',0),('def45028-fb7c-49ac-90aa-6854aa69a110','a',0),('def45028-fb7c-49ac-90aa-6854aa69a110','user2',0);
/*!40000 ALTER TABLE `chatmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatroom`
--

DROP TABLE IF EXISTS `chatroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatroom` (
  `chatRoomId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`chatRoomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatroom`
--

LOCK TABLES `chatroom` WRITE;
/*!40000 ALTER TABLE `chatroom` DISABLE KEYS */;
INSERT INTO `chatroom` VALUES ('19d78c50-6f2f-44a2-a858-ecb6f72b9849','','2023-12-24 11:50:12'),('1a022941-0d2d-43aa-badb-55eec1ba4adc','','2023-12-24 08:27:24'),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','group hmm','2023-12-28 03:36:16'),('47ea2359-75b2-438a-a152-3834cfc67632','Group chat FANEN','2023-12-28 01:45:03'),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','group anhem','2023-12-30 00:49:32'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','','2023-12-20 03:35:11'),('74946fbb-d5a3-47ce-8fff-9c2b1187c23b','','2024-01-06 09:04:22'),('7c4fd6f1-6700-42b9-b353-f52f9f3cefe9','','2024-01-06 09:00:22'),('8b2346da-6bae-4d06-a920-b3e5237b3b2d','Group chat group user1+2','2023-12-28 01:59:00'),('a72663ea-8c22-4131-8381-280b1215e520','','2024-01-06 09:04:27'),('b4a58a68-7719-46e6-82d4-59ac8018087c','','2024-01-01 10:36:36'),('c352b89b-8d06-40c2-84d0-14f89b746837','','2023-12-24 11:57:18'),('def45028-fb7c-49ac-90aa-6854aa69a110','','2023-12-24 11:55:44');
/*!40000 ALTER TABLE `chatroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `user1` varchar(255) NOT NULL,
  `user2` varchar(255) NOT NULL,
  PRIMARY KEY (`user1`,`user2`),
  KEY `FK_FRIEND_USER_2_idx` (`user2`),
  CONSTRAINT `FK_FRIEND_USER_1` FOREIGN KEY (`user1`) REFERENCES `user` (`username`),
  CONSTRAINT `FK_FRIEND_USER_2` FOREIGN KEY (`user2`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES ('kiet','a'),('user1','a'),('a','kiet'),('user1','ngoc'),('a','user1'),('ngoc','user1');
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `chatRoomId` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `scrollPosition` int NOT NULL,
  `sentAt` timestamp NOT NULL,
  PRIMARY KEY (`chatRoomId`,`username`,`sentAt`),
  CONSTRAINT `FK_MESSAGE_CHATMEMBER` FOREIGN KEY (`chatRoomId`, `username`) REFERENCES `chatmember` (`chatRoomId`, `username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('1a022941-0d2d-43aa-badb-55eec1ba4adc','a','hi user1','',0,'2023-12-30 02:48:31'),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','a','chao moi nguoi minh ten la a','',0,'2023-12-28 12:54:48'),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','a','chào mọi người lần nữa, xin lỗi nãy quên bật telex','',0,'2023-12-28 12:58:02'),('1a7cf1ae-d3c6-4aa4-90aa-1d8fa3933bcf','a','chào mọi người lần nữa, xin lỗi nãy quên bật telex','',0,'2023-12-28 12:58:40'),('47ea2359-75b2-438a-a152-3834cfc67632','a','ayyyyy','',0,'2023-12-30 02:15:39'),('47ea2359-75b2-438a-a152-3834cfc67632','a','hello group','',0,'2023-12-30 02:48:38'),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','a','alooo','',0,'2023-12-30 01:34:41'),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','a','nhan group cai','',0,'2023-12-30 02:15:31'),('689e198c-17d1-4c5c-b092-e3db0ad6f58b','kiet','gi day','',0,'2023-12-30 01:34:46'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','testing','',0,'2023-12-23 23:53:20'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','testingggggg','',0,'2023-12-23 23:53:25'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','ayoooo','',0,'2023-12-24 09:48:02'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','aloooo','',0,'2023-12-24 11:14:31'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','2222','',0,'2023-12-25 13:56:06'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','aloo','',0,'2023-12-26 15:24:53'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','hnmmm','',0,'2023-12-26 15:27:22'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','aaaa','',0,'2023-12-26 15:27:26'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','brrrr','',38,'2023-12-26 15:27:41'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','hello','',90,'2023-12-26 15:32:22'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','ayyy','',208,'2023-12-26 15:33:17'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','eeeee','',586,'2023-12-27 14:03:31'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','toi la a','',776,'2023-12-28 01:05:45'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','ua','',966,'2023-12-28 01:09:28'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a nhan ne','',1051,'2023-12-28 01:12:03'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a nhan ne','',1136,'2023-12-28 01:13:40'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a nhan ne','',1221,'2023-12-28 01:15:42'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a','',1411,'2023-12-28 01:20:07'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a','',1601,'2023-12-28 01:20:29'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','a ne','',1791,'2023-12-28 01:25:29'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','a','aloo','',1908,'2023-12-30 02:15:18'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','nhắn gì nhiều thế','',491,'2023-12-27 13:53:01'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','alooo','',576,'2023-12-27 13:57:54'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','hmmm','',661,'2023-12-27 14:02:49'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','ayyyy','',851,'2023-12-28 00:57:44'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','kiet nhan ne','',1041,'2023-12-28 01:09:18'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','kiet','',1421,'2023-12-28 01:19:57'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','kiet','',1736,'2023-12-28 01:20:17'),('7037b1c1-9bb6-4cf1-ad9c-a6feba813620','kiet','kiet ne','',1926,'2023-12-28 01:25:21'),('b4a58a68-7719-46e6-82d4-59ac8018087c','ngoc','hi','',0,'2024-01-01 10:37:37'),('b4a58a68-7719-46e6-82d4-59ac8018087c','ngoc','nothing','',0,'2024-01-01 10:40:47'),('b4a58a68-7719-46e6-82d4-59ac8018087c','user1','yea wahss','',0,'2024-01-01 10:40:35'),('c352b89b-8d06-40c2-84d0-14f89b746837','a','aloo','',0,'2023-12-30 02:48:27');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `loginTime` timestamp NOT NULL,
  `username` varchar(255) NOT NULL,
  `logoutTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `usersChatCount` int DEFAULT '0',
  `groupsChatCount` int DEFAULT '0',
  PRIMARY KEY (`loginTime`,`username`),
  KEY `FK_SESSION_USER_idx` (`username`),
  CONSTRAINT `FK_SESSION_USER` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES ('2023-12-30 02:15:00','a','2023-12-30 02:15:43',1,2),('2023-12-30 02:48:20','a','2023-12-30 02:48:42',2,1),('2023-12-31 02:48:04','a','2023-12-31 02:48:52',0,0),('2023-12-31 03:09:27','a','2023-12-31 03:09:29',0,0),('2023-12-31 03:10:29','a','2023-12-31 03:10:31',0,0),('2024-01-01 01:45:11','ngoc','2024-01-01 01:45:20',0,0),('2024-01-01 01:45:49','ngoc','2024-01-01 01:45:53',0,0),('2024-01-01 01:56:02','ngoc','2024-01-01 01:59:13',0,0),('2024-01-01 01:59:52','ngoc','2024-01-01 02:09:39',0,0),('2024-01-01 08:44:48','ngoc','2024-01-01 09:42:53',0,0),('2024-01-01 10:06:04','ngoc',NULL,0,0),('2024-01-01 10:35:18','ngoc',NULL,0,0),('2024-01-01 10:40:10','user1',NULL,0,0),('2024-01-01 10:59:29','ngoc','2024-01-01 11:47:37',0,0),('2024-01-01 10:59:43','user1',NULL,0,0),('2024-01-01 11:15:52','a','2024-01-01 11:35:45',0,0),('2024-01-01 11:34:39','ngoc',NULL,0,0),('2024-01-01 11:35:59','user1',NULL,0,0),('2024-01-01 11:38:08','user1','2024-01-01 11:47:39',0,0),('2024-01-06 08:48:05','a','2024-01-06 09:02:40',0,0),('2024-01-06 08:55:41','vit','2024-01-06 09:04:45',0,0),('2024-01-06 09:05:32','a',NULL,0,0);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spamreport`
--

DROP TABLE IF EXISTS `spamreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spamreport` (
  `sender` varchar(255) NOT NULL,
  `reportTime` timestamp NOT NULL,
  `reportedUser` varchar(255) NOT NULL,
  PRIMARY KEY (`sender`,`reportTime`),
  KEY `FK_SPAMREPORT_USER_2_idx` (`reportedUser`),
  CONSTRAINT `FK_SPAMREPORT_USER_1` FOREIGN KEY (`sender`) REFERENCES `user` (`username`),
  CONSTRAINT `FK_SPAMREPORT_USER_2` FOREIGN KEY (`reportedUser`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spamreport`
--

LOCK TABLES `spamreport` WRITE;
/*!40000 ALTER TABLE `spamreport` DISABLE KEYS */;
INSERT INTO `spamreport` VALUES ('a','2024-01-06 09:07:18','kiet'),('vit','2024-01-06 09:00:26','kiet'),('user1','2024-01-01 11:05:09','ngoc'),('a','2024-01-06 09:00:09','user1');
/*!40000 ALTER TABLE `spamreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `createdat` timestamp NOT NULL,
  `islocked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a',NULL,NULL,NULL,NULL,'a',1,'0cc175b9c0f1b6a831c399e269772661','2023-12-09 10:01:53',0),('kiet',NULL,NULL,NULL,NULL,'a@a',0,'202cb962ac59075b964b07152d234b70','2023-12-09 05:47:36',0),('ngoc',NULL,NULL,NULL,NULL,'dungtuyetngoc@gmail.com',0,'f08cb005517e3c4484732023460f243a','2023-12-31 15:51:51',0),('user1','User One','123 Main St','1990-01-01','M','panngoc21@clc.fitus.edu.vn',0,'575b7b66ef3b6f8eeeaf1241d46288c1','2023-12-03 08:25:43',0),('user2','User Two','456 Maple Ave','1992-02-02','F','user2@example.com',0,'password2','2023-12-03 09:25:43',1),('user3','User Three','789 Oak Blvd','1994-03-03','M','user3@example.com',0,'password3','2023-12-03 06:25:43',0),('vit',NULL,NULL,NULL,NULL,'ngocphamanh2003@gmail.com',0,'0cc175b9c0f1b6a831c399e269772661','2024-01-06 08:55:35',0);
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

-- Dump completed on 2024-01-06 16:09:10
