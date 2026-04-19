-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: xitemtask
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_cat_category`
--

DROP TABLE IF EXISTS `t_cat_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cat_category` (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id',
  `pids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id集合',
  `seq` bigint DEFAULT NULL COMMENT '排序(时间戳)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_pid` (`pid`) USING BTREE,
  KEY `actable_idx_pids` (`pids`) USING BTREE,
  KEY `actable_idx_seq` (`seq`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='公用分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cat_category`
--

LOCK TABLES `t_cat_category` WRITE;
/*!40000 ALTER TABLE `t_cat_category` DISABLE KEYS */;
INSERT INTO `t_cat_category` VALUES ('任务状态','0','0,',0,'1','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('进行中','1','0,1,',2,'2','1','2013-05-27 08:00:00','1','1','2026-04-12 10:32:55',1),('任务优先级','0','0,',0,'21','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('低','21','0,21,',2,'22','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('中','21','0,21,',3,'23','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('高','21','0,21,',4,'24','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('待测试','1','0,1,',3,'3','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('已完成','1','0,1,',4,'4','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1),('待办','1','0,1,',1,'5','1','2013-05-27 08:00:00','1','1','2026-04-12 10:32:53',1),('暂停','1','0,1,',5,'6','1','2013-05-27 08:00:00','1','1','2025-01-30 15:38:26',1);
/*!40000 ALTER TABLE `t_cat_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cms_article`
--

DROP TABLE IF EXISTS `t_cms_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cms_article` (
  `category_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务状态',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `level_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '优先级',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目Id',
  `stime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计划开始时间',
  `etime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计划结束时间',
  `atype` int DEFAULT NULL COMMENT '类型（1-项目任务 2-项目文章）',
  `roadmap_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '里程碑id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE,
  KEY `actable_idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章表（任务表）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cms_article`
--

LOCK TABLES `t_cms_article` WRITE;
/*!40000 ALTER TABLE `t_cms_article` DISABLE KEYS */;
INSERT INTO `t_cms_article` VALUES ('5','任务A','2045129290713509890','1','2026-04-17 21:16:34','1','1','2026-04-17 21:18:04',1,'22','2045128674939351042','2026-04-09','2026-04-10',1,''),('2','任务B','2045129638857519105','1','2026-04-17 21:17:57','1','1','2026-04-17 21:19:30',1,'23','2045128674939351042','2026-04-20','2026-04-22',1,'');
/*!40000 ALTER TABLE `t_cms_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cms_article_data`
--

DROP TABLE IF EXISTS `t_cms_article_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cms_article_data` (
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
  `copyfrom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来源',
  `relation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关文章Ids，逗号分隔',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文章详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cms_article_data`
--

LOCK TABLES `t_cms_article_data` WRITE;
/*!40000 ALTER TABLE `t_cms_article_data` DISABLE KEYS */;
INSERT INTO `t_cms_article_data` VALUES ('&lt;p&gt;这是一个示例任务。&lt;br&gt;任务描述可以填写该任务的内容，具体情况。&lt;br&gt;任务可以是新功能，新想法，也可以是问题和BUG。&lt;/p&gt;',NULL,NULL,'2045129290713509890'),('&lt;p&gt;这是另外一个示例任务。&lt;/p&gt;',NULL,NULL,'2045129638857519105');
/*!40000 ALTER TABLE `t_cms_article_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cms_article_user`
--

DROP TABLE IF EXISTS `t_cms_article_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cms_article_user` (
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `aid` varchar(50) DEFAULT NULL COMMENT '任务id',
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actable_uni_aid_user_id` (`aid`,`user_id`),
  KEY `actable_idx_create_organ_id` (`create_organ_id`),
  KEY `actable_idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务用户关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cms_article_user`
--

LOCK TABLES `t_cms_article_user` WRITE;
/*!40000 ALTER TABLE `t_cms_article_user` DISABLE KEYS */;
INSERT INTO `t_cms_article_user` VALUES ('1','2045129290713509890','2045129670033780738','1','2026-04-17 21:18:04','1',NULL,NULL,1);
/*!40000 ALTER TABLE `t_cms_article_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cms_comments`
--

DROP TABLE IF EXISTS `t_cms_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cms_comments` (
  `content` text COMMENT '评论内容',
  `type` int DEFAULT NULL COMMENT '评论类型（1-主评论 2-回复）',
  `com_id` varchar(50) DEFAULT NULL COMMENT '评论类型为回复时，记录主评论id',
  `aid` varchar(50) DEFAULT NULL COMMENT '任务id',
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`),
  KEY `actable_idx_create_organ_id` (`create_organ_id`),
  KEY `actable_idx_status` (`status`),
  KEY `actable_idx_aid` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cms_comments`
--

LOCK TABLES `t_cms_comments` WRITE;
/*!40000 ALTER TABLE `t_cms_comments` DISABLE KEYS */;
INSERT INTO `t_cms_comments` VALUES ('&lt;p&gt;这是一个示例评论。&lt;br&gt;您可以在这里回复最新的情况或想法。111111&lt;/p&gt;',1,NULL,'2045129290713509890','2045151177262821378','1','2026-04-17 22:43:32','1','1','2026-04-17 22:44:48',1),('&lt;p&gt;1111111&lt;/p&gt;',2,'2045151177262821378','2045129290713509890','2045151515671851010','1','2026-04-17 22:44:53','1',NULL,NULL,1);
/*!40000 ALTER TABLE `t_cms_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project`
--

DROP TABLE IF EXISTS `t_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_project` (
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `content` text COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `actable_idx_create_organ_id` (`create_organ_id`),
  KEY `actable_idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project`
--

LOCK TABLES `t_project` WRITE;
/*!40000 ALTER TABLE `t_project` DISABLE KEYS */;
INSERT INTO `t_project` VALUES ('项目A','2045128674939351042','1','2026-04-17 21:14:07','1','1','2026-04-17 21:14:40',1,'&lt;p&gt;该项目为示例项目。&lt;br&gt;这里可以填写该项目的描述，如项目背景，项目目标等信息。&lt;/p&gt;');
/*!40000 ALTER TABLE `t_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project_user`
--

DROP TABLE IF EXISTS `t_project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_project_user` (
  `pid` varchar(50) DEFAULT NULL COMMENT '项目Id',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户Id',
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `read_flag` int DEFAULT '1' COMMENT '用户权限（ 1-正常可编辑 0-只读）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actable_uni_pid_user_id` (`pid`,`user_id`),
  KEY `actable_idx_create_organ_id` (`create_organ_id`),
  KEY `actable_idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目用户关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project_user`
--

LOCK TABLES `t_project_user` WRITE;
/*!40000 ALTER TABLE `t_project_user` DISABLE KEYS */;
INSERT INTO `t_project_user` VALUES ('2045128674939351042','1','2045128674951933954','1','2026-04-17 21:14:07','1','1','2026-04-17 21:14:40',1,1);
/*!40000 ALTER TABLE `t_project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_roadmap`
--

DROP TABLE IF EXISTS `t_roadmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_roadmap` (
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `stime` varchar(20) DEFAULT NULL COMMENT '计划开始时间',
  `etime` varchar(20) DEFAULT NULL COMMENT '计划结束时间',
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `pid` varchar(50) DEFAULT NULL COMMENT '项目Id',
  PRIMARY KEY (`id`),
  KEY `actable_idx_create_organ_id` (`create_organ_id`),
  KEY `actable_idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='里程碑';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_roadmap`
--

LOCK TABLES `t_roadmap` WRITE;
/*!40000 ALTER TABLE `t_roadmap` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_roadmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dict`
--

DROP TABLE IF EXISTS `t_sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_dict` (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `val` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '值',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `seq` int DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_type` (`type`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dict`
--

LOCK TABLES `t_sys_dict` WRITE;
/*!40000 ALTER TABLE `t_sys_dict` DISABLE KEYS */;
INSERT INTO `t_sys_dict` VALUES ('系统用户账户号上限','9999','dictidmaxusercount',1,'dictidmaxusercount','1','2024-06-27 12:00:31','1','1','2024-06-30 09:41:30',1);
/*!40000 ALTER TABLE `t_sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_function`
--

DROP TABLE IF EXISTS `t_sys_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_function` (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单标识',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id',
  `seq` int DEFAULT NULL COMMENT '排序',
  `show_flag` int DEFAULT NULL COMMENT '可见 1-显示 0-隐藏',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `target` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '_self' COMMENT '菜单打开方式',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_url` (`url`) USING BTREE,
  KEY `actable_idx_seq` (`seq`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_function`
--

LOCK TABLES `t_sys_function` WRITE;
/*!40000 ALTER TABLE `t_sys_function` DISABLE KEYS */;
INSERT INTO `t_sys_function` VALUES ('首页','/admin/system/index','index','4',500,0,NULL,'_self','1','1','2024-05-03 13:32:00','1','1','2024-05-31 21:35:12',0),('系统管理','#','sys','4',1000,1,NULL,'_self','1000','1','2024-05-03 13:32:00','1',NULL,NULL,1),('角色管理','/admin/system/roleList','sys:role','1000',1020,1,NULL,'_self','1001','1','2024-05-03 13:32:00','1',NULL,NULL,0),('文件上传范例','/admin/upload/fileUploadDemo','sys:upload','1000',1080,1,NULL,'_self','1002','1','2024-05-03 13:32:00','1','1','2024-05-10 19:03:51',0),('菜单管理','/admin/system/functionList','sys:fun','1000',1030,1,NULL,'_self','1003','1','2024-05-03 13:32:00','1',NULL,NULL,0),('日志查询','/admin/record/recordList','sys:record','1000',1060,1,NULL,'_self','1004','1','2024-05-03 13:32:00','1',NULL,NULL,1),('字典管理','/admin/system/dictList','sys:dict','1000',1050,1,NULL,'_self','1005','1','2024-05-03 13:32:00','1',NULL,NULL,0),('用户管理','/admin/system/userList','sys:user','1000',1010,1,NULL,'_self','1006','1','2024-05-03 13:32:00','1',NULL,NULL,1),('机构管理','/admin/organ/organList','sys:organ','1000',1040,1,NULL,'_self','1007','1','2024-05-03 13:32:00','1',NULL,NULL,0),('重置密码','/admin/system/resetPassword','sys:user:resetpwd','1006',100,0,NULL,'_self','1009','1','2024-05-23 16:32:00','1',NULL,NULL,1),('新增','/admin/system/userEdit','sys:user:add','1006',200,0,NULL,'_self','1010','1','2024-05-23 16:32:00','1',NULL,NULL,1),('删除','/admin/system/userDelete','sys:user:del','1006',300,0,NULL,'_self','1011','1','2024-05-23 16:32:00','1',NULL,NULL,1),('编辑','/admin/system/userEdit','sys:user:edit','1006',400,0,NULL,'_self','1012','1','2024-05-23 16:32:00','1',NULL,NULL,1),('启用/停用','/admin/system/userStatus','sys:user:status','1006',500,0,NULL,'_self','1013','1','2024-05-23 16:32:00','1',NULL,NULL,1),('新增','/admin/system/roleEdit','sys:role:add','1001',100,0,NULL,'_self','1014','1','2024-05-23 16:32:00','1',NULL,NULL,0),('删除','/admin/system/roleDelete','sys:role:del','1001',200,0,NULL,'_self','1015','1','2024-05-23 16:32:00','1',NULL,NULL,0),('编辑','/admin/system/roleEdit','sys:role:edit','1001',300,0,NULL,'_self','1016','1','2024-05-23 16:32:00','1',NULL,NULL,0),('权限设置','/admin/system/roleFunction','sys:role:rolefun','1001',400,0,NULL,'_self','1017','1','2024-05-23 16:32:00','1',NULL,NULL,0),('分配用户','/admin/system/userListByRole','sys:role:roleuser','1001',500,0,NULL,'_self','1018','1','2024-05-23 16:32:00','1',NULL,NULL,0),('文件管理（我的文件）','/admin/file/fileList','file:list:index','1000',1055,1,NULL,'_self','1879036264531288066','1','2025-01-14 13:22:10','1','1','2025-01-18 13:42:48',0),('任务设置','#','file:list','4',1055,1,'mdi mdi-settings-outline','_self','1879036264531288077','1','2025-01-14 13:22:10','1','1','2025-01-18 13:42:48',1),('任务状态','/admin/category/article/categoryList','article:category','1879036264531288077',1080,1,NULL,'_self','18801','1','2025-01-30 15:32:23','1','1','2025-01-30 15:32:23',1),('任务','#','article','5',3000,1,'mdi mdi-pin-outline','_self','1880490630882680833','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('仪表盘','/admin/system/index','dashboard','2',2000,1,'mdi mdi-speedometer','_self','1880490630882680837','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('项目中心','/admin/project/projectIndex','project:index','2',2000,1,'mdi mdi-home-flood','_self','1880490630882680839','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('资料库','#','data','5',3000,1,'mdi mdi-server','_self','1880490630882680855','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('文章','/admin/cms/articlelistwiki','data:cms','1880490630882680855',3000,1,NULL,'_self','1880490630882680866','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('文件','/admin/file/fileListProject','data:file','1880490630882680855',3000,1,NULL,'_self','1880490630882680877','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1),('待办任务','/admin/cms/articleList','article:list','1880490630882680833',100,1,NULL,'_self','1880490902526779393','1','2025-01-18 13:42:23','1','1','2025-01-18 13:43:12',1),('里程碑','/admin/roadmap/list','article:list:group','1880490630882680833',200,1,NULL,'_self','1880490902526779395','1','2025-01-18 13:42:23','1','1','2025-01-18 13:43:12',1),('任务优先级','/admin/category/article/categoryListLevel','article:category:level','1879036264531288077',1080,1,NULL,'_self','18807','1','2025-01-30 15:32:23','1','1','2025-01-30 15:32:23',1),('我的工作','/admin/system/index?mclick=1880490630882680837&funId=2','mytask','0',0,1,'mdi mdi-monitor-cellphone','_self','2','1','2025-01-18 13:42:23','1',NULL,NULL,1),('个人设置','/admin/system/userEditByUser','userset','1000',1005,1,NULL,'_self','3','1','2025-01-18 13:42:23','1',NULL,NULL,1),('设置','/admin/system/index?mclick=3&funId=4','systemset','0',0,1,'mdi mdi-settings','_self','4','1','2025-01-18 13:42:23','1',NULL,NULL,1),('项目(下拉选择项目)','/admin/system/index?mclick=7&funId=5','project','0',0,1,NULL,'_self','5','1','2025-01-18 13:42:23','1',NULL,NULL,1),('项目概述','/admin/project/projectView','project:view','5',1000,1,'mdi mdi-presentation','_self','7','1','2025-01-18 13:41:18','1','1','2025-01-18 13:43:04',1);
/*!40000 ALTER TABLE `t_sys_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_organ`
--

DROP TABLE IF EXISTS `t_sys_organ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_organ` (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构名称',
  `organ_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构编号',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id',
  `pids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级id集合',
  `seq` int DEFAULT NULL COMMENT '排序',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_pid` (`pid`) USING BTREE,
  KEY `actable_idx_pids` (`pids`) USING BTREE,
  KEY `actable_idx_seq` (`seq`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='机构表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_organ`
--

LOCK TABLES `t_sys_organ` WRITE;
/*!40000 ALTER TABLE `t_sys_organ` DISABLE KEYS */;
INSERT INTO `t_sys_organ` VALUES ('上海','a1','0','',100,'1','1','2024-05-04 11:11:11','1','1','2024-05-31 21:39:09',1),('松江','a2','1','1',500,'2','1','2024-05-04 11:11:11','1','1','2024-06-10 11:13:25',1),('九亭','a3','4','1,4',300,'3','1','2024-05-04 11:11:11','1','1','2024-05-10 19:54:51',1),('徐汇','a4','1','1',400,'4','1','2024-05-04 11:11:11','1','1','2024-05-10 19:58:09',1);
/*!40000 ALTER TABLE `t_sys_organ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_record`
--

DROP TABLE IF EXISTS `t_sys_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_record` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `do_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '访问路径',
  `ips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地址',
  `method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提交方式',
  `params` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求内容',
  `useragent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游览器信息',
  `type` int DEFAULT NULL COMMENT '日志类型(1-操作日志 2-异常日志)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_record`
--

LOCK TABLES `t_sys_record` WRITE;
/*!40000 ALTER TABLE `t_sys_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role`
--

DROP TABLE IF EXISTS `t_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_role` (
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES ('管理员','1','1','2024-05-03 13:32:00','1','1','2026-04-09 20:20:28',1),('普通用户','2042216182987739139','1','2026-04-09 20:20:55','1',NULL,NULL,1);
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role_function`
--

DROP TABLE IF EXISTS `t_sys_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_role_function` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
  `fun_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `actable_uni_role_id_fun_id` (`role_id`,`fun_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色菜单多对多表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role_function`
--

LOCK TABLES `t_sys_role_function` WRITE;
/*!40000 ALTER TABLE `t_sys_role_function` DISABLE KEYS */;
INSERT INTO `t_sys_role_function` VALUES ('8','1','1'),('1','1','1000'),('2','1','1001'),('3','1','1002'),('4','1','1003'),('5','1','1004'),('6','1','1005'),('7','1','1006'),('9','1','1007'),('31','1','1009'),('32','1','1010'),('33','1','1011'),('34','1','1012'),('35','1','1013'),('36','1','1014'),('37','1','1015'),('38','1','1016'),('39','1','1017'),('40','1','1018'),('42','1','1879036264531288066'),('53','1','1879036264531288077'),('45','1','18801'),('43','1','1880490630882680833'),('50','1','1880490630882680837'),('51','1','1880490630882680839'),('55','1','1880490630882680855'),('56','1','1880490630882680866'),('57','1','1880490630882680877'),('44','1','1880490902526779393'),('49','1','1880490902526779395'),('54','1','18807'),('46','1','2'),('47','1','3'),('48','1','4'),('52','1','5'),('59','1','7'),('2043170307208491012','2042216182987739139','1000'),('2043156165382688774','2042216182987739139','1880490630882680833'),('2043156165382688770','2042216182987739139','1880490630882680837'),('2043156165382688772','2042216182987739139','1880490630882680839'),('2043955230256734210','2042216182987739139','1880490630882680855'),('2043955230256734211','2042216182987739139','1880490630882680866'),('2043955230256734212','2042216182987739139','1880490630882680877'),('2043156165382688775','2042216182987739139','1880490902526779393'),('2043156165382688773','2042216182987739139','1880490902526779395'),('2043156165382688769','2042216182987739139','2'),('2043170307208491010','2042216182987739139','3'),('2043170307208491011','2042216182987739139','4'),('2043156165382688771','2042216182987739139','5'),('60','2042216182987739139','7');
/*!40000 ALTER TABLE `t_sys_role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_role`
--

DROP TABLE IF EXISTS `t_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_user_role` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `actable_uni_user_id_role_id` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_role`
--

LOCK TABLES `t_sys_user_role` WRITE;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` VALUES ('1','1','1');
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_userinfo`
--

DROP TABLE IF EXISTS `t_sys_userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_userinfo` (
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户姓名',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `password_error` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码错误次数(分钟,错误次数)',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `phone_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户机构',
  `permission_type` int DEFAULT NULL COMMENT '权限类型(0-自己创建的数据 1-管辖机构的当前级及以下数据 2-全部数据)',
  `permission_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '权限',
  `life_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号有效期(超过该日期后无法登录)',
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_date` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_organ_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建机构id',
  `last_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人id',
  `last_update` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新时间',
  `status` int DEFAULT NULL COMMENT '删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)',
  `job_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户职位',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
  `init_flag` int DEFAULT '0' COMMENT '是否初始化(0-没有初始化 1-已初始化)',
  `join_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邀请标识(用户id的MD5)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `actable_uni_login_name` (`login_name`) USING BTREE,
  KEY `actable_idx_login_name` (`login_name`) USING BTREE,
  KEY `actable_idx_password` (`password`) USING BTREE,
  KEY `actable_idx_life_date` (`life_date`) USING BTREE,
  KEY `actable_idx_create_organ_id` (`create_organ_id`) USING BTREE,
  KEY `actable_idx_status` (`status`) USING BTREE,
  KEY `actable_idx_join_key` (`join_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_userinfo`
--

LOCK TABLES `t_sys_userinfo` WRITE;
/*!40000 ALTER TABLE `t_sys_userinfo` DISABLE KEYS */;
INSERT INTO `t_sys_userinfo` VALUES ('管理员','admin','','','','','1',NULL,NULL,'9999-01-01 00:00:00','1','1','2024-05-03 13:32:00','1','1','2026-04-17 22:42:19',1,'管理员','',0,'79c7eab1b07c49a1b3ba91c219b9c430');
/*!40000 ALTER TABLE `t_sys_userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-19 12:22:35
