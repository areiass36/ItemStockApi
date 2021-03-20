CREATE DATABASE IF NOT EXISTS `itemstock`;

USE itemstock;

CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p0wr4vfyr2lyifm8avi67mqw5` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `added_date` date NOT NULL,
  `brand` varchar(255) NOT NULL,
  `max` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `person_id` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lcsp6a1tpwb8tfywqhrsm2uvg` (`name`),
  KEY `FKa3m63pl3j6icu0kidc8c2orfm` (`person_id`),
  CONSTRAINT `FKa3m63pl3j6icu0kidc8c2orfm` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
