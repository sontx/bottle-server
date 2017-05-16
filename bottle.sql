-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 14, 2017 at 10:38 AM
-- Server version: 5.5.52-0+deb8u1
-- PHP Version: 5.6.27-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bottle`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `description`, `photoUrl`) VALUES
(1, 'Love', 'Love makes us change.', 'http://az616578.vo.msecnd.net/files/2016/07/24/6360493048587078022008349599_love-01.jpg'),
(2, 'Family', 'Family is the place of return.', 'http://prosperityedwell.com/wp-content/uploads/2016/06/familypic.jpg'),
(3, 'Sad', 'Everything will be fine.', 'https://i.ytimg.com/vi/aWIE0PX1uXk/maxresdefault.jpg'),
(4, 'Travel', 'Wake up in another part of the world.', 'http://api.theweek.com/sites/default/files/styles/tw_image_9_4/public/42-68254765.jpg?itok=3jb4Uvus&resize=1260x560'),
(5, 'Dissipated', 'We are younggggggg', 'http://ladysmithgazette.co.za/wp-content/uploads/sites/64/2016/12/bar-1.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `geo_message`
--

CREATE TABLE IF NOT EXISTS `geo_message` (
`id` int(11) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `addressName` varchar(100) DEFAULT NULL,
  `emotion` int(11) NOT NULL DEFAULT '0',
  `detailId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

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

