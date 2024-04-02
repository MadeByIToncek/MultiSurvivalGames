-- --------------------------------------------------------
-- Host:                         141.144.245.135
-- Server version:               11.2.3-MariaDB-1:11.2.3+maria~ubu2204 - mariadb.org binary distribution
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table s18_msg.config
DROP TABLE IF EXISTS `config`;
CREATE TABLE IF NOT EXISTS `config` (
  `key` varchar(256) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table s18_msg.config: ~1 rows (approximately)
INSERT INTO `config` (`key`, `value`) VALUES
	('master', 'mp-node1');

-- Dumping structure for table s18_msg.players
DROP TABLE IF EXISTS `players`;
CREATE TABLE IF NOT EXISTS `players` (
  `team` int(10) unsigned NOT NULL,
  `uuid` text NOT NULL,
  `points` int(10) unsigned NOT NULL DEFAULT 0,
  UNIQUE KEY `uuid` (`uuid`(256)) USING BTREE,
  KEY `team-id` (`team`),
  CONSTRAINT `team-id` FOREIGN KEY (`team`) REFERENCES `teams` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_czech_ci;

-- Dumping data for table s18_msg.players: ~0 rows (approximately)

-- Dumping structure for table s18_msg.servers
DROP TABLE IF EXISTS `servers`;
CREATE TABLE IF NOT EXISTS `servers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_czech_ci;

-- Dumping data for table s18_msg.servers: ~0 rows (approximately)

-- Dumping structure for table s18_msg.spawns
DROP TABLE IF EXISTS `spawns`;
CREATE TABLE IF NOT EXISTS `spawns` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `world` int(10) unsigned NOT NULL,
  `x` int(11) NOT NULL DEFAULT 0,
  `y` int(11) NOT NULL DEFAULT 0,
  `z` int(11) NOT NULL DEFAULT 0,
  `pitch` float NOT NULL DEFAULT 0,
  `yaw` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `world` (`world`),
  CONSTRAINT `world` FOREIGN KEY (`world`) REFERENCES `worlds` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table s18_msg.spawns: ~24 rows (approximately)
INSERT INTO `spawns` (`id`, `world`, `x`, `y`, `z`, `pitch`, `yaw`) VALUES
	(1, 1, 12, 30, 10, 132.6, 0),
	(2, 1, 8, 30, 12, 149.9, 0),
	(3, 1, 4, 30, 14, 165.7, 0),
	(4, 1, 0, 30, 14, 180, 0),
	(5, 1, -4, 30, 14, -166.2, 0),
	(6, 1, -8, 30, 12, -150.8, 0),
	(7, 1, -12, 30, 9, -134.9, 0),
	(8, 1, -15, 30, 5, -118.1, 0),
	(9, 1, -17, 30, 1, -103.4, 0),
	(10, 1, -17, 30, -3, -90, 0),
	(11, 1, -17, 30, -7, -76.5, 0),
	(12, 1, -15, 30, -11, -62, 0),
	(13, 1, -12, 30, -15, -44.9, 0),
	(14, 1, -8, 30, -18, -28.3, 0),
	(15, 1, -4, 30, -20, -13.3, 0),
	(16, 1, 0, 30, -20, 0, 0),
	(17, 1, 4, 30, -20, 13.3, 0),
	(18, 1, 8, 30, -18, 28.6, 0),
	(19, 1, 12, 30, -15, 45, 0),
	(20, 1, 15, 30, -11, 62, 0),
	(21, 1, 17, 30, -7, 77, 0),
	(22, 1, 17, 30, -3, 90, 0),
	(23, 1, 17, 30, 1, 103.5, 0),
	(24, 1, 15, 30, 5, 117.7, 0);

-- Dumping structure for table s18_msg.teams
DROP TABLE IF EXISTS `teams`;
CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `server` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `server` (`server`),
  CONSTRAINT `FK_teams_servers` FOREIGN KEY (`server`) REFERENCES `servers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_czech_ci;

-- Dumping data for table s18_msg.teams: ~0 rows (approximately)

-- Dumping structure for table s18_msg.worlds
DROP TABLE IF EXISTS `worlds`;
CREATE TABLE IF NOT EXISTS `worlds` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table s18_msg.worlds: ~1 rows (approximately)
INSERT INTO `worlds` (`id`, `name`) VALUES
	(1, 'sg4');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
