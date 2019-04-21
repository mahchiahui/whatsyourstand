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
-- Database: `14819db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `userid` int(11) NOT NULL,
  `admin_level` int(11) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userid`, `admin_level`) VALUES
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
CREATE TABLE IF NOT EXISTS `candidate` (
  `userid` int(100) NOT NULL,
  `realname` varchar(256) NOT NULL,
  `age` int(100) NOT NULL,
  `location` varchar(256) NOT NULL,
  `workplace` varchar(256) NOT NULL,
  `political_affiliation` varchar(256) NOT NULL,
  `political_goal` varchar(256) NOT NULL,
  `education` varchar(256) NOT NULL,
  `profile_photo_path` varchar(256) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cookie`
--

DROP TABLE IF EXISTS `cookie`;
CREATE TABLE IF NOT EXISTS `cookie` (
  `cookieid` varchar(100) NOT NULL,
  `userid` varchar(100) NOT NULL,
  `time_stamp` datetime NOT NULL,
  PRIMARY KEY (`cookieid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cookie`
--

INSERT INTO `cookie` (`cookieid`, `userid`, `time_stamp`) VALUES
('1123', '1', '2019-04-05 18:53:06'),
('123', '1', '2019-04-05 18:53:06'),
('2suGwwZEQRzVxIc2cpjbbCDCjavEdo60', '1', '2019-04-21 10:38:34'),
('8GF5GHWC3sJxx21HYUYo9f4VrkOgiOwv', '6', '2019-04-06 03:40:39'),
('BhKNZqyaBH7SkZzdl3XXqfVx7PTlBvmx', '1', '2019-04-08 17:53:11'),
('CQcRWybWIsRYN6Amu1p14Blo996iKZ6h', '6', '2019-04-06 03:45:25'),
('dSzzd7rlerJMxwN0l6s2rGnhCblwLgag', '6', '2019-04-08 17:54:45'),
('e6oCtUvvShXwA3LxHVLXZtMVMZo61C0L', '1', '2019-04-06 03:43:01'),
('E8CbklkDDurbyjgvuY8G0ohuG4NzSJRV', '3', '2019-04-06 03:54:44'),
('EFZ6ThxgTRHWP9nPkaKvbXZy0OUv8TcK', '1', '2019-04-06 03:39:31'),
('fLwWQBTXSO7PERLsfqxfdueGYU0gaCa5', '1', '2019-04-06 03:55:05'),
('fWNsy9QAYNXBDUDHW32C9yaPz2SLjrxX', '6', '2019-04-06 03:41:29'),
('g0tcos7ZLef0DwS0tTwhRAxTj2lDsdfu', '3', '2019-04-08 17:30:58'),
('io1nOZd4g8rIHOdFvt72isoJBvOYPvWU', '1', '2019-04-06 05:04:44'),
('lAVBDxXKV7nWoQlo7S9c4tUf5RyVFr9j', '1', '2019-04-06 03:45:34'),
('LK67CAyzJa31Ybe0bfzMFUBemy8zH8JQ', '3', '2019-04-21 11:09:23'),
('Nr9oREHpcnDwuNv43PtMkPF5ZtltP9u8', '3', '2019-04-06 03:45:13'),
('pIGirNBizlJsiIMLeKmOnHCCMAQyOpjn', '1', '2019-04-17 19:28:26'),
('UbvZDJofwr8vrvBVNK4mW7Pz0okStXt0', '1', '2019-04-21 11:06:21'),
('uwwg9uOshyYyLLIQqu2FnS0WytK0Nexd', '1', '2019-04-21 09:34:31'),
('xgRR7op3ArQseC8eyGNi3nXzk2V6syjy', '6', '2019-04-06 03:54:57'),
('YKdKsHc3G3Sc8jgLgjI5YP3L8ORLFY82', '3', '2019-04-06 03:46:24');

-- --------------------------------------------------------

--
-- Table structure for table `rootuser`
--

DROP TABLE IF EXISTS `rootuser`;
CREATE TABLE IF NOT EXISTS `rootuser` (
  `userid` int(100) NOT NULL,
  `username` varchar(300) NOT NULL,
  `hashpwd` varchar(300) NOT NULL,
  `role` int(100) NOT NULL,
  `request_del` int(100) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rootuser`
--

INSERT INTO `rootuser` (`userid`, `username`, `hashpwd`, `role`, `request_del`) VALUES
(1, 'admin', '$2a$12$qQMb1Cr6gwW9WcAk2n9XRuIS/fFPy8w7ezkJy3RorAWAsNYFfm8du', 0, 0),
(3, 'voter', '$2a$12$LorRTCT9pjr6sHYKLCO/H.tEguKG1dd79EojicHaHLzMJzyLRqg1W', 1, 0),
(6, 'candidate', '$2a$12$1tajeqpxcvasfvQ2ZUX3YuM.15Lmomho4Usb0iwMBzTjdxRAuM3WW', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `testValue` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`testValue`) VALUES
('works'),
('works'),
('works'),
('works');

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
CREATE TABLE IF NOT EXISTS `token` (
  `tokenid` int(100) NOT NULL,
  `token` varchar(300) NOT NULL,
  `time_stamp` datetime NOT NULL,
  PRIMARY KEY (`tokenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`tokenid`, `token`, `time_stamp`) VALUES
(1, '2', '2019-04-21 10:36:48'),
(2, '3', '2019-04-21 10:36:54'),
(3, 'JESeEkkmfMWYjXAc3NuWNLK2YnCCf8Jp', '2019-04-21 10:38:50'),
(4, 'to2GXF2xggJnbDaOj7LkJSdCplamlnG7', '2019-04-21 10:38:56'),
(5, 'zsQO6sk4zJ4qF3uOPkTihTJcO0PgqGcJ', '2019-04-21 10:52:27'),
(6, 'LxfEgcJHL5ME5kT7OEVzmQqQbUzxWHfO', '2019-04-21 10:52:33');

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

DROP TABLE IF EXISTS `voter`;
CREATE TABLE IF NOT EXISTS `voter` (
  `userid` int(100) NOT NULL,
  `location` varchar(300) NOT NULL,
  `email` varchar(300) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `voter`
--

INSERT INTO `voter` (`userid`, `location`, `email`) VALUES
(2, '', ''),
(3, '', ''),
(4, '', ''),
(6, '', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