CREATE TABLE IF NOT EXISTS `message_detail` (
`id` int(11) NOT NULL,
  `text` varchar(4000) NOT NULL,
  `mediaUrl` varchar(1000) DEFAULT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'text',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `message_detail`
--

INSERT INTO `message_detail` (`id`, `text`, `mediaUrl`, `type`, `timestamp`) VALUES
(82, 'what the hell', NULL, 'text', '2017-05-09 10:09:02'),
(83, 'i miss you my girl', NULL, 'text', '2017-05-10 01:00:31');

-- --------------------------------------------------------

--
-- Table structure for table `public_profile`
--

CREATE TABLE IF NOT EXISTS `public_profile` (
  `id` varchar(255) NOT NULL,
  `displayName` varchar(50) NOT NULL,
  `avatarUrl` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `public_profile`
--

INSERT INTO `public_profile` (`id`, `displayName`, `avatarUrl`) VALUES
('i''m your boss', 'Trần Xuân Sơn', 'https://scontent.fdad3-1.fna.fbcdn.net/v/t1.0-9/536012_153138571496960_213017345_n.jpg?oh=4c9ddadd76fbef40ea1f4cecf32e975c&oe=595BD2A1'),
('SylPMAdxMOT39xCAl0fWfmoBllV2', 'Trần Xuân Sơn', 'https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/536012_153138571496960_213017345_n.jpg?oh=b9c4df5db8be814a350b4f0346b1615e&oe=5929B2A8'),
('uevDn10DswM057WIhhn0pKOwuA22', 'Nô Em', 'https://scontent.xx.fbcdn.net/v/t1.0-1/s100x100/17424833_1110533652425703_5268199772934492342_n.jpg?oh=8a792d2a7ba364f75fb4c83382368e0a&oe=59580B4B'),
('vFBmLL64eQQjA4YIeaLBHkzdGHP2', 'Easygoing Jun', 'https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/15873093_1059188877543891_5524787132159939494_n.jpg?oh=a3d2b4a4cdccf2801f1e66617f728c05&oe=592A9ABB');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE IF NOT EXISTS `room` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  `categoryId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `name`, `description`, `photoUrl`, `categoryId`) VALUES
(1, 'We are FA-er', 'We don''t need love, we just need free.', 'http://orig03.deviantart.net/a7ce/f/2010/239/d/f/forever_alone_by_foreveraloneplz.png', 1),
(3, 'Love story', 'Have a love story and want to share even it''s sad!', 'http://52lovestories.com/wp-content/uploads/2011/03/croppedheart.jpg', 1),
(4, 'Emotional counselor', 'Tell us what is your trouble!', 'http://mitrafathi.com/wp-content/uploads/benefits-of-Emotional-Self-awareness.1-300x232.jpg', 1),
(7, 'Just broke up', 'Don''t be sad, they don''t deserve you.', 'http://www.scriptmag.com/wp-content/uploads/love-broken-up-heart1.jpg', 1),
(9, 'Show off your lover', 'You are proud of your lover.', 'https://s-media-cache-ak0.pinimg.com/736x/e2/21/ef/e221ef1647087fd721797d631e94cae2.jpg', 1),
(10, 'Family story', 'Have a sad story about your family?', 'https://thumbs.dreamstime.com/t/portrait-sad-family-three-nature-62086066.jpg', 2),
(11, 'Family''s photos', 'Moments of the family.', 'http://www.latina.com/sites/default/files/Screen-shot-2015-03-27-at-1.26.34-PM.jpg', 2),
(12, 'Mother-in-law', 'Humm, your mother-in-law blames you?', 'http://www.scarymommy.com/wp-content/uploads/2014/07/mother-in-law.jpg', 2),
(13, 'Trash-bin', 'We are listening your trouble...', 'http://ph-live-01.slatic.net/p/3/otto-trash-bin-with-wheels-240-liters-7391-680493-1-catalog_233.jpg', 3),
(14, 'Stress for work', 'Put the work aside and go for beer.', 'https://adrenalfatiguesolution.com/wp-content/uploads/2014/03/job-stress.jpg', 3),
(15, 'Stress for love', 'I know your feel bro.', 'https://wholisticfitliving.com/wp-content/uploads/2011/09/Unhappy-Couple.jpg', 3),
(16, 'Travel abroad', 'Wake up in a foreign country.', 'https://www.uhs.umich.edu/files/uhs/styles/large/public/field/image/TrvHealthInsuWeb.jpg?itok=EOGI4r2y', 4),
(17, 'Domestic tourism', 'See the snow in the north and swim in the south', 'http://media.crossingtravel.com/files/tag/2015/09/27/vietnam-travel-destinations-3072.jpg', 4),
(18, 'Food', 'Art of eating', 'http://static.ielts-fighter.com/uploads/2016/06/27/picture/d05f8fce86fbb14d6eb491c812b0ca1c_88762487-junk-food.jpg', 4),
(19, 'Go with me!', 'You have a travel plan and you wanna go with a strange person!', 'http://img2.10bestmedia.com/Images/Photos/300954/Seaquarium-beach_59_330x220.jpg', 4),
(20, 'Northern', 'Traditional', 'http://cms.hallovietnam.vn:8080/data_files/regions_files/image/201506/20/h1-3-_tricc.jpg', 5),
(21, 'Central', 'Mixed', 'https://media-cdn.tripadvisor.com/media/photo-s/06/ea/ba/32/da-nang.jpg', 5),
(22, 'South', 'modern', 'https://cdn3.ivivu.com/2014/10/du-lich-sai-gon-cam-nang-tu-a-den-z-iVIVU.com-16-370x215.jpg', 5);

-- --------------------------------------------------------

--
-- Table structure for table `room_message`
--

CREATE TABLE IF NOT EXISTS `room_message` (
`id` int(11) NOT NULL,
  `roomId` int(11) NOT NULL,
  `detailId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_setting`
--

CREATE TABLE IF NOT EXISTS `user_setting` (
`id` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `currentRoomId` int(11) DEFAULT NULL,
  `currentLatitude` double NOT NULL DEFAULT '16.054237',
  `currentLongitude` double NOT NULL DEFAULT '108.214714',
  `messageId` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_setting`
--

INSERT INTO `user_setting` (`id`, `userId`, `currentRoomId`, `currentLatitude`, `currentLongitude`, `messageId`) VALUES
(1, 'i''m your boss', 1, 16.048705, 108.17559, NULL),
(2, 'vFBmLL64eQQjA4YIeaLBHkzdGHP2', 19, 16.061164108516074, 108.18076103925705, NULL),
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
 ADD PRIMARY KEY (`id`), ADD KEY `detailId` (`detailId`), ADD KEY `userId` (`userId`);

--
-- Indexes for table `message_detail`
--
ALTER TABLE `message_detail`
 ADD PRIMARY KEY (`id`), ADD KEY `type` (`type`);

--
-- Indexes for table `public_profile`
--
ALTER TABLE `public_profile`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
 ADD PRIMARY KEY (`id`), ADD KEY `categoryId` (`categoryId`);

--
-- Indexes for table `room_message`
--
ALTER TABLE `room_message`
 ADD PRIMARY KEY (`id`), ADD KEY `roomI` (`roomId`), ADD KEY `detailId` (`detailId`), ADD KEY `userId` (`userId`);

--
-- Indexes for table `user_setting`
--
ALTER TABLE `user_setting`
 ADD PRIMARY KEY (`id`), ADD KEY `userId` (`userId`), ADD KEY `messageId` (`messageId`), ADD KEY `currentRoomId` (`currentRoomId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `geo_message`
--
ALTER TABLE `geo_message`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `message_detail`
--
ALTER TABLE `message_detail`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=87;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `room_message`
--
ALTER TABLE `room_message`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_setting`
--
ALTER TABLE `user_setting`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
