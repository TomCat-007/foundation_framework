package com.example.web.pojo;

import common.config.snowflake.SnowflakeIdGenId;
import common.config.typeHandler.JSONObjectTypeHandler;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
 * 资源表(Resource)实体类
 *
 * @author zhangguiyuan
 * @description 资源表(Resource)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_resource")
public class Resource implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源标识
     */
    private String sign;

    /**
     * 路径
     */
    private String path;

    /**
     * 资源类型：1：application/json；5：application/octet-stream；
     */
    private Byte type;

    /**
     * 是否需要授权：1：需要；0：不需要
     */
    @Column(name = "require_auth")
    private Boolean requireAuth;

    /**
     * 属性
     */
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private ObjectNode props;

    /**
     * 备注
     */
    private String remark;


    /**
     * 是否删除: 0 未删除, 1 已删除
     */
    @LogicDelete
    @Column(name = "is_deleted")
    private Boolean deleted;

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
     * 更新身份 ID
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
