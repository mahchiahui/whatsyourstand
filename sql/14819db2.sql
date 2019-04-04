-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 04, 2019 at 03:28 AM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `14819db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `userid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `candidate`
--

CREATE TABLE `candidate` (
  `userid` int(11) NOT NULL,
  `realname` varchar(65) NOT NULL,
  `age` int(11) NOT NULL,
  `location` varchar(65) NOT NULL,
  `workplace` varchar(65) NOT NULL,
  `political_affiliation` varchar(65) NOT NULL,
  `political_goal` varchar(256) NOT NULL,
  `education` varchar(65) NOT NULL,
  `profile_photo_path` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cookie`
--

CREATE TABLE `cookie` (
  `cookieid` varchar(123) NOT NULL,
  `userid` varchar(123) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cookie`
--

INSERT INTO `cookie` (`cookieid`, `userid`, `timestamp`) VALUES
('67a01ed83fhdlal09pvft9m4ea', '3', '2019-04-03 23:10:29'),
('7878245skggb6k066eqok3f4ci', '3', '2019-04-03 22:49:27'),
('ef3o1abqnpbn0b9nmnfnf61n1b', '5', '2019-03-31 01:55:29'),
('fsltsl6c0k6nb8vjdd0vd3opsr', '5', '2019-03-31 01:57:18'),
('klrj38ts79be1bb96b02d54o8q', '5', '2019-04-03 23:08:15'),
('o8qmqukg1vllc0iferp7pibu1s', '3', '2019-04-03 22:01:26'),
('uitm76hu5t11m55p5v2sjkshf0', '3', '2019-04-03 22:46:29');

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `tokenid` int(123) NOT NULL,
  `token` varchar(256) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`tokenid`, `token`, `timestamp`) VALUES
(1, 'token1', '2019-03-29 10:15:17');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userid` int(123) NOT NULL,
  `name` varchar(256) NOT NULL,
  `hashpwd` varchar(256) NOT NULL,
  `role` int(11) NOT NULL,
  `request_del` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `name`, `hashpwd`, `role`, `request_del`) VALUES
(3, '111', '$2a$11$FtamEgXLyINfXR69Z.M9DOQ.BRuAjIFJlbczu8dWn72AmlAZdXjHm', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

CREATE TABLE `voter` (
  `userid` int(11) NOT NULL,
  `email` varchar(65) NOT NULL,
  `location` varchar(65) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cookie`
--
ALTER TABLE `cookie`
  ADD PRIMARY KEY (`cookieid`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`tokenid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);
