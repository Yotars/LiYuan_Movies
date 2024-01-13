-- 判断 liyuanmovies 是否存在，存在则删除
DROP DATABASE IF EXISTS liyuanmovies;
-- 创建数据库 liyuanmovies
CREATE DATABASE IF NOT EXISTS liyuanmovies CHARACTER SET 'utf8' COLLATE utf8_general_ci;

-- 启动 liyuanmovies
USE liyuanmovies;

-- 创建表 user
CREATE TABLE `user`  (
  `uId` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `power` int NOT NULL COMMENT '用户权限',
  PRIMARY KEY (`uId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 创建表 info
CREATE TABLE `info`  (
  `uId` int NOT NULL COMMENT '用户id',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `head` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `birth` date NULL DEFAULT NULL COMMENT '用户生日',
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户地址',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `mail` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`uId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建表 oder
CREATE TABLE `oder`  (
  `oId` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `uId` int NOT NULL COMMENT '用户ID',
  `oNumber` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `oDetails` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单详情',
  `oMessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单信息',
  `oDate` datetime NOT NULL COMMENT '订单创建时间',
  `oStatus` tinyint NOT NULL COMMENT '订单状态: 0-已取消 1-待处理 2-已完成',
  PRIMARY KEY (`oId`, `uId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建表 movies
CREATE TABLE `movies`  (
  `mId` int NOT NULL AUTO_INCREMENT COMMENT '电影id',
  `mName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电影名称',
  `mImage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电影图片',
  `director` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导演',
  `actors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '演员',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电影类型',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `launchDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上映时间',
  `filmLength` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时长',
  `IMDb` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IMDb',
  `Intro` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`mId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 284 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建表 type
CREATE TABLE `type`  (
  `t` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电影类型'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建表 room
CREATE TABLE `room`  (
  `rId` int NOT NULL AUTO_INCREMENT COMMENT '放映厅id',
  `rName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '放映厅名称',
  `mId` int NOT NULL COMMENT '电影id',
  `start` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `rTotal` int NULL DEFAULT NULL COMMENT '座位总数',
  `rest` int NULL DEFAULT NULL COMMENT '剩余座位',
  PRIMARY KEY (`rId`, `mId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建表 seat
CREATE TABLE `seat`  (
  `sId` int NOT NULL AUTO_INCREMENT COMMENT '座位ID',
  `rId` int NOT NULL COMMENT '放映室ID',
  `status` tinyint NOT NULL COMMENT '座位状态: 0-未选择 1-已选择',
  `r` int NOT NULL COMMENT '座位: 行',
  `l` int NOT NULL COMMENT '座位: 列',
  PRIMARY KEY (`sId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
