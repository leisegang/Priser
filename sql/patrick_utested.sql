-- phpMyAdmin SQL Dump
-- version 4.1.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 03. Nov, 2014 17:20 PM
-- Server version: 5.1.73-cll
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `patrick_utested`
--
CREATE DATABASE IF NOT EXISTS `patrick_utested` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `patrick_utested`;

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `price`
--

DROP TABLE IF EXISTS `price`;
CREATE TABLE IF NOT EXISTS `price` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(3) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=39 ;

--
-- Dataark for tabell `price`
--

INSERT INTO `price` (`pid`, `price`, `uid`) VALUES
(1, 32, 1),
(2, 32, 1),
(3, 42, 1),
(4, 34, 1),
(5, 54, 1),
(6, 51, 1),
(7, 56, 1),
(8, 47, 1),
(9, 39, 1),
(10, 44, 1),
(11, 32, 2),
(12, 77, 2),
(13, 43, 2),
(14, 71, 2),
(15, 61, 2),
(16, 67, 2),
(17, 59, 2),
(18, 41, 2),
(19, 89, 2),
(20, 29, 2),
(21, 74, 1),
(22, 75, 2),
(23, 33, 3),
(24, 72, 3),
(25, 54, 4),
(26, 79, 4),
(27, 44, 5),
(28, 78, 5),
(29, 65, 6),
(30, 72, 6),
(31, 74, 1),
(32, 74, 2),
(33, 74, 3),
(34, 74, 4),
(35, 74, 5),
(36, 74, 6),
(37, 75, 4),
(38, 88, 2);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `rating`
--

DROP TABLE IF EXISTS `rating`;
CREATE TABLE IF NOT EXISTS `rating` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dataark for tabell `rating`
--

INSERT INTO `rating` (`rid`, `rating`, `uid`) VALUES
(1, 4, 1),
(2, 5, 2);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `utested`
--

DROP TABLE IF EXISTS `utested`;
CREATE TABLE IF NOT EXISTS `utested` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `url` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `picurl` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mapurl` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dataark for tabell `utested`
--

INSERT INTO `utested` (`uid`, `name`, `description`, `url`, `picurl`, `mapurl`) VALUES
(1, 'Kick', 'Yay', 'kick.no', 'http://priser.leisegang.no/bilder/kick.png', 'map.kick.no'),
(2, 'Javel', 'Musikk', 'javel.no', 'pic.javel.no', 'map.javel.no'),
(4, 'Victoria', '', '', '', ''),
(3, 'Hos Naboen', '', '', '', ''),
(5, 'Havana Pub', '', '', '', ''),
(6, 'Leopold', '', '', '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
