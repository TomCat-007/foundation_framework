/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : example_dev

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 24/03/2023 15:07:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account` (
                               `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                               `organ_id` bigint unsigned NOT NULL COMMENT '机构 ID',
                               `identity_id` bigint NOT NULL COMMENT '身份 ID',
                               `platform` tinyint NOT NULL DEFAULT '1' COMMENT '账号所属平台：0 超级管理员 1 用户 5 机构管理员',
                               `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '姓名',
                               `mobile` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号',
                               `gender` tinyint DEFAULT NULL COMMENT '性别: 1 男 0 女',
                               `is_enabled` tinyint NOT NULL DEFAULT '0' COMMENT '账户是否启用: 1 已启用, 0 未启用',
                               `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                               `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                               `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                               `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                               `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='账户表';

-- ----------------------------
-- Records of sys_account
-- ----------------------------
BEGIN;
INSERT INTO `sys_account` (`id`, `organ_id`, `identity_id`, `platform`, `name`, `mobile`, `gender`, `is_enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1, 1, 1, 0, '', '', NULL, 1, 0, 0, 0, 0, 0, '2021-11-29 07:45:39', '2023-03-24 14:55:36');
INSERT INTO `sys_account` (`id`, `organ_id`, `identity_id`, `platform`, `name`, `mobile`, `gender`, `is_enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1463692360646004736, 1463692360646004739, 1530353558849982464, 5, '', '', NULL, 1, 0, 0, 0, 0, 0, '2021-11-24 08:08:37', '2023-03-24 14:55:36');
COMMIT;

-- ----------------------------
-- Table structure for sys_account_role_union
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_role_union`;
CREATE TABLE `sys_account_role_union` (
                                          `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                          `account_id` bigint NOT NULL COMMENT '账户 ID',
                                          `role_id` bigint NOT NULL COMMENT '角色 ID',
                                          `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                                          `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                          `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                          `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                          `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='账户与角色关联表';

-- ----------------------------
-- Records of sys_account_role_union
-- ----------------------------
BEGIN;
INSERT INTO `sys_account_role_union` (`id`, `account_id`, `role_id`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1, 1, 1, 0, 0, 0, 0, 0, '2022-05-28 09:01:35', '2023-03-24 14:56:21');
INSERT INTO `sys_account_role_union` (`id`, `account_id`, `role_id`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1404722679155633725, 1463692360646004736, 1404722679155658753, 0, 0, 0, 0, 0, '2023-03-24 14:59:31', '2023-03-24 14:59:43');
COMMIT;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
                                  `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '上级 ID,0 表示顶级',
                                  `prev_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '前驱节点 ID,0 表示头部节点',
                                  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
                                  `label` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签, 通常用于渲染表头',
                                  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                                  `type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '类型: 1 文本, 2 数值, 5 区划',
                                  `item_type` tinyint unsigned DEFAULT '0' COMMENT '子类型: 0: 无, 1 文本, 2 数值, 5 区划',
                                  `order_num` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '元素序号',
                                  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注描述',
                                  `is_enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用:1:已启用:0:未启用',
                                  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除:1:已删除:0:未删除',
                                  `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                  `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                  `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                  `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新账户 ID',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE KEY `uk_code_deleted` (`code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_element
