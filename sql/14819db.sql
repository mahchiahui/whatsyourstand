-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 25, 2019 at 04:45 AM
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
  `userid` int(100) NOT NULL,
  `admin_level` int(100) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userid`, `admin_level`) VALUES
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `answerid` int(100) NOT NULL,
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `content` varchar(256) NOT NULL,
  `created_time` datetime NOT NULL,
  `last_mod_time` datetime NOT NULL,
  `upvote` int(100) NOT NULL,
  `downvote` int(100) NOT NULL,
  PRIMARY KEY (`answerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`answerid`, `questionid`, `userid`, `content`, `created_time`, `last_mod_time`, `upvote`, `downvote`) VALUES
(1, 3, 4, 'answer1', '2019-04-20 07:00:00', '2019-04-20 08:00:00', 1, 1),
(2, 3, 5, 'I hope this works well!!!', '2019-04-24 01:44:56', '2019-04-24 22:25:01', 0, 0),
(3, 1, 5, 'i hope this works well too', '2019-04-24 03:42:06', '2019-04-24 03:42:06', 0, 0);

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

--
-- Dumping data for table `candidate`
--

INSERT INTO `candidate` (`userid`, `realname`, `age`, `location`, `workplace`, `political_affiliation`, `political_goal`, `education`, `profile_photo_path`) VALUES
(4, 'Sarah', 18, 'Pittsburgh', 'CMU', 'CMU', 'CMU', 'CMU', 'html/img/profile-pic.jpg'),
(5, 'Paul Watson', 30, 'Pittsburgh', 'Congress', 'Democratic', 'Make American Great Again.', 'Master of Public Administration, CMU', 'html/img/profile-pic.jpg');

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
('5jG8hpg8IznIjjOI7hPd7rM0JfCi4e7p', '1', '2019-04-25 00:44:33'),
('9PIc2pJ08TnwfL4F2TmUrBHSovsawkj6', '3', '2019-04-20 14:10:10'),
('AlOyRgj3YDs6iYaiwYyVVHZ5qM9crzvV', '5', '2019-04-23 23:20:31'),
('ddU5I2TJ5N2geyo7KDlp2Q7VTIXD0r73', '3', '2019-04-25 00:34:00'),
('fMuGkUQTkjtGzeVjodyVJ7CZ5AhQ5nFu', '5', '2019-04-25 00:03:52'),
('GTmyrMbEaVxNvoVIJNafCG5GYeeWycsI', '3', '2019-04-24 23:17:48'),
('IYHJtebdBwP89w782m0sbH4aYzUSuqVt', '5', '2019-04-24 22:24:09'),
('JGWUuo6TQK6BWoYqrMtMHQBLsWD0oA1x', '5', '2019-04-24 23:54:54'),
('pjWDQVNgbTUoPXinE8V5CMc4rXrDjvS8', '1', '2019-04-24 03:43:46'),
('Pp563d5VHpoj1niyqGjBjyfEthWiDcZK', '3', '2019-04-24 21:52:20'),
('rqEqsB034Ma9JxrlMJg4QovtaIrw1QZS', '5', '2019-04-23 21:33:34'),
('t9oPLm7n4KyqAZLJkJFwpmXSqmaffmAN', '3', '2019-04-24 23:59:11'),
('XUYDVQpEpcI0FTgikoTnvBfl5Pw9craP', '3', '2019-04-24 22:17:58');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(256) NOT NULL,
  `created_time` datetime NOT NULL,
  `last_mod_time` datetime NOT NULL,
  `location` varchar(256) NOT NULL,
  `num_answer` int(100) NOT NULL,
  `upvote` int(100) NOT NULL,
  `downvote` int(100) NOT NULL,
  `problematic` tinyint(1) NOT NULL,
  PRIMARY KEY (`questionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`questionid`, `userid`, `title`, `description`, `created_time`, `last_mod_time`, `location`, `num_answer`, `upvote`, `downvote`, `problematic`) VALUES
(1, 0, '1', '1', '2019-04-19 09:00:00', '2019-04-19 10:00:00', 'pitt', 0, 0, 0, 1),
(2, 0, '1', '1', '2019-04-19 11:45:50', '2019-04-19 11:45:50', 'Pittsburgh', 0, 0, 0, 0),
(3, 3, 'title1', 'des1', '2019-04-19 13:46:18', '2019-04-19 13:46:18', 'Pittsburgh', 2, 2, 1, 0),
(4, 3, 'title2', 'des2', '2019-04-20 09:56:01', '2019-04-20 09:56:01', 'Pittsburgh', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `reportid` int(100) NOT NULL,
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `content` varchar(256) NOT NULL,
  PRIMARY KEY (`reportid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`reportid`, `questionid`, `userid`, `content`) VALUES
(1, 3, 3, 'Violence');

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
(4, '222', '$2a$11$V9n8NRJVkhBXTFufCZpQ.uPNwuSNfZw6Y3YqgFCDYrrAC84jmFW8e', 2, 0),
(5, 'candidate', '$2a$12$qQMb1Cr6gwW9WcAk2n9XRuIS/fFPy8w7ezkJy3RorAWAsNYFfm8du', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE IF NOT EXISTS `status` (
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `status_type` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`questionid`, `userid`, `status_type`) VALUES
(3, 3, 1);

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
(3, '1234', '2019-04-08 17:53:56');

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
(3, 'pittsburgh', '123@gmail.com');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
