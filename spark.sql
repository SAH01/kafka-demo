/*
Navicat MySQL Data Transfer

Source Server         : reliable
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : spark

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2022-10-10 10:15:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for content_num
-- ----------------------------
DROP TABLE IF EXISTS `content_num`;
CREATE TABLE `content_num` (
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `num` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
