package com.example.web.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.config.snowflake.SnowflakeIdGenId;
import common.config.typeHandler.JSONObjectTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 对象存储记录(ObjectStorageRecord)实体类
 *
 * @author zhanghuiyuan
 * @description 对象存储记录(ObjectStorageRecord)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_object_storage_record")
public class ObjectStorageRecord implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 存储配置
     */
    @Column(name = "profile")
    private String profile;

    /**
     * 对象名
     */
    @Column(name = "object_name")
    private String objectName;

    /**
     * 文件访问地址
     */
    @Column(name = "object_url")
    private String objectUrl;

    /**
     * 基础存储路径
     */
    @Column(name = "base_path")
    private String basePath;

    /**
     * 存储路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 原始文件名
     */
    @Column(name = "original_filename")
    private String originalFilename;

    /**
     * 存储的文件名
     */
    @Column(name = "save_filename")
    private String saveFilename;

    /**
     * 文件扩展名
     */
    @Column(name = "ext")
    private String ext;

    /**
     * 内容类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 文件大小，单位字节
     */
    @Column(name = "size")
    private Long size;

    /**
     * 文件状态：0：未存储；5：已存储
     */
    @Column(name = "file_status")
    private Integer fileStatus;

    /**
     * 文件所属对象id
     */
    @Column(name = "object_id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @Column(name = "object_type")
    private String objectType;

    /**
     * 用户元数据
     */
    @Column(name = "user_metadata")
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private ObjectNode userMetadata;

    /**
     * 元数据
     */
    @Column(name = "metadata")
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private ObjectNode metadata;

    /**
     * 是否删除：1：已删除；0：未删除
     */
    @LogicDelete
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建身份 ID
     */
    @Column(name = "create_identity_id")
    private Long createIdentityId;

    /**
     * 更新身份 ID
     */
    @Column(name = "update_identity_id")
    private Long updateIdentityId;

    /**
     * 创建账户 ID
     */
    @Column(name = "create_account_id")
    private Long createAccountId;

    /**
     * 更新账户 ID
     */
    @Column(name = "update_account_id")
    private Long updateAccountId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
