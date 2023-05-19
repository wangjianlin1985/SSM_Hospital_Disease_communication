/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : hospital

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2019-07-21 23:11:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `condition`
-- ----------------------------
DROP TABLE IF EXISTS `condition`;
CREATE TABLE `condition` (
  `id` varchar(32) NOT NULL,
  `userid` varchar(32) DEFAULT NULL COMMENT '所属病人',
  `remarks` text COMMENT '病情描述',
  `deptid` varchar(32) DEFAULT NULL COMMENT '科室',
  `time` varchar(32) DEFAULT NULL COMMENT '时间',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `replycount` int(11) DEFAULT '0' COMMENT '回复数暂时未用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of condition
-- ----------------------------
INSERT INTO `condition` VALUES ('1f5b75a31f844b0f986523b92e06553c', 'a521c64799c3490ca17d6018a74e084c', '我的鼻子经常不通，流鼻涕，特别是冬天，闻到辣的味道就打喷嚏，怎么办啊？', '4559b5822ad44f18b0d50cae5ea54803', '2019-07-21 22:03:34', '我鼻子经常不通', '0');
INSERT INTO `condition` VALUES ('2eaa238aedb5482388b8484e671fa107', 'a521c64799c3490ca17d6018a74e084c', '宝宝38个月，双眼向内斜视，俗称斗鸡眼。转动都没有问题，只是向前直视时，两眼中间明显不留白。16个月时在妇幼保健院查过一次，当时没有近视和散光问题，医生说大了会慢慢变好。目前看来貌似有好转，但不明显，还是斗鸡眼。今天又去市里最好的医院眼科检查，医生用笔照了下眼球说眼球位置是正的，然后开单子叫再做一下快速验光，后由于宝宝太小不能配合滴眼药水，没有能进行检查。医生说我家宝宝眼距宽，等大了鼻梁高了会好起来。想问一下，这种情况有没有专用的矫正眼镜，治疗的最佳时间控制在几岁以内？', '9e1ca0818b68414cb0c5d96ce8925bf1', '2019-04-21 11:02:44', '小孩子斗鸡眼', null);

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` varchar(32) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '科室名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序越大越靠前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('4559b5822ad44f18b0d50cae5ea54803', '口鼻科', '98');
INSERT INTO `dept` VALUES ('4c0f43481af64f7da0a266f6b63378ce', '内分泌科', '97');
INSERT INTO `dept` VALUES ('8a3fb3a6fe944ca4af4816477776f602', '脑科', '100');
INSERT INTO `dept` VALUES ('9e1ca0818b68414cb0c5d96ce8925bf1', '心脏内科', '99');

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logId` varchar(32) NOT NULL COMMENT '日志',
  `userName` varchar(30) DEFAULT NULL COMMENT '操作人',
  `createTime` varchar(30) DEFAULT NULL COMMENT '时间',
  `content` text COMMENT '详细',
  `operation` varchar(300) DEFAULT NULL COMMENT '操作类型（增删改）',
  `ip` varchar(60) DEFAULT NULL COMMENT 'IP地址',
  `module` varchar(40) DEFAULT NULL COMMENT '所属模块',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志记录';

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuId` varchar(32) NOT NULL COMMENT '菜单ID',
  `menuName` varchar(50) DEFAULT NULL COMMENT '名称',
  `menuUrl` varchar(100) DEFAULT NULL COMMENT '方法',
  `parentId` varchar(32) DEFAULT NULL COMMENT '父ID',
  `menuDescription` varchar(200) DEFAULT NULL COMMENT '描述',
  `state` varchar(20) DEFAULT NULL COMMENT '状态/OPEN/CLOSED',
  `iconCls` varchar(50) DEFAULT NULL COMMENT '图标',
  `seq` int(11) DEFAULT NULL COMMENT '顺序排序',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('29c53a3761f14f67a9401212e30fc923', '科室管理', 'dept/deptIndex.htm', '54475f4f787b45daa3df6f2ee0b312aa', '', 'open', 'icon-', '5');
INSERT INTO `menu` VALUES ('54475f4f787b45daa3df6f2ee0b312aa', '系统管理', '', 'assadd5325af432db5dc825499dede71', '', 'closed', 'icon-permission', '1');
INSERT INTO `menu` VALUES ('57295d5325af432db5dc825499dede71', '信息管理', '', 'assadd5325af432db5dc825499dede71', '', 'closed', 'icon-265', '2');
INSERT INTO `menu` VALUES ('5c796c8fedf8430b8f07dfe27f4c48dd', '角色管理', 'role/roleIndex.htm', '54475f4f787b45daa3df6f2ee0b312aa', '', 'open', 'icon-486', '3');
INSERT INTO `menu` VALUES ('688800249b264683bb3d3de01f0072a5', '病情管理', 'condition/adminIndex.htm', '57295d5325af432db5dc825499dede71', '', 'open', 'icon-1', '1');
INSERT INTO `menu` VALUES ('922b1f2962574a9084f80ea0ceb2f0c5', '菜单管理', 'menu/menuIndex.htm', '54475f4f787b45daa3df6f2ee0b312aa', '', 'open', 'icon-menuManage', '4');
INSERT INTO `menu` VALUES ('a9b30efaf3344a8b9afaf86d420b81bf', '我的病情', 'condition/patientIndex.htm', '57295d5325af432db5dc825499dede71', '', 'open', 'icon-', '1');
INSERT INTO `menu` VALUES ('assadd5325af432db5dc825499dede71', '医院随访系统', '', '-1', '主菜单', 'closed', 'icon-home', '1');
INSERT INTO `menu` VALUES ('f6c7b05fef0d4b75a3496fcf281c2524', '用户管理', 'user/userIndex.htm', '54475f4f787b45daa3df6f2ee0b312aa', '', 'open', 'icon-489', '2');
INSERT INTO `menu` VALUES ('fbd230f960ea4f128574c1f744fbb747', '病人病情', 'condition/doctorIndex.htm', '57295d5325af432db5dc825499dede71', '', 'open', 'icon-', '1');

-- ----------------------------
-- Table structure for `operation`
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `operationId` varchar(64) NOT NULL COMMENT '具体的方法',
  `operationName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `menuId` varchar(32) DEFAULT NULL COMMENT '所属菜单',
  `menuName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`operationId`),
  KEY `menuId` (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='具体的页面按钮上的方法\r\n（此自增ID至少从10000开始）';

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES ('05d03b893558479599156bd469a83c', '清空交流', '688800249b264683bb3d3de01f0072a5', '病情管理');
INSERT INTO `operation` VALUES ('05da9b23441449b88cf56a3c762b4c', '授权', '5c796c8fedf8430b8f07dfe27f4c48dd', '角色管理');
INSERT INTO `operation` VALUES ('0d44338b1b0a4b60b83be9427fd6b2', '删除', 'a9b30efaf3344a8b9afaf86d420b81bf', '我的病情');
INSERT INTO `operation` VALUES ('387889d24a9242238449e40871daf2', '详情', '688800249b264683bb3d3de01f0072a5', '病情管理');
INSERT INTO `operation` VALUES ('56d57a539e5b4a9bbada9ce9024f68', '修改', '29c53a3761f14f67a9401212e30fc923', '科室管理');
INSERT INTO `operation` VALUES ('570c269b28dc483da09f32fa75fb1c', '添加', '5c796c8fedf8430b8f07dfe27f4c48dd', '角色管理');
INSERT INTO `operation` VALUES ('66ca80a3965c43c395a98cc16a5ebe', '添加', 'a9b30efaf3344a8b9afaf86d420b81bf', '我的病情');
INSERT INTO `operation` VALUES ('6806a8021f1a41bba56753da2c83ab', '删除', '29c53a3761f14f67a9401212e30fc923', '科室管理');
INSERT INTO `operation` VALUES ('6de09cc41d834dd3a5b66f9bcbc3a9', '删除', '922b1f2962574a9084f80ea0ceb2f0c5', '菜单管理');
INSERT INTO `operation` VALUES ('73740dfbdfdd497ab949b0052e972b', '添加', '29c53a3761f14f67a9401212e30fc923', '科室管理');
INSERT INTO `operation` VALUES ('7581831ec7504ea592241df49441b5', '删除', '5c796c8fedf8430b8f07dfe27f4c48dd', '角色管理');
INSERT INTO `operation` VALUES ('78cfc2d05f394e619300c3c22ba51c', '添加', '922b1f2962574a9084f80ea0ceb2f0c5', '菜单管理');
INSERT INTO `operation` VALUES ('795f2fefd6ea4e4cae96ef6633ca9a', '修改', '5c796c8fedf8430b8f07dfe27f4c48dd', '角色管理');
INSERT INTO `operation` VALUES ('7e609bcf3b5b4313bd6bd01e1bcb36', '添加', 'f6c7b05fef0d4b75a3496fcf281c2524', '用户管理');
INSERT INTO `operation` VALUES ('7f1f1c3bf1de4e5fbc8e95a4851871', '修改', '922b1f2962574a9084f80ea0ceb2f0c5', '菜单管理');
INSERT INTO `operation` VALUES ('8a5cc94bc02c4ec9a082524bc9b396', '修改', 'f6c7b05fef0d4b75a3496fcf281c2524', '用户管理');
INSERT INTO `operation` VALUES ('a0c7e24260eb4bdc90f6c87dfd069c', '修改', 'a9b30efaf3344a8b9afaf86d420b81bf', '我的病情');
INSERT INTO `operation` VALUES ('b9d2596fb6914b8392809ec16be0cb', '删除', 'f6c7b05fef0d4b75a3496fcf281c2524', '用户管理');
INSERT INTO `operation` VALUES ('bcdc2f0457fc44c694dbcc2d81f6fb', '详情', 'fbd230f960ea4f128574c1f744fbb747', '病人病情');
INSERT INTO `operation` VALUES ('ebf5a2f3af5f4d67ad05fd9a5691b2', '按钮管理', '922b1f2962574a9084f80ea0ceb2f0c5', '菜单管理');

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` varchar(32) NOT NULL COMMENT '交流回复ID',
  `time` varchar(32) DEFAULT NULL COMMENT '时间',
  `remarks` text COMMENT '内容',
  `doctorid` varchar(32) DEFAULT NULL COMMENT '医生ID',
  `patientid` varchar(32) DEFAULT NULL COMMENT '病人ID',
  `conditionid` varchar(32) DEFAULT NULL COMMENT '病情ID',
  `userid` varchar(32) DEFAULT NULL COMMENT '发言者ID',
  `type` char(1) DEFAULT NULL COMMENT '1公共 2私聊',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('166b4d76d0574139af1dbabf7492a72f', '2019-07-21 22:04:00', '有医生帮我看看吗？', null, null, '1f5b75a31f844b0f986523b92e06553c', 'a521c64799c3490ca17d6018a74e084c', '1');
INSERT INTO `reply` VALUES ('187f91eebbe94b4e955e455f8bbfdf4b', '2019-07-21 22:14:58', '我看你的鼻子是有过敏性鼻炎，是慢性的！', null, null, '1f5b75a31f844b0f986523b92e06553c', '8752707d57e840749ff97946b9ec492f', '1');
INSERT INTO `reply` VALUES ('4c0eb914fef240d194497654ddba9463', '2019-04-21 19:30:05', '根据您的情况，我觉得是流感的可能。您可以继续观察体温情况，如果连续72小时不发烧，那么才可以认定不发烧了。因为您用的药里面含有激素，所以哪怕现在没有发烧也不能确定是不是因为治疗有效果。我是建议您服用一些抗病毒的药物，比如奥司他韦胶囊75mg，每天2次；疏风解毒胶囊，每次4粒，每天3次。\n至于易出汗，这个反应了您的现在身体比较虚弱。口干说明发烧后身体缺水的表现，需要补充水分。', '6568b5534af34e7b989b2a4d4e28d1e2', 'a521c64799c3490ca17d6018a74e084c', '2eaa238aedb5482388b8484e671fa107', '6568b5534af34e7b989b2a4d4e28d1e2', '2');
INSERT INTO `reply` VALUES ('5f918d4ba9f446d4acffed264d6fc4b2', '2019-07-21 23:08:52', '谢谢医生提醒', '8752707d57e840749ff97946b9ec492f', 'a521c64799c3490ca17d6018a74e084c', '1f5b75a31f844b0f986523b92e06553c', 'a521c64799c3490ca17d6018a74e084c', '2');
INSERT INTO `reply` VALUES ('7f5a101cd0f34f9ba32db4d44813aa93', '2019-04-21 20:57:37', '医生啊，我还有不拉不拉的问题', '6568b5534af34e7b989b2a4d4e28d1e2', 'a521c64799c3490ca17d6018a74e084c', '2eaa238aedb5482388b8484e671fa107', 'a521c64799c3490ca17d6018a74e084c', '2');
INSERT INTO `reply` VALUES ('8070e7d36c014e89af7a186c02f2f339', '2019-04-21 19:29:48', '确实只是美观问题，等看鼻梁发育情况，如长大后改善不够明显再考虑整形美容。', null, null, '2eaa238aedb5482388b8484e671fa107', '6568b5534af34e7b989b2a4d4e28d1e2', '1');
INSERT INTO `reply` VALUES ('9073cfd32fa242fcb794bd6a4e4280cc', '2019-07-21 22:18:04', '给你私聊下，不要相信任何医托骗子', '8752707d57e840749ff97946b9ec492f', 'a521c64799c3490ca17d6018a74e084c', '1f5b75a31f844b0f986523b92e06553c', '8752707d57e840749ff97946b9ec492f', '2');
INSERT INTO `reply` VALUES ('a80455639074498285853ccd7a2c0bf9', '2019-04-21 20:57:47', '啊。什么问题啊', '6568b5534af34e7b989b2a4d4e28d1e2', 'a521c64799c3490ca17d6018a74e084c', '2eaa238aedb5482388b8484e671fa107', '6568b5534af34e7b989b2a4d4e28d1e2', '2');
INSERT INTO `reply` VALUES ('ab117e8991464ef5a37bff6d9cfe74fa', '2019-07-21 22:15:18', '需要慢慢治疗', null, null, '1f5b75a31f844b0f986523b92e06553c', '8752707d57e840749ff97946b9ec492f', '1');
INSERT INTO `reply` VALUES ('d4dbf9c198b7458d8e8d6d538e8f2378', '2019-04-21 19:29:34', '根据你提供的病情信息，没有发现存在斜视，诊断是存在内眦赘皮 。内眦赘皮就是鼻梁两边的内眼角皮肤多挡住眼白比较多。所以看起来好像两只眼睛向里斜视，其实眼睛是正位的。一般不需要处理，可以用手牵拉一下两侧的皮肤就可以看到正常了。科学的判断是否斜视，是主要是看黑眼珠里面的两个亮点是否对称，你提供的照片两边亮点很对称，所以不存在斜视。 随着孩子的年龄长大，鼻梁增高以后，内眦赘皮会有所改善，等到成年以后如果还影响美容的话，可以进行开眼角手术就好了。这种情况没有专用的矫正眼镜，治疗的时间在几岁至成年均可。妈妈不用担心，日常生活上要注意用眼的习惯，多到户外活动，看绿色的环境。希望以上建议对你有帮助！祝早日康复！', null, null, '2eaa238aedb5482388b8484e671fa107', '6568b5534af34e7b989b2a4d4e28d1e2', '1');
INSERT INTO `reply` VALUES ('f7e94a49447b4065bd24c7c10572cdd1', '2019-04-21 21:02:12', '呵呵', '6568b5534af34e7b989b2a4d4e28d1e2', 'a521c64799c3490ca17d6018a74e084c', '2eaa238aedb5482388b8484e671fa107', '6568b5534af34e7b989b2a4d4e28d1e2', '2');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` varchar(32) NOT NULL COMMENT '角色ID',
  `roleName` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `menuIds` longtext COMMENT '菜单IDs',
  `operationIds` longtext COMMENT '按钮IDS',
  `roleDescription` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', 'assadd5325af432db5dc825499dede71,54475f4f787b45daa3df6f2ee0b312aa,f6c7b05fef0d4b75a3496fcf281c2524,5c796c8fedf8430b8f07dfe27f4c48dd,922b1f2962574a9084f80ea0ceb2f0c5,29c53a3761f14f67a9401212e30fc923,57295d5325af432db5dc825499dede71,688800249b264683bb3d3de01f0072a5', '7e609bcf3b5b4313bd6bd01e1bcb36,8a5cc94bc02c4ec9a082524bc9b396,b9d2596fb6914b8392809ec16be0cb,05da9b23441449b88cf56a3c762b4c,570c269b28dc483da09f32fa75fb1c,7581831ec7504ea592241df49441b5,795f2fefd6ea4e4cae96ef6633ca9a,6de09cc41d834dd3a5b66f9bcbc3a9,78cfc2d05f394e619300c3c22ba51c,7f1f1c3bf1de4e5fbc8e95a4851871,ebf5a2f3af5f4d67ad05fd9a5691b2,56d57a539e5b4a9bbada9ce9024f68,6806a8021f1a41bba56753da2c83ab,73740dfbdfdd497ab949b0052e972b,05d03b893558479599156bd469a83c,387889d24a9242238449e40871daf2', '拥有全部权限的超级管理员角色');
INSERT INTO `role` VALUES ('1eaa49c355344db6a99334d40163fddb', '病人', 'assadd5325af432db5dc825499dede71,57295d5325af432db5dc825499dede71,a9b30efaf3344a8b9afaf86d420b81bf', '0d44338b1b0a4b60b83be9427fd6b2,66ca80a3965c43c395a98cc16a5ebe,a0c7e24260eb4bdc90f6c87dfd069c', '');
INSERT INTO `role` VALUES ('815f6fd1f3904e25b779120f7e3b63be', '医生', 'assadd5325af432db5dc825499dede71,57295d5325af432db5dc825499dede71,fbd230f960ea4f128574c1f744fbb747', 'bcdc2f0457fc44c694dbcc2d81f6fb', '');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `roleId` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `description` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `deptid` varchar(32) DEFAULT NULL COMMENT '科室',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '1', '', null, '9e1ca0818b68414cb0c5d96ce8925bf1', '管理员', null);
INSERT INTO `user` VALUES ('6568b5534af34e7b989b2a4d4e28d1e2', 'A001', '123456', '815f6fd1f3904e25b779120f7e3b63be', '', '13866778899@qq.com', '9e1ca0818b68414cb0c5d96ce8925bf1', '张琳', '13866778899');
INSERT INTO `user` VALUES ('8752707d57e840749ff97946b9ec492f', 'A002', '123456', '815f6fd1f3904e25b779120f7e3b63be', '', '13866667777@qq.com', '4559b5822ad44f18b0d50cae5ea54803', '刘能', '13866667777');
INSERT INTO `user` VALUES ('a521c64799c3490ca17d6018a74e084c', '340000200001010001', '123456', '1eaa49c355344db6a99334d40163fddb', '', '13877776666@qq.com', '9e1ca0818b68414cb0c5d96ce8925bf1', '赵四', '13877776666');
