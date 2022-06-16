/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : hap_forum

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 06/06/2022 21:57:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `profile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `simple_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_picture` tinyint(1) NOT NULL,
  `main_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `label_id` json NOT NULL COMMENT '多个labelId组成的jsonArray',
  `topping` json NULL COMMENT '置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析1', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[710975262845566976, 710975274044358657]', NULL, 1, '2022-03-16 13:36:58', '2022-03-16 13:36:58');
INSERT INTO `article` VALUES (710859085800538112, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[1, 2]', NULL, 0, '2022-03-16 14:20:12', '2022-03-16 14:20:12');
INSERT INTO `article` VALUES (710859105421492224, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[1, 2]', NULL, 0, '2022-03-16 14:20:18', '2022-03-16 14:20:18');
INSERT INTO `article` VALUES (710859508192116736, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[1, 2]', NULL, 0, '2022-03-16 14:21:54', '2022-03-16 14:21:54');
INSERT INTO `article` VALUES (710860619493933056, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[1, 2]', NULL, 0, '2022-03-16 14:26:17', '2022-03-16 14:26:17');
INSERT INTO `article` VALUES (710860631049240577, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '关于俄乌冲突的分析', '2022年2月23日，乌克兰议会批准在全国实施紧急状态。2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2022年2月23日，乌克兰议会批准在全国实施紧急状态。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，俄罗斯总统普京已决定在顿巴斯地区进行特别军事行动；当日，俄军已登陆乌克兰敖德萨。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰管理部门宣布关闭全国领空，乌克兰总统泽连斯基表示，乌克兰全境将进入战时状态，首都基辅地铁免费开放，地铁站将作为防空洞使用；俄军开始对乌军东部部队和其他地区的军事指挥中心、机场进行炮击。乌克兰国民卫队司令部被摧毁。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰宣布与俄罗斯断交。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2月24日，乌克兰边防部队称俄军突入基辅地区。当地时间2月24日，乌克兰基辅市政府发出防空警报，通知所有人立即前往民防避难所避难。当地时间26日，乌克兰基辅市市长宣布，该市地铁转为避难所，不再提供运输服务。</p>\r\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3月2日，乌克兰已经关闭其驻俄罗斯圣彼得堡的总领馆。13日清晨，俄军对利沃夫州亚沃洛夫斯基训练场的空袭共造成9人死亡，57人受伤。</p>', 0, '', '[1, 2]', NULL, 0, '2022-03-16 14:26:22', '2022-03-16 14:26:22');
INSERT INTO `article` VALUES (710861302376955905, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 0, '2022-03-16 14:29:02', '2022-03-16 14:29:02');
INSERT INTO `article` VALUES (710861313265369088, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 0, '2022-03-16 14:29:04', '2022-03-16 14:29:04');
INSERT INTO `article` VALUES (710861316885053441, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 0, '2022-03-16 14:29:05', '2022-03-16 14:29:05');
INSERT INTO `article` VALUES (710861319842037760, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 1, '2022-03-16 14:29:06', '2022-03-16 14:29:06');
INSERT INTO `article` VALUES (710861323931484161, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 0, '2022-03-16 14:29:07', '2022-03-16 14:29:07');
INSERT INTO `article` VALUES (710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '3·15 晚会曝光多品牌电动自行车公然违规提速', '3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·', '3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门。3·15 晚会曝光多品牌电动自行车公然违规提速，绿源、小牛等被点名，电动车行速为何被商家预留了后门', 0, '', '[1, 2, 3]', NULL, 0, '2022-03-16 14:29:08', '2022-03-16 14:29:08');
INSERT INTO `article` VALUES (716606240876134401, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '上海回应哮喘病人借除颤仪被拒去世', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;哮喘急性发作的主要治疗是缓解气道痉挛和纠正低氧，一般不会迅速的心脏骤停。如果说心脏停了，那么就说明已经严重低氧酸中毒了。因为哮喘', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>哮喘急性发作的主要治疗是缓解气道痉挛和纠正低氧，一般不会迅速的心脏骤停。如果说心脏停了，那么就说明已经严重低氧酸中毒了。因为哮喘发作会引起严重的气道狭窄，所以通常的心肺复苏流程很难起效（低氧无法改善），需要机械通气呼吸机，同时要迅速扩张气道。除颤仪在心肺复苏中的作用是纠正室颤等恶性心律失常，如果没有是不需要除颤仪的，但是除颤仪是可以帮助判断有无室颤的。我在学习心肺复苏的时候，流程是先判断病人生命体征，然后呼救，寻找除颤仪是在呼救这一步的，但这不是必须的，胸外按压是最重要的，是为了维持循环，但是在严重缺氧的情况下，胸外按压的作用都可能是极其有限的，所以，还是要早点纠正哮喘发作，常备激素和其他口服药。</p>', 0, NULL, '[710975262845566976, 710975274044358657]', NULL, 1, '2022-04-01 10:57:22', '2022-04-01 10:57:22');
INSERT INTO `article` VALUES (716607937400799232, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '3 月 31 日上海新增本土确诊病例 358 例', '上海以一己之力毁掉了中国健康码和疫情风险趋于划定体系的公信力。上海本轮疫情累计感染者已达三万多例，但上海至今没有高风险区域，从上海出来的感染者健康码居然还是绿色。即便是上海内部疫情已经遍地开花，从上海', '<p>上海以一己之力毁掉了中国健康码和疫情风险趋于划定体系的公信力。上海本轮疫情累计感染者已达三万多例，但上海至今没有高风险区域，从上海出来的感染者健康码居然还是绿色。即便是上海内部疫情已经遍地开花，从上海外溢出去的感染者也遍布全国各地。但近日一些在上海被查出阳性的感染者居然还能在不受到上海管控的情况下，从上海坐高铁离开。</p>', 0, NULL, '[710975262845566976, 710975274044358657]', NULL, 0, '2022-04-01 11:04:06', '2022-04-01 11:04:06');
INSERT INTO `article` VALUES (716608801234485249, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '新冠病毒又现「新变种XE 」传播速度更快，英国已有多人确诊', '有朋友问OmicronBA.1和BA.2的重组变异体XE。目前所了解的信息有限，XE是今年2月15日在英国首次被发现（目前也只有英国发现）。基因测序的结果表明，XE的ORF1a部分更多是来自BA.1，', '<p>有朋友问Omicron BA.1和BA.2的重组变异体XE。</p>\n<p>目前所了解的信息有限，XE是今年2月15日在英国首次被发现（目前也只有英国发现）。</p>\n<p>基因测序的结果表明，XE的ORF1a部分更多是来自BA.1，而其余部分更多来自BA.2，特别是S蛋白部分的基因。</p>', 0, NULL, '[710975262845566976, 710975275038408704]', NULL, 0, '2022-04-01 11:07:32', '2022-04-01 11:07:32');
INSERT INTO `article` VALUES (716650035592298496, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '北京协和医学院肿瘤学研究生复试331分逆袭390分', '我是陈鑫的发小，因为怕被网暴所以匿名了，下面是我和他的一些聊天记录，包括今年刚出成绩后的聊天，去年他写论文时候聊天。我们俩是一个村的，从小玩到大，他父母就是在家种棚的农民，而不是网上说的“院长”“博导', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>我是陈鑫的发小，因为怕被网暴所以匿名了，下面是我和他的一些聊天记录，包括今年刚出成绩后的聊天，去年他写论文时候聊天。 我们俩是一个村的，从小玩到大，他父母就是在家种棚的农民，而不是网上说的&ldquo;院长&rdquo;&ldquo;博导&rdquo;之类。的确他不是一个有天赋的人，高考的时候成绩很一般，他想当医生，所以报考了济宁医学院临床医学专业。进入大学后，跟着导师进实验室，做实验写论文，去年暑假他也没有回家，一直在学校学习和医院里实习。</p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp;去年写论文的时候，本该是准备考研的时候，当时他也很纠结，因为他考研的内容的确学的并不好，但他想去个好点的学校，所以想努力把论文发出来，因为他本科学历不好，应试能力也一般，论文对他来说挺重要的。论文发表出来以后，他决定一战报个好学校试一下，考不上就二战。今年刚出成绩的时候，他也觉得希望不大，已经开始看调剂了。</p>\n<p>&nbsp; &nbsp; &nbsp; 他英语的确不好，但网上说英语差就写不了sci也是有失偏颇的，因为可以用翻译软件翻译后再润色，他之前还让我给他改过论文，聊天记录已经放下面了。</p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp;网上说他6篇sci一作也是不实的，是他发了一篇sci的影响因子为6.58。他的论文成果我也放在下面了，是他之前发给我他做的简历的截图。</p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp;我知道我发小一直以来的努力，也知道他家庭的真实情况，希望大家能够理性评判，谢谢大家。</p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp;评论区有说他父亲是教授的，我就再加一张截图，是之前聊到要不要继续读书，他爸妈都是农民，在家种棚供他上学，他觉得他多读一天书，他父母就要多干一天活。济宁医学院发的辟谣声明。</p>', 0, NULL, '[710975262845566976]', NULL, 1, '2022-04-01 13:51:23', '2022-04-01 13:51:23');
INSERT INTO `article` VALUES (731833596062466048, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:25:25', '2022-05-13 11:25:25');
INSERT INTO `article` VALUES (731838651620655104, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:45:30', '2022-05-13 11:45:30');
INSERT INTO `article` VALUES (731839585071398913, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:49:14', '2022-05-13 11:49:14');
INSERT INTO `article` VALUES (731839739576975360, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:49:51', '2022-05-13 11:49:51');
INSERT INTO `article` VALUES (731841139346243585, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:51:45', '2022-05-13 11:51:45');
INSERT INTO `article` VALUES (731841959903100928, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:56:12', '2022-05-13 11:56:12');
INSERT INTO `article` VALUES (731842168288706561, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 11:58:44', '2022-05-13 11:58:44');
INSERT INTO `article` VALUES (731842958692712448, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 12:01:07', '2022-05-13 12:01:07');
INSERT INTO `article` VALUES (731843316533952513, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 12:02:41', '2022-05-13 12:02:41');
INSERT INTO `article` VALUES (731843575301537792, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 12:04:07', '2022-05-13 12:04:07');
INSERT INTO `article` VALUES (731854011824603137, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 12:05:08', '2022-05-13 12:05:08');
INSERT INTO `article` VALUES (731858616620941312, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 12:46:59', '2022-05-13 12:46:59');
INSERT INTO `article` VALUES (731861074139152384, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '专眼电专算存山', 'etdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdoloretempordeseruntvoluptateetdolor', 'et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate et dolore tempor deserunt voluptate', 0, 'http://dummyimage.com/400x400', '[76]', NULL, 1, '2022-05-13 13:06:23', '2022-05-13 13:06:23');
INSERT INTO `article` VALUES (731866465694646273, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:35:00', '2022-05-13 13:35:00');
INSERT INTO `article` VALUES (731867921214603264, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:41:50', '2022-05-13 13:41:50');
INSERT INTO `article` VALUES (731868518894534657, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:44:12', '2022-05-13 13:44:12');
INSERT INTO `article` VALUES (731868652755746816, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:44:44', '2022-05-13 13:44:44');
INSERT INTO `article` VALUES (731869019233058817, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:46:12', '2022-05-13 13:46:12');
INSERT INTO `article` VALUES (731869965950386176, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:49:57', '2022-05-13 13:49:57');
INSERT INTO `article` VALUES (731870702470168577, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:52:52', '2022-05-13 13:52:52');
INSERT INTO `article` VALUES (731872038121111552, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 13:58:11', '2022-05-13 13:58:11');
INSERT INTO `article` VALUES (731874551473897473, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 14:04:44', '2022-05-13 14:04:44');
INSERT INTO `article` VALUES (731874950817775616, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 14:08:41', '2022-05-13 14:08:41');
INSERT INTO `article` VALUES (731875102114709505, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 14:09:55', '2022-05-13 14:09:55');
INSERT INTO `article` VALUES (731875685835997184, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 14:10:29', '2022-05-13 14:10:29');
INSERT INTO `article` VALUES (731876138460119041, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '米种名你教', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[52, 58]', NULL, 1, '2022-05-13 14:12:50', '2022-05-13 14:12:50');
INSERT INTO `article` VALUES (739920949016526848, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这个人很懒,没有个人简介', '队能列力', 'labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人通过然后今天有寄托于可以返回发帖labore2俄企鹅个个人', 'labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖 labore 2俄企鹅个个人通过然后今天有寄托于可以返回发帖', 0, 'http://dummyimage.com/400x400', '[710975262845566976, 710975274044358657]', NULL, 1, '2022-06-04 19:01:41', '2022-06-04 19:01:41');

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `comment_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `topping` json NULL COMMENT '置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_comment
-- ----------------------------
INSERT INTO `article_comment` VALUES (710868907493163008, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:14', '2022-03-16 14:59:14');
INSERT INTO `article_comment` VALUES (710868915864993793, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:17', '2022-03-16 14:59:17');
INSERT INTO `article_comment` VALUES (710868916477362176, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:17', '2022-03-16 14:59:17');
INSERT INTO `article_comment` VALUES (710868917202976769, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:17', '2022-03-16 14:59:17');
INSERT INTO `article_comment` VALUES (710868917857288192, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:17', '2022-03-16 14:59:17');
INSERT INTO `article_comment` VALUES (710868918566125569, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:17', '2022-03-16 14:59:17');
INSERT INTO `article_comment` VALUES (710868919170105344, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:18', '2022-03-16 14:59:18');
INSERT INTO `article_comment` VALUES (710868919836999681, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '穷则莱阳钢管，富则莱茵金属', NULL, 1, '2022-03-16 14:59:18', '2022-03-16 14:59:18');
INSERT INTO `article_comment` VALUES (710869078775955456, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；消灭纳粹，消灭亚速营；', NULL, 1, '2022-03-16 14:59:56', '2022-03-16 14:59:56');
INSERT INTO `article_comment` VALUES (710869081204457473, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:56', '2022-03-16 14:59:56');
INSERT INTO `article_comment` VALUES (710869081858768896, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:56', '2022-03-16 14:59:56');
INSERT INTO `article_comment` VALUES (710869082563411969, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710869083272249344, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710869083976892417, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710869084606038016, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710869085210017793, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710869085788831744, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '3消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 14:59:57', '2022-03-16 14:59:57');
INSERT INTO `article_comment` VALUES (710883367352008704, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '2消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 15:56:42', '2022-03-16 15:56:42');
INSERT INTO `article_comment` VALUES (710883819393122305, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '1消灭纳粹，消灭亚速营', NULL, 1, '2022-03-16 15:58:30', '2022-03-16 15:58:30');
INSERT INTO `article_comment` VALUES (715859146271883264, 710848206937784320, 1, 'avatar', 'nickname', '哒哒哒', NULL, 1, '2022-03-30 09:28:40', '2022-03-30 09:28:40');
INSERT INTO `article_comment` VALUES (715859743280726017, 710848206937784320, 1, 'avatar', 'nickname', '哒哒哒', NULL, 1, '2022-03-30 09:31:03', '2022-03-30 09:31:03');
INSERT INTO `article_comment` VALUES (715975121373757440, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '哒哒哒', NULL, 1, '2022-03-30 17:09:31', '2022-03-30 17:09:31');
INSERT INTO `article_comment` VALUES (716015153589518336, 710848206937784320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '哒哒哒', NULL, 1, '2022-03-30 19:48:35', '2022-03-30 19:48:35');
INSERT INTO `article_comment` VALUES (716032584227749889, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 1, '2022-03-30 20:57:51', '2022-03-30 20:57:51');
INSERT INTO `article_comment` VALUES (716033477891325952, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 1, '2022-03-30 21:01:24', '2022-03-30 21:01:24');
INSERT INTO `article_comment` VALUES (716063768202706945, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的有这么丝滑吗', NULL, 1, '2022-03-30 23:01:46', '2022-03-30 23:01:46');
INSERT INTO `article_comment` VALUES (716063937119911936, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的有这么丝滑吗', NULL, 1, '2022-03-30 23:02:26', '2022-03-30 23:02:26');
INSERT INTO `article_comment` VALUES (716064649249816577, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的有这么丝滑吗', NULL, 1, '2022-03-30 23:05:16', '2022-03-30 23:05:16');
INSERT INTO `article_comment` VALUES (716074122362224640, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 1, '2022-03-30 23:42:55', '2022-03-30 23:42:55');
INSERT INTO `article_comment` VALUES (716074564857102336, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 1, '2022-03-30 23:44:40', '2022-03-30 23:44:40');
INSERT INTO `article_comment` VALUES (716075442674597888, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 1, '2022-03-30 23:48:10', '2022-03-30 23:48:10');
INSERT INTO `article_comment` VALUES (716075474190598145, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 0, '2022-03-30 23:48:17', '2022-03-30 23:48:17');
INSERT INTO `article_comment` VALUES (716075822032617472, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 0, '2022-03-30 23:49:40', '2022-03-30 23:49:40');
INSERT INTO `article_comment` VALUES (716076007890616321, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '真的吗？我不信', NULL, 0, '2022-03-30 23:50:24', '2022-03-30 23:50:24');
INSERT INTO `article_comment` VALUES (716340836010295296, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 0, '2022-03-31 17:22:44', '2022-03-31 17:22:44');
INSERT INTO `article_comment` VALUES (716340881359110145, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '对的', NULL, 0, '2022-03-31 17:22:55', '2022-03-31 17:22:55');
INSERT INTO `article_comment` VALUES (716343945465954304, 710861328381640704, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '我再也不买了', NULL, 0, '2022-03-31 17:35:06', '2022-03-31 17:35:06');
INSERT INTO `article_comment` VALUES (716608969530933248, 716608801234485249, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '恐怖如斯', NULL, 0, '2022-04-01 11:08:12', '2022-04-01 11:08:12');
INSERT INTO `article_comment` VALUES (716650125316849665, 716650035592298496, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '说得好', NULL, 1, '2022-04-01 13:51:45', '2022-04-01 13:51:45');
INSERT INTO `article_comment` VALUES (716981999218196480, 716650035592298496, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '牛逼啊', NULL, 1, '2022-04-02 11:50:29', '2022-04-02 11:50:29');
INSERT INTO `article_comment` VALUES (718509849716981760, 716650035592298496, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '达娃大', NULL, 1, '2022-04-06 17:01:37', '2022-04-06 17:01:37');
INSERT INTO `article_comment` VALUES (740319880254849025, 731868652755746816, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '不得不如此', NULL, 1, '2022-06-05 21:26:54', '2022-06-05 21:26:54');
INSERT INTO `article_comment` VALUES (740320781732413440, 710848206937784320, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '不得不如此', NULL, 1, '2022-06-05 21:30:29', '2022-06-05 21:30:29');
INSERT INTO `article_comment` VALUES (740322682649706496, 710848206937784320, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '不得不如此', NULL, 1, '2022-06-05 21:38:02', '2022-06-05 21:38:02');

-- ----------------------------
-- Table structure for article_comment_child
-- ----------------------------
DROP TABLE IF EXISTS `article_comment_child`;
CREATE TABLE `article_comment_child`  (
  `comment_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  `parent_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `child_to_child` tinyint(1) NOT NULL COMMENT '是否为子评论间回复',
  `child_target` bigint UNSIGNED NULL DEFAULT NULL COMMENT '回复目标，仅在子评论间回复时存在',
  `child_target_name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复目标名冗余字段',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_comment_child
-- ----------------------------
INSERT INTO `article_comment_child` VALUES (710910792676933632, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:41', '2022-03-16 17:45:41');
INSERT INTO `article_comment_child` VALUES (710910802290278401, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:43', '2022-03-16 17:45:43');
INSERT INTO `article_comment_child` VALUES (710910805071101952, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:44', '2022-03-16 17:45:44');
INSERT INTO `article_comment_child` VALUES (710910807784816641, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:45', '2022-03-16 17:45:45');
INSERT INTO `article_comment_child` VALUES (710910810645331968, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:45', '2022-03-16 17:45:45');
INSERT INTO `article_comment_child` VALUES (710910813535207425, 710848206937784320, 710868907493163008, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '那必须', 1, '2022-03-16 17:45:46', '2022-03-16 17:45:46');
INSERT INTO `article_comment_child` VALUES (712402279407288326, 710848206937784320, 710868915864993793, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:04', '2022-03-20 21:54:04');
INSERT INTO `article_comment_child` VALUES (716081569395113984, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, NULL, '我也不信', 1, '2022-03-31 00:12:30', '2022-03-31 00:12:30');
INSERT INTO `article_comment_child` VALUES (716269759271796736, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 716081569395113984, NULL, '我也是', 1, '2022-03-31 12:40:18', '2022-03-31 12:40:18');
INSERT INTO `article_comment_child` VALUES (716275020556402689, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 716269759271796736, NULL, '是这样的', 1, '2022-03-31 13:01:13', '2022-03-31 13:01:13');
INSERT INTO `article_comment_child` VALUES (716275784389492736, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 716275020556402689, NULL, '牛皮', 1, '2022-03-31 13:04:15', '2022-03-31 13:04:15');
INSERT INTO `article_comment_child` VALUES (716279569107124224, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 716081569395113984, '加藤惠', '真的假的', 1, '2022-03-31 13:19:17', '2022-03-31 13:19:17');
INSERT INTO `article_comment_child` VALUES (716327483514814465, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '我也是', 1, '2022-03-31 16:29:41', '2022-03-31 16:29:41');
INSERT INTO `article_comment_child` VALUES (716327643124858880, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '我也是', 1, '2022-03-31 16:30:19', '2022-03-31 16:30:19');
INSERT INTO `article_comment_child` VALUES (716327843121856513, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 716081569395113984, '加藤惠', '我也是', 1, '2022-03-31 16:31:07', '2022-03-31 16:31:07');
INSERT INTO `article_comment_child` VALUES (716344244721156097, 710861328381640704, 716032584227749889, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '真的这么牛？', 1, '2022-03-31 17:36:17', '2022-03-31 17:36:17');
INSERT INTO `article_comment_child` VALUES (716662088465907712, 710848206937784320, 710868916477362176, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '放大无法', 1, '2022-04-01 14:39:17', '2022-04-01 14:39:17');
INSERT INTO `article_comment_child` VALUES (716666646680305665, 716650035592298496, 716650125316849665, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '哒哒哒', 1, '2022-04-01 14:57:24', '2022-04-01 14:57:24');
INSERT INTO `article_comment_child` VALUES (739944669252681728, 710848206937784320, 710868907493163008, 1518946330134302721, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, 37, NULL, 'ipsum laborum', 1, '2022-06-04 20:35:57', '2022-06-04 20:35:57');

-- ----------------------------
-- Table structure for creator
-- ----------------------------
DROP TABLE IF EXISTS `creator`;
CREATE TABLE `creator`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱(同时作为账户)',
  `mobile` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
  `password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `profile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '个人简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `role` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色(admin, creator, viewer)',
  `account_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '0-封禁，1-正常',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1518946330134302722 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of creator
-- ----------------------------
INSERT INTO `creator` VALUES (1518946330134302721, 'jth@qq.com', '19815032765', '$2a$10$HT2m6Z9QfFtDmx.WrscNL.vJhfj.7zPRoan1BLMkOXNGiIRd9w//q', '加藤惠', '这个人很懒,没有个人简介', 'https://file.heartape.com/picture/avatar-1.jpg', 'creator', '1', 1, '2022-04-26 21:33:20', '2022-04-26 21:33:20');

-- ----------------------------
-- Table structure for creator_follow
-- ----------------------------
DROP TABLE IF EXISTS `creator_follow`;
CREATE TABLE `creator_follow`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `target_id` int NOT NULL,
  `special` tinyint(1) NOT NULL COMMENT '是否特别关注',
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of creator_follow
-- ----------------------------

-- ----------------------------
-- Table structure for discuss_comment
-- ----------------------------
DROP TABLE IF EXISTS `discuss_comment`;
CREATE TABLE `discuss_comment`  (
  `comment_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  `discuss_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `topping` json NULL COMMENT '置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discuss_comment
-- ----------------------------
INSERT INTO `discuss_comment` VALUES (712404640716554240, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:42', '2022-03-20 20:41:42');
INSERT INTO `discuss_comment` VALUES (712404657934172161, 712402302140416000, 712404154688995329, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:46', '2022-03-20 20:41:46');
INSERT INTO `discuss_comment` VALUES (712404658609455104, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:46', '2022-03-20 20:41:46');
INSERT INTO `discuss_comment` VALUES (712404659339264001, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:47', '2022-03-20 20:41:47');
INSERT INTO `discuss_comment` VALUES (712404660119404544, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:47', '2022-03-20 20:41:47');
INSERT INTO `discuss_comment` VALUES (712404660874379265, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:47', '2022-03-20 20:41:47');
INSERT INTO `discuss_comment` VALUES (712404661620965376, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:47', '2022-03-20 20:41:47');
INSERT INTO `discuss_comment` VALUES (712404662321414145, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:47', '2022-03-20 20:41:47');
INSERT INTO `discuss_comment` VALUES (712404663088971776, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:48', '2022-03-20 20:41:48');
INSERT INTO `discuss_comment` VALUES (712404663797809153, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:48', '2022-03-20 20:41:48');
INSERT INTO `discuss_comment` VALUES (712404672471629824, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:50', '2022-03-20 20:41:50');
INSERT INTO `discuss_comment` VALUES (712404673046249473, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', '说得好,不能惯着', NULL, 1, '2022-03-20 20:41:50', '2022-03-20 20:41:50');
INSERT INTO `discuss_comment` VALUES (716367690964402176, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:09:27', '2022-03-31 19:09:27');
INSERT INTO `discuss_comment` VALUES (716369475674308609, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这不就是？', NULL, 1, '2022-03-31 19:16:32', '2022-03-31 19:16:32');
INSERT INTO `discuss_comment` VALUES (716369652158038016, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 'good', NULL, 1, '2022-03-31 19:17:15', '2022-03-31 19:17:15');
INSERT INTO `discuss_comment` VALUES (716370142165991425, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '发热', NULL, 1, '2022-03-31 19:19:11', '2022-03-31 19:19:11');
INSERT INTO `discuss_comment` VALUES (716370300459024384, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '发热', NULL, 1, '2022-03-31 19:19:49', '2022-03-31 19:19:49');
INSERT INTO `discuss_comment` VALUES (716378563376316416, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:52:39', '2022-03-31 19:52:39');
INSERT INTO `discuss_comment` VALUES (716378636327845889, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:52:57', '2022-03-31 19:52:57');
INSERT INTO `discuss_comment` VALUES (716379536651976704, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:56:31', '2022-03-31 19:56:31');
INSERT INTO `discuss_comment` VALUES (716379861362409473, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:57:49', '2022-03-31 19:57:49');
INSERT INTO `discuss_comment` VALUES (716380283288420352, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '是这样的', NULL, 1, '2022-03-31 19:59:29', '2022-03-31 19:59:29');
INSERT INTO `discuss_comment` VALUES (716381033875898369, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '好的', NULL, 1, '2022-03-31 20:02:28', '2022-03-31 20:02:28');
INSERT INTO `discuss_comment` VALUES (716382269404610560, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '沟通', NULL, 1, '2022-03-31 20:07:23', '2022-03-31 20:07:23');
INSERT INTO `discuss_comment` VALUES (716383259109359617, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '割肉', NULL, 1, '2022-03-31 20:11:19', '2022-03-31 20:11:19');
INSERT INTO `discuss_comment` VALUES (716383338977296384, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '果然', NULL, 1, '2022-03-31 20:11:38', '2022-03-31 20:11:38');
INSERT INTO `discuss_comment` VALUES (716383368714911745, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '', NULL, 1, '2022-03-31 20:11:45', '2022-03-31 20:11:45');
INSERT INTO `discuss_comment` VALUES (716383370552016896, 712402279407288321, 712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '', NULL, 1, '2022-03-31 20:11:45', '2022-03-31 20:11:45');
INSERT INTO `discuss_comment` VALUES (716384353713651713, 712402279407288321, 712404155519467520, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '一样的', NULL, 1, '2022-03-31 20:15:40', '2022-03-31 20:15:40');
INSERT INTO `discuss_comment` VALUES (716384770526806016, 712402279407288321, 712404156295413761, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '这么叼', NULL, 1, '2022-03-31 20:17:19', '2022-03-31 20:17:19');
INSERT INTO `discuss_comment` VALUES (716385097133064193, 712402279407288321, 712404157046194176, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '大哥大', NULL, 1, '2022-03-31 20:18:37', '2022-03-31 20:18:37');
INSERT INTO `discuss_comment` VALUES (716385460724695040, 712402279407288321, 712404157729865729, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '发热', NULL, 1, '2022-03-31 20:20:04', '2022-03-31 20:20:04');
INSERT INTO `discuss_comment` VALUES (716386049168769025, 712402279407288321, 712404160816873473, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '得到', NULL, 0, '2022-03-31 20:22:24', '2022-03-31 20:22:24');
INSERT INTO `discuss_comment` VALUES (716386605408976896, 712402279407288321, 712404158455480320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '第一', NULL, 0, '2022-03-31 20:24:37', '2022-03-31 20:24:37');
INSERT INTO `discuss_comment` VALUES (716387200467468289, 712402279407288321, 712404159101403137, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '什么东西', NULL, 0, '2022-03-31 20:26:58', '2022-03-31 20:26:58');

-- ----------------------------
-- Table structure for discuss_comment_child
-- ----------------------------
DROP TABLE IF EXISTS `discuss_comment_child`;
CREATE TABLE `discuss_comment_child`  (
  `comment_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  `discuss_id` bigint NOT NULL,
  `parent_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `child_to_child` tinyint(1) NOT NULL COMMENT '是否为子评论间回复',
  `child_target` bigint NULL DEFAULT NULL COMMENT '回复目标，仅在子评论间回复时存在',
  `child_target_name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复目标名冗余字段',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discuss_comment_child
-- ----------------------------
INSERT INTO `discuss_comment_child` VALUES (712422825255763968, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:53:58', '2022-03-20 21:53:58');
INSERT INTO `discuss_comment_child` VALUES (712422839092772865, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:01', '2022-03-20 21:54:01');
INSERT INTO `discuss_comment_child` VALUES (712422839730307072, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:01', '2022-03-20 21:54:01');
INSERT INTO `discuss_comment_child` VALUES (712422840325898241, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:01', '2022-03-20 21:54:01');
INSERT INTO `discuss_comment_child` VALUES (712422840934072320, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:01', '2022-03-20 21:54:01');
INSERT INTO `discuss_comment_child` VALUES (712422841361891329, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:02', '2022-03-20 21:54:02');
INSERT INTO `discuss_comment_child` VALUES (712422841793904640, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:02', '2022-03-20 21:54:02');
INSERT INTO `discuss_comment_child` VALUES (712422842347552769, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:02', '2022-03-20 21:54:02');
INSERT INTO `discuss_comment_child` VALUES (712422843291271168, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:02', '2022-03-20 21:54:02');
INSERT INTO `discuss_comment_child` VALUES (712422843962359809, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:02', '2022-03-20 21:54:02');
INSERT INTO `discuss_comment_child` VALUES (712422852728455168, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 0, 0, NULL, '是这样的', 1, '2022-03-20 21:54:04', '2022-03-20 21:54:04');
INSERT INTO `discuss_comment_child` VALUES (716394708825276416, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, NULL, '好家伙', 1, '2022-03-31 20:56:49', '2022-03-31 20:56:49');
INSERT INTO `discuss_comment_child` VALUES (716394766413070337, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, NULL, '大哥', 1, '2022-03-31 20:57:02', '2022-03-31 20:57:02');
INSERT INTO `discuss_comment_child` VALUES (716394845207265280, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 712422839092772865, NULL, '无所谓', 1, '2022-03-31 20:57:21', '2022-03-31 20:57:21');
INSERT INTO `discuss_comment_child` VALUES (716395376273260544, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 712422839092772865, '加藤惠', '回答我就', 1, '2022-03-31 20:59:28', '2022-03-31 20:59:28');
INSERT INTO `discuss_comment_child` VALUES (716395934807752705, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 1, 712422839092772865, '加藤惠', '对面', 1, '2022-03-31 21:01:41', '2022-03-31 21:01:41');
INSERT INTO `discuss_comment_child` VALUES (716397232764485632, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '福娃福娃', 1, '2022-03-31 21:06:50', '2022-03-31 21:06:50');
INSERT INTO `discuss_comment_child` VALUES (716397249134854145, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '发我发我', 1, '2022-03-31 21:06:54', '2022-03-31 21:06:54');
INSERT INTO `discuss_comment_child` VALUES (716397274061602816, 712402279407288321, 712404143410511872, 712404640716554240, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', 0, NULL, '加藤惠', '就业体检通用', 1, '2022-03-31 21:07:00', '2022-03-31 21:07:00');

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `label_id` bigint NOT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  `level` int NOT NULL COMMENT '最多3级',
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `main_picture` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主图',
  `simple_introduce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduce` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES (710975262845566976, NULL, 1, '标签1', '', '这是一个1级标签', '这是一个1级标签', 1, '2022-03-16 22:01:51', '2022-03-16 22:01:51');
INSERT INTO `label` VALUES (710975274044358657, 710975262845566976, 2, '标签2', '', '这是一个1级标签', '这是一个1级标签', 1, '2022-03-16 22:01:55', '2022-03-16 22:01:55');
INSERT INTO `label` VALUES (710975275038408704, 710975262845566976, 2, '标签3', '', '这是一个1级标签', '这是一个1级标签', 1, '2022-03-16 22:01:55', '2022-03-16 22:01:55');

-- ----------------------------
-- Table structure for label_follow
-- ----------------------------
DROP TABLE IF EXISTS `label_follow`;
CREATE TABLE `label_follow`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `label_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label_follow
-- ----------------------------

-- ----------------------------
-- Table structure for link_guide
-- ----------------------------
DROP TABLE IF EXISTS `link_guide`;
CREATE TABLE `link_guide`  (
  `guide_id` bigint NOT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '外部链接',
  `topping` tinyint(1) NOT NULL COMMENT '是否置顶',
  `sequence` int NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`guide_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of link_guide
-- ----------------------------
INSERT INTO `link_guide` VALUES (1502953715672866817, '百度', 'https://www.baidu.com', 1, 7, 1, '2022-03-13 18:24:24', '2022-03-13 18:24:24');
INSERT INTO `link_guide` VALUES (1502953730113855489, '百度', 'https://www.baidu.com', 1, 3, 1, '2022-03-13 18:24:27', '2022-03-13 18:24:27');
INSERT INTO `link_guide` VALUES (1502953734064889857, '百度', 'https://www.baidu.com', 1, 2, 1, '2022-03-13 18:24:28', '2022-03-13 18:24:28');
INSERT INTO `link_guide` VALUES (1502953931385921538, '百度', 'https://www.baidu.com', 0, 1, 1, '2022-03-13 18:25:15', '2022-03-13 18:25:15');
INSERT INTO `link_guide` VALUES (1502953932111536129, '百度', 'https://www.baidu.com', 0, 8, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');
INSERT INTO `link_guide` VALUES (1502953932761653249, '百度', 'https://www.baidu.com', 0, 1, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');
INSERT INTO `link_guide` VALUES (1502953933411770370, '百度', 'https://www.baidu.com', 0, 9, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');
INSERT INTO `link_guide` VALUES (1502953934057693186, '百度', 'https://www.baidu.com', 0, 4, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');
INSERT INTO `link_guide` VALUES (1502953934519066625, '百度', 'https://www.baidu.com', 0, 10, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');
INSERT INTO `link_guide` VALUES (1502953935034966017, '百度', 'https://www.baidu.com', 0, 5, 1, '2022-03-13 18:25:16', '2022-03-13 18:25:16');

-- ----------------------------
-- Table structure for message_notification
-- ----------------------------
DROP TABLE IF EXISTS `message_notification`;
CREATE TABLE `message_notification`  (
  `message_id` bigint NOT NULL,
  `uid` bigint NOT NULL COMMENT '触发消息通知的用户',
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `main_id` bigint NOT NULL COMMENT '消息主体',
  `main_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息主体类型，如文章、话题等',
  `target_uid` bigint NOT NULL COMMENT '接受通知的用户',
  `target_id` bigint NOT NULL,
  `target_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息所对应的目标类型，如子评论对应父评论',
  `target_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息所对应的目标名称',
  `action` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '1:点赞；2:关注；3:讨论；4:回复',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_notification
-- ----------------------------
INSERT INTO `message_notification` VALUES (736533634688745472, 1518946330134302721, '加藤惠', 731875685835997184, '1', 1518946330134302721, 731875685835997184, '11', '米种名你教', '1', 1, '2022-05-26 10:41:43', '2022-05-26 10:41:43');
INSERT INTO `message_notification` VALUES (736534616982159361, 1518946330134302721, '加藤惠', 731875685835997184, '1', 1518946330134302721, 731875685835997184, '11', '米种名你教', '1', 1, '2022-05-26 10:45:37', '2022-05-26 10:45:37');
INSERT INTO `message_notification` VALUES (736535070851989504, 1518946330134302721, '加藤惠', 731875685835997184, '1', 1518946330134302721, 731875685835997184, '11', '米种名你教', '1', 1, '2022-05-26 10:47:25', '2022-05-26 10:47:25');
INSERT INTO `message_notification` VALUES (736535314339725313, 1518946330134302721, '加藤惠', 731875685835997184, '1', 1518946330134302721, 731875685835997184, '11', '米种名你教', '1', 1, '2022-05-26 10:48:23', '2022-05-26 10:48:23');
INSERT INTO `message_notification` VALUES (739941263544942592, 1518946330134302721, '加藤惠', 710848206937784320, '1', 1, 710848206937784320, '11', '关于俄乌冲突的分析1', '1', 1, '2022-06-04 20:22:25', '2022-06-04 20:22:25');
INSERT INTO `message_notification` VALUES (739942982567854081, 1518946330134302721, '加藤惠', 710848206937784320, '1', 1, 710910792676933632, '13', '那必须', '2', 1, '2022-06-04 20:29:15', '2022-06-04 20:29:15');
INSERT INTO `message_notification` VALUES (739949563158200320, 1518946330134302721, '加藤惠', 712402279407288321, '2', 1, 712404143410511872, '22', '全体抵制996,资本家不应该惯着', '1', 1, '2022-06-04 20:55:23', '2022-06-04 20:55:23');
INSERT INTO `message_notification` VALUES (739949801398861825, 1518946330134302721, '加藤惠', 712402279407288321, '2', 1, 712404143410511872, '22', '全体抵制996,资本家不应该惯着', '2', 1, '2022-06-04 20:56:20', '2022-06-04 20:56:20');
INSERT INTO `message_notification` VALUES (739953391043608576, 1518946330134302721, '加藤惠', 712402279407288321, '2', 1, 712404640716554240, '23', '说得好,不能惯着', '1', 1, '2022-06-04 21:10:36', '2022-06-04 21:10:36');
INSERT INTO `message_notification` VALUES (740317939978207232, 1518946330134302721, '加藤惠', 731868652755746816, '1', 1518946330134302721, 731868652755746816, '11', '米种名你教', '1', 1, '2022-06-05 21:19:11', '2022-06-05 21:19:11');

-- ----------------------------
-- Table structure for private_letter
-- ----------------------------
DROP TABLE IF EXISTS `private_letter`;
CREATE TABLE `private_letter`  (
  `letter_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `target_uid` bigint NOT NULL,
  `simple_content` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `look_up` tinyint NOT NULL COMMENT '是否已读',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`letter_id`) USING BTREE,
  UNIQUE INDEX `creator_list_index`(`target_uid`, `uid`, `status`, `avatar`, `nickname`, `created_time`) USING BTREE,
  INDEX `creator_unread_count_index`(`uid`, `target_uid`, `look_up`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of private_letter
-- ----------------------------

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `topic_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称冗余',
  `profile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '个人简介冗余',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `simple_description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_picture` tinyint(1) NOT NULL COMMENT '图片类型的话题',
  `main_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `label_id` json NOT NULL,
  `topping` json NULL COMMENT '置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (712402279407288321, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '打工人该不该躺平?', '打工人该不该躺平,拒绝996', '打工人该不该躺平,拒绝996；打工人该不该躺平,拒绝996；打工人该不该躺平,拒绝996；打工人该不该躺平,拒绝996；打工人该不该躺平,拒绝996；打工人该不该躺平,拒绝996；', 0, '', '[710975262845566976, 710975274044358657]', NULL, 0, '2022-03-20 20:32:19', '2022-03-20 20:32:19');
INSERT INTO `topic` VALUES (712402296050286592, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '打工人该不该躺平?', '打工人该不该躺平,拒绝996', '打工人该不该躺平,拒绝996', 0, '', '[710975262845566976, 710975274044358657]', NULL, 0, '2022-03-20 20:32:23', '2022-03-20 20:32:23');
INSERT INTO `topic` VALUES (712402298944356353, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '打工人该不该躺平?', '打工人该不该躺平,拒绝996', '打工人该不该躺平,拒绝996', 0, '', '[710975262845566976, 710975274044358657]', NULL, 1, '2022-03-20 20:32:24', '2022-03-20 20:32:24');
INSERT INTO `topic` VALUES (712402302140416000, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', '考公务员真有这么香?', '打工人该不该躺平,拒绝996', '打工人该不该躺平,拒绝996', 0, '', '[710975262845566976, 710975274044358657]', NULL, 1, '2022-03-20 20:32:25', '2022-03-20 20:32:25');
INSERT INTO `topic` VALUES (716616937601236993, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '如何看待「流调中最辛苦的中国人」？', '1月19日，在北京市召开的第269场新冠疫情防控新闻发布会上', '1月19日，在北京市召开的第269场新冠疫情防控新闻发布会上', 0, NULL, '[710975274044358657, 710975277768900608]', NULL, 0, '2022-04-01 11:39:52', '2022-04-01 11:39:52');
INSERT INTO `topic` VALUES (716617006513651712, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', '如何看待「流调中最辛苦的中国人」？', '1月19日，在北京市召开的第269场新冠疫情防控新闻发布会上', '1月19日，在北京市召开的第269场新冠疫情防控新闻发布会上', 0, NULL, '[]', NULL, 1, '2022-04-01 11:40:08', '2022-04-01 11:40:08');

-- ----------------------------
-- Table structure for topic_discuss
-- ----------------------------
DROP TABLE IF EXISTS `topic_discuss`;
CREATE TABLE `topic_discuss`  (
  `discuss_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称冗余',
  `profile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '个人简介冗余',
  `topic_id` bigint NOT NULL,
  `simple_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `topping` json NULL COMMENT '置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）',
  `status` tinyint(1) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`discuss_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_discuss
-- ----------------------------
INSERT INTO `topic_discuss` VALUES (712404143410511872, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:44', '2022-03-20 20:39:44');
INSERT INTO `topic_discuss` VALUES (712404154688995329, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402302140416000, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:46', '2022-03-20 20:39:46');
INSERT INTO `topic_discuss` VALUES (712404155519467520, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404156295413761, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404157046194176, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404157729865729, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404158455480320, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404159101403137, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:47', '2022-03-20 20:39:47');
INSERT INTO `topic_discuss` VALUES (712404159919292416, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (712404160816873473, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (712404161479573504, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (712404162133884929, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (712404162729476096, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (712404163186655233, 1, 'https://file.heartape.com/picture/avatar-1.jpg', 'nickname', 'profile', 712402279407288321, '全体抵制996,资本家不应该惯着', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>1全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>2全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>3全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>4全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>5全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>6全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>7全体抵制996,资本家不应该惯着。</p>\n<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>8全体抵制996,资本家不应该惯着。</p>', NULL, 1, '2022-03-20 20:39:48', '2022-03-20 20:39:48');
INSERT INTO `topic_discuss` VALUES (716618820613046273, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', 716617006513651712, '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;另外增长率不同于R0，虽然两者都可以用来描述传染性的强弱。其实也有很多种，比如在之前广东CDC发表的讲去年广州疫情的论文中，用的', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>另外增长率不同于R0，虽然两者都可以用来描述传染性的强弱。其实也有很多种，比如在之前广东CDC发表的讲去年广州疫情的论文中，用的就是epidemic growth rate（r），r=[R0 -1]/GT）。所以R0反映的是病毒的内生传染性，在现实当中我们会用Rt更准确一些（t=time），体现各种防疫措施对病毒实时传染性的影响。Rt和r都是比较容易监测的，也都反映了病毒在真实世界的传染性，但它们和R0确实是不同的。目前WHO和英国都未将其单独升级为一种VUI或VOC，说明影响力暂时没有那么高（就像之前一度在自媒体上炒得很火的Deltacron一样），目前只需要科研人员密切监测即可。</p>', NULL, 1, '2022-04-01 11:47:21', '2022-04-01 11:47:21');
INSERT INTO `topic_discuss` VALUES (716619862826287104, 1, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', '路人女主', 716617006513651712, '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;另外增长率不同于R0，虽然两者都可以用来描述传染性的强弱。其实也有很多种，比如在之前广东CDC发表的讲去年广州疫情的论文中，用的', '<p><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span><span class=\"mce-nbsp-wrap\" contenteditable=\"false\">&nbsp;&nbsp;&nbsp;</span>另外增长率不同于R0，虽然两者都可以用来描述传染性的强弱。其实也有很多种，比如在之前广东CDC发表的讲去年广州疫情的论文中，用的就是epidemic growth rate（r），r=[R0 -1]/GT）。所以R0反映的是病毒的内生传染性，在现实当中我们会用Rt更准确一些（t=time），体现各种防疫措施对病毒实时传染性的影响。Rt和r都是比较容易监测的，也都反映了病毒在真实世界的传染性，但它们和R0确实是不同的。目前WHO和英国都未将其单独升级为一种VUI或VOC，说明影响力暂时没有那么高（就像之前一度在自媒体上炒得很火的Deltacron一样），目前只需要科研人员密切监测即可。</p>', NULL, 1, '2022-04-01 11:51:29', '2022-04-01 11:51:29');

-- ----------------------------
-- Table structure for topic_follow
-- ----------------------------
DROP TABLE IF EXISTS `topic_follow`;
CREATE TABLE `topic_follow`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `topic_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_follow
-- ----------------------------

-- ----------------------------
-- Procedure structure for private_letter_data
-- ----------------------------
DROP PROCEDURE IF EXISTS `private_letter_data`;
delimiter ;;
CREATE PROCEDURE `private_letter_data`()
BEGIN
declare i int default 2;
declare j int default 0;
 
 repeat
 set i = i + 1;
 set j = i / 10 + 1;
 INSERT INTO `private_letter` VALUES (718512535782817794 + i, i, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', j, '这是给1的私信', '这是给1的私信', 0, 1, i, '2022-04-06 17:14:17', i, '2022-04-06 17:12:16');
 until i = 1000
end repeat;
 commit;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for private_letter_data_copy1_copy1
-- ----------------------------
DROP PROCEDURE IF EXISTS `private_letter_data_copy1_copy1`;
delimiter ;;
CREATE PROCEDURE `private_letter_data_copy1_copy1`()
BEGIN
declare i int default 2;
declare j int default 0;
 
 repeat
 set i = i + 1;
 set j = i % 100 + 1;
 INSERT INTO `private_letter` VALUES (718512535782817790 + i, i, 'https://file.heartape.com/picture/avatar-1.jpg', '加藤惠', j, '这是给1的私信', '这是给1的私信', 0, 1, i, '2022-04-06 17:14:17', i, '2022-04-06 17:12:16');
 until i = 1000000
end repeat;
 commit;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