-- ----------------------------
DROP TABLE IF EXISTS `sys_element`;
CREATE TABLE `sys_element` (
                               `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                               `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '上级元素 ID, 顶级元素 ID 为 0',
                               `prev_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '当前节点的前驱节点 ID, 如果当前节点的前驱节点 ID 为 0, 则当前节点为头节点',
                               `resource_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '资源 ID',
                               `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '元素名称',
                               `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '路径',
                               `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组件',
                               `is_visible` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可见：1：可见；0：不可见',
                               `type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '元素类型：1：menu；5：page；10：button；\n',
                               `icon` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '图标',
                               `order_num` int unsigned NOT NULL DEFAULT '1' COMMENT '元素序号',
                               `props` json NOT NULL COMMENT '属性',
                               `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
                               `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                               `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                               `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                               `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                               `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `uk_platform_component_path_deleted` (`path`,`component`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT=' 页面元素表';

-- ----------------------------
-- Records of sys_element
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_identity
-- ----------------------------
DROP TABLE IF EXISTS `sys_identity`;
CREATE TABLE `sys_identity` (
                                `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                `organ_id` bigint unsigned NOT NULL COMMENT '机构 ID',
                                `identity_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '身份标识码',
                                `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
                                `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
                                `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
                                `phone_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号',
                                `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
                                `email_verified` tinyint NOT NULL DEFAULT '0' COMMENT '邮箱是否认证: 1 已认证, 0 未认证',
                                `phone_number_verified` tinyint NOT NULL DEFAULT '0' COMMENT '手机号是否认证: 1 已认证, 0 未认证',
                                `is_enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用: 0 未启用, 1 已启用',
                                `is_expired` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否过期: 0 未过期, 1 已过期',
                                `is_locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定: 0 未锁定, 1 已锁定',
                                `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                                `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `uk_identity_code_deleted` (`identity_code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='身份表';

-- ----------------------------
-- Records of sys_identity
-- ----------------------------
BEGIN;
INSERT INTO `sys_identity` (`id`, `organ_id`, `identity_code`, `username`, `password`, `nickname`, `phone_number`, `email`, `email_verified`, `phone_number_verified`, `is_enabled`, `is_expired`, `is_locked`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1, 1, 'admin', 'admin', '$2a$10$zA4JsKP5dC3lDpGKWZLeyOZmkDjCQyJLJZf0QgZnCz0nCFVez5tj6', '', '13100000001', '', 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, '2021-11-29 07:43:51', '2023-03-24 15:07:14');
INSERT INTO `sys_identity` (`id`, `organ_id`, `identity_code`, `username`, `password`, `nickname`, `phone_number`, `email`, `email_verified`, `phone_number_verified`, `is_enabled`, `is_expired`, `is_locked`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1530353558849982464, 1463692360646004739, 'PHL0YwLkdl9uj4a0e4kHUVhxqCZ1XnEM', 'PHL0YwLkdl9uj4a0e4kHUVhxqCZ1XnEM', '$2a$10$zA4JsKP5dC3lDpGKWZLeyOZmkDjCQyJLJZf0QgZnCz0nCFVez5tj6', '', '', '', 0, 0, 1, 0, 0, 0, 1463692360646004742, 1463692360646004742, 1463692360646004736, 1463692360646004736, '2022-05-28 09:01:35', '2023-03-24 14:51:52');
COMMIT;

-- ----------------------------
-- Table structure for sys_object_storage_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_object_storage_record`;
CREATE TABLE `sys_object_storage_record` (
                                             `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                             `account_id` bigint unsigned NOT NULL COMMENT '账户 ID',
                                             `profile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
                                             `object_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对象名',
                                             `object_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件访问地址',
                                             `base_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '基础存储路径',
                                             `path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '存储路径',
                                             `original_filename` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始文件名',
                                             `save_filename` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储的文件名',
                                             `ext` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件扩展名',
                                             `content_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
                                             `size` bigint unsigned NOT NULL COMMENT '文件大小,单位字节',
                                             `file_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '文件状态:0:未存储:5:已存储',
                                             `object_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件所属对象id',
                                             `object_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件所属对象类型,例如用户头像,评价图片',
                                             `user_metadata` json NOT NULL COMMENT '用户元数据',
                                             `metadata` json NOT NULL COMMENT '元数据',
                                             `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                                             `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                             `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                             `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                             `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新账户 ID',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='对象存储记录表';

-- ----------------------------
-- Records of sys_object_storage_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_organ
-- ----------------------------
DROP TABLE IF EXISTS `sys_organ`;
CREATE TABLE `sys_organ` (
                             `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                             `code` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
                             `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
                             `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '上级 ID，顶级 ID 为 0',
                             `prev_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '当前节点的前驱节点 ID，如果当前节点的前驱节点 ID 为 0，则当前节点为头节点',
                             `order_num` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '排序',
                             `is_enabled` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否启用：1 启用，0 未启用',
                             `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                             `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                             `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                             `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                             `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新账户 ID',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='机构表';

-- ----------------------------
-- Records of sys_organ
-- ----------------------------
BEGIN;
INSERT INTO `sys_organ` (`id`, `code`, `name`, `parent_id`, `prev_id`, `order_num`, `is_enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1, 'adminOgran', '管理员默认机构', 0, 0, 0, 0, 0, 0, 0, 0, 0, '2023-03-24 14:53:36', '2023-03-24 14:53:58');
INSERT INTO `sys_organ` (`id`, `code`, `name`, `parent_id`, `prev_id`, `order_num`, `is_enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1463692360646004739, 'demoOrgan', '测试机构', 0, 0, 0, 1, 0, 0, 0, 0, 0, '2023-03-24 14:53:09', '2023-03-24 14:54:02');
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
                                `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源名称',
                                `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源标识',
                                `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '路径',
                                `type` tinyint NOT NULL COMMENT '资源类型：1：application/json；5：application/octet-stream；',
                                `require_auth` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否需要授权：1：需要；0：不需要',
                                `props` json NOT NULL COMMENT '属性',
                                `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
                                `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                                `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `uk_platform_sign_path_deleted` (`sign`,`path`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` (`id`, `name`, `sign`, `path`, `type`, `require_auth`, `props`, `remark`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1457648680776765440, '身份概要', 'identity:profile:*', '/open/identity/profile/{userId}', 1, 1, '{}', '', 0, 1457648678537007106, 1457648678537007106, 1457648678537007107, 1457648678537007107, '2021-11-08 09:58:21', '2021-11-08 09:58:21');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                            `organ_id` bigint unsigned NOT NULL COMMENT '机构 ID',
                            `role_label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色标签',
                            `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
                            `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                            `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                            `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                            `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                            `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `uk_organ_platform_name_deleted` (`organ_id`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `organ_id`, `role_label`, `remark`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1, 1, '超级管理员角色', '', 0, 0, 0, 0, 0, '2021-11-30 11:01:06', '2023-03-24 14:57:34');
INSERT INTO `sys_role` (`id`, `organ_id`, `role_label`, `remark`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`, `create_time`, `update_time`) VALUES (1404722679155658753, 1463692360646004739, '测试机构角色', '', 0, 0, 0, 0, 0, '2021-11-30 11:01:39', '2023-03-24 14:58:17');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_element_union
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_element_union`;
CREATE TABLE `sys_role_element_union` (
                                          `id` bigint unsigned NOT NULL COMMENT '主键 ID',
                                          `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
                                          `element_id` bigint unsigned NOT NULL COMMENT '元素 ID',
                                          `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除, 1 已删除',
                                          `create_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建身份 ID',
                                          `update_identity_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                          `create_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建账户 ID',
                                          `update_account_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新身份 ID',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色与页面元素关联表';

-- ----------------------------
-- Records of sys_role_element_union
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
