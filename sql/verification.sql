-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 05, 2019 at 09:48 PM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `verification`
--

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

CREATE TABLE `voter` (
  `voterID` int(100) NOT NULL,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `hashedPN` varchar(100) CHARACTER SET latin1 NOT NULL,
  `city` varchar(100) CHARACTER SET latin1 NOT NULL,
  `locationDocumentPath` varchar(100) CHARACTER SET latin1 NOT NULL,
  `email` varchar(100) CHARACTER SET latin1 NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `voter`
--

INSERT INTO `voter` (`voterID`, `name`, `hashedPN`, `city`, `locationDocumentPath`, `email`) VALUES
(1, 'Chia Hui Mah', '4129036789', 'Pittsburgh', 'C:\\Users\\mahch\\Desktop\\whatsyourstand\\app\\target\\app-1.0-SNAPSHOT\\data\\ChiaHuiMahDoc.pdf', 'mahchiahui@hotmail.sg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `voter`
--
ALTER TABLE `voter`
  ADD PRIMARY KEY (`voterID`);
