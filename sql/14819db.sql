-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 05, 2019 at 09:45 PM
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
  `admin_level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userid`, `admin_level`) VALUES
(1, 1);

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cookie`
--

CREATE TABLE `cookie` (
  `cookieid` varchar(123) NOT NULL,
  `userid` varchar(123) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rootuser`
--

CREATE TABLE `rootuser` (
  `userid` int(123) NOT NULL,
  `username` varchar(256) CHARACTER SET latin1 NOT NULL,
  `hashpwd` varchar(256) CHARACTER SET latin1 NOT NULL,
  `role` int(11) NOT NULL,
  `request_del` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rootuser`
--

INSERT INTO `rootuser` (`userid`, `username`, `hashpwd`, `role`, `request_del`) VALUES
(2, '111', '$2a$11$YL0xDMROw.vJQ6KZDuMoxO8WjS5eaia7fe2pVhXc6q/ZD8/USjkoy', 1, 0),
(1, '000', '$2a$11$VGBHcm7i.b6zjJmU66vvx.y3ycTJcUcIT8dLz.pBDOjLfvMjtGc2q', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `tokenid` int(123) NOT NULL,
  `token` varchar(256) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

CREATE TABLE `voter` (
  `userid` int(11) NOT NULL,
  `location` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `voter`
--

INSERT INTO `voter` (`userid`, `location`, `email`) VALUES
(2, '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `candidate`
--
ALTER TABLE `candidate`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `cookie`
--
ALTER TABLE `cookie`
  ADD PRIMARY KEY (`cookieid`);

--
-- Indexes for table `rootuser`
--
ALTER TABLE `rootuser`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`tokenid`);

--
-- Indexes for table `voter`
--
ALTER TABLE `voter`
  ADD PRIMARY KEY (`userid`);
