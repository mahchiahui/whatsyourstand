-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 21, 2019 at 03:28 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `verification`
--

-- --------------------------------------------------------

--
-- Table structure for table `phonenumtoken`
--

DROP TABLE IF EXISTS `phonenumtoken`;
CREATE TABLE IF NOT EXISTS `phonenumtoken` (
  `token` int(100) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `phonenumtoken`
--

INSERT INTO `phonenumtoken` (`token`) VALUES
(103348),
(174331),
(246622),
(755858),
(945669);

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

DROP TABLE IF EXISTS `voter`;
CREATE TABLE IF NOT EXISTS `voter` (
  `voterID` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `hashedPN` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `locationDocumentPath` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`voterID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `voter`
--

INSERT INTO `voter` (`voterID`, `name`, `hashedPN`, `city`, `locationDocumentPath`, `email`) VALUES
(4, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(5, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(6, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(7, 'Chia Hui Mah 123', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMah123Doc.pdf', 'mahchiahui@hotmail.sg'),
(8, 'Chia Hui Mah', '4129036789', 'Pittsburgh', '/opt/tomcat/webapps/ROOT/data/ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(9, 'Chia Hui Mah 123', '4129036789', 'Pittsburgh', '/opt/tomcat/webapps/ROOT/data/ChiaHuiMah123Doc.pdf', 'mahchiahui@hotmail.sg'),
(10, 'Mah Chia Hui', '4129036789', 'Singapore', '/opt/tomcat/webapps/app-1.0-SNAPSHOT/data/MahChiaHuiDoc.pdf', 'chuah68@gmail.com'),
(11, 'Chia Hui Mah 123123', '4129036789', 'Pittsburgh', '/opt/tomcat/webapps/app-1.0-SNAPSHOT/data/ChiaHuiMah123123Doc.pdf', 'mahchiahui@hotmail.sg'),
(12, 'Chia Hui Mah', '4129036789', 'Pittsburgh', '/opt/tomcat/webapps/app-1.0-SNAPSHOT/data/ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(13, 'fe', '4129036789', 'few e', '/opt/tomcat/webapps/ROOT/data/feDoc.pdf', 'fdwe@andrew.cmu.edu'),
(14, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg'),
(15, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
