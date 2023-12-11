-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.1.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para eventsystem
CREATE DATABASE IF NOT EXISTS `eventsystem` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `eventsystem`;

-- Volcando estructura para tabla eventsystem.events
CREATE TABLE IF NOT EXISTS `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `description` varchar(5000) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL,
  `address` varchar(500) NOT NULL DEFAULT '0',
  `city` varchar(50) NOT NULL DEFAULT '0',
  `postal_code` int(11) NOT NULL DEFAULT 0,
  `price` double NOT NULL DEFAULT 0,
  `room` int(11) NOT NULL DEFAULT 0,
  `place_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_events_places` (`place_id`),
  CONSTRAINT `FK_events_places` FOREIGN KEY (`place_id`) REFERENCES `places` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla eventsystem.events: ~2 rows (aproximadamente)
INSERT INTO `events` (`id`, `name`, `description`, `date`, `address`, `city`, `postal_code`, `price`, `room`, `place_id`) VALUES
	(14, 'Hyatt Zilara Cancun', 'Welcome to Hyatt Zilara Cancun: an Adults-only, all-inclusive resort (min. age 18). Prominently located on the widest stretch of beach in the heart of Cancun\'s Hotel Zone, Hyatt Zilara Cancun is an all-suite, all-inclusive resort where guests experience traditional Mexican warmth in an intimate yet casual setting. Our resort features spacious suites, all of them with breathtaking views of the Caribbean and offers world-class dining, an impressive array of upscale amenities, outstanding service from an attentive and thoughtful staff, and spectacular grounds and facilities. Hyatt Zilara Cancun offers a myriad of activities and conveniences including sophisticated culinary options at six gourmet restaurants, rejuvenating treatments and services at Zen Spa, mixology experiences at The Den, wine and spirits tastings, enhanced pool and beach butler service and more.', '2023-12-11 00:00:00', 'Blvd. Kukulcan Km 11.5 Zona Hotelera, Cancun 77500 Mexico', 'Cancun', 77500, 131.15383780627505, 19, 19),
	(15, 'Hotel Austria', 'Hotel Austria is right in the middle of the historical Old City of Vienna, a short walking distance from St. Stephen\'s Cathedral, a few minutes away from the Vienna State Opera, the Hofburg and the famous shopping streets. Check-in into one of our 46 comfortable and peaceful rooms, equipped with free wireless LAN (WI-FI) hotspot, phone with mailbox, minibar, tea and coffee making facilities and most of all, a uniquely individual ambience!', '2023-12-11 00:00:00', 'Fleischmarkt 20/Wolfengasse 3, Vienna 1010 Austria', 'Vienna', 1010, 92.66196958907423, 10, 20);

-- Volcando estructura para tabla eventsystem.places
CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `address` varchar(500) NOT NULL DEFAULT '0',
  `city` varchar(50) NOT NULL DEFAULT '0',
  `postal_code` int(11) NOT NULL DEFAULT 0,
  `latitude` varchar(50) NOT NULL DEFAULT '0',
  `longitude` varchar(50) NOT NULL DEFAULT '0',
  `tripAdvisor_link` varchar(300) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla eventsystem.places: ~3 rows (aproximadamente)
INSERT INTO `places` (`id`, `name`, `address`, `city`, `postal_code`, `latitude`, `longitude`, `tripAdvisor_link`) VALUES
	(19, 'Hyatt Zilara Cancun', 'Blvd. Kukulcan Km 11.5 Zona Hotelera, Cancun 77500 Mexico', 'Cancun', 77500, '21.119164', '-86.75646', 'https://www.tripadvisor.com/Hotel_Review-g150807-d642781-Reviews-Hyatt_Zilara_Cancun-Cancun_Yucatan_Peninsula.html?m=66827'),
	(20, 'Hotel Austria', 'Fleischmarkt 20/Wolfengasse 3, Vienna 1010 Austria', 'Vienna', 1010, '48.21021', '16.37713', 'https://www.tripadvisor.com/Hotel_Review-g190454-d235666-Reviews-Hotel_Austria-Vienna.html?m=66827'),
	(21, 'Austria Hof Lodge', '924 Canyon Boulevard, Mammoth Lakes, CA 93546', 'Mammoth Lakes', 93546, '37.64738', '-119.0001', 'https://www.tripadvisor.com/Hotel_Review-g60791-d235465-Reviews-Austria_Hof_Lodge-Mammoth_Lakes_California.html?m=66827');

-- Volcando estructura para tabla eventsystem.reservations
CREATE TABLE IF NOT EXISTS `reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `event_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK__events` (`event_id`),
  CONSTRAINT `FK__events` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla eventsystem.reservations: ~2 rows (aproximadamente)
INSERT INTO `reservations` (`id`, `user_name`, `date`, `quantity`, `event_id`) VALUES
	(22, 'Diego', '2023-12-11 00:00:00', 9, 14),
	(23, 'Diego', '2023-12-11 00:00:00', 4, 15);

-- Volcando estructura para tabla eventsystem.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

-- Volcando datos para la tabla eventsystem.roles: ~2 rows (aproximadamente)
INSERT INTO `roles` (`id`, `name`) VALUES
	(1, 'ADMIN'),
	(2, 'USUARIO');

-- Volcando estructura para tabla eventsystem.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_number` int(11) NOT NULL DEFAULT 0,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `last_name` varchar(50) NOT NULL DEFAULT '0',
  `birth_date` date NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '0',
  `phone_number` int(11) NOT NULL DEFAULT 0,
  `password` varchar(50) NOT NULL DEFAULT '0',
  `rol_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_user_to_rol` (`rol_id`),
  CONSTRAINT `FK_users_roles` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla eventsystem.users: ~4 rows (aproximadamente)
INSERT INTO `users` (`id`, `id_number`, `name`, `last_name`, `birth_date`, `email`, `phone_number`, `password`, `rol_id`) VALUES
	(2, 402640041, 'Carlos', 'Bravo', '2001-04-08', 'bravo123@gmail.com', 70463777, 'bravo123', 1),
	(3, 12345678, 'Diego', 'Morera', '2004-04-29', 'diego123@gmail.com', 85552515, 'diego123', 2),
	(8, 208440383, 'Paulo', 'Jimenez', '2004-09-30', 'paulo123@gmail.com', 70567777, 'paulo123', 2),
	(9, 123456789, 'Paulo', 'a', '2023-12-27', 'paulo123@gmail.com', 65434564, '123gd', 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
