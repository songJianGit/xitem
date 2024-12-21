/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : xitem

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 21/12/2024 16:11:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner`  (
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `release_status` int NULL DEFAULT NULL COMMENT '发布状态（0-初始 1-发布 2-下架）',
  `seq` bigint NULL DEFAULT NULL COMMENT '排序字段（存的时间戳，用作排序）',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_banner
-- ----------------------------
INSERT INTO `t_banner` VALUES ('/fileinfo/bannerimg/2024/06/01/1e1a2b5e524c4559a564e326237163fe.jpg', 1, 1717238127014, '1796852281446457346', '1', '2024-06-01 18:32:22', '1', '1', '2024-06-01 18:44:21', 1);
INSERT INTO `t_banner` VALUES ('/fileinfo/bannerimg/2024/06/01/9ed71747e86f4493afcc96066a1de055.jpg', 1, 1717894991588, '1796852304049561602', '1', '2024-06-01 18:32:27', '1', '1', '2024-06-09 14:17:44', 1);
INSERT INTO `t_banner` VALUES ('/fileinfo/bannerimg/2024/06/01/f0d29aab5d2446879706fe92634eb87a.jpg', 1, 1717237947856, '1796853055509454850', '1', '2024-06-01 18:35:27', '1', '1', '2024-06-01 18:44:15', 1);

-- ----------------------------
-- Table structure for t_cat_category
-- ----------------------------
DROP TABLE IF EXISTS `t_cat_category`;
CREATE TABLE `t_cat_category`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `pids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id集合',
  `seq` bigint NULL DEFAULT NULL COMMENT '排序(时间戳)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_pid`(`pid`) USING BTREE,
  INDEX `actable_idx_pids`(`pids`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公用分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cat_category
-- ----------------------------
INSERT INTO `t_cat_category` VALUES ('羽毛球', '3', 'course,3', 3, '1799997774250815490', '1', '2024-06-10 10:51:26', '1', '1', '2024-06-22 15:14:47', 1);
INSERT INTO `t_cat_category` VALUES ('语文', 'question', 'question', 1717991118790, '1800011332002951169', '1', '2024-06-10 11:45:18', '1', '1', '2024-06-22 15:13:32', 1);
INSERT INTO `t_cat_category` VALUES ('数学', 'question', 'question', 1717991124342, '1800011355235201026', '1', '2024-06-10 11:45:24', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('综合', 'question', 'question', 1717991132750, '1800011390492520449', '1', '2024-06-10 11:45:32', '1', '1', '2024-06-22 15:13:32', 1);
INSERT INTO `t_cat_category` VALUES ('一年级数学', '1800011355235201026', 'question,1800011355235201026', 1717991144517, '1800011439859478530', '1', '2024-06-10 11:45:44', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('二年级数学', '1800011355235201026', 'question,1800011355235201026', 1719040401246, '1804412341634662402', '1', '2024-06-22 15:13:21', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('足球', '3', 'course,3', 1719040496268, '1804412740080959491', '1', '2024-06-22 15:14:56', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('绘画', '2', 'course,2', 1719040513258, '1804412811317018627', '1', '2024-06-22 15:15:13', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('舞蹈', '2', 'course,2', 1719040526466, '1804412866757328897', '1', '2024-06-22 15:15:26', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('综合类', 'course', 'course', 1719051820030, '1804460235439894530', '1', '2024-06-22 18:23:40', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('艺术课程', 'course', 'course', 2, '2', '1', '2024-06-10 10:36:00', '1', '1', '2024-06-22 15:14:22', 1);
INSERT INTO `t_cat_category` VALUES ('体育课程', 'course', 'course', 1717987886370, '3', '1', '2024-06-10 10:36:00', '1', '1', '2024-06-22 15:14:33', 1);
INSERT INTO `t_cat_category` VALUES ('课程目录', '0', NULL, 1, 'course', '1', '2024-06-10 10:36:00', '1', NULL, NULL, 1);
INSERT INTO `t_cat_category` VALUES ('题目目录', '0', NULL, 1, 'question', '1', '2024-06-10 10:36:00', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_cus_course
-- ----------------------------
DROP TABLE IF EXISTS `t_cus_course`;
CREATE TABLE `t_cus_course`  (
  `course_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程分类',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `learn_time` int NULL DEFAULT NULL COMMENT '课程时长（分钟）',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程封面',
  `seq` bigint NULL DEFAULT NULL COMMENT '排序字段',
  `release_status` int NULL DEFAULT NULL COMMENT '发布状态（0-初始 1-发布 2-下架）',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `course_file_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课件资源id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE,
  INDEX `actable_idx_title`(`title`) USING BTREE,
  INDEX `actable_idx_release_status`(`release_status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cus_course
-- ----------------------------
INSERT INTO `t_cus_course` VALUES ('1804460235439894530', '视频课程', 15, NULL, 1719051845044, 1, '1804460340352020482', '1', '2024-06-22 18:24:05', '1', '1', '2024-06-22 18:27:56', 1, '1807214774953959425', '这是一个视频课程');
INSERT INTO `t_cus_course` VALUES ('1804460235439894530', 'pdf课程', 10, NULL, 1719052073801, 1, '1804461299752923138', '1', '2024-06-22 18:27:53', '1', '1', '2024-06-22 18:27:56', 1, '1807217019321835521', '这是一个pdf课程');

-- ----------------------------
-- Table structure for t_cus_course_file
-- ----------------------------
DROP TABLE IF EXISTS `t_cus_course_file`;
CREATE TABLE `t_cus_course_file`  (
  `course_file_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课件分类',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课件标题',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课件备注',
  `course_type` int NULL DEFAULT NULL COMMENT '课件类型|1:scorm,2:video,3:website,5:office或pdf类型',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cus_course_file
-- ----------------------------
INSERT INTO `t_cus_course_file` VALUES (NULL, '视频课件', '这是一个视频课件', 2, '1807214774953959425', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1);
INSERT INTO `t_cus_course_file` VALUES (NULL, 'pdf课件', '这是一个pdf课件', 5, '1807217019321835521', '1', '2024-06-25 08:58:08', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_cus_course_file_item
-- ----------------------------
DROP TABLE IF EXISTS `t_cus_course_file_item`;
CREATE TABLE `t_cus_course_file_item`  (
  `course_file_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课件id',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE,
  INDEX `actable_idx_course_file_id_seq`(`course_file_id`, `seq`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课件资源清单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cus_course_file_item
-- ----------------------------
INSERT INTO `t_cus_course_file_item` VALUES ('1807214774953959425', 0, '1807214775021068289', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/def/2024/06/30/b3e0a88acf6846169e21a3f5996a61f2.mp4');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 0, '1807217028243120129', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img0.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 1, '1807217028243120130', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img1.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 2, '1807217028243120131', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img2.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 3, '1807217028243120132', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img3.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 4, '1807217028243120133', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img4.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 5, '1807217028243120134', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img5.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 6, '1807217028243120135', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img6.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 7, '1807217028243120136', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img7.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 8, '1807217028243120137', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img8.png');
INSERT INTO `t_cus_course_file_item` VALUES ('1807217019321835521', 9, '1807217028243120138', '1', '2024-06-25 08:49:13', '1', NULL, NULL, 1, '/fileinfo/coursepdfimg/2024/06/30/1807217019321835521/img9.png');

-- ----------------------------
-- Table structure for t_cus_course_user
-- ----------------------------
DROP TABLE IF EXISTS `t_cus_course_user`;
CREATE TABLE `t_cus_course_user`  (
  `course_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程id',
  `total` int NULL DEFAULT NULL COMMENT '学习总时长（秒）',
  `precent` int NULL DEFAULT NULL COMMENT '进度（百分比）',
  `complete_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'precent达到100的时间',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_create_user_id_course_id_status`(`create_user_id`, `course_id`, `status`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户学习记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cus_course_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_ex_exam
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_exam`;
CREATE TABLE `t_ex_exam`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考试标题',
  `duration` int NULL DEFAULT NULL COMMENT '考试时长（分钟）',
  `max_num` int NULL DEFAULT NULL COMMENT '用户的最大考试次数',
  `paper_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷id',
  `release_status` int NULL DEFAULT NULL COMMENT '发布状态（0-初始 1-发布 2-下架）',
  `ex_type` int NULL DEFAULT NULL COMMENT '考试类型（1-公开考试 0-授权考试）',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `stime` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `etime` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE,
  INDEX `actable_idx_release_status`(`release_status`) USING BTREE,
  INDEX `actable_idx_ex_type`(`ex_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考试表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_exam
-- ----------------------------
INSERT INTO `t_ex_exam` VALUES ('数学考试', 20, -1, '1805174726469238786', 1, 1, '1805175158977478657', '1', '2024-06-24 17:44:31', '1', '1', '2024-06-24 17:44:40', 1, '2024-06-24 17:44:08', '2024-06-30 17:44:12');

-- ----------------------------
-- Table structure for t_ex_exam_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_exam_auth`;
CREATE TABLE `t_ex_exam_auth`  (
  `exam_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考试id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_user_id_exam_id`(`user_id`, `exam_id`) USING BTREE,
  INDEX `actable_idx_exam_id`(`exam_id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考试授权' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_exam_auth
-- ----------------------------

-- ----------------------------
-- Table structure for t_ex_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_paper`;
CREATE TABLE `t_ex_paper`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷标题',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_paper
-- ----------------------------
INSERT INTO `t_ex_paper` VALUES ('一年级数学试卷（100题）', '1805174726469238786', '1', '2024-06-24 17:42:47', '1', '1', '2024-06-24 17:43:51', 1);

-- ----------------------------
-- Table structure for t_ex_qrs
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_qrs`;
CREATE TABLE `t_ex_qrs`  (
  `qr_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则id',
  `qid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题id',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `score` double(7, 2) NULL DEFAULT NULL COMMENT '题目分值',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_qr_id_qid`(`qr_id`, `qid`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '规则-题目-多对多' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_qrs
-- ----------------------------
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869503002', 1, 1.00, '1805174822103564290', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869502997', 2, 1.00, '1805174822170673153', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869502992', 3, 1.00, '1805174822170673154', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869502987', 4, 1.00, '1805174822170673155', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869502982', 5, 1.00, '1805174822170673156', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368869502977', 6, 1.00, '1805174822170673157', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199861', 7, 1.00, '1805174822170673158', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199856', 8, 1.00, '1805174822170673159', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199851', 9, 1.00, '1805174822170673160', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199846', 10, 1.00, '1805174822170673161', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199841', 11, 1.00, '1805174822170673162', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199836', 12, 1.00, '1805174822170673163', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199831', 13, 1.00, '1805174822170673164', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199826', 14, 1.00, '1805174822170673165', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199821', 15, 1.00, '1805174822170673166', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199816', 16, 1.00, '1805174822170673167', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368798199811', 17, 1.00, '1805174822170673168', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091026', 18, 1.00, '1805174822170673169', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091021', 19, 1.00, '1805174822170673170', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091016', 20, 1.00, '1805174822170673171', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091011', 21, 1.00, '1805174822170673172', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091006', 22, 1.00, '1805174822170673173', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731091001', 23, 1.00, '1805174822170673174', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090996', 24, 1.00, '1805174822170673175', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090991', 25, 1.00, '1805174822170673176', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090986', 26, 1.00, '1805174822170673177', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090981', 27, 1.00, '1805174822170673178', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090976', 28, 1.00, '1805174822170673179', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090971', 29, 1.00, '1805174822170673180', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090966', 30, 1.00, '1805174822170673181', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090961', 31, 1.00, '1805174822170673182', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090956', 32, 1.00, '1805174822170673183', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090951', 33, 1.00, '1805174822170673184', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368731090946', 34, 1.00, '1805174822170673185', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176444', 35, 1.00, '1805174822170673186', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176439', 36, 1.00, '1805174822170673187', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176434', 37, 1.00, '1805174822170673188', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176429', 38, 1.00, '1805174822170673189', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176424', 39, 1.00, '1805174822170673190', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176419', 40, 1.00, '1805174822170673191', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176414', 41, 1.00, '1805174822170673192', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176409', 42, 1.00, '1805174822170673193', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176404', 43, 1.00, '1805174822170673194', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176399', 44, 1.00, '1805174822170673195', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176394', 45, 1.00, '1805174822170673196', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368668176389', 46, 1.00, '1805174822170673197', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067561', 47, 1.00, '1805174822170673198', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067556', 48, 1.00, '1805174822170673199', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067551', 49, 1.00, '1805174822170673200', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067546', 50, 1.00, '1805174822170673201', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067541', 51, 1.00, '1805174822170673202', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067536', 52, 1.00, '1805174822170673203', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067531', 53, 1.00, '1805174822170673204', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368601067526', 54, 1.00, '1805174822170673205', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958701', 55, 1.00, '1805174822170673206', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958696', 56, 1.00, '1805174822170673207', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958691', 57, 1.00, '1805174822170673208', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958686', 58, 1.00, '1805174822170673209', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958681', 59, 1.00, '1805174822170673210', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958676', 60, 1.00, '1805174822170673211', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958671', 61, 1.00, '1805174822170673212', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958666', 62, 1.00, '1805174822170673213', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368533958661', 63, 1.00, '1805174822170673214', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849849', 64, 1.00, '1805174822170673215', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849844', 65, 1.00, '1805174822170673216', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849839', 66, 1.00, '1805174822170673217', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849834', 67, 1.00, '1805174822170673218', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849829', 68, 1.00, '1805174822170673219', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849824', 69, 1.00, '1805174822170673220', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849819', 70, 1.00, '1805174822170673221', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849814', 71, 1.00, '1805174822170673222', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849809', 72, 1.00, '1805174822170673223', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849804', 73, 1.00, '1805174822170673224', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849799', 74, 1.00, '1805174822170673225', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368466849794', 75, 1.00, '1805174822170673226', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935290', 76, 1.00, '1805174822170673227', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935285', 77, 1.00, '1805174822170673228', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935280', 78, 1.00, '1805174822170673229', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935275', 79, 1.00, '1805174822170673230', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935270', 80, 1.00, '1805174822170673231', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935265', 81, 1.00, '1805174822170673232', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935260', 82, 1.00, '1805174822170673233', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935255', 83, 1.00, '1805174822170673234', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935250', 84, 1.00, '1805174822170673235', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935245', 85, 1.00, '1805174822170673236', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935240', 86, 1.00, '1805174822170673237', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368403935235', 87, 1.00, '1805174822170673238', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826415', 88, 1.00, '1805174822170673239', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826410', 89, 1.00, '1805174822170673240', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826405', 90, 1.00, '1805174822170673241', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826400', 91, 1.00, '1805174822170673242', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826395', 92, 1.00, '1805174822170673243', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826390', 93, 1.00, '1805174822170673244', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826385', 94, 1.00, '1805174822170673245', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826380', 95, 1.00, '1805174822170673246', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826375', 96, 1.00, '1805174822170673247', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805173368336826370', 97, 1.00, '1805174822170673248', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805169815069278211', 98, 1.00, '1805174822170673249', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805169552551985154', 99, 1.00, '1805174822170673250', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);
INSERT INTO `t_ex_qrs` VALUES ('1805174740532740098', '1805169264428466177', 100, 1.00, '1805174822170673251', '1', '2024-06-24 17:43:10', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ex_question
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_question`;
CREATE TABLE `t_ex_question`  (
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目标题',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `qtype` int NULL DEFAULT NULL COMMENT '题目类型(0-是非 1-单选 2-多选)',
  `qcategory` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题目分类（字典id）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考试的题目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_question
-- ----------------------------
INSERT INTO `t_ex_question` VALUES ('100-20-12的得数(     )。', '1805169264428466177', '1', '2024-06-24 17:21:05', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('比39多24的数是', '1805169552551985154', '1', '2024-06-24 17:22:14', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('(     )-16=24+18', '1805169815069278211', '1', '2024-06-24 17:23:17', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('1+3+5=(     )。', '1805173368336826370', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('2+3+5=(     )。', '1805173368336826375', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('3+3+5=(     )。', '1805173368336826380', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('4+3+5=(     )。', '1805173368336826385', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('5+3+5=(     )。', '1805173368336826390', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('6+3+5=(     )。', '1805173368336826395', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('7+3+5=(     )。', '1805173368336826400', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('8+3+5=(     )。', '1805173368336826405', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('9+3+5=(     )。', '1805173368336826410', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('10+3+5=(     )。', '1805173368336826415', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('11+3+5=(     )。', '1805173368403935235', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('12+3+5=(     )。', '1805173368403935240', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('13+3+5=(     )。', '1805173368403935245', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('14+3+5=(     )。', '1805173368403935250', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('15+3+5=(     )。', '1805173368403935255', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('16+3+5=(     )。', '1805173368403935260', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('17+3+5=(     )。', '1805173368403935265', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('18+3+5=(     )。', '1805173368403935270', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('19+3+5=(     )。', '1805173368403935275', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('20+3+5=(     )。', '1805173368403935280', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('21+3+5=(     )。', '1805173368403935285', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('22+3+5=(     )。', '1805173368403935290', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('23+3+5=(     )。', '1805173368466849794', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('24+3+5=(     )。', '1805173368466849799', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('25+3+5=(     )。', '1805173368466849804', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('26+3+5=(     )。', '1805173368466849809', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('27+3+5=(     )。', '1805173368466849814', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('28+3+5=(     )。', '1805173368466849819', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('29+3+5=(     )。', '1805173368466849824', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('30+3+5=(     )。', '1805173368466849829', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('31+3+5=(     )。', '1805173368466849834', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('32+3+5=(     )。', '1805173368466849839', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('33+3+5=(     )。', '1805173368466849844', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('34+3+5=(     )。', '1805173368466849849', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('35+3+5=(     )。', '1805173368533958661', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('36+3+5=(     )。', '1805173368533958666', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('37+3+5=(     )。', '1805173368533958671', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('38+3+5=(     )。', '1805173368533958676', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('39+3+5=(     )。', '1805173368533958681', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('40+3+5=(     )。', '1805173368533958686', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('41+3+5=(     )。', '1805173368533958691', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('42+3+5=(     )。', '1805173368533958696', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('43+3+5=(     )。', '1805173368533958701', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('44+3+5=(     )。', '1805173368601067526', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('45+3+5=(     )。', '1805173368601067531', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('46+3+5=(     )。', '1805173368601067536', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('47+3+5=(     )。', '1805173368601067541', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('48+3+5=(     )。', '1805173368601067546', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('49+3+5=(     )。', '1805173368601067551', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('50+3+5=(     )。', '1805173368601067556', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('51+3+5=(     )。', '1805173368601067561', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('52+3+5=(     )。', '1805173368668176389', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('53+3+5=(     )。', '1805173368668176394', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('54+3+5=(     )。', '1805173368668176399', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('55+3+5=(     )。', '1805173368668176404', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('56+3+5=(     )。', '1805173368668176409', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('57+3+5=(     )。', '1805173368668176414', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('58+3+5=(     )。', '1805173368668176419', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('59+3+5=(     )。', '1805173368668176424', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('60+3+5=(     )。', '1805173368668176429', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('61+3+5=(     )。', '1805173368668176434', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('62+3+5=(     )。', '1805173368668176439', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('63+3+5=(     )。', '1805173368668176444', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('64+3+5=(     )。', '1805173368731090946', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('65+3+5=(     )。', '1805173368731090951', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('66+3+5=(     )。', '1805173368731090956', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('67+3+5=(     )。', '1805173368731090961', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('68+3+5=(     )。', '1805173368731090966', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('69+3+5=(     )。', '1805173368731090971', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('70+3+5=(     )。', '1805173368731090976', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('71+3+5=(     )。', '1805173368731090981', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('72+3+5=(     )。', '1805173368731090986', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('73+3+5=(     )。', '1805173368731090991', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('74+3+5=(     )。', '1805173368731090996', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('75+3+5=(     )。', '1805173368731091001', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('76+3+5=(     )。', '1805173368731091006', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('77+3+5=(     )。', '1805173368731091011', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('78+3+5=(     )。', '1805173368731091016', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('79+3+5=(     )。', '1805173368731091021', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('80+3+5=(     )。', '1805173368731091026', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('81+3+5=(     )。', '1805173368798199811', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('82+3+5=(     )。', '1805173368798199816', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('83+3+5=(     )。', '1805173368798199821', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('84+3+5=(     )。', '1805173368798199826', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('85+3+5=(     )。', '1805173368798199831', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('86+3+5=(     )。', '1805173368798199836', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('87+3+5=(     )。', '1805173368798199841', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('88+3+5=(     )。', '1805173368798199846', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('89+3+5=(     )。', '1805173368798199851', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('90+3+5=(     )。', '1805173368798199856', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('91+3+5=(     )。', '1805173368798199861', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('92+3+5=(     )。', '1805173368869502977', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('93+3+5=(     )。', '1805173368869502982', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('94+3+5=(     )。', '1805173368869502987', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('95+3+5=(     )。', '1805173368869502992', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('96+3+5=(     )。', '1805173368869502997', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('97+3+5=(     )。', '1805173368869503002', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1, 1, '1800011439859478530');
INSERT INTO `t_ex_question` VALUES ('鲁迅的真名是什么？', '1805174123894554627', '1', '2024-06-24 17:40:24', '1', NULL, NULL, 1, 1, '1800011332002951169');

-- ----------------------------
-- Table structure for t_ex_question_option
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_question_option`;
CREATE TABLE `t_ex_question_option`  (
  `qid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题目id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项标题',
  `option_right` int NULL DEFAULT NULL COMMENT '是否正确答案(0-否 1-是)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_qid`(`qid`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目的选项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_question_option
-- ----------------------------
INSERT INTO `t_ex_question_option` VALUES ('1805169264428466177', '大于70', 0, '1805169264495575041', '1', '2024-06-24 17:21:05', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169264428466177', '等于70', 0, '1805169264495575042', '1', '2024-06-24 17:21:05', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169264428466177', '小于70', 1, '1805169264495575043', '1', '2024-06-24 17:21:05', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169552551985154', '63', 1, '1805169552551985155', '1', '2024-06-24 17:22:14', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169552551985154', '53', 0, '1805169552551985156', '1', '2024-06-24 17:22:14', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169552551985154', '15', 0, '1805169552551985157', '1', '2024-06-24 17:22:14', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169815069278211', '48', 0, '1805169815069278212', '1', '2024-06-24 17:23:17', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169815069278211', '27', 0, '1805169815069278213', '1', '2024-06-24 17:23:17', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805169815069278211', '58', 1, '1805169815069278214', '1', '2024-06-24 17:23:17', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826370', '10', 0, '1805173368336826371', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826370', '2', 0, '1805173368336826372', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826370', '5', 0, '1805173368336826373', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826370', '9', 1, '1805173368336826374', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826375', '11', 0, '1805173368336826376', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826375', '2', 0, '1805173368336826377', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826375', '5', 0, '1805173368336826378', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826375', '10', 1, '1805173368336826379', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826380', '12', 0, '1805173368336826381', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826380', '2', 0, '1805173368336826382', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826380', '5', 0, '1805173368336826383', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826380', '11', 1, '1805173368336826384', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826385', '13', 0, '1805173368336826386', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826385', '2', 0, '1805173368336826387', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826385', '5', 0, '1805173368336826388', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826385', '12', 1, '1805173368336826389', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826390', '9', 0, '1805173368336826391', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826390', '2', 0, '1805173368336826392', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826390', '5', 0, '1805173368336826393', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826390', '13', 1, '1805173368336826394', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826395', '13', 0, '1805173368336826396', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826395', '2', 0, '1805173368336826397', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826395', '5', 0, '1805173368336826398', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826395', '14', 1, '1805173368336826399', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826400', '13', 0, '1805173368336826401', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826400', '2', 0, '1805173368336826402', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826400', '5', 0, '1805173368336826403', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826400', '15', 1, '1805173368336826404', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826405', '13', 0, '1805173368336826406', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826405', '2', 0, '1805173368336826407', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826405', '5', 0, '1805173368336826408', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826405', '16', 1, '1805173368336826409', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826410', '13', 0, '1805173368336826411', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826410', '2', 0, '1805173368336826412', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826410', '5', 0, '1805173368336826413', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826410', '17', 1, '1805173368336826414', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826415', '13', 0, '1805173368336826416', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826415', '2', 0, '1805173368336826417', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826415', '5', 0, '1805173368336826418', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368336826415', '18', 1, '1805173368403935234', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935235', '13', 0, '1805173368403935236', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935235', '2', 0, '1805173368403935237', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935235', '5', 0, '1805173368403935238', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935235', '19', 1, '1805173368403935239', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935240', '13', 0, '1805173368403935241', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935240', '2', 0, '1805173368403935242', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935240', '5', 0, '1805173368403935243', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935240', '20', 1, '1805173368403935244', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935245', '13', 0, '1805173368403935246', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935245', '2', 0, '1805173368403935247', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935245', '5', 0, '1805173368403935248', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935245', '21', 1, '1805173368403935249', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935250', '13', 0, '1805173368403935251', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935250', '2', 0, '1805173368403935252', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935250', '5', 0, '1805173368403935253', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935250', '22', 1, '1805173368403935254', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935255', '13', 0, '1805173368403935256', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935255', '2', 0, '1805173368403935257', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935255', '5', 0, '1805173368403935258', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935255', '23', 1, '1805173368403935259', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935260', '13', 0, '1805173368403935261', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935260', '2', 0, '1805173368403935262', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935260', '5', 0, '1805173368403935263', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935260', '24', 1, '1805173368403935264', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935265', '13', 0, '1805173368403935266', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935265', '2', 0, '1805173368403935267', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935265', '5', 0, '1805173368403935268', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935265', '25', 1, '1805173368403935269', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935270', '13', 0, '1805173368403935271', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935270', '2', 0, '1805173368403935272', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935270', '5', 0, '1805173368403935273', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935270', '26', 1, '1805173368403935274', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935275', '13', 0, '1805173368403935276', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935275', '2', 0, '1805173368403935277', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935275', '5', 0, '1805173368403935278', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935275', '27', 1, '1805173368403935279', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935280', '13', 0, '1805173368403935281', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935280', '2', 0, '1805173368403935282', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935280', '5', 0, '1805173368403935283', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935280', '28', 1, '1805173368403935284', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935285', '13', 0, '1805173368403935286', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935285', '2', 0, '1805173368403935287', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935285', '5', 0, '1805173368403935288', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935285', '29', 1, '1805173368403935289', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935290', '13', 0, '1805173368403935291', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935290', '2', 0, '1805173368403935292', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935290', '5', 0, '1805173368403935293', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368403935290', '30', 1, '1805173368403935294', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849794', '13', 0, '1805173368466849795', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849794', '2', 0, '1805173368466849796', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849794', '5', 0, '1805173368466849797', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849794', '31', 1, '1805173368466849798', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849799', '13', 0, '1805173368466849800', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849799', '2', 0, '1805173368466849801', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849799', '5', 0, '1805173368466849802', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849799', '32', 1, '1805173368466849803', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849804', '13', 0, '1805173368466849805', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849804', '2', 0, '1805173368466849806', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849804', '5', 0, '1805173368466849807', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849804', '33', 1, '1805173368466849808', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849809', '13', 0, '1805173368466849810', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849809', '2', 0, '1805173368466849811', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849809', '5', 0, '1805173368466849812', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849809', '34', 1, '1805173368466849813', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849814', '13', 0, '1805173368466849815', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849814', '2', 0, '1805173368466849816', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849814', '5', 0, '1805173368466849817', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849814', '35', 1, '1805173368466849818', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849819', '13', 0, '1805173368466849820', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849819', '2', 0, '1805173368466849821', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849819', '5', 0, '1805173368466849822', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849819', '36', 1, '1805173368466849823', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849824', '13', 0, '1805173368466849825', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849824', '2', 0, '1805173368466849826', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849824', '5', 0, '1805173368466849827', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849824', '37', 1, '1805173368466849828', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849829', '13', 0, '1805173368466849830', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849829', '2', 0, '1805173368466849831', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849829', '5', 0, '1805173368466849832', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849829', '38', 1, '1805173368466849833', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849834', '13', 0, '1805173368466849835', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849834', '2', 0, '1805173368466849836', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849834', '5', 0, '1805173368466849837', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849834', '39', 1, '1805173368466849838', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849839', '13', 0, '1805173368466849840', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849839', '2', 0, '1805173368466849841', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849839', '5', 0, '1805173368466849842', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849839', '40', 1, '1805173368466849843', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849844', '13', 0, '1805173368466849845', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849844', '2', 0, '1805173368466849846', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849844', '5', 0, '1805173368466849847', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849844', '41', 1, '1805173368466849848', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849849', '13', 0, '1805173368466849850', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849849', '2', 0, '1805173368533958658', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849849', '5', 0, '1805173368533958659', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368466849849', '42', 1, '1805173368533958660', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958661', '13', 0, '1805173368533958662', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958661', '2', 0, '1805173368533958663', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958661', '5', 0, '1805173368533958664', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958661', '43', 1, '1805173368533958665', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958666', '13', 0, '1805173368533958667', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958666', '2', 0, '1805173368533958668', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958666', '5', 0, '1805173368533958669', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958666', '44', 1, '1805173368533958670', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958671', '13', 0, '1805173368533958672', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958671', '2', 0, '1805173368533958673', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958671', '5', 0, '1805173368533958674', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958671', '45', 1, '1805173368533958675', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958676', '13', 0, '1805173368533958677', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958676', '2', 0, '1805173368533958678', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958676', '5', 0, '1805173368533958679', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958676', '46', 1, '1805173368533958680', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958681', '13', 0, '1805173368533958682', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958681', '2', 0, '1805173368533958683', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958681', '5', 0, '1805173368533958684', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958681', '47', 1, '1805173368533958685', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958686', '13', 0, '1805173368533958687', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958686', '2', 0, '1805173368533958688', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958686', '5', 0, '1805173368533958689', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958686', '48', 1, '1805173368533958690', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958691', '13', 0, '1805173368533958692', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958691', '2', 0, '1805173368533958693', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958691', '5', 0, '1805173368533958694', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958691', '49', 1, '1805173368533958695', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958696', '13', 0, '1805173368533958697', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958696', '2', 0, '1805173368533958698', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958696', '5', 0, '1805173368533958699', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958696', '50', 1, '1805173368533958700', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958701', '13', 0, '1805173368601067522', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958701', '2', 0, '1805173368601067523', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958701', '5', 0, '1805173368601067524', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368533958701', '51', 1, '1805173368601067525', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067526', '13', 0, '1805173368601067527', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067526', '2', 0, '1805173368601067528', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067526', '5', 0, '1805173368601067529', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067526', '52', 1, '1805173368601067530', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067531', '13', 0, '1805173368601067532', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067531', '2', 0, '1805173368601067533', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067531', '5', 0, '1805173368601067534', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067531', '53', 1, '1805173368601067535', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067536', '13', 0, '1805173368601067537', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067536', '2', 0, '1805173368601067538', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067536', '5', 0, '1805173368601067539', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067536', '54', 1, '1805173368601067540', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067541', '13', 0, '1805173368601067542', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067541', '2', 0, '1805173368601067543', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067541', '5', 0, '1805173368601067544', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067541', '55', 1, '1805173368601067545', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067546', '13', 0, '1805173368601067547', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067546', '2', 0, '1805173368601067548', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067546', '5', 0, '1805173368601067549', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067546', '56', 1, '1805173368601067550', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067551', '13', 0, '1805173368601067552', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067551', '2', 0, '1805173368601067553', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067551', '5', 0, '1805173368601067554', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067551', '57', 1, '1805173368601067555', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067556', '13', 0, '1805173368601067557', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067556', '2', 0, '1805173368601067558', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067556', '5', 0, '1805173368601067559', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067556', '58', 1, '1805173368601067560', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067561', '13', 0, '1805173368668176385', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067561', '2', 0, '1805173368668176386', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067561', '5', 0, '1805173368668176387', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368601067561', '59', 1, '1805173368668176388', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176389', '13', 0, '1805173368668176390', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176389', '2', 0, '1805173368668176391', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176389', '5', 0, '1805173368668176392', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176389', '60', 1, '1805173368668176393', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176394', '13', 0, '1805173368668176395', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176394', '2', 0, '1805173368668176396', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176394', '5', 0, '1805173368668176397', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176394', '61', 1, '1805173368668176398', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176399', '13', 0, '1805173368668176400', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176399', '2', 0, '1805173368668176401', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176399', '5', 0, '1805173368668176402', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176399', '62', 1, '1805173368668176403', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176404', '13', 0, '1805173368668176405', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176404', '2', 0, '1805173368668176406', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176404', '5', 0, '1805173368668176407', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176404', '63', 1, '1805173368668176408', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176409', '13', 0, '1805173368668176410', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176409', '2', 0, '1805173368668176411', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176409', '5', 0, '1805173368668176412', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176409', '64', 1, '1805173368668176413', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176414', '13', 0, '1805173368668176415', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176414', '2', 0, '1805173368668176416', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176414', '5', 0, '1805173368668176417', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176414', '65', 1, '1805173368668176418', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176419', '13', 0, '1805173368668176420', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176419', '2', 0, '1805173368668176421', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176419', '5', 0, '1805173368668176422', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176419', '66', 1, '1805173368668176423', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176424', '13', 0, '1805173368668176425', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176424', '2', 0, '1805173368668176426', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176424', '5', 0, '1805173368668176427', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176424', '67', 1, '1805173368668176428', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176429', '13', 0, '1805173368668176430', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176429', '2', 0, '1805173368668176431', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176429', '5', 0, '1805173368668176432', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176429', '68', 1, '1805173368668176433', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176434', '13', 0, '1805173368668176435', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176434', '2', 0, '1805173368668176436', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176434', '5', 0, '1805173368668176437', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176434', '69', 1, '1805173368668176438', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176439', '13', 0, '1805173368668176440', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176439', '2', 0, '1805173368668176441', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176439', '5', 0, '1805173368668176442', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176439', '70', 1, '1805173368668176443', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176444', '13', 0, '1805173368668176445', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176444', '2', 0, '1805173368668176446', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176444', '5', 0, '1805173368668176447', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368668176444', '71', 1, '1805173368668176448', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090946', '13', 0, '1805173368731090947', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090946', '2', 0, '1805173368731090948', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090946', '5', 0, '1805173368731090949', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090946', '72', 1, '1805173368731090950', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090951', '13', 0, '1805173368731090952', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090951', '2', 0, '1805173368731090953', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090951', '5', 0, '1805173368731090954', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090951', '73', 1, '1805173368731090955', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090956', '13', 0, '1805173368731090957', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090956', '2', 0, '1805173368731090958', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090956', '5', 0, '1805173368731090959', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090956', '74', 1, '1805173368731090960', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090961', '13', 0, '1805173368731090962', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090961', '2', 0, '1805173368731090963', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090961', '5', 0, '1805173368731090964', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090961', '75', 1, '1805173368731090965', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090966', '13', 0, '1805173368731090967', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090966', '2', 0, '1805173368731090968', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090966', '5', 0, '1805173368731090969', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090966', '76', 1, '1805173368731090970', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090971', '13', 0, '1805173368731090972', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090971', '2', 0, '1805173368731090973', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090971', '5', 0, '1805173368731090974', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090971', '77', 1, '1805173368731090975', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090976', '13', 0, '1805173368731090977', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090976', '2', 0, '1805173368731090978', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090976', '5', 0, '1805173368731090979', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090976', '78', 1, '1805173368731090980', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090981', '13', 0, '1805173368731090982', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090981', '2', 0, '1805173368731090983', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090981', '5', 0, '1805173368731090984', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090981', '79', 1, '1805173368731090985', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090986', '13', 0, '1805173368731090987', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090986', '2', 0, '1805173368731090988', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090986', '5', 0, '1805173368731090989', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090986', '80', 1, '1805173368731090990', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090991', '13', 0, '1805173368731090992', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090991', '2', 0, '1805173368731090993', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090991', '5', 0, '1805173368731090994', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090991', '81', 1, '1805173368731090995', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090996', '13', 0, '1805173368731090997', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090996', '2', 0, '1805173368731090998', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090996', '5', 0, '1805173368731090999', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731090996', '82', 1, '1805173368731091000', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091001', '13', 0, '1805173368731091002', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091001', '2', 0, '1805173368731091003', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091001', '5', 0, '1805173368731091004', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091001', '83', 1, '1805173368731091005', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091006', '13', 0, '1805173368731091007', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091006', '2', 0, '1805173368731091008', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091006', '5', 0, '1805173368731091009', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091006', '84', 1, '1805173368731091010', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091011', '13', 0, '1805173368731091012', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091011', '2', 0, '1805173368731091013', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091011', '5', 0, '1805173368731091014', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091011', '85', 1, '1805173368731091015', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091016', '13', 0, '1805173368731091017', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091016', '2', 0, '1805173368731091018', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091016', '5', 0, '1805173368731091019', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091016', '86', 1, '1805173368731091020', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091021', '13', 0, '1805173368731091022', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091021', '2', 0, '1805173368731091023', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091021', '5', 0, '1805173368731091024', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091021', '87', 1, '1805173368731091025', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091026', '13', 0, '1805173368731091027', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091026', '2', 0, '1805173368731091028', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091026', '5', 0, '1805173368731091029', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368731091026', '88', 1, '1805173368798199810', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199811', '13', 0, '1805173368798199812', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199811', '2', 0, '1805173368798199813', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199811', '5', 0, '1805173368798199814', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199811', '89', 1, '1805173368798199815', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199816', '13', 0, '1805173368798199817', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199816', '2', 0, '1805173368798199818', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199816', '5', 0, '1805173368798199819', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199816', '90', 1, '1805173368798199820', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199821', '13', 0, '1805173368798199822', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199821', '2', 0, '1805173368798199823', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199821', '5', 0, '1805173368798199824', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199821', '91', 1, '1805173368798199825', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199826', '13', 0, '1805173368798199827', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199826', '2', 0, '1805173368798199828', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199826', '5', 0, '1805173368798199829', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199826', '92', 1, '1805173368798199830', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199831', '13', 0, '1805173368798199832', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199831', '2', 0, '1805173368798199833', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199831', '5', 0, '1805173368798199834', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199831', '93', 1, '1805173368798199835', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199836', '13', 0, '1805173368798199837', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199836', '2', 0, '1805173368798199838', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199836', '5', 0, '1805173368798199839', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199836', '94', 1, '1805173368798199840', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199841', '13', 0, '1805173368798199842', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199841', '2', 0, '1805173368798199843', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199841', '5', 0, '1805173368798199844', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199841', '95', 1, '1805173368798199845', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199846', '13', 0, '1805173368798199847', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199846', '2', 0, '1805173368798199848', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199846', '5', 0, '1805173368798199849', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199846', '96', 1, '1805173368798199850', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199851', '13', 0, '1805173368798199852', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199851', '2', 0, '1805173368798199853', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199851', '5', 0, '1805173368798199854', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199851', '97', 1, '1805173368798199855', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199856', '13', 0, '1805173368798199857', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199856', '2', 0, '1805173368798199858', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199856', '5', 0, '1805173368798199859', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199856', '98', 1, '1805173368798199860', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199861', '13', 0, '1805173368798199862', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199861', '2', 0, '1805173368798199863', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199861', '5', 0, '1805173368798199864', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368798199861', '99', 1, '1805173368798199865', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502977', '13', 0, '1805173368869502978', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502977', '2', 0, '1805173368869502979', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502977', '5', 0, '1805173368869502980', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502977', '100', 1, '1805173368869502981', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502982', '13', 0, '1805173368869502983', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502982', '2', 0, '1805173368869502984', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502982', '5', 0, '1805173368869502985', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502982', '101', 1, '1805173368869502986', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502987', '13', 0, '1805173368869502988', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502987', '2', 0, '1805173368869502989', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502987', '5', 0, '1805173368869502990', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502987', '102', 1, '1805173368869502991', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502992', '13', 0, '1805173368869502993', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502992', '2', 0, '1805173368869502994', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502992', '5', 0, '1805173368869502995', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502992', '103', 1, '1805173368869502996', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502997', '13', 0, '1805173368869502998', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502997', '2', 0, '1805173368869502999', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502997', '5', 0, '1805173368869503000', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869502997', '104', 1, '1805173368869503001', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869503002', '13', 0, '1805173368869503003', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869503002', '2', 0, '1805173368869503004', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869503002', '5', 0, '1805173368869503005', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805173368869503002', '105', 1, '1805173368869503006', '1', '2024-06-24 17:37:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805174123894554627', '周树人', 1, '1805174123957469186', '1', '2024-06-24 17:40:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805174123894554627', '矛盾', 0, '1805174123957469187', '1', '2024-06-24 17:40:24', '1', NULL, NULL, 1);
INSERT INTO `t_ex_question_option` VALUES ('1805174123894554627', '老周', 0, '1805174123957469188', '1', '2024-06-24 17:40:24', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ex_question_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_question_rule`;
CREATE TABLE `t_ex_question_rule`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `paper_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷id',
  `seq` bigint NULL DEFAULT NULL COMMENT '排序(将时间用作排序标识)',
  `num` int NULL DEFAULT NULL COMMENT '抽取多少题',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_paper_id`(`paper_id`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽题规则' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_question_rule
-- ----------------------------
INSERT INTO `t_ex_question_rule` VALUES ('规则1', '1805174726469238786', 1719222171327, 100, '1805174740532740098', '1', '2024-06-24 17:42:51', '1', '1', '2024-06-24 17:43:17', 1);

-- ----------------------------
-- Table structure for t_ex_user_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_user_paper`;
CREATE TABLE `t_ex_user_paper`  (
  `paper_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷id',
  `exam_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考试id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `sub_status` int NULL DEFAULT NULL COMMENT '提交状态(0-初始 1-已提交)',
  `sub_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交时间',
  `score` double(7, 2) NULL DEFAULT NULL COMMENT '用户总分',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_exam_id`(`exam_id`) USING BTREE,
  INDEX `actable_idx_user_id_paper_id_exam_id_sub_status`(`user_id`, `paper_id`, `exam_id`, `sub_status`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-试卷-多对多' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_user_paper
-- ----------------------------

-- ----------------------------
-- Table structure for t_ex_user_paper_question
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_user_paper_question`;
CREATE TABLE `t_ex_user_paper_question`  (
  `user_paper_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户答题记录id',
  `qid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题id',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户答案',
  `score` double(7, 2) NULL DEFAULT NULL COMMENT '用户得分',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `qscore` double(7, 2) NULL DEFAULT NULL COMMENT '题目分数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_user_paper_id`(`user_paper_id`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答题记录的对应题目信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_ex_user_paper_question
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `val` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_type`(`type`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('系统用户账户号上限', '10', 'dictidmaxusercount', 1, 'dictidmaxusercount', '1', '2024-06-27 12:00:31', '1', '1', '2024-06-30 09:41:30', 1);

-- ----------------------------
-- Table structure for t_sys_function
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_function`;
CREATE TABLE `t_sys_function`  (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单标识',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `show_flag` int NULL DEFAULT NULL COMMENT '可见 1-显示 0-隐藏',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `target` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '_self' COMMENT '菜单打开方式',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_url`(`url`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_function
-- ----------------------------
INSERT INTO `t_sys_function` VALUES ('后台首页', '/admin/system/index', 'index', '0', 500, 0, NULL, '_self', '1', '1', '2024-05-03 13:32:00', '1', '1', '2024-05-31 21:35:12', 1);
INSERT INTO `t_sys_function` VALUES ('系统管理', '#', 'sys', '0', 1000, 1, NULL, '_self', '1000', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('角色管理', '/admin/system/roleList', 'sys:role', '1000', 1020, 1, NULL, '_self', '1001', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('文件上传范例', '/admin/upload/fileUploadDemo', 'sys:upload', '1000', 1080, 1, NULL, '_self', '1002', '1', '2024-05-03 13:32:00', '1', '1', '2024-05-10 19:03:51', 1);
INSERT INTO `t_sys_function` VALUES ('菜单管理', '/admin/system/functionList', 'sys:fun', '1000', 1030, 1, NULL, '_self', '1003', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('日志查询', '/admin/record/recordList', 'sys:record', '1000', 1060, 1, NULL, '_self', '1004', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('字典管理', '/admin/system/dictList', 'sys:dict', '1000', 1050, 1, NULL, '_self', '1005', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('用户管理', '/admin/system/userList', 'sys:user', '1000', 1010, 1, NULL, '_self', '1006', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('机构管理', '/admin/organ/organList', 'sys:organ', '1000', 1040, 1, NULL, '_self', '1007', '1', '2024-05-03 13:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('问题反馈', '/admin/workorder/list', 'sys:wordorder', '1000', 1070, 1, NULL, '_self', '1008', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('重置密码', '/admin/system/resetPassword', 'sys:user:resetpwd', '1006', 100, 0, NULL, '_self', '1009', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('新增', '/admin/system/userEdit', 'sys:user:add', '1006', 200, 0, NULL, '_self', '1010', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('删除', '/admin/system/userDelete', 'sys:user:del', '1006', 300, 0, NULL, '_self', '1011', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('编辑', '/admin/system/userEdit', 'sys:user:edit', '1006', 400, 0, NULL, '_self', '1012', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('启用/停用', '/admin/system/userStatus', 'sys:user:status', '1006', 500, 0, NULL, '_self', '1013', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('新增', '/admin/system/roleEdit', 'sys:role:add', '1001', 100, 0, NULL, '_self', '1014', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('删除', '/admin/system/roleDelete', 'sys:role:del', '1001', 200, 0, NULL, '_self', '1015', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('编辑', '/admin/system/roleEdit', 'sys:role:edit', '1001', 300, 0, NULL, '_self', '1016', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('权限设置', '/admin/system/roleFunction', 'sys:role:rolefun', '1001', 400, 0, NULL, '_self', '1017', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('分配用户', '/admin/system/userListByRole', 'sys:role:roleuser', '1001', 500, 0, NULL, '_self', '1018', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('考试管理', '#', 'exam', '0', 2000, 1, NULL, '_self', '2000', '1', '2024-05-12 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('题目管理', '/admin/question/list', 'exam:question', '2000', 2010, 1, NULL, '_self', '2001', '1', '2024-05-12 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('试卷管理', '/admin/paper/list', 'exam:paper', '2000', 2020, 1, NULL, '_self', '2002', '1', '2024-05-14 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('考试', '/admin/exam/list', 'exam:exam', '2000', 2030, 1, NULL, '_self', '2003', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('题目目录', '/admin/category/question/categoryList', 'exam:question:cat', '2000', 2005, 1, NULL, '_self', '2004', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('轮播图', '/admin/banner/list', 'banner', '0', 3000, 1, NULL, '_self', '3008', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('课程管理', '#', 'course', '0', 4000, 1, NULL, '_self', '4000', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('课程', '/admin/course/list', 'course:course', '4000', 4030, 1, NULL, '_self', '4001', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('课件库', '/admin/coursefile/list', 'course:coursefile', '4000', 4020, 1, NULL, '_self', '4002', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);
INSERT INTO `t_sys_function` VALUES ('课程目录', '/admin/category/course/categoryList', 'course:course:cat', '4000', 4010, 1, NULL, '_self', '4003', '1', '2024-05-23 16:32:00', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_sys_organ
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organ`;
CREATE TABLE `t_sys_organ`  (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `organ_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构编号',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `pids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id集合',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_pid`(`pid`) USING BTREE,
  INDEX `actable_idx_pids`(`pids`) USING BTREE,
  INDEX `actable_idx_seq`(`seq`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_organ
-- ----------------------------
INSERT INTO `t_sys_organ` VALUES ('上海', 'a1', '0', '', 100, '1', '1', '2024-05-04 11:11:11', '1', '1', '2024-05-31 21:39:09', 1);
INSERT INTO `t_sys_organ` VALUES ('松江', 'a2', '1', '1', 500, '2', '1', '2024-05-04 11:11:11', '1', '1', '2024-06-10 11:13:25', 1);
INSERT INTO `t_sys_organ` VALUES ('九亭', 'a3', '4', '1,4', 300, '3', '1', '2024-05-04 11:11:11', '1', '1', '2024-05-10 19:54:51', 1);
INSERT INTO `t_sys_organ` VALUES ('徐汇', 'a4', '1', '1', 400, '4', '1', '2024-05-04 11:11:11', '1', '1', '2024-05-10 19:58:09', 1);

-- ----------------------------
-- Table structure for t_sys_record
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_record`;
CREATE TABLE `t_sys_record`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `do_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `ips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交方式',
  `params` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求内容',
  `useragent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '游览器信息',
  `type` int NULL DEFAULT NULL COMMENT '日志类型(1-操作日志 2-异常日志)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_record
-- ----------------------------
INSERT INTO `t_sys_record` VALUES ('1807214437610283010', '1', '2024-06-30 08:47:53', '/xitem/admin/system/index', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214445789175810', '1', '2024-06-30 08:47:54', '/xitem/admin/system/userList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1006\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214445986308098', '1', '2024-06-30 08:47:55', '/xitem/admin/system/userListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214464076341250', '1', '2024-06-30 08:47:59', '/xitem/admin/system/roleList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214464281862145', '1', '2024-06-30 08:47:59', '/xitem/admin/system/roleListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214473647742978', '1', '2024-06-30 08:48:01', '/xitem/admin/system/functionList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1003\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214484603265026', '1', '2024-06-30 08:48:04', '/xitem/admin/organ/organList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1007\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214484863311874', '1', '2024-06-30 08:48:04', '/xitem/admin/organ/pageById', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214484863311875', '1', '2024-06-30 08:48:04', '/xitem/admin/organ/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719708484255\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214489711927298', '1', '2024-06-30 08:48:05', '/xitem/admin/system/dictList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1005\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214489976168449', '1', '2024-06-30 08:48:05', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214492782157825', '1', '2024-06-30 08:48:06', '/xitem/admin/record/recordList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1004\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214492979290113', '1', '2024-06-30 08:48:06', '/xitem/admin/record/recordListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214503192420354', '1', '2024-06-30 08:48:08', '/xitem/admin/workorder/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1008\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214503515381761', '1', '2024-06-30 08:48:08', '/xitem/admin/workorder/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214508951199746', '1', '2024-06-30 08:48:10', '/xitem/admin/upload/fileUploadDemo', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1002\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214529008361474', '1', '2024-06-30 08:48:14', '/xitem/admin/category/question/categoryList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"2004\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214529343905793', '1', '2024-06-30 08:48:14', '/xitem/admin/category/question/pageById', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214529343905794', '1', '2024-06-30 08:48:14', '/xitem/admin/category/question/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719708494867\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214535811522562', '1', '2024-06-30 08:48:16', '/xitem/admin/question/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"2001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214536071569410', '1', '2024-06-30 08:48:16', '/xitem/admin/category/question/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719708496473\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214536071569411', '1', '2024-06-30 08:48:16', '/xitem/admin/question/data', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214544963493889', '1', '2024-06-30 08:48:18', '/xitem/admin/paper/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"2002\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214545164820482', '1', '2024-06-30 08:48:18', '/xitem/admin/paper/data', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214550130876417', '1', '2024-06-30 08:48:19', '/xitem/admin/exam/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"2003\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214550399311874', '1', '2024-06-30 08:48:19', '/xitem/admin/exam/data', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214563909165058', '1', '2024-06-30 08:48:23', '/xitem/admin/banner/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"3008\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214564165017602', '1', '2024-06-30 08:48:23', '/xitem/admin/banner/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214573077913602', '1', '2024-06-30 08:48:25', '/xitem/admin/category/course/categoryList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4003\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214573405069314', '1', '2024-06-30 08:48:25', '/xitem/admin/category/course/pageById', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214573405069315', '1', '2024-06-30 08:48:25', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719708505369\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214591008563201', '1', '2024-06-30 08:48:29', '/xitem/admin/coursefile/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4002\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214591276998657', '1', '2024-06-30 08:48:29', '/xitem/admin/coursefile/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214595488079874', '1', '2024-06-30 08:48:30', '/xitem/admin/course/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214595748126722', '1', '2024-06-30 08:48:30', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214595748126723', '1', '2024-06-30 08:48:30', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719708510695\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214617587867649', '1', '2024-06-30 08:48:35', '/xitem/admin/coursefile/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4002\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214617722085377', '1', '2024-06-30 08:48:35', '/xitem/admin/coursefile/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214625934532609', '1', '2024-06-30 08:48:37', '/xitem/admin/coursefile/edit', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214689042030594', '1', '2024-06-30 08:48:52', '/xitem/admin/upload/fileUploadMain', '0:0:0:0:0:0:0:1', 'GET', '{\"type\":[\"mp4\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214743165329409', '1', '2024-06-30 08:49:05', '/xitem/admin/upload/fileUpload', '0:0:0:0:0:0:0:1', 'POST', '{\"uuid\":[\"bd0ae231a5c54af8a826103dec41c22e\"],\"id\":[\"WU_FILE_0\"],\"name\":[\"big_buck_bunny.mp4\"],\"type\":[\"video/mp4\"],\"lastModifiedDate\":[\"Wed Sep 20 2023 17:26:23 GMT+0800 (中国标准时间)\"],\"size\":[\"5510872\"],\"chunks\":[\"2\"],\"chunk\":[\"1\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214743429570561', '1', '2024-06-30 08:49:05', '/xitem/admin/upload/fileUpload', '0:0:0:0:0:0:0:1', 'POST', '{\"uuid\":[\"bd0ae231a5c54af8a826103dec41c22e\"],\"id\":[\"WU_FILE_0\"],\"name\":[\"big_buck_bunny.mp4\"],\"type\":[\"video/mp4\"],\"lastModifiedDate\":[\"Wed Sep 20 2023 17:26:23 GMT+0800 (中国标准时间)\"],\"size\":[\"5510872\"],\"chunks\":[\"2\"],\"chunk\":[\"0\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214744025161730', '1', '2024-06-30 08:49:06', '/xitem/admin/upload/fileMerge', '0:0:0:0:0:0:0:1', 'POST', '{\"chunks\":[\"2\"],\"suffix\":[\"mp4\"],\"uuid\":[\"bd0ae231a5c54af8a826103dec41c22e\"],\"fileid\":[\"wu_file_0\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214750635384834', '1', '2024-06-30 08:49:07', '/xitem/admin/upload/saveFile', '0:0:0:0:0:0:0:1', 'POST', '{\"infos\":[\"[{\"name\":\"big_buck_bunny.mp4\",\"size\":5510872,\"urlTemp\":\"/temp/bd0ae231a5c54af8a826103dec41c22e/wu_file_0.mp4\"}]\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214774823936001', '1', '2024-06-30 08:49:13', '/xitem/admin/coursefile/save', '0:0:0:0:0:0:0:1', 'POST', '{\"id\":[\"\"],\"title\":[\"视频课件\"],\"courseType\":[\"2\"],\"remarks\":[\"这是一个视频课件\"],\"fileInfos\":[\"/fileinfo/def/2024/06/30/b3e0a88acf6846169e21a3f5996a61f2.mp4\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214775021068290', '1', '2024-06-30 08:49:13', '/xitem/admin/coursefile/list', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214775218200577', '1', '2024-06-30 08:49:13', '/xitem/admin/coursefile/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214784391143425', '1', '2024-06-30 08:49:15', '/xitem/admin/coursefile/edit', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807214844029952001', '1', '2024-06-30 08:49:29', '/xitem/admin/upload/fileUploadMain', '0:0:0:0:0:0:0:1', 'GET', '{\"type\":[\"pdf\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807216989072515074', '1', '2024-06-30 08:58:01', '/xitem/admin/upload/fileUpload', '0:0:0:0:0:0:0:1', 'POST', '{\"uuid\":[\"f89aa79e0e2d49d580c723e5026ed892\"],\"id\":[\"WU_FILE_0\"],\"name\":[\"新建 DOCX 文档.pdf\"],\"type\":[\"application/pdf\"],\"lastModifiedDate\":[\"Sun Jun 30 2024 08:57:23 GMT+0800 (中国标准时间)\"],\"size\":[\"245080\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807216989592608770', '1', '2024-06-30 08:58:01', '/xitem/admin/upload/fileMerge', '0:0:0:0:0:0:0:1', 'POST', '{\"chunks\":[\"1\"],\"suffix\":[\"pdf\"],\"uuid\":[\"f89aa79e0e2d49d580c723e5026ed892\"],\"fileid\":[\"wu_file_0\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217001856753665', '1', '2024-06-30 08:58:04', '/xitem/admin/upload/saveFile', '0:0:0:0:0:0:0:1', 'POST', '{\"infos\":[\"[{\"name\":\"新建 DOCX 文档.pdf\",\"size\":245080,\"urlTemp\":\"/temp/f89aa79e0e2d49d580c723e5026ed892/wu_file_0.pdf\"}]\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217019254726658', '1', '2024-06-30 08:58:08', '/xitem/admin/coursefile/save', '0:0:0:0:0:0:0:1', 'POST', '{\"id\":[\"\"],\"title\":[\"pdf课件\"],\"courseType\":[\"5\"],\"remarks\":[\"这是一个pdf课件\"],\"fileInfos\":[\"/fileinfo/def/2024/06/30/6fa1e5ca1261428498732283d625b7a1.pdf\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217019384750081', '1', '2024-06-30 08:58:08', '/xitem/admin/coursefile/list', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217019581882370', '1', '2024-06-30 08:58:08', '/xitem/admin/coursefile/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217041769750530', '1', '2024-06-30 08:58:13', '/xitem/admin/coursefile/preview', '0:0:0:0:0:0:0:1', 'GET', '{\"courseFileId\":[\"1807217019321835521\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217157100527618', '1', '2024-06-30 08:58:41', '/xitem/admin/course/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217157364768770', '1', '2024-06-30 08:58:41', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709121427\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217157364768771', '1', '2024-06-30 08:58:41', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217169519861762', '1', '2024-06-30 08:58:44', '/xitem/admin/course/edit', '0:0:0:0:0:0:0:1', 'GET', '{\"id\":[\"1804461299752923138\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217169586970626', NULL, '2024-06-30 08:58:44', NULL, NULL, NULL, 'java.lang.NullPointerException\r\n	at com.xxsword.xitem.admin.controller.CourseController.edit(CourseController.java:79)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:529)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:623)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n	at org.apache.catalina.core.ApplicationFi', NULL, 2);
INSERT INTO `t_sys_record` VALUES ('1807217180139839489', '1', '2024-06-30 08:58:46', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807217180139839490', '1', '2024-06-30 08:58:46', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709126868\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218291403866114', '1', '2024-06-30 09:03:11', '/xitem/admin/system/index', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218308101390337', '1', '2024-06-30 09:03:15', '/xitem/admin/course/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218308369825793', '1', '2024-06-30 09:03:15', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709395844\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218308369825794', '1', '2024-06-30 09:03:15', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218317534380033', '1', '2024-06-30 09:03:18', '/xitem/admin/course/edit', '0:0:0:0:0:0:0:1', 'GET', '{\"id\":[\"1804461299752923138\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218324958298113', '1', '2024-06-30 09:03:19', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218324958298114', '1', '2024-06-30 09:03:19', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709399809\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218336488439809', '1', '2024-06-30 09:03:22', '/xitem/admin/course/courseUser', '0:0:0:0:0:0:0:1', 'GET', '{\"courseId\":[\"1804461299752923138\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218336748486657', '1', '2024-06-30 09:03:22', '/xitem/admin/course/courseUserData', '0:0:0:0:0:0:0:1', 'GET', '{\"courseId\":[\"1804461299752923138\"],\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218342624706561', '1', '2024-06-30 09:03:24', '/xitem/admin/course/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218342821838850', '1', '2024-06-30 09:03:24', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709404061\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218342821838851', '1', '2024-06-30 09:03:24', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218346600906754', '1', '2024-06-30 09:03:24', '/xitem/admin/course/courseUser', '0:0:0:0:0:0:0:1', 'GET', '{\"courseId\":[\"1804460340352020482\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218346735124481', '1', '2024-06-30 09:03:25', '/xitem/admin/course/courseUserData', '0:0:0:0:0:0:0:1', 'GET', '{\"courseId\":[\"1804460340352020482\"],\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218351772483585', '1', '2024-06-30 09:03:26', '/xitem/admin/course/list', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218351969615873', '1', '2024-06-30 09:03:26', '/xitem/admin/category/course/data', '0:0:0:0:0:0:0:1', 'GET', '{\"_\":[\"1719709406246\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807218351969615874', '1', '2024-06-30 09:03:26', '/xitem/admin/course/listData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221594502152193', '1', '2024-06-30 09:16:19', '/xitem/admin/system/index', '127.0.0.1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221604664950786', '1', '2024-06-30 09:16:21', '/xitem/admin/system/userList', '127.0.0.1', 'GET', '{\"mclick\":[\"1006\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221604987912193', '1', '2024-06-30 09:16:21', '/xitem/admin/system/userListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221611065458689', '1', '2024-06-30 09:16:23', '/xitem/admin/system/roleList', '127.0.0.1', 'GET', '{\"mclick\":[\"1001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221611266785281', '1', '2024-06-30 09:16:23', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221615586918401', '1', '2024-06-30 09:16:24', '/xitem/admin/system/functionList', '127.0.0.1', 'GET', '{\"mclick\":[\"1003\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221623207968770', '1', '2024-06-30 09:16:26', '/xitem/admin/system/roleList', '127.0.0.1', 'GET', '{\"mclick\":[\"1001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221623400906754', '1', '2024-06-30 09:16:26', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221628157247489', '1', '2024-06-30 09:16:27', '/xitem/admin/system/roleFunction', '127.0.0.1', 'GET', '{\"roleId\":[\"1\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221644196265986', '1', '2024-06-30 09:16:31', '/xitem/admin/system/roleFunctionSave', '127.0.0.1', 'POST', '{\"roleId\":[\"1\"],\"funIds\":[\"1\",\"1000\",\"1001\",\"1014\",\"1015\",\"1016\",\"1017\",\"1018\",\"1003\",\"1007\",\"1005\",\"1004\",\"1008\",\"1002\",\"2000\",\"2004\",\"2001\",\"2002\",\"2003\",\"3008\",\"4000\",\"4003\",\"4002\",\"4001\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221644326289410', '1', '2024-06-30 09:16:31', '/xitem/admin/system/roleList', '127.0.0.1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221644519227393', '1', '2024-06-30 09:16:31', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221684432224257', '1', '2024-06-30 09:16:40', '/xitem/admin/system/roleEdit', '127.0.0.1', 'GET', '{\"roleId\":[\"1\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221689607995393', '1', '2024-06-30 09:16:42', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221693198319617', '1', '2024-06-30 09:16:42', '/xitem/admin/system/roleFunction', '127.0.0.1', 'GET', '{\"roleId\":[\"1\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221699934371841', '1', '2024-06-30 09:16:44', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221776971153410', '1', '2024-06-30 09:17:02', '/xitem/admin/system/roleFunction', '127.0.0.1', 'GET', '{\"roleId\":[\"1\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807221799133855746', '1', '2024-06-30 09:17:08', '/xitem/admin/system/roleListData', '127.0.0.1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227897513414658', '1', '2024-06-30 09:41:22', '/xitem/admin/system/index', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227916555554817', '1', '2024-06-30 09:41:26', '/xitem/admin/system/dictList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1005\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227916811407361', '1', '2024-06-30 09:41:26', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227933349548034', '1', '2024-06-30 09:41:30', '/xitem/admin/system/dictDelete', '0:0:0:0:0:0:0:1', 'GET', '{\"dictIds\":[\"dictidmaxusercount\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227933550874626', '1', '2024-06-30 09:41:30', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227937816481793', '1', '2024-06-30 09:41:31', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227939561312257', '1', '2024-06-30 09:41:32', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227940282732545', '1', '2024-06-30 09:41:32', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227940941238273', '1', '2024-06-30 09:41:32', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227941599744001', '1', '2024-06-30 09:41:32', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227942392467458', '1', '2024-06-30 09:41:32', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227979017129986', '1', '2024-06-30 09:41:41', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227981504352257', '1', '2024-06-30 09:41:42', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227982158663681', '1', '2024-06-30 09:41:42', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807227982875889666', '1', '2024-06-30 09:41:42', '/xitem/admin/system/dictListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807240258328686593', '1', '2024-06-30 10:30:29', '/xitem/admin/system/index', '0:0:0:0:0:0:0:1', 'GET', '{}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807240270500556802', '1', '2024-06-30 10:30:32', '/xitem/admin/system/userList', '0:0:0:0:0:0:0:1', 'GET', '{\"mclick\":[\"1006\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807240270823518210', '1', '2024-06-30 10:30:32', '/xitem/admin/system/userListData', '0:0:0:0:0:0:0:1', 'GET', '{\"offset\":[\"0\"],\"limit\":[\"10\"],\"current\":[\"1\"],\"size\":[\"10\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807240396417757186', '1', '2024-06-30 10:31:02', '/xitem/admin/system/resetPassword', '0:0:0:0:0:0:0:1', 'POST', '{\"userId\":[\"1\"],\"password\":[\"Admin9900\"]}', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1);
INSERT INTO `t_sys_record` VALUES ('1807294502742130690', NULL, '2024-06-30 14:06:02', NULL, NULL, NULL, 'org.springframework.dao.DataIntegrityViolationException: \r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'useragent\' at row 1\r\n### The error may exist in com/xxsword/xitem/admin/mapper/system/RecordMapper.java (best guess)\r\n### The error may involve com.xxsword.xitem.admin.mapper.system.RecordMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO t_sys_record  ( id, user_id, create_date, do_path, ips, method, params, useragent, type )  VALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?  )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'useragent\' at row 1\n; Data truncation: Data too long for column \'useragent\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'useragent\' at row 1\r\n	at org.springframework.jdbc.support.SQLStateSQLExceptionTranslator.doTranslate(SQLStateSQLExceptionTranslator.java:104)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:73)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:82)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:82)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:92)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:439)\r\n	at com.sun.proxy.$Proxy70.insert(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:272)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:59)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:149)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:90)\r\n	at com.sun.proxy.$Proxy95.insert(Unknown Source)\r\n	at com.baomidou.mybatisplus.extension.service.IService.save(IService.java:60)\r\n	at com.baomidou.mybatisplus.extension.service.IService$$FastClassBySpringCGLIB$$f8525d18.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy.invokeMethod(CglibAopProxy.java:386)\r\n	at org.springframework.aop.framework.CglibAopProxy.access$000(CglibAopProxy.java:85)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:703)\r\n	at com.xxsword.xitem.admin.service.system.impl.RecordServiceImpl$$EnhancerBySpringCGLIB$$5825da1a.save(<generated>)\r\n	at com.xxsword.xitem.admin.interceptor.RecordInterceptor.preHandle(RecordInterceptor.java:26)\r\n	at org.springframework.web.servlet.HandlerExecutionChain.app', NULL, 2);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('超级管理员', '1', '1', '2024-05-03 13:32:00', '1', '1', '2024-05-31 21:24:33', 1);

-- ----------------------------
-- Table structure for t_sys_role_function
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_function`;
CREATE TABLE `t_sys_role_function`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `fun_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_role_id_fun_id`(`role_id`, `fun_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单多对多表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_role_function
-- ----------------------------
INSERT INTO `t_sys_role_function` VALUES ('8', '1', '1');
INSERT INTO `t_sys_role_function` VALUES ('1', '1', '1000');
INSERT INTO `t_sys_role_function` VALUES ('2', '1', '1001');
INSERT INTO `t_sys_role_function` VALUES ('3', '1', '1002');
INSERT INTO `t_sys_role_function` VALUES ('4', '1', '1003');
INSERT INTO `t_sys_role_function` VALUES ('5', '1', '1004');
INSERT INTO `t_sys_role_function` VALUES ('6', '1', '1005');
INSERT INTO `t_sys_role_function` VALUES ('7', '1', '1006');
INSERT INTO `t_sys_role_function` VALUES ('9', '1', '1007');
INSERT INTO `t_sys_role_function` VALUES ('30', '1', '1008');
INSERT INTO `t_sys_role_function` VALUES ('31', '1', '1009');
INSERT INTO `t_sys_role_function` VALUES ('32', '1', '1010');
INSERT INTO `t_sys_role_function` VALUES ('33', '1', '1011');
INSERT INTO `t_sys_role_function` VALUES ('34', '1', '1012');
INSERT INTO `t_sys_role_function` VALUES ('35', '1', '1013');
INSERT INTO `t_sys_role_function` VALUES ('36', '1', '1014');
INSERT INTO `t_sys_role_function` VALUES ('37', '1', '1015');
INSERT INTO `t_sys_role_function` VALUES ('38', '1', '1016');
INSERT INTO `t_sys_role_function` VALUES ('39', '1', '1017');
INSERT INTO `t_sys_role_function` VALUES ('40', '1', '1018');
INSERT INTO `t_sys_role_function` VALUES ('20', '1', '2000');
INSERT INTO `t_sys_role_function` VALUES ('21', '1', '2001');
INSERT INTO `t_sys_role_function` VALUES ('22', '1', '2002');
INSERT INTO `t_sys_role_function` VALUES ('23', '1', '2003');
INSERT INTO `t_sys_role_function` VALUES ('29', '1', '2004');
INSERT INTO `t_sys_role_function` VALUES ('24', '1', '3008');
INSERT INTO `t_sys_role_function` VALUES ('25', '1', '4000');
INSERT INTO `t_sys_role_function` VALUES ('26', '1', '4001');
INSERT INTO `t_sys_role_function` VALUES ('27', '1', '4002');
INSERT INTO `t_sys_role_function` VALUES ('28', '1', '4003');

-- ----------------------------
-- Table structure for t_sys_upload_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_upload_log`;
CREATE TABLE `t_sys_upload_log`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小(单位：字节)',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分片方式上传的文件信息记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_upload_log
-- ----------------------------
INSERT INTO `t_sys_upload_log` VALUES ('1807214750832517121', '2024-06-30 08:49:07', '/fileinfo/def/2024/06/30/b3e0a88acf6846169e21a3f5996a61f2.mp4', 'big_buck_bunny.mp4', 5510872, '1');
INSERT INTO `t_sys_upload_log` VALUES ('1807217001919668226', '2024-06-30 08:58:04', '/fileinfo/def/2024/06/30/6fa1e5ca1261428498732283d625b7a1.pdf', '新建 DOCX 文档.pdf', 245080, '1');

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_user_id_role_id`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for t_sys_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_userinfo`;
CREATE TABLE `t_sys_userinfo`  (
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `password_error` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码错误次数(分钟,错误次数)',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户机构',
  `permission_type` int NULL DEFAULT NULL COMMENT '权限类型(0-自己创建的数据 1-管辖机构的当前级及以下数据 2-全部数据)',
  `permission_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '权限',
  `life_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号有效期(超过该日期后无法登录)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `actable_uni_login_name`(`login_name`) USING BTREE,
  UNIQUE INDEX `actable_uni_phone_no`(`phone_no`) USING BTREE,
  INDEX `actable_idx_login_name`(`login_name`) USING BTREE,
  INDEX `actable_idx_password`(`password`) USING BTREE,
  INDEX `actable_idx_life_date`(`life_date`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_userinfo
-- ----------------------------
INSERT INTO `t_sys_userinfo` VALUES ('管理老登', '管理员', 'admin', 'oTXUfTKxDGhddBt2VjS4BQ==', '', '1@qq.com', '15511111111', '1', NULL, NULL, '2026-05-06 13:32:00', '1', '1', '2024-05-03 13:32:00', '1', '1', '2024-06-22 11:36:43', 1);

-- ----------------------------
-- Table structure for t_time_period
-- ----------------------------
DROP TABLE IF EXISTS `t_time_period`;
CREATE TABLE `t_time_period`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cost` int NULL DEFAULT NULL COMMENT '本段时长(秒)',
  `ob_type` int NULL DEFAULT NULL COMMENT 'TimerType的code',
  `ob_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'TimerType的code的业务id',
  `start_stamp` bigint NULL DEFAULT NULL COMMENT '开始时间戳',
  `end_stamp` bigint NULL DEFAULT NULL COMMENT '结束时间戳',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `cost_item` int NULL DEFAULT NULL COMMENT '本段小段时长(秒)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时段落信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_period
-- ----------------------------

-- ----------------------------
-- Table structure for t_time_timer
-- ----------------------------
DROP TABLE IF EXISTS `t_time_timer`;
CREATE TABLE `t_time_timer`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ob_type` int NULL DEFAULT NULL COMMENT 'TimerType的code',
  `ob_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'TimerType的code对应的业务id',
  `total_time` int NULL DEFAULT NULL COMMENT '记录总时长（秒）',
  `start_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第一次更新的时间',
  `last_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一次更新时间',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_user_id_ob_id_ob_type`(`user_id`, `ob_id`, `ob_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_timer
-- ----------------------------

-- ----------------------------
-- Table structure for t_time_trace
-- ----------------------------
DROP TABLE IF EXISTS `t_time_trace`;
CREATE TABLE `t_time_trace`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `device` int NULL DEFAULT NULL COMMENT '设备信息（Device的code）',
  `time_stamp` bigint NULL DEFAULT NULL COMMENT '时间戳',
  `period_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '段落id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_time_stamp`(`time_stamp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时跟踪信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_trace
-- ----------------------------

-- ----------------------------
-- Table structure for t_time_trace_0
-- ----------------------------
DROP TABLE IF EXISTS `t_time_trace_0`;
CREATE TABLE `t_time_trace_0`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `device` int NULL DEFAULT NULL COMMENT '设备信息（Device的code）',
  `time_stamp` bigint NULL DEFAULT NULL COMMENT '时间戳',
  `period_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '段落id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_time_stamp`(`time_stamp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时跟踪信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_trace_0
-- ----------------------------

-- ----------------------------
-- Table structure for t_time_trace_1
-- ----------------------------
DROP TABLE IF EXISTS `t_time_trace_1`;
CREATE TABLE `t_time_trace_1`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `device` int NULL DEFAULT NULL COMMENT '设备信息（Device的code）',
  `time_stamp` bigint NULL DEFAULT NULL COMMENT '时间戳',
  `period_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '段落id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_time_stamp`(`time_stamp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时跟踪信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_trace_1
-- ----------------------------

-- ----------------------------
-- Table structure for t_time_trace_2
-- ----------------------------
DROP TABLE IF EXISTS `t_time_trace_2`;
CREATE TABLE `t_time_trace_2`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `device` int NULL DEFAULT NULL COMMENT '设备信息（Device的code）',
  `time_stamp` bigint NULL DEFAULT NULL COMMENT '时间戳',
  `period_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '段落id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_time_stamp`(`time_stamp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计时跟踪信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_time_trace_2
-- ----------------------------

-- ----------------------------
-- Table structure for t_work_order
-- ----------------------------
DROP TABLE IF EXISTS `t_work_order`;
CREATE TABLE `t_work_order`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单标题',
  `work_status` int NULL DEFAULT NULL COMMENT '工单状态(0-未关闭 1-已关闭)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_work_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_work_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_work_order_item`;
CREATE TABLE `t_work_order_item`  (
  `work_order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int NULL DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `file_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片附件',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_create_organ_id`(`create_organ_id`) USING BTREE,
  INDEX `actable_idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单对话记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_work_order_item
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
