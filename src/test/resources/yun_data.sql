/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : yun

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-31 17:45:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for yun_data
-- ----------------------------
DROP TABLE IF EXISTS `yun_data`;
CREATE TABLE `yun_data` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `share_id` bigint(20) DEFAULT NULL,
  `data_id` bigint(20) DEFAULT NULL,
  `share_name` varchar(255) DEFAULT NULL,
  `uk` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `share_time` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yun_data
-- ----------------------------
INSERT INTO `yun_data` VALUES ('1', '4065999921', '1640251637113738394', 'Windows10官方激活密钥.docx', '3914156804', '', '2016-10-31 17:39:32', '2016-08-29 20:42:34', 'http://himg.bdimg.com/sys/portrait/item/05e9db49.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('2', '158806', '2138587363609856768', '国家开发银行辽宁分行2012校园招聘大礼包_备战国家开发银行辽宁分行2012校园招聘_大街网.pdf', '20249593', '', '2016-10-31 17:40:43', '2013-07-15 16:00:05', 'http://himg.bdimg.com/sys/portrait/item/b5012630.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('3', '2527806257', '4729438820713358273', '绿软分享吧.url', '3809476486', '', '2016-10-31 17:40:56', '2015-06-10 21:31:00', 'http://himg.bdimg.com/sys/portrait/item/b1e3590b.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('4', '478793', '5415104561327005310', '[制胜新托福阅读].罗杰斯.扫描版.part6.rar', '1060600830', '', '2016-10-31 17:41:07', '2013-06-14 17:41:17', 'http://himg.bdimg.com/sys/portrait/item/353f2133.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('5', '782903879', '305424504105489511', '(单词及课文) 课本录音(外研必修3).rar', '3691288441', '', '2016-10-31 17:41:41', '2015-02-13 09:04:47', 'http://himg.bdimg.com/sys/portrait/item/d9dca600.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('6', '3558837818', '416721368484144523', '希威社-惜惜.rar', '2086873', '', '2016-10-31 17:41:46', '2015-05-25 14:40:30', 'http://himg.bdimg.com/sys/portrait/item/9900061b.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('7', '2416120425', '1552885457701750626', '【DO NOT FORGET SUNI, EXO】130817 EXO 汝矣岛签售会 XIUMIN FOCUS(1).mp4', '657485367', '', '2016-10-31 17:41:59', '2013-08-24 16:51:13', 'http://himg.bdimg.com/sys/portrait/item/2027e834.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('8', '2503196121', '4753236469846786433', '美国信用卡生成器V1.1(支持32位系统 国外很多网站申请用得上).zip', '2986611120', '', '2016-10-31 17:42:20', '2016-01-22 13:52:45', 'http://himg.bdimg.com/sys/portrait/item/5fb26f00.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('9', '3328645650', '4152970117790793732', '斩龙.txt', '1980714964', '', '2016-10-31 17:42:38', '2015-01-18 19:46:02', 'http://himg.bdimg.com/sys/portrait/item/01760b0b.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('10', '3917055574', '1374410889493145224', '[LAMP兄弟连李明老师讲Linux].22_第7讲.Linux用户管理（四）.wmv', '1762131113', '', '2016-10-31 17:42:51', '2013-08-23 09:13:55', 'http://himg.bdimg.com/sys/portrait/item/b2697603.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('11', '2213070330', '7960681339427167371', '名侦探柯南(全集)_20130702233848.torrent', '436751272', '', '2016-10-31 17:42:58', '2013-07-02 23:39:38', 'http://himg.bdimg.com/sys/portrait/item/051a770c.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('12', '3218468928', '6912857311483428955', '[www.xskw.net 新时空家园] 张学友 - 聆听经典二(2009)[FLAC]{DTS-ES}.rar', '4080012339', '', '2016-10-31 17:43:07', '2014-01-14 23:03:23', 'http://himg.bdimg.com/sys/portrait/item/42f3ec34.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('13', '1401759544', '707073359667569911', '赵氏孤儿_12893272.uvz', '4147176078', '', '2016-10-31 17:43:18', '2013-07-05 09:03:33', 'http://himg.bdimg.com/sys/portrait/item/acf75134.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('14', '3070681821', '3719602261355548962', 'websocket-demo.rar', '87353774', '', '2016-10-31 17:43:43', '2015-09-12 09:57:07', 'http://himg.bdimg.com/sys/portrait/item/a7057130.jpg', '1', '0');
INSERT INTO `yun_data` VALUES ('15', '157337', '8723873430743558812', '酒红色高贵全系列PPT模板@PPT精选.ppt', '4248058874', '', '2016-10-31 17:44:15', '2013-07-15 15:58:28', 'http://himg.bdimg.com/sys/portrait/item/75fd2530.jpg', '1', '0');

-- ----------------------------
-- Table structure for yun_user
-- ----------------------------
DROP TABLE IF EXISTS `yun_user`;
CREATE TABLE `yun_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `uk` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `follow_count` int(11) DEFAULT NULL,
  `fans_count` int(11) DEFAULT NULL,
  `pubshare_count` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `flag` tinyint(1) DEFAULT NULL,
  `follow_crawled` tinyint(1) DEFAULT NULL,
  `fans_crawled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yun_user
-- ----------------------------
INSERT INTO `yun_user` VALUES ('1', 'wj_****2010', '2970607962', '2016-10-31 15:24:41', 'http://himg.bdimg.com/sys/portrait/item/afb1850b.jpg', '2', '7', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('2', '谷歌***注册', '3039026190', '2016-10-31 15:24:41', 'http://himg.bdimg.com/sys/portrait/item/92b5d127.jpg', '0', '968', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('3', '夏洛***超清', '74586267', '2016-10-31 15:24:44', 'http://himg.bdimg.com/sys/portrait/item/56044476.jpg', '65', '6751', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('4', '51****影论坛', '1820858747', '2016-10-31 15:24:44', 'http://himg.bdimg.com/sys/portrait/item/576ca48c.jpg', '2', '21387', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('5', '峰**路飞', '473071427', '2016-10-31 15:24:44', 'http://himg.bdimg.com/sys/portrait/item/311c9c36.jpg', '29', '3872', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('6', '拉**盖饭', '436751272', '2016-10-31 15:24:44', 'http://himg.bdimg.com/sys/portrait/item/051a770c.jpg', '300', '49932', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('7', '文**哥i', '576295296', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/df225f5d.jpg', '0', '1080', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('8', '影视***小生', '2910640242', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/96adad78.jpg', '6', '8367', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('9', 'yj****fyo', '2237748362', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/1a855565.jpg', '23', '17', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('10', '南**94', '3009018991', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/b2b3b05d.jpg', '119', '22', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('11', 'V**柠檬', '913566194', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/a7362d77.jpg', '95', '17', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('12', '月光**99', '3946715181', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/56ebf23a.jpg', '4', '17', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('13', '小*小宝', '3090019870', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/b8b8c129.jpg', '77', '14', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('14', '贝贝**m3', '2016536098', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/a878fd35.jpg', '28', '13', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('15', 'WS**XX', '878918845', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/72346267.jpg', '94', '14', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('16', '霸霸***哪里', '4148451404', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/16f79340.jpg', '24', '13', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('17', 'no***ing', '371706205', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/87168223.jpg', '28', '14', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('18', 'Oo***莫生', '3139823178', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/a8bb9521.jpg', '62', '12', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('19', '****', '1413178968', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/2854873f.jpg', '6', '9', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('20', '愤怒***519', '2187496868', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/c3827b66.jpg', '11', '9', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('21', '黔*幽女', '2458113411', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/9b925c87.jpg', '1', '12', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('22', 'k*猎', '3262560450', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/f6c21d72.jpg', '106', '53', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('23', 'Co****e搁浅', '3612093117', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/64d76248.jpg', '7', '8', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('24', 'lov****0131', '1478244183', '2016-10-31 15:24:45', 'http://himg.bdimg.com/sys/portrait/item/79588818.jpg', '51', '6', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('25', 'B**影视', '2838601171', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/d3a90c35.jpg', '11', '183506', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('26', '无法***用户', '1613361073', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/a1606e2d.jpg', '1', '66737', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('27', '追*大咖', '3375467638', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/c2c9a935.jpg', '47', '1021827', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('28', '美**中营', '4080204509', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/b4f30236.jpg', '3', '1407729', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('29', '动**舰店', '3375832228', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/52c97b33.jpg', '36', '1430172', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('30', 'ZE***漫网', '4281407862', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/57ffa935.jpg', '4', '1334517', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('31', '火影***OL', '2050437676', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/7c7af333.jpg', '303', '128067', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('32', '日**来了', '3291077', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/79001a36.jpg', '18', '1041144', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('33', 'S**RB', '2147543332', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/a780fb04.jpg', '0', '94113', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('34', 'JI**_Y', '3458316195', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/f9ce7c25.jpg', '6', '263', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('35', '天天***69', '4096827176', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/d1f4f734.jpg', '299', '51247', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('36', 'Des****0328', '2469157423', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/1493f028.jpg', '299', '40046', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('37', 'zhan*****8900', '3643760757', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/2ed9aa2b.jpg', '299', '38379', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('38', '51**np', '53561478', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/06035935.jpg', '299', '15072', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('39', '水中**倒影', '3189004793', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/17be2610.jpg', '311', '39376', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('40', 'Xiu****UM4', '657485367', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/2027e834.jpg', '299', '14891', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('41', 'nin****hong', '1828982259', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/436d2c00.jpg', '300', '22251', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('42', '新**家园', '4080012339', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/42f3ec34.jpg', '299', '31854', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('43', 'qq***937', '3291415467', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/4dc4742b.jpg', '299', '11388', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('44', '葬*然', '3037190414', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/97b5d103.jpg', '4', '6360', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('45', '卑微***光纪', '1293993914', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/894d6524.jpg', '200', '91953', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('46', 'ev**an', '4077152221', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/29f30200.jpg', '300', '31547', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('47', 'mj**01', '4147176078', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/acf75134.jpg', '300', '47277', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('48', '电子**图书', '740332826', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/db2cc524.jpg', '299', '50284', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('49', '菜*IN', '908233900', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/c2367326.jpg', '0', '103575', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('50', 'me9****6793', '1025710192', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/563daf27.jpg', '46', '27931', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('51', '紫*南雨', '708249947', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/472a8433.jpg', '181', '91572', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('52', 'O上**嫣O', '3962892461', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/beec7230.jpg', '299', '8812', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('53', '星**道路', '906082835', '2016-10-31 15:24:49', 'http://himg.bdimg.com/sys/portrait/item/f436cc05.jpg', '272', '10826', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('54', '248****389', '271657707', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/64103435.jpg', '299', '4764', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('55', '名字***细胞', '254831542', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/250f6934.jpg', '299', '40778', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('56', '〆﹏**而安', '3792872147', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/ece20c16.jpg', '302', '51443', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('57', 'car****oppy', '339045189', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/25149a31.jpg', '299', '5754', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('58', 'wei****ina', '1762131113', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/b2697603.jpg', '300', '107521', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('59', 'ra****uis', '53863755', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/ab039431.jpg', '299', '4422', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('60', '历练***翔2', '2301478148', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/8b89db29.jpg', '349', '36816', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('61', 'edi****llie', '2687885695', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/afa0a031.jpg', '299', '5452', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('62', 'wl***ly', '3691288441', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/d9dca600.jpg', '299', '63328', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('63', '考*小子', '1060600830', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/353f2133.jpg', '299', '17768', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('64', 'jes*****uis6', '2771763071', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/f1a5a031.jpg', '299', '4144', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('65', '风神***23', '739395834', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/062c2516.jpg', '299', '7367', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('66', 'IA****ter', '137172738', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/5908dd29.jpg', '303', '8732', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('67', 'qq4****6262', '137306991', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/6d08b02b.jpg', '299', '1362526', null, '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('68', '漫*萌梦', '3811547187', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/d6e3ec2b.jpg', '299', '5061', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('69', 'NBA*****over', '4163581050', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/7af8a52f.jpg', '210', '9019', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('70', '高*虎', '2718733743', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/dba27008.jpg', '299', '37275', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('71', '光棍***人10', '4062736046', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/20f2712c.jpg', '299', '3268', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('72', 'ky****006', '4228292336', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/d0fc2f02.jpg', '356', '12677', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('73', 'huo*****8128', '2097313421', '2016-10-31 15:24:53', 'http://himg.bdimg.com/sys/portrait/item/387d5206.jpg', '305', '4192', null, '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('74', 'Ran****tep', '3827062481', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/18e40e18.jpg', '16', '4579', '149', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('75', 'Not****ada', '3137955895', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/26bbe80d.jpg', '5', '0', '10', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('76', '62****999', '973536426', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/b23a7502.jpg', '303', '19482', '11', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('77', '锴钰', '1041079305', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/d23ed609.jpg', '287', '17810', '21', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('78', '天*落', '1260264262', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/554b991a.jpg', '278', '47497', '504', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('79', '海*os', '3037222610', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/18b50d0c.jpg', '464', '18863', '48', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('80', '恋スル****OID', '2367035611', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/568d0412.jpg', '299', '5620', '240', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('81', '180*****459', '1300587334', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/2d4d9981.jpg', '75', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('82', 'an****ite', '402766185', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/f718b605.jpg', '300', '9602', '63', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('83', '木*里盼', '374523880', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/89163756.jpg', '484', '1', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('84', '牵*梁少', '1712449518', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/a9663115.jpg', '469', '39', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('85', 'q*nc', '2819032915', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/49a88c03.jpg', '308', '4512', '8', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('86', '大**百劫', '2271813225', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/5087b66d.jpg', '64', '0', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('87', '人人***斯卡', '2671209892', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/239f7b33.jpg', '318', '2781509', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('88', 'gl***mu', '87353774', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/a7057130.jpg', '13', '33', '12', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('89', '热门**资源', '3728148270', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/49def133.jpg', '307', '2813601', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('90', 'Lula******hine', '2000010477', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/f2773231.jpg', '11', '1', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('91', '冀**摩智', '706872215', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/4d2a4826.jpg', '11', '3', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('92', 'WYW****204', '3996642348', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/a2eef333.jpg', '305', '37303', '107', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('93', 'Ju***_2', '676670735', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/6328d051.jpg', '92', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('94', 'Ci***cus', '2100777771', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/1d7df433.jpg', '305', '1808137', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('95', '怖拉**口臭', '1714016779', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/9c66d42d.jpg', '2', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('96', '纯*乐i', '1949795117', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/cd74f233.jpg', '316', '2052781', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('97', 'cy****795', '539714163', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/2c20ac2f.jpg', '216', '6', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('98', '142****984', '3221505874', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/09c08d00.jpg', '47', '0', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('99', '工口**rz', '1362602795', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/e551f433.jpg', '302', '6274', '63', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('100', '丹墨**01', '979021223', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/e33a785e.jpg', '25', '3', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('101', '一**榴莲', '1832355372', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/c86df333.jpg', '298', '1857126', '9', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('102', 'Z41****363', '3914156804', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/05e9db49.jpg', '191', '1', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('103', '乱来**46', '1381793067', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/3352f458.jpg', '58', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('104', '1**df', '3125309093', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/20ba7a4c.jpg', '54', '1', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('105', 'mi****666', '1567055550', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/105d6163.jpg', '200', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('106', '舞*山', '4228664392', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/02fc9708.jpg', '431', '7', '2', '1', '0', '1', '0');
INSERT INTO `yun_user` VALUES ('107', '究竟**谈话', '2703280002', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/89a15d24.jpg', '642', '10', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('108', '尽在****234', '1980714964', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/01760b0b.jpg', '304', '56913', '1383', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('109', 'BJ***406', '2000477971', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/9177cc38.jpg', '9', '1', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('110', '大*节', '3492591982', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/f7d0b128.jpg', '15', '19255', '2', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('111', '俨*赡', '192678933', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/420bca78.jpg', '2000', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('112', '影人**ar', '2285238882', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/b488bd31.jpg', '299', '27504', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('113', '素你***rt', '3693227929', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/61dc4626.jpg', '35', '5', '7', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('114', 'zeronu*********63.com', '561464989', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/08214273.jpg', '1', '0', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('115', 'app****g果果', '4161558649', '2016-10-31 17:15:35', 'http://himg.bdimg.com/sys/portrait/item/16f8a608.jpg', '72', '1', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('116', '马忠****032', '404766515', '2016-10-31 17:37:11', 'http://himg.bdimg.com/sys/portrait/item/7118ec24.jpg', '1', '10930', '283', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('117', 'sho****108', '168370734', '2016-10-31 17:37:11', 'http://himg.bdimg.com/sys/portrait/item/6c0af10d.jpg', '7', '12', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('118', 'i**NA', '2047459206', '2016-10-31 17:37:11', 'http://himg.bdimg.com/sys/portrait/item/f17a590d.jpg', '116', '332', '56', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('119', '草*小筑', '2956159362', '2016-10-31 17:37:11', 'http://himg.bdimg.com/sys/portrait/item/27b05d37.jpg', '6', '197', '30', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('120', '安*尼斯', '2869211135', '2016-10-31 17:37:11', 'http://himg.bdimg.com/sys/portrait/item/e1ab2000.jpg', '7', '6385', '41', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('121', 'mk**yw', '656365447', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/1927581b.jpg', '9', '1044', '173', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('122', '鼎尖***作室', '1397543135', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/9e530048.jpg', '0', '227', '12', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('123', 'M*X吧', '1883335722', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/2270f545.jpg', '2', '4539', '10', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('124', '钱*泽', '3408558481', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/37cb4e2e.jpg', '1', '639', '11', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('125', 'arc****lege', '3678033845', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/19db6a3e.jpg', '5', '22847', '354', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('126', 'sunl******1154', '3204524419', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/67bf5c05.jpg', '2', '967', '19', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('127', '龙马***ga', '3506820311', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/9ad10801.jpg', '8', '1274', '6', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('128', '建筑**小卒', '777301104', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/e22eaf50.jpg', '24', '1655', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('129', '放**山沟', '3325994282', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/ebc6f53a.jpg', '0', '722', '32', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('130', '小月**12', '1346001246', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/1750813e.jpg', '1', '2267', '50', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('131', 'da****_ou', '1225596986', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/6e49e509.jpg', '16', '48', '38', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('132', 'yij*****ichi', '1879763615', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/a470400e.jpg', '15', '476', '2', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('133', 'x61****555', '3809476486', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/b1e3590b.jpg', '295', '26320', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('134', 'qf****lt1', '2050019436', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/9e7ab334.jpg', '315', '6830', '18', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('135', '37****379', '2485166979', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/ed945c24.jpg', '299', '5996', '1', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('136', '41***101', '2988459501', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/0bb23224.jpg', '309', '9450', '4', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('137', '风*云影', '3860886991', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/3be61024.jpg', '299', '27402', '22', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('138', '2**LJ', '2416457930', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/7690150c.jpg', '40', '992', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('139', '何*桐', '2268579154', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/8b878d33.jpg', '299', '3510', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('140', 'YM**码哥', '2636858560', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/0a9d1f2f.jpg', '299', '54441', '246', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('141', '黑*完', '2086873', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/9900061b.jpg', '300', '4316', '182', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('142', '闫杰****561', '4144794311', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/c4f71808.jpg', '299', '13808', '89', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('143', 'bb****iil', '1477190803', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/6a584c08.jpg', '748', '2113', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('144', 'Ro***_58', '3457956649', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/75cef618.jpg', '303', '18890', '0', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('145', 'ra****lee', '1174767099', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/cb462401.jpg', '300', '1752', '2', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('146', '萌**狐狸', '3090645548', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/ccb8f333.jpg', '305', '8589', '100', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('147', '壁*分享', '4046951470', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/daf1f133.jpg', '301', '744661', '125', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('148', '纷*人世', '2986611120', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/5fb26f00.jpg', '300', '14745', '213', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('149', 'al***********63.com', '4248058874', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/75fd2530.jpg', '299', '10281', '422', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('150', '28****564', '2989321065', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/25b2b629.jpg', '299', '2827', '116', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('151', 'fe*************26.com', '20249593', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/b5012630.jpg', '299', '9014', '694', '1', '0', '0', '0');
INSERT INTO `yun_user` VALUES ('152', '曲艺**收藏', '1966581154', '2016-10-31 17:37:12', 'http://himg.bdimg.com/sys/portrait/item/eb757d33.jpg', '302', '54670', '55', '1', '0', '0', '0');
