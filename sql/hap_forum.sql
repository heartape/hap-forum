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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow_creator_relation
-- ----------------------------
DROP TABLE IF EXISTS `follow_creator_relation`;
CREATE TABLE `follow_creator_relation`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `target_id` int NOT NULL,
  `special` tinyint(1) NOT NULL COMMENT '是否特别关注',
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow_label_relation
-- ----------------------------
DROP TABLE IF EXISTS `follow_label_relation`;
CREATE TABLE `follow_label_relation`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `label_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow_topic_relation
-- ----------------------------
DROP TABLE IF EXISTS `follow_topic_relation`;
CREATE TABLE `follow_topic_relation`  (
  `follow_id` int NOT NULL,
  `uid` int NOT NULL,
  `topic_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `label_id` bigint NOT NULL,
  `level` int NOT NULL COMMENT '最多3级',
  `parent_id` json NOT NULL,
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `main_picture` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主图',
  `simple_introduce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduce` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`guide_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  `created_by` bigint NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_by` bigint NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`discuss_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
