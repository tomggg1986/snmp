-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.5.23 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-01-05 10:21:58
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for SNMP
CREATE DATABASE IF NOT EXISTS `snmp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `SNMP`;


-- Dumping structure for table SNMP.bulk
CREATE TABLE IF NOT EXISTS `bulk` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nonrepeaters` int(10) DEFAULT NULL,
  `maxrepetitions` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.bulk: ~0 rows (approximately)
/*!40000 ALTER TABLE `bulk` DISABLE KEYS */;
INSERT INTO `bulk` (`id`, `nonrepeaters`, `maxrepetitions`) VALUES
	(1, 0, 7);
/*!40000 ALTER TABLE `bulk` ENABLE KEYS */;


-- Dumping structure for table SNMP.bulkoid
CREATE TABLE IF NOT EXISTS `bulkoid` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `oid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.bulkoid: ~1 rows (approximately)
/*!40000 ALTER TABLE `bulkoid` DISABLE KEYS */;
INSERT INTO `bulkoid` (`id`, `oid`) VALUES
	(1, '.1.3.6.1.2.1.2.2.1.2'),
	(2, '.1.3.6.1.2.1.2.2.1.6');
/*!40000 ALTER TABLE `bulkoid` ENABLE KEYS */;


-- Dumping structure for table SNMP.device
CREATE TABLE IF NOT EXISTS `device` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `ipaddres` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.device: ~5 rows (approximately)
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` (`ID`, `name`, `ipaddres`) VALUES
	(1, 'RB450G', '10.1.0.51'),
	(2, 'RB411AH', '10.1.0.52'),
	(8, 'Epson', '10.1.0.10');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;


-- Dumping structure for table SNMP.getoid
CREATE TABLE IF NOT EXISTS `getoid` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `oid` varchar(30) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.getoid: ~2 rows (approximately)
/*!40000 ALTER TABLE `getoid` DISABLE KEYS */;
INSERT INTO `getoid` (`id`, `oid`) VALUES
	(1, '1.3.6.1.4.1.14988.1.1.3.8.0'),
	(8, '1.3.6.1.4.1.14988.1.1.3.8.0');
/*!40000 ALTER TABLE `getoid` ENABLE KEYS */;


-- Dumping structure for table SNMP.nextoid
CREATE TABLE IF NOT EXISTS `nextoid` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `oid` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.nextoid: ~2 rows (approximately)
/*!40000 ALTER TABLE `nextoid` DISABLE KEYS */;
INSERT INTO `nextoid` (`id`, `oid`) VALUES
	(1, '.1.3.6.1.2.1.2.2.1.2'),
	(2, '.1.3.6.1.2.1.2.2.1.6');
/*!40000 ALTER TABLE `nextoid` ENABLE KEYS */;


-- Dumping structure for table SNMP.profils
CREATE TABLE IF NOT EXISTS `profils` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `securityname` varchar(20) DEFAULT NULL,
  `authprot` varchar(20) DEFAULT NULL,
  `authpass` varchar(20) DEFAULT NULL,
  `privprot` varchar(20) DEFAULT NULL,
  `privpass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table SNMP.profils: ~5 rows (approximately)
/*!40000 ALTER TABLE `profils` DISABLE KEYS */;
INSERT INTO `profils` (`id`, `name`, `version`, `securityname`, `authprot`, `authpass`, `privprot`, `privpass`) VALUES
	(3, 'SNMPv3authpriv', 'snmpv3', 'public', 'AuthMD5', 'ptokuptoku', 'PrivDES', 'ptokuptoku'),
	(4, 'SNMPv2', 'snmpv2c', 'public', 'AuthSHA', '', '', ''),
	(5, 'SNMPv1', 'snmpv1', 'public', 'AuthSHA', '', '', ''),
	(6, 'SNMPv3OnlyAuth', 'snmpv3', 'public', 'AuthMD5', 'ptokuptoku', '', ''),
	(7, 'SNMPv3NoSecu', 'snmpv3', 'public', '', '', '', ''),
	(8, 'ssss', 'snmpv3', 'public', 'AuthMD5', 'ptokuptoku', 'PrivDES', 'ptokuptoku');
/*!40000 ALTER TABLE `profils` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
