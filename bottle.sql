-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 11, 2017 at 01:10 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+07:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bottle`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci	 DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `description`, `photoUrl`) VALUES
(1, 'Love', 'Some description about love category.', 'http://az616578.vo.msecnd.net/files/2016/07/24/6360493048587078022008349599_love-01.jpg'),
(2, 'Hate', 'Some description about hate category.', NULL),
(3, 'Happy', 'Some description about happy category.', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `geo_message`
--

CREATE TABLE `geo_message` (
  `id` int(11) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `addressName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `emotion` int(11) NOT NULL DEFAULT '0',
  `detailId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `geo_message`
--

INSERT INTO `geo_message` (`id`, `longitude`, `latitude`, `addressName`, `emotion`, `detailId`, `userId`) VALUES
(34, 108.16721253097056, 16.04386888216254, NULL, 3, 82, 'SylPMAdxMOT39xCAl0fWfmoBllV2'),
(35, 108.18552564829587, 16.062786002645133, NULL, 2, 83, 'uevDn10DswM057WIhhn0pKOwuA22');

-- --------------------------------------------------------

--
-- Table structure for table `message_detail`
--

CREATE TABLE `message_detail` (
  `id` int(11) NOT NULL,
  `text` varchar(4000) CHARACTER SET utf8 NOT NULL,
  `mediaUrl` varchar(1000) DEFAULT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'text',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `message_detail`
--

INSERT INTO `message_detail` (`id`, `text`, `mediaUrl`, `type`, `timestamp`) VALUES
(82, 'what the hell', NULL, 'text', '2017-05-09 17:09:02'),
(83, 'i miss you my girl', NULL, 'text', '2017-05-10 08:00:31'),
(88, 'xcczxx', '20170511171410-kan4qgkqt8iee8mdjp6b0l6fcc-0000000000000-jpg', 'photo', '2017-05-11 10:14:10');

-- --------------------------------------------------------

--
-- Table structure for table `public_profile`
--

CREATE TABLE `public_profile` (
  `id` varchar(255) NOT NULL,
  `displayName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `public_profile`
--

INSERT INTO `public_profile` (`id`, `displayName`, `avatarUrl`) VALUES
('i\'m your boss', 'Trần Xuân Sơn', 'https://scontent.fdad3-1.fna.fbcdn.net/v/t1.0-9/536012_153138571496960_213017345_n.jpg?oh=4c9ddadd76fbef40ea1f4cecf32e975c&oe=595BD2A1'),
('SylPMAdxMOT39xCAl0fWfmoBllV2', 'Trần Xuân Sơn', 'https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/536012_153138571496960_213017345_n.jpg?oh=b9c4df5db8be814a350b4f0346b1615e&oe=5929B2A8'),
('uevDn10DswM057WIhhn0pKOwuA22', 'Nô Em', 'https://scontent.xx.fbcdn.net/v/t1.0-1/s100x100/17424833_1110533652425703_5268199772934492342_n.jpg?oh=8a792d2a7ba364f75fb4c83382368e0a&oe=59580B4B'),
('vFBmLL64eQQjA4YIeaLBHkzdGHP2', 'Easygoing Jun', 'https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/15873093_1059188877543891_5524787132159939494_n.jpg?oh=a3d2b4a4cdccf2801f1e66617f728c05&oe=592A9ABB');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  `categoryId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `name`, `description`, `photoUrl`, `categoryId`) VALUES
(1, 'Da nang lover', 'Some description about that room.', 'http://monthoflove.ca/wp-content/files_mf/cache/th_6aac64e860d10b175b5ffba12b15e54f_210.png', 1),
(3, 'DUT FA-er group', 'We are FA-er, we are DUT-er and we are one.', NULL, 2),
(4, 'another one', 'dont care this room', 'http://icons.iconarchive.com/icons/miniartx/heart-valentines-day/128/heart-love-you-icon.png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `room_message`
--

CREATE TABLE `room_message` (
  `id` int(11) NOT NULL,
  `roomId` int(11) NOT NULL,
  `detailId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `room_message`
--

INSERT INTO `room_message` (`id`, `roomId`, `detailId`, `userId`) VALUES
(5, 1, 88, 'uevDn10DswM057WIhhn0pKOwuA22');

-- --------------------------------------------------------

--
-- Table structure for table `user_setting`
--

CREATE TABLE `user_setting` (
  `id` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `currentRoomId` int(11) DEFAULT NULL,
  `currentLatitude` double NOT NULL DEFAULT '16.054237',
  `currentLongitude` double NOT NULL DEFAULT '108.214714',
  `messageId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_setting`
--

INSERT INTO `user_setting` (`id`, `userId`, `currentRoomId`, `currentLatitude`, `currentLongitude`, `messageId`) VALUES
(1, 'i\'m your boss', 1, 16.048705, 108.17559, NULL),
(2, 'vFBmLL64eQQjA4YIeaLBHkzdGHP2', 1, 16.069466108188305, 108.17940786480904, NULL),
(3, 'uevDn10DswM057WIhhn0pKOwuA22', 1, 16.051218844166375, 108.18059775978327, 35),
(4, 'SylPMAdxMOT39xCAl0fWfmoBllV2', 1, 16.05598549315926, 108.18282198160887, 34);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `geo_message`
--
ALTER TABLE `geo_message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `detailId` (`detailId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `message_detail`
--
ALTER TABLE `message_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type`);

--
-- Indexes for table `public_profile`
--
ALTER TABLE `public_profile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indexes for table `room_message`
--
ALTER TABLE `room_message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `roomI` (`roomId`),
  ADD KEY `detailId` (`detailId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `user_setting`
--
ALTER TABLE `user_setting`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `messageId` (`messageId`),
  ADD KEY `currentRoomId` (`currentRoomId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `geo_message`
--
ALTER TABLE `geo_message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `message_detail`
--
ALTER TABLE `message_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `room_message`
--
ALTER TABLE `room_message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user_setting`
--
ALTER TABLE `user_setting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `geo_message`
--
ALTER TABLE `geo_message`
  ADD CONSTRAINT `geo_message_ibfk_1` FOREIGN KEY (`detailId`) REFERENCES `message_detail` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `geo_message_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `public_profile` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `room_message`
--
ALTER TABLE `room_message`
  ADD CONSTRAINT `room_message_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `room_message_ibfk_2` FOREIGN KEY (`detailId`) REFERENCES `message_detail` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `room_message_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `public_profile` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_setting`
--
ALTER TABLE `user_setting`
  ADD CONSTRAINT `user_setting_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `public_profile` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_setting_ibfk_2` FOREIGN KEY (`messageId`) REFERENCES `geo_message` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `user_setting_ibfk_3` FOREIGN KEY (`currentRoomId`) REFERENCES `room` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
