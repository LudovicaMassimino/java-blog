-- MySQL dump 10.13  Distrib 9.0.1, for macos15.0 (arm64)
--
-- Host: localhost    Database: db_javablog
-- ------------------------------------------------------
-- Server version	8.4.0

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_date` date DEFAULT NULL,
  `body` varchar(10000) NOT NULL,
  `image` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author_id` int DEFAULT NULL,
  `category_id` int NOT NULL,
  `status` enum('APPROVED','IN_REVIEW','REJECTED') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgwrtdbqvt9ucntp82nd3yiuec` (`author_id`),
  KEY `FKy5kkohbk00g0w88fi05k2hcw` (`category_id`),
  CONSTRAINT `FKgwrtdbqvt9ucntp82nd3yiuec` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKy5kkohbk00g0w88fi05k2hcw` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'2025-04-01','Quisque nec justo ipsum. Ut sit amet felis ac metus rhoncus dapibus eu quis justo. Suspendisse posuere sagittis fringilla. Praesent non viverra tortor. Curabitur condimentum metus diam, non feugiat ante dapibus vitae. Quisque sollicitudin dictum luctus. Integer non diam velit. Prova put da Postman','uploads/premium_photo-1726754457459-d2dfa2e3a434.avif','Java Post',3,2,'APPROVED','2025-04-01 13:37:10.688570'),(2,'2025-04-03','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas laoreet sem sit amet erat laoreet, pulvinar feugiat quam viverra. Donec non hendrerit eros. Quisque posuere varius laoreet. Nulla facilisi. Aenean mattis odio sem, vitae tristique nisl pretium non. Curabitur vulputate ornare elit sed porttitor. Fusce tristique leo sit amet convallis ullamcorper. Curabitur consequat nec sapien vitae venenatis. Nulla facilisi. Donec varius ut neque quis scelerisque. Fusce feugiat ante sit amet leo faucibus efficitur. Phasellus at est laoreet, lacinia lectus at, varius purus. Ut in sapien id ligula tristique porta tristique id ligula. Praesent ligula arcu, vehicula quis facilisis ac, faucibus a elit. Phasellus ut lectus in mi vehicula venenatis. Ut convallis molestie leo, in egestas arcu consec','uploads/sfondo.png','JS Post',1,2,'APPROVED','2025-04-03 11:57:07.907039'),(3,'2025-04-03','Cras vestibulum, lorem eget pellentesque tincidunt, augue libero tristique felis, quis feugiat urna turpis id tellus. Nunc eros felis, commodo vel est eget, lacinia congue leo. Sed nec odio dictum, fringilla nunc ac, efficitur diam. Aliquam erat volutpat. Vestibulum consectetur tempus tempor. Morbi at egestas ante. Fusce hendrerit metus sit amet nisi vehicula, vitae consectetur urna mattis. Donec tempor eros id feugiat rhoncus. Sed venenatis nunc non est mattis tempus. Fusce porta massa vitae odio sollicitudin tempor. Pellentesque condimentum dolor eu maximus ultrices. Proin tempor faucibus metus, non ornare tellus euismod nec. Vivamus tristique nec est at ultricies.\r\n\r\n','uploads/sfondo.png','Test 1',1,3,'IN_REVIEW','2025-04-03 12:00:47.661663'),(34,'2025-04-04','Donec velit purus, volutpat eget vestibulum id, fermentum et leo. Phasellus vulputate nulla ex. Etiam orci magna, varius vel turpis nec, suscipit egestas ligula. Curabitur imperdiet nisl mi, vel faucibus odio maximus id. Suspendisse potenti. Aliquam volutpat metus urna, at luctus felis condimentum vel. Suspendisse et massa imperdiet, bibendum nisi eget, sodales augue. Fusce bibendum quam sed porta mollis.\r\n\r\n','uploads/background-body.jpg','Python Post',1,6,'APPROVED','2025-04-04 11:00:45.058584'),(35,'2025-04-09','In id eros dignissim, porttitor enim sit amet, iaculis eros. Donec viverra, est et maximus faucibus, elit enim fringilla nisi, sit amet varius nisl nisl sed mi. Maecenas lacinia nibh quis eleifend varius. Donec et lorem orci. Aenean venenatis eros id enim ultrices, dapibus consequat enim hendrerit. Fusce sed enim congue, lacinia velit vel, ultrices quam. Donec et turpis lobortis, auctor urna ut, lacinia mi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin nec maximus sapien, at congue ante. Curabitur interdum ex eu mauris mollis tempor. Nunc vitae dui nec nisl efficitur placerat. Vivamus vel dapibus neque. Nunc id mattis augue. Morbi laoreet nisl sit amet aliquam semper.\r\n\r\n','uploads/premium_photo-1726754457459-d2dfa2e3a434.avif','Laravel Post',2,4,'APPROVED','2025-04-09 11:18:42.235287');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Java'),(2,'JS'),(3,'PHP'),(4,'Laravel'),(5,'Vue'),(6,'Python'),(7,'Node'),(8,'C');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `photo_profile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'{noop}password','ADMIN','admin','uploads/fotoCV-2.png'),(2,'{noop}password','USER','user1',NULL),(3,'{noop}password','USER','user2',NULL),(4,'{noop}password','USER','user3',NULL),(5,'{noop}password','USER','user4',NULL),(6,'{noop}password','USER','user5',NULL),(7,'{noop}password','USER','user6',NULL),(8,'{noop}password','USER','user7',NULL),(9,'{bcrypt}$2a$10$cKMfu8O5CPn2Oy1BNDvjq.4Hh2tlQIha2Gqyt7FuX1PEPioxOo5cC','USER','user8',NULL),(10,'{bcrypt}$2a$10$.6agNN7jJN3evXdKpqkUTedzfhgq5cI7hpuHP8zl0ypU7.yGS/RgK','USER','user9',NULL),(11,'{bcrypt}$2a$10$/ggLbQUxbPf9kJSTELqS2OGksUZR0BtGGG7JkQpepVBDLId51znyS','USER','user10',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_javablog'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-24 19:09:09
