-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: localhost    Database: dbsysweb
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285svcgstjk3k94kv412gce6x` (`person_id`),
  CONSTRAINT `FK285svcgstjk3k94kv412gce6x` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Conteúdo teste do Armando.',1,NULL),(2,'Novo teste de conteúdo do usuário armando.',1,NULL),(3,'teste 3 editado por armando de novo por armando',1,NULL),(4,'Comentário da Maria editado',2,NULL),(5,NULL,1,'2019-03-19 17:43:58'),(6,NULL,1,'2019-03-19 17:46:31'),(7,NULL,1,'2019-03-19 17:49:56'),(8,'teste debug de novo comentário sobre o Bel em Fortaleza',1,'2019-03-19 17:55:38'),(9,'teste',1,'2019-03-19 18:01:51'),(10,'teste de novo comentário de post',1,'2019-03-19 18:11:32'),(11,'Gostei da analogia com Computação em Núvem',2,'2019-03-19 18:22:32'),(12,'E essa imagem de programação concorrente?',2,'2019-03-19 18:23:13'),(13,'Final de tarde me Miami.',1,'2019-03-19 18:30:03'),(14,'Show do Chicabana na Alura em Teresina',1,'2019-03-19 18:32:20'),(15,'Esse show do Bel em Fortaleza foi massa!',2,'2019-03-19 18:43:45'),(16,'Quem foi para esse show?',NULL,'2019-03-19 20:04:09'),(17,'Quem foi para esse show?',16,'2019-03-19 20:07:00'),(18,'Massa!',16,'2019-03-19 20:09:47'),(19,'Tem que testar mesmo!!!',1,'2019-03-19 20:20:33'),(20,'Só a aristocracia de Sampa!',17,'2019-03-19 20:27:07'),(21,'É isso aí!',17,'2019-03-19 20:29:52'),(22,'Faltou colocar mais detalhes em cada etapa...',1,'2019-03-19 20:44:05'),(23,'Vou procurar mais detalhes e ver se consigo algo mais detalhado.',17,'2019-03-19 20:45:37'),(24,'Aí é tranquilo de mais...',1,'2019-03-19 21:03:01');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKemsnreyk6g37uoja1ngeog5sp` (`user_id`),
  CONSTRAINT `FKemsnreyk6g37uoja1ngeog5sp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Rua Território Fernando de Noronha 2112','64007250','Teresina',0,0,'Armando Soares Sousa','PI',1),(2,'Rua Júlio Cesar','60000000','Fortaleza',0,0,'Maria Joaquina de Amaral Pereira Goes','CE',2),(16,'Rua do Teste','64000000','Teste',0,0,'Teste da Silva Sauro','TE',34),(17,'End Teste','65000000','Teste',0,0,'Teste Segundo ','TE',35);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_comments`
--

