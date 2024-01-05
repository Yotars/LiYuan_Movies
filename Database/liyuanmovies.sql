-- 判断 liyuanmovies 是否存在，存在则删除
DROP DATABASE IF EXISTS liyuanmovies;
-- 创建数据库 liyuanmovies
CREATE DATABASE IF NOT EXISTS liyuanmovies CHARACTER SET 'utf8' COLLATE utf8_general_ci;

-- 启动 liyuanmovies
USE liyuanmovies;

-- 创建表 user
CREATE TABLE `user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `power` int NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 插入数据 user
INSERT INTO `user` VALUES (1, 'root', '1234', 0);
