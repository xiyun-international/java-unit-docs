DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(36) NOT NULL,
  `user_name` varchar(30) NULL DEFAULT NULL,
  `password` varchar(50) NULL DEFAULT NULL,
  `mobile` varchar(13) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `crate_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `t_user` VALUES('zyq','zyq','123456','17612345678',NOW(),NOW());