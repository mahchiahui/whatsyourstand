-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 21, 2019 at 03:23 PM
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
  `userid` int(100) NOT NULL,
  `admin_level` int(100) NOT NULL
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

CREATE TABLE `answer` (
  `answerid` int(100) NOT NULL,
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `content` varchar(256) NOT NULL,
  `created_time` datetime NOT NULL,
  `last_mod_time` datetime NOT NULL,
  `upvote` int(100) NOT NULL,
  `downvote` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`answerid`, `questionid`, `userid`, `content`, `created_time`, `last_mod_time`, `upvote`, `downvote`) VALUES
(1, 3, 4, 'answer1', '2019-04-20 07:00:00', '2019-04-20 08:00:00', 1, 1),
(2, 3, 5, 'answer2', '2019-04-20 09:00:00', '2019-04-20 10:00:00', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `candidate`
--

CREATE TABLE `candidate` (
  `userid` int(100) NOT NULL,
  `realname` varchar(256) NOT NULL,
  `age` int(100) NOT NULL,
  `location` varchar(256) NOT NULL,
  `workplace` varchar(256) NOT NULL,
  `political_affiliation` varchar(256) NOT NULL,
  `political_goal` varchar(256) NOT NULL,
  `education` varchar(256) NOT NULL,
  `profile_photo_path` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `candidate`
--

INSERT INTO `candidate` (`userid`, `realname`, `age`, `location`, `workplace`, `political_affiliation`, `political_goal`, `education`, `profile_photo_path`) VALUES
(4, 'Sarah', 18, 'Pittsburgh', 'CMU', 'CMU', 'CMU', 'CMU', '/sarah'),
(5, 'Paul Watson', 30, 'Pittsburgh', 'Congress', 'Democratic', 'Make American Great Again.', 'Master of Public Administration, CMU', '/paul');

-- --------------------------------------------------------

--
-- Table structure for table `cookie`
--

CREATE TABLE `cookie` (
  `cookieid` varchar(100) NOT NULL,
  `userid` varchar(100) NOT NULL,
  `time_stamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cookie`
--

INSERT INTO `cookie` (`cookieid`, `userid`, `time_stamp`) VALUES
('9PIc2pJ08TnwfL4F2TmUrBHSovsawkj6', '3', '2019-04-20 14:10:10');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
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
  `problematic` tinyint(1) NOT NULL
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

CREATE TABLE `report` (
  `reportid` int(100) NOT NULL,
  `questionid` int(100) NOT NULL,
  `userid` int(100) NOT NULL,
  `content` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rootuser`
--

CREATE TABLE `rootuser` (
  `userid` int(100) NOT NULL,
  `username` varchar(300) NOT NULL,
  `hashpwd` varchar(300) NOT NULL,
  `role` int(100) NOT NULL,
  `request_del` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rootuser`
--

INSERT INTO `rootuser` (`userid`, `username`, `hashpwd`, `role`, `request_del`) VALUES
(1, 'admin', '$2a$12$qQMb1Cr6gwW9WcAk2n9XRuIS/fFPy8w7ezkJy3RorAWAsNYFfm8du', 0, 0),
(3, 'voter', '$2a$12$LorRTCT9pjr6sHYKLCO/H.tEguKG1dd79EojicHaHLzMJzyLRqg1W', 1, 0),
(4, '222', '$2a$11$V9n8NRJVkhBXTFufCZpQ.uPNwuSNfZw6Y3YqgFCDYrrAC84jmFW8e', 2, 0),
(5, 'candidate', '$2a$11$3d.7QnbflHxoTae7tiCJxe.jXTzsLa5ER98rVk854nrF3JjRrbwhO', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
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

CREATE TABLE `test` (
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

CREATE TABLE `token` (
  `tokenid` int(100) NOT NULL,
  `token` varchar(300) NOT NULL,
  `time_stamp` datetime NOT NULL
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

CREATE TABLE `voter` (
  `userid` int(100) NOT NULL,
  `location` varchar(300) NOT NULL,
  `email` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `voter`
--

INSERT INTO `voter` (`userid`, `location`, `email`) VALUES
(3, '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`answerid`);

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
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportid`);

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
