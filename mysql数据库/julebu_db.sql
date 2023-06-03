/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : julebu_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-05 22:50:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL COMMENT 'departmentNo',
  `departmentName` varchar(20) NOT NULL COMMENT '部门名称',
  `bornDate` varchar(20) default NULL COMMENT '成立日期',
  `departmentDesc` varchar(800) NOT NULL COMMENT '部门描述',
  PRIMARY KEY  (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '人事部', '2018-03-07', '管理人事信息');
INSERT INTO `t_department` VALUES ('BM002', '销售部', '2018-03-15', '销售公司产品信息');
INSERT INTO `t_department` VALUES ('BM003', '教练部', '2018-03-01', '本俱乐部教练们');

-- ----------------------------
-- Table structure for `t_device`
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `deviceId` int(11) NOT NULL auto_increment COMMENT '设备id',
  `deviceClassObj` int(11) NOT NULL COMMENT '设备类别',
  `deviceName` varchar(50) NOT NULL COMMENT '设备名称',
  `devicePhoto` varchar(60) NOT NULL COMMENT '设备图片',
  `price` float NOT NULL COMMENT '设备单价',
  `deviceCount` int(11) NOT NULL COMMENT '设备库存',
  `deviceDesc` varchar(5000) NOT NULL COMMENT '设备描述',
  `devicePlace` varchar(30) NOT NULL COMMENT '设备位置',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`deviceId`),
  KEY `deviceClassObj` (`deviceClassObj`),
  CONSTRAINT `t_device_ibfk_1` FOREIGN KEY (`deviceClassObj`) REFERENCES `t_deviceclass` (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_device
-- ----------------------------
INSERT INTO `t_device` VALUES ('1', '1', '侠客X7跑步机健身房专用', 'upload/c8af89e8-1d24-4017-bc58-c2146443d4bd.jpg', '1498', '33', '<ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>上市时间:&nbsp;2018年春季</p></li><li><p>产地:&nbsp;中国</p></li><li><p>可持续输出马力:&nbsp;2hp(不含)－3hp（含）</p></li><li><p>品牌:&nbsp;侠客</p></li><li><p>系列:&nbsp;家用免安装</p></li><li><p>屏幕类型:&nbsp;液晶显示屏</p></li><li><p>展开尺寸:&nbsp;130x56x116cm</p></li><li><p>峰值马力:&nbsp;3hp</p></li><li><p>折叠尺寸:&nbsp;39x56x130cm</p></li><li><p>是否商场同款:&nbsp;是</p></li><li><p>跑带区域:&nbsp;110x41cm</p></li><li><p>跑带宽度:&nbsp;42以下（不含）</p></li><li><p>跑步机净重:&nbsp;33kg</p></li><li><p>跑步机最大承重:&nbsp;130kg</p></li><li><p>配送方式:&nbsp;配送到买家家中</p></li><li><p>颜色分类:&nbsp;2018新品/免安装/新技术/超大触屏/超大承重/典雅白&nbsp;2018新品/免安装/新技术/超大触屏/钢琴烤漆/亮黑色</p></li><li><p>持续输出马力:&nbsp;1.8hp</p></li><li><p>货号:&nbsp;X7</p></li><li><p>健身器材分类:&nbsp;家用跑步机</p></li><li><p>驱动类型:&nbsp;电子</p></li><li><p>跑步机类型:&nbsp;多功能跑步机</p></li><li><p>心率测试:&nbsp;无线心率测试</p></li><li><p>是否可折叠:&nbsp;是</p></li><li><p>售后服务:&nbsp;全国联保</p></li></ul><p><br/></p>', '仓库2', '2018-03-13 00:02:27');
INSERT INTO `t_device` VALUES ('2', '2', 'HP/惠普 M126a多功能黑白激光打印机', 'upload/0add9e69-3d56-4cb5-85f6-e6669a8784f6.jpg', '1548', '12', '<ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>证书编号：2015010905792994</p></li><li><p>证书状态：有效</p></li><li><p>产品名称：激光打印、复印、扫描一体机</p></li><li><p>3C规格型号：LaserJet Pro MFP M126a（规定型号：SHNGC-1202-00）、LaserJe...</p></li><li><p>产品名称：HP/惠普 LaserJet Pro MF...</p></li><li><p>品牌:&nbsp;HP/惠普</p></li><li><p>惠普型号:&nbsp;LaserJet Pro MFP M126a</p></li><li><p>生产企业:&nbsp;惠普科技有限公司</p></li><li><p>能效等级:&nbsp;一级</p></li><li><p>远程打印方式:&nbsp;无</p></li><li><p>颜色分类:&nbsp;惠普 M126a（打印复印扫描+20页/分钟）+全国5仓发货</p></li><li><p>一体机类型:&nbsp;黑白激光多功能一体机</p></li><li><p>涵盖功能:&nbsp;复印&nbsp;打印&nbsp;扫描</p></li><li><p>耗材类型:&nbsp;鼓粉一体</p></li><li><p>最大幅面:&nbsp;A4</p></li><li><p>是否支持网络打印:&nbsp;不支持</p></li><li><p>是否支持自动双面打印:&nbsp;手动</p></li><li><p>接口类型:&nbsp;USB</p></li></ul><p><br/></p>', '仓库1', '2018-03-21 15:35:37');

-- ----------------------------
-- Table structure for `t_deviceclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_deviceclass`;
CREATE TABLE `t_deviceclass` (
  `classId` int(11) NOT NULL auto_increment COMMENT '类别id',
  `className` varchar(40) NOT NULL COMMENT '类别名称',
  `classDesc` varchar(500) NOT NULL COMMENT '类别描述',
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_deviceclass
-- ----------------------------
INSERT INTO `t_deviceclass` VALUES ('1', '健身器械', '用于健身的设备');
INSERT INTO `t_deviceclass` VALUES ('2', '办公设备', '用于办公的设备');

-- ----------------------------
-- Table structure for `t_devicerepair`
-- ----------------------------
DROP TABLE IF EXISTS `t_devicerepair`;
CREATE TABLE `t_devicerepair` (
  `repairId` int(11) NOT NULL auto_increment COMMENT '维修id',
  `deviceObj` int(11) NOT NULL COMMENT '维修的设备',
  `deviceQuestion` varchar(60) NOT NULL COMMENT '设备问题',
  `repairCount` int(11) NOT NULL COMMENT '维修数量',
  `repairPlace` varchar(20) NOT NULL COMMENT '送修地点',
  `custTime` varchar(20) NOT NULL COMMENT '花费时间',
  `costMoney` float NOT NULL COMMENT '维修费',
  `repairResult` varchar(60) NOT NULL COMMENT '维修结果',
  `repairMemo` varchar(800) default NULL COMMENT '维修备注',
  PRIMARY KEY  (`repairId`),
  KEY `deviceObj` (`deviceObj`),
  CONSTRAINT `t_devicerepair_ibfk_1` FOREIGN KEY (`deviceObj`) REFERENCES `t_device` (`deviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_devicerepair
-- ----------------------------
INSERT INTO `t_devicerepair` VALUES ('1', '1', '跑步有点卡', '1', '成都机械厂', '1天', '200', '换了一个卡伦', '修好了');
INSERT INTO `t_devicerepair` VALUES ('2', '2', '部分字迹模糊', '1', '成都电子设备厂', '5小时', '200', '换了一个电路板', '修好了');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(30) NOT NULL COMMENT 'employeeNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `departmentObj` varchar(20) NOT NULL COMMENT '所在部门',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '员工照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `employeeDesc` varchar(8000) NOT NULL COMMENT '员工档案变动',
  PRIMARY KEY  (`employeeNo`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_employee_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '123', 'BM002', '张晓芬', '女', '2018-03-07', 'upload/f104b892-867a-4720-a324-1070fd2f2da9.jpg', '13980833984', 'xiaofen@163.com', '四川成都跳蹬河路', '<p>此员工2017年12月入职试用期</p><p>2018年2月转正，直接进入销售部门</p>');
INSERT INTO `t_employee` VALUES ('EM002', '123', 'BM003', '李晓文', '男', '2018-03-08', 'upload/ce60f32c-70ac-463f-b771-520467af09da.jpg', '13598098082', 'xiaowen@163.com', '四川自贡市', '<p>2018年入职当我们俱乐部的游泳教练</p>');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我想锻炼身体', '我的身体有点肥，想瘦下来', 'user1', '2018-03-13 00:02:02', '可以来跑步 ', '2018-03-13 00:02:07');
INSERT INTO `t_leaveword` VALUES ('2', '我想学游泳', '我不会游泳，是个旱鸭子，想学习！', 'user1', '2018-03-21 12:29:55', '咱们这里有有游泳教练，也有游泳池，来！', '2018-03-21 15:29:03');

-- ----------------------------
-- Table structure for `t_salary`
-- ----------------------------
DROP TABLE IF EXISTS `t_salary`;
CREATE TABLE `t_salary` (
  `salaryId` int(11) NOT NULL auto_increment COMMENT '工资id',
  `year` varchar(20) NOT NULL COMMENT '工资年份',
  `month` varchar(20) NOT NULL COMMENT '工资月份',
  `salaryMoney` float NOT NULL COMMENT '工资金额',
  `employeeObj` varchar(30) NOT NULL COMMENT '发放员工',
  `getDate` varchar(20) default NULL COMMENT '发放日期',
  `salaryMemo` varchar(800) default NULL COMMENT '工资备注',
  PRIMARY KEY  (`salaryId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_salary_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_salary
-- ----------------------------
INSERT INTO `t_salary` VALUES ('1', '2018', '2', '3200', 'EM001', '2018-03-05', 'tes');
INSERT INTO `t_salary` VALUES ('2', '2018', '2', '3300', 'EM002', '2018-03-01', '奖励了100全勤奖');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `zhiye` varchar(20) NOT NULL COMMENT '职业',
  `birthDate` varchar(20) default NULL COMMENT '生日',
  `userPhoto` varchar(60) NOT NULL COMMENT '会员照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系方式',
  `email` varchar(50) NOT NULL COMMENT 'Email',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `jsmb` varchar(800) NOT NULL COMMENT '健身目标',
  `jsxg` varchar(800) NOT NULL COMMENT '健身习惯',
  `khbz` varchar(800) default NULL COMMENT '客户备注',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '王超', '男', '双鱼林', '2018-03-06', 'upload/1fe77d19-d13e-4da0-82c9-72cfd792dcf7.jpg', '13958342342', 'dashen@163.com', '四川成都红星路13号', '练出8块腹肌', '每天早上晨练', '尽快安排', '2018-03-12 23:58:27');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王千里', '男', '老板', '2018-03-15', 'upload/27299fd7-9f13-46a7-b186-260d5f8095c2.jpg', '13980839843', 'laoban@126.com', '四川南充滨江路', '练出一个好身材', '周末锻炼', '我是一个自由人', '2018-03-21 14:50:20');

-- ----------------------------
-- Table structure for `t_xiaofei`
-- ----------------------------
DROP TABLE IF EXISTS `t_xiaofei`;
CREATE TABLE `t_xiaofei` (
  `xiaofeiId` int(11) NOT NULL auto_increment COMMENT '消费id',
  `xiaofeiName` varchar(60) NOT NULL COMMENT '消费项目',
  `xiaofeiDesc` varchar(8000) NOT NULL COMMENT '消费详情',
  `xiaofeiMoney` float NOT NULL COMMENT '消费金额',
  `memberObj` varchar(30) NOT NULL COMMENT '消费会员',
  `xiaofeiTime` varchar(20) default NULL COMMENT '消费时间',
  `dqsj` varchar(20) NOT NULL COMMENT '到期时间',
  `xiaofeiMemo` varchar(800) default NULL COMMENT '消费备注',
  PRIMARY KEY  (`xiaofeiId`),
  KEY `memberObj` (`memberObj`),
  CONSTRAINT `t_xiaofei_ibfk_1` FOREIGN KEY (`memberObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_xiaofei
-- ----------------------------
INSERT INTO `t_xiaofei` VALUES ('1', '购买健身套餐', '<p>选择了我们一个健身教练，买了半年的健身套餐！</p>', '6000', 'user1', '2018-03-06 23:59:53', '2018-09-06', '测试消费');
INSERT INTO `t_xiaofei` VALUES ('2', '购买健身器材', '<p>从咱们公司购买了一个跑步机</p>', '2000', 'user1', '2018-03-21 15:27:42', '--', '购买商品');
