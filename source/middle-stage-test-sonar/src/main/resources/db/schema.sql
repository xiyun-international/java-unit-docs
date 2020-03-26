DROP TABLE IF EXISTS `mc_dinner_type`;
CREATE TABLE `mc_dinner_type`  (
  `dinner_type_id` int(10) NOT NULL COMMENT '餐别主键id',
  `dinner_type_name` varchar(100) NOT NULL DEFAULT '' COMMENT '餐别名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '餐别描述',
  `default_start_time` int(10) NOT NULL DEFAULT 0 COMMENT '就餐开始时间(转换成分如:8:30转换为8*60+30=510)',
  `default_end_time` int(10) NOT NULL DEFAULT 0 COMMENT '就餐结束时间(转换成分如:8:30转换为8*60+30=510)',
  `valid_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(0:无效1:有效)',
  `create_time` datetime(0) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`dinner_type_id`)
);

DROP TABLE IF EXISTS `mc_dinner`;
CREATE TABLE `mc_dinner`  (
  `dinner_id` int(10) NOT NULL COMMENT '自增主键id',
  `dinner_type_id` int(10) NOT NULL DEFAULT 0 COMMENT '餐别ID',
  `canteen_id` int(10) NOT NULL DEFAULT 0 COMMENT '门店ID',
  `start_time` int(10) NOT NULL DEFAULT 0 COMMENT '就餐开始时间(转换成分如:8:30转换为8*60+30=510)',
  `end_time` int(10) NOT NULL DEFAULT 0 COMMENT '就餐结束时间(转换成分如:8:30转换为8*60+30=510)',
  `delete_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(0:无效1:有效)',
  `create_user_name` varchar(36) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_id` int(10) NOT NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user_name` varchar(36) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_user_id` int(10) NOT NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`dinner_id`)
);