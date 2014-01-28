-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: cmsdb
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `USER2GROUP`
--

DROP TABLE IF EXISTS `USER2GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER2GROUP` (
  `USER_ID` bigint(20) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`GROUP_ID`),
  KEY `FK_2g92je13gs6r0ho6ad6po1tpa` (`GROUP_ID`),
  KEY `FK_3oo8ihb2vq1pojb839i5iqseq` (`USER_ID`),
  CONSTRAINT `FK_2g92je13gs6r0ho6ad6po1tpa` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK_3oo8ihb2vq1pojb839i5iqseq` FOREIGN KEY (`USER_ID`) REFERENCES `user_accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER2GROUP`
--

LOCK TABLES `USER2GROUP` WRITE;
/*!40000 ALTER TABLE `USER2GROUP` DISABLE KEYS */;
INSERT INTO `USER2GROUP` VALUES (1,1),(2,1),(1,2);
/*!40000 ALTER TABLE `USER2GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,'_administrator'),(2,'_superadministrator');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `id` bigint(20) NOT NULL,
  `imageData` longblob,
  `imageType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layout`
--

DROP TABLE IF EXISTS `layout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layout` (
  `id` bigint(20) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layout`
--

LOCK TABLES `layout` WRITE;
/*!40000 ALTER TABLE `layout` DISABLE KEYS */;
INSERT INTO `layout` VALUES (1,1,'BasicLayout'),(2,0,'DefaultLayout');
/*!40000 ALTER TABLE `layout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pageComments`
--

DROP TABLE IF EXISTS `pageComments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pageComments` (
  `id` bigint(20) NOT NULL,
  `authorEmail` varchar(255) DEFAULT NULL,
  `authorName` varchar(255) DEFAULT NULL,
  `authorWebsite` varchar(255) DEFAULT NULL,
  `cDate` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `page_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_knej3790h849pebr4c4khkiqt` (`page_id`),
  CONSTRAINT `FK_knej3790h849pebr4c4khkiqt` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pageComments`
--

LOCK TABLES `pageComments` WRITE;
/*!40000 ALTER TABLE `pageComments` DISABLE KEYS */;
INSERT INTO `pageComments` VALUES (1,'bla@hotmail.com','van ik','www.minsite.be',NULL,1,'1.bla bal comentaar is gemakkelijk e',1),(2,'bla@hotmail.com','van ik','www.minsite2.be',NULL,1,'2.bla bal comentaar is gemakkelijk e',2),(3,'bla@hotmail.com','van ik','www.minsite.be',NULL,1,'3.bla bal comentaar is gemakkelijk e',1);
/*!40000 ALTER TABLE `pageComments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `id` bigint(20) NOT NULL,
  `associatedDate` datetime DEFAULT NULL,
  `body` longtext,
  `cdate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `edate` datetime DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ord` int(11) NOT NULL,
  `parentID` bigint(20) NOT NULL,
  `special` int(11) NOT NULL,
  `template` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `vars` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES (1,NULL,'<p></p>\n\n<p><a href=\"#anker\">#anker</a></p>\n\n<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px\">\n	<tbody>\n		<tr>\n			<td>msqdf</td>\n			<td>fff</td>\n		</tr>\n		<tr>\n			<td>f</td>\n			<td>lmj</td>\n		</tr>\n		<tr>\n			<td>l</td>\n			<td>mmm</td>\n		</tr>\n		<tr>\n			<td>sdfsdf</td>\n			<td>ffff</td>\n		</tr>\n	</tbody>\n</table>\n\n<p>qsdfqsdfqsdfqsdfsdf<img alt=\"\" src=\"/cmsUploadFolder/free-computer-wallpapers2-1.jpg\" style=\"float:left; height:188px; margin:5px; opacity:0.9; width:250px\" /></p>\n\n<p>sqfdqdf</p>\n\n<div>\n<p>&micro;&micro;&micro;<img alt=\"\" src=\"/cmsUploadFolder/cubes.jpg\" style=\"float:right; height:113px; margin:20px; width:200px\" /></p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<div>\n<p>&nbsp;</p>\n\n<div>\n<p>anker testje&nbsp;<a id=\"anker\" name=\"anker\"></a></p>\n\n<div lang=\"javascript\" style=\"background:#eee;border:1px solid #ccc;padding:5px 10px;\" title=\"adv titel\">\n<p>nen div met wa rdinformatie</p>\n\n<p>funtion(){</p>\n\n<p>&nbsp;sqldmkfj &nbsp;mslkdf msqkdf qsmkjf</p>\n\n<p>}</p>\n</div>\n\n<p>[[jCMS:pageComments{}]]</p>\n</div>\n\n<p>&nbsp;</p>\n</div>\n</div>\n',NULL,NULL,NULL,NULL,'testPage1',1,0,1,NULL,'den titel',1,NULL),(2,NULL,' <p>Page2</p>',NULL,NULL,NULL,NULL,'testPage2',2,0,0,NULL,'ook root normaal',1,NULL),(3,NULL,'<p></p>\n\n<p>child1</p>\n\n<div class=\"rdsucces\">\n<p>rdsucces</p>\n</div>\n<div class=\"rderror\">\n<p>rderror</p>\n</div>\n\n<div class=\"rdalert\">\n<p>rdalert</p>\n</div>\n\n<div class=\"rdinfo\">\n<p>rdinfo</p>\n</div>\n\n<p>&nbsp;</p>\n',NULL,NULL,NULL,NULL,'testPage2-child1',1,2,0,NULL,'testPage2-child1',1,NULL),(4,NULL,'[<p> child2 </p>',NULL,NULL,NULL,NULL,'testPage2-child2',2,2,0,NULL,'testPage2-child2',1,NULL);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_accounts`
--

DROP TABLE IF EXISTS `user_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_accounts` (
  `id` bigint(20) NOT NULL,
  `activationKey` varchar(255) DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `extras` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_accounts`
--

LOCK TABLES `user_accounts` WRITE;
/*!40000 ALTER TABLE `user_accounts` DISABLE KEYS */;
INSERT INTO `user_accounts` VALUES (1,NULL,1,'demuynck_ruben@hotmail.com',NULL,'zimzimma'),(2,NULL,0,'rudy@hotmail.com',NULL,'zimzimma');
/*!40000 ALTER TABLE `user_accounts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-24 14:33:49
