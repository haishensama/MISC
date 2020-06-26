/*
Navicat MySQL Data Transfer

Source Server         : localhost_3307
Source Server Version : 50140
Source Host           : localhost:3307
Source Database       : mysql-mms

Target Server Type    : MYSQL
Target Server Version : 50140
File Encoding         : 65001

Date: 2019-09-28 14:23:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_arrange`
-- ----------------------------
DROP TABLE IF EXISTS `tb_arrange`;
CREATE TABLE `tb_arrange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_time` datetime DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `hall_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dfh1vm934fumtet70iwayi1t8` (`movie_id`),
  KEY `FK_gxwccfdgjm8t5qbv44suv6faj` (`hall_id`),
  CONSTRAINT `FK_dfh1vm934fumtet70iwayi1t8` FOREIGN KEY (`movie_id`) REFERENCES `tb_movie` (`id`),
  CONSTRAINT `FK_gxwccfdgjm8t5qbv44suv6faj` FOREIGN KEY (`hall_id`) REFERENCES `tb_hall` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_arrange
-- ----------------------------
INSERT INTO `tb_arrange` VALUES ('12', '2019-09-28 14:00:00', '100', '38', '2019-09-28 12:00:00', '1', '18');
INSERT INTO `tb_arrange` VALUES ('13', '2019-09-28 14:20:00', '90', '38', '2019-09-28 12:00:00', '2', '18');
INSERT INTO `tb_arrange` VALUES ('14', '2019-09-28 16:20:00', '90', '38', '2019-09-28 14:00:00', '2', '19');
INSERT INTO `tb_arrange` VALUES ('15', '2019-09-28 16:20:00', '100', '38', '2019-09-28 14:00:00', '1', '19');

-- ----------------------------
-- Table structure for `tb_hall`
-- ----------------------------
DROP TABLE IF EXISTS `tb_hall`;
CREATE TABLE `tb_hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `col` int(11) DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_hall
-- ----------------------------
INSERT INTO `tb_hall` VALUES ('1', '10', '', '100', '10');
INSERT INTO `tb_hall` VALUES ('2', '9', '', '90', '10');

-- ----------------------------
-- Table structure for `tb_movie`
-- ----------------------------
DROP TABLE IF EXISTS `tb_movie`;
CREATE TABLE `tb_movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `plot` varchar(255) DEFAULT NULL,
  `premiere` datetime DEFAULT NULL,
  `score` float DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_movie
-- ----------------------------
INSERT INTO `tb_movie` VALUES ('18', '黄渤，杜江，张译，葛优', '陈凯歌', '我和我的祖国', '七位导演分别取材新中国成立70周年以来，祖国经历的无数个历史性经典瞬间。讲述普通人与国家之间息息相关密不可分的动人故事。聚焦大时代大事件下，小人物和国家之间，看似遥远实则密切的关联，唤醒全球华人共同回忆。', '2019-09-28 12:00:00', '9.8', '/movie-imgs/1.jpg');
INSERT INTO `tb_movie` VALUES ('19', '张涵予，欧豪', '刘伟强', '中国机长', '据2018年5月14日四川航空3U8633航班机组成功处置特情真实事件改编。机组执行航班任务时，在万米高空突遇驾驶舱风挡玻璃爆裂脱落、座舱释压的极端罕见险情，生死关头，他们临危不乱、果断应对、正确处置，确保了机上全部人员的生命安全，创造了世界民航史上的奇迹。', '2019-09-28 14:00:00', '9.5', '/movie-imgs/2.jpg');
INSERT INTO `tb_movie` VALUES ('20', '吴京，张译', '李仁港', '攀登者', '1960年，中国登山队向珠峰发起冲刺，完成了世界首次北坡登顶这一不可能的任务。15 年后，方五洲（吴京 饰）和曲松林（张译 饰）在气象学家徐缨（章子怡 饰）的帮助下，带领李国梁（井柏然 饰）、杨光（胡歌 饰）等年轻队员再次挑战世界之巅。迎接他们的将是更加严酷的现实，也是生与死的挑战', '2019-09-28 16:00:00', '9', '/movie-imgs/3.jpg');
INSERT INTO `tb_movie` VALUES ('21', '肖战，李沁', '程小东', '诛仙Ⅰ', '草庙村被屠，少年张小凡（肖战 饰）双亲离世，被青云门大竹峰收留。机缘巧合之下，他习得佛门天音功法，又意外获得魔教法器烧火棍，踏上强者之路的同时，也让他陷入了巨大的危机。至魔法器的现世，与陆雪琪（李沁 饰）、碧瑶（孟美岐 饰）、田灵儿（唐艺昕 饰）三个女生间命运的交错，都让他原本单纯的人生轨迹充满变数。一个勇者驳斥命运的传奇之旅就此展开', '2019-09-28 20:00:00', '7', '/movie-imgs/4.jpg');

-- ----------------------------
-- Table structure for `tb_sale`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sale`;
CREATE TABLE `tb_sale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seat` int(11) DEFAULT NULL,
  `arrange_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5w4f4680w30v12ab9vty2nnuk` (`arrange_id`),
  KEY `FK_dlda5cn5411i45379b5oo5vo3` (`user_id`),
  CONSTRAINT `FK_5w4f4680w30v12ab9vty2nnuk` FOREIGN KEY (`arrange_id`) REFERENCES `tb_arrange` (`id`),
  CONSTRAINT `FK_dlda5cn5411i45379b5oo5vo3` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sale
-- ----------------------------
INSERT INTO `tb_sale` VALUES ('21', '98', '12', '7');
INSERT INTO `tb_sale` VALUES ('22', '49', '14', '7');
INSERT INTO `tb_sale` VALUES ('23', '50', '14', '7');
INSERT INTO `tb_sale` VALUES ('24', '-10', '14', '7');
INSERT INTO `tb_sale` VALUES ('25', '-1', '14', '7');
INSERT INTO `tb_sale` VALUES ('26', '70', '14', '7');
INSERT INTO `tb_sale` VALUES ('27', '79', '14', '7');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('3', '2017-04-11 10:14:07', 'asd', '', '21232f297a57a5a743894a0e4a801fc3', 'admin', '123', 'admin');
INSERT INTO `tb_user` VALUES ('7', '2019-09-28 14:10:00', 'cj@qq.com', '', '28198b369067e88dab9fefe85484dbf4', 'vip', '15823451423', 'cj');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');