DROP TABLE IF EXISTS `person_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_comments` (
  `person_id` bigint(20) NOT NULL,
  `comments_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_1fhnhr9u849d5j7cmvgp47p0h` (`comments_id`),
  KEY `FK712s55m4rkh22g5ep831fokcx` (`person_id`),
  CONSTRAINT `FK712s55m4rkh22g5ep831fokcx` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKo4gwj5lpphdrpkhriyladi4xo` FOREIGN KEY (`comments_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_comments`
--

LOCK TABLES `person_comments` WRITE;
/*!40000 ALTER TABLE `person_comments` DISABLE KEYS */;
INSERT INTO `person_comments` VALUES (1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,13),(1,14),(1,19),(1,22),(1,24),(2,11),(2,12),(2,15),(16,17),(16,18),(17,21),(17,23);
/*!40000 ALTER TABLE `person_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_pictures`
--

DROP TABLE IF EXISTS `person_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_pictures` (
  `person_id` bigint(20) NOT NULL,
  `pictures_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_jqqg29t07q75nwoiohvqvfyn6` (`pictures_id`),
  KEY `FKtmnbbk3jp0dit0qqr2lu7sev8` (`person_id`),
  CONSTRAINT `FK8ujewad87wn7tt46i974jbwxq` FOREIGN KEY (`pictures_id`) REFERENCES `picture` (`id`),
  CONSTRAINT `FKtmnbbk3jp0dit0qqr2lu7sev8` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_pictures`
--

LOCK TABLES `person_pictures` WRITE;
/*!40000 ALTER TABLE `person_pictures` DISABLE KEYS */;
INSERT INTO `person_pictures` VALUES (1,6),(1,7),(1,17),(1,18),(1,22),(2,14),(2,15),(16,20),(17,21),(17,23);
/*!40000 ALTER TABLE `person_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_posts`
--

DROP TABLE IF EXISTS `person_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_posts` (
  `person_id` bigint(20) NOT NULL,
  `posts_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ftx42i397lg8u9wrsihay8kn0` (`posts_id`),
  KEY `FKc8ohp3cyp3iokdepp2qalso6m` (`person_id`),
  CONSTRAINT `FKc8ohp3cyp3iokdepp2qalso6m` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKqvkeglu3miqu2rh0wk7lpaaie` FOREIGN KEY (`posts_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_posts`
--

LOCK TABLES `person_posts` WRITE;
/*!40000 ALTER TABLE `person_posts` DISABLE KEYS */;
INSERT INTO `person_posts` VALUES (1,2),(1,3),(1,4),(1,5),(1,6),(2,7),(2,8),(2,9),(16,10),(16,11),(17,12),(17,13);
/*!40000 ALTER TABLE `person_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41i6hfm4sdrkrm9krf2h1i9fx` (`person_id`),
  KEY `FK24liocg7lhfngonriw16m0usw` (`post_id`),
  CONSTRAINT `FK24liocg7lhfngonriw16m0usw` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FK41i6hfm4sdrkrm9krf2h1i9fx` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (3,'Armando no teste 3 de salva imagem no banco','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-08-10.png',1,NULL,NULL),(4,'teste 4 do armando','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-18-07.png',1,NULL,NULL),(5,'Teste 5 de imagem salva pelo usuário.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-32-00.png',1,'1-2019-03-18-12-32-00',NULL),(6,'Teste 6','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-33-13.png',1,'1-2019-03-18-12-33-13',2),(7,'teste 7 de inserção de imagem de usuário','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-42-07.png',1,'1-2019-03-18-12-42-07',3),(13,'teste 14','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-59-48.png',1,'1-2019-03-18-12-59-48',NULL),(14,'Foto da Maria','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/2-2019-03-18-13-56-06.png',2,'2-2019-03-18-13-56-06',9),(15,'Foto 2 da Maria','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/2-2019-03-18-13-56-39.png',2,'2-2019-03-18-13-56-39',8),(17,'New York...','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-19-12-40-30.png',1,'1-2019-03-19-12-40-30',4),(18,'Hipódromo de São Paulo.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-19-12-44-17.png',1,'1-2019-03-19-12-44-17',5),(19,'Miami...','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-19-13-05-02.png',1,'1-2019-03-19-13-05-02',6),(20,'Resumo sobre teste de software.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/16-2019-03-19-20-16-29.png',16,'16-2019-03-19-20-16-29',11),(21,'Etapas para certificação de teste de software.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/17-2019-03-19-20-32-55.png',17,'17-2019-03-19-20-32-55',12),(22,'Explorando a Computação em Nuvem com a Amazon.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-19-20-51-16.png',1,'1-2019-03-19-20-51-16',NULL),(23,'Teste no parque...','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/17-2019-03-19-21-00-59.png',17,'17-2019-03-19-21-00-59',13);
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `likes` int(11) NOT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkenxtm1pl4w6rchuhelil8lf4` (`person_id`),
  KEY `FKlw8ljyti8buqh3bu8poougxto` (`picture_id`),
  CONSTRAINT `FKkenxtm1pl4w6rchuhelil8lf4` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKlw8ljyti8buqh3bu8poougxto` FOREIGN KEY (`picture_id`) REFERENCES `picture` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (2,'2019-03-19 11:05:16',0,1,6),(3,'2019-03-19 11:06:07',0,1,7),(4,'2019-03-19 12:40:46',0,1,17),(5,'2019-03-19 12:44:25',0,1,18),(6,'2019-03-19 13:05:22',0,1,19),(7,'2019-03-19 13:08:07',0,2,15),(8,'2019-03-19 18:31:19',0,2,15),(9,'2019-03-19 18:31:45',0,2,14),(10,'2019-03-19 20:17:34',0,16,20),(11,'2019-03-19 20:19:01',0,16,20),(12,'2019-03-19 20:37:15',0,17,21),(13,'2019-03-19 21:02:04',0,17,23);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_comments`
--

DROP TABLE IF EXISTS `post_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_comments` (
  `post_id` bigint(20) NOT NULL,
  `comments_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_gq9be62nx9c9hc0uyhakey771` (`comments_id`),
  KEY `FKmws3vvhl5o4t7r7sajiqpfe0b` (`post_id`),
  CONSTRAINT `FKmws3vvhl5o4t7r7sajiqpfe0b` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKrvgf8o4dg5kamt01me5gjqodf` FOREIGN KEY (`comments_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_comments`
--

LOCK TABLES `post_comments` WRITE;
/*!40000 ALTER TABLE `post_comments` DISABLE KEYS */;
INSERT INTO `post_comments` VALUES (2,11),(3,12),(4,18),(5,20),(6,13),(7,9),(7,10),(7,15),(7,17),(9,14),(10,19),(10,21),(12,22),(12,23),(13,24);
/*!40000 ALTER TABLE `post_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER'),(4,'GUEST');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `id` (`id`),
  KEY `FKd21kkcigxa21xuby5i3va9ncs` (`person_id`),
  CONSTRAINT `FKd21kkcigxa21xuby5i3va9ncs` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('armando','$2a$10$bkmVrasmzmeK4Uq/uashx..pta97Koxhi41..rbami8RSjVDJ/zNO',1,1,'armando@ufpi.edu.br',1),('maria','$2a$10$QDZqbS8KUkaLACXFrZUpqOtDFc/wMBqC.ZaEu.XgvagmOLAfDUhoq',1,2,'maria@gmail.com',2),('teste','$2a$10$h.XG2Pr8HYIDBOXQHctKbeLWmMEZZ.tscCXDCPsDC5OF2zxcjtOyu',1,34,'teste@gmail.com',16),('teste2','$2a$10$wFAuEdSSIZqxzUOOWv47CefhsSI8j1Wrkz5TwDfgPkrXkMKv7XIGC',1,35,'teste2@gmail.com',17);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK15d410tj6juko0sq9k4km60xq` (`roles_id`),
  KEY `FKml90kef4w2jy7oxyqv742tsfc` (`users_id`),
  CONSTRAINT `FK15d410tj6juko0sq9k4km60xq` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(1,2),(2,2),(34,2),(35,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-19 21:11:50
