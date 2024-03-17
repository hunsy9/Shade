-- MySQL dump 10.19  Distrib 10.3.35-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: shade
-- ------------------------------------------------------
-- Server version	10.3.35-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category_master`
--


DROP TABLE IF EXISTS `category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_master` (
  `category_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(11) NOT NULL,
  `project_id` bigint(11) NOT NULL,
  `prev` varchar(50) NOT NULL DEFAULT '',
  `next` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `org_id` (`org_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `category_master_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization_master` (`org_id`),
  CONSTRAINT `category_master_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `organization_project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_master`
--

LOCK TABLES `category_master` WRITE;
/*!40000 ALTER TABLE `category_master` DISABLE KEYS */;
-- INSERT INTO `category_master` VALUES (93,25,30,'kimkitae','server1'),(123,27,33,'testCate1','testCate2'),(124,28,34,'level1','level2'),(127,25,30,'haict','ezon'),(128,25,30,'kimkitae',NULL),(131,25,30,'kimkitae',NULL),(134,25,30,'kimkitae',NULL),(135,29,35,'Back','Node-AWS'),(136,29,35,'DB',NULL),(137,29,35,'Back','Redis-Azure'),(139,25,36,'aadf','sadf'),(141,25,31,'as','ass2'),(142,33,42,'Naver','Naver-1');
/*!40000 ALTER TABLE `category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_contributor`
--

DROP TABLE IF EXISTS `organization_contributor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_contributor` (
  `org_id` bigint(11) NOT NULL,
  `contributor_id` bigint(11) unsigned NOT NULL,
  `contributor_email` varchar(50) NOT NULL DEFAULT '',
  `state` varchar(30) NOT NULL,
  `enroll_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  KEY `org_id` (`org_id`),
  KEY `contributor_id` (`contributor_id`),
  KEY `contributor_email` (`contributor_email`),
  CONSTRAINT `organization_contributor_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization_master` (`org_id`),
  CONSTRAINT `organization_contributor_ibfk_2` FOREIGN KEY (`contributor_id`) REFERENCES `user_master` (`user_id`),
  CONSTRAINT `organization_contributor_ibfk_3` FOREIGN KEY (`contributor_email`) REFERENCES `user_master` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_contributor`
--

LOCK TABLES `organization_contributor` WRITE;
/*!40000 ALTER TABLE `organization_contributor` DISABLE KEYS */;
-- INSERT INTO `organization_contributor` VALUES (25,23,'yungs0917@naver.com','admin','2023-08-09 23:12:29'),(25,24,'kt8619@naver.com','contributor','2023-08-24 04:34:21'),(27,23,'yungs0917@naver.com','admin','2023-08-24 04:35:23'),(27,24,'kt8619@naver.com','contributor','2023-08-24 04:36:21'),(28,23,'yungs0917@naver.com','admin','2023-08-24 04:55:15'),(28,24,'kt8619@naver.com','contributor','2023-08-24 04:59:13'),(29,24,'kt8619@naver.com','admin','2023-09-12 06:26:06'),(29,23,'yungs0917@naver.com','contributor','2023-09-12 06:26:52'),(31,23,'yungs0917@naver.com','admin','2023-09-26 04:29:46'),(32,23,'yungs0917@naver.com','admin','2023-09-26 04:30:42'),(33,23,'yungs0917@naver.com','admin','2023-10-29 15:02:10');
/*!40000 ALTER TABLE `organization_contributor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_master`
--

DROP TABLE IF EXISTS `organization_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_master` (
  `org_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `org_name` varchar(50) NOT NULL DEFAULT '',
  `org_admin_id` bigint(11) unsigned NOT NULL,
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `org_name` (`org_name`),
  KEY `org_admin_id` (`org_admin_id`),
  CONSTRAINT `organization_master_ibfk_1` FOREIGN KEY (`org_admin_id`) REFERENCES `user_master` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_master`
--

LOCK TABLES `organization_master` WRITE;
/*!40000 ALTER TABLE `organization_master` DISABLE KEYS */;
-- INSERT INTO `organization_master` VALUES (25,'oslab',23),(27,'testOrg',23),(28,'oidc',23),(29,'AIproject',24),(31,'test_org',23),(32,'aiai',23),(33,'D2SF',23);
/*!40000 ALTER TABLE `organization_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_project`
--

DROP TABLE IF EXISTS `organization_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_project` (
  `project_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(11) NOT NULL,
  `project_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`project_id`),
  KEY `org_id` (`org_id`),
  CONSTRAINT `organization_project_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization_master` (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_project`
--

LOCK TABLES `organization_project` WRITE;
/*!40000 ALTER TABLE `organization_project` DISABLE KEYS */;
-- INSERT INTO `organization_project` VALUES (30,25,'stosysProj'),(31,25,'testproj'),(33,27,'testProj'),(34,28,'oidc'),(35,29,'PencilRecog'),(36,25,'newTest'),(37,25,'hi'),(42,33,'D2SF Proj');
/*!40000 ALTER TABLE `organization_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_master`
--

DROP TABLE IF EXISTS `server_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_master` (
  `server_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `org_id` bigint(11) NOT NULL,
  `category_id` bigint(11) NOT NULL,
  `server_name` varchar(255) NOT NULL DEFAULT '',
  `server_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`server_id`),
  KEY `org_id` (`org_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `server_master_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization_master` (`org_id`),
  CONSTRAINT `server_master_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category_master` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_master`
--

LOCK TABLES `server_master` WRITE;
/*!40000 ALTER TABLE `server_master` DISABLE KEYS */;
-- INSERT INTO `server_master` VALUES (214,25,93,'ezon1','sdfasd'),(217,25,93,'ezon3','asdf');
/*!40000 ALTER TABLE `server_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_privileges`
--

DROP TABLE IF EXISTS `server_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_privileges` (
  `user_id` bigint(11) unsigned NOT NULL,
  `server_id` bigint(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `server_id` (`server_id`),
  CONSTRAINT `server_privileges_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_master` (`user_id`),
  CONSTRAINT `server_privileges_ibfk_2` FOREIGN KEY (`server_id`) REFERENCES `server_master` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_privileges`
--

LOCK TABLES `server_privileges` WRITE;
/*!40000 ALTER TABLE `server_privileges` DISABLE KEYS */;
/*!40000 ALTER TABLE `server_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_master`
--

DROP TABLE IF EXISTS `user_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_master` (
  `user_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(50) NOT NULL DEFAULT '',
  `user_password` varchar(255) NOT NULL DEFAULT '',
  `user_name` varchar(11) NOT NULL DEFAULT '',
  `enroll_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `last_login` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_name` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_master`
--

LOCK TABLES `user_master` WRITE;
/*!40000 ALTER TABLE `user_master` DISABLE KEYS */;
-- INSERT INTO `user_master` VALUES (23,'yungs0917@naver.com','0917','seunghun','2023-08-09 23:12:17','2023-08-09 23:12:17',NULL),(24,'kt8619@naver.com','0000','kimkitae','2023-08-09 23:12:34','2023-08-09 23:12:34',NULL);
INSERT INTO `user_master` VALUES (1,'root@email.com','rootroot','admin',current_timestamp() ,current_timestamp(),NULL)
/*!40000 ALTER TABLE `user_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_organization`
--

DROP TABLE IF EXISTS `user_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_organization` (
  `user_id` bigint(11) unsigned NOT NULL,
  `org_id` bigint(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `org_id` (`org_id`),
  CONSTRAINT `user_organization_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_master` (`user_id`),
  CONSTRAINT `user_organization_ibfk_2` FOREIGN KEY (`org_id`) REFERENCES `organization_master` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_organization`
--

LOCK TABLES `user_organization` WRITE;
/*!40000 ALTER TABLE `user_organization` DISABLE KEYS */;
-- INSERT INTO `user_organization` VALUES (23,25),(24,25),(23,27),(24,27),(23,28),(24,28),(24,29),(23,29),(23,31),(23,32),(23,33);
/*!40000 ALTER TABLE `user_organization` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-07  2:11:25
