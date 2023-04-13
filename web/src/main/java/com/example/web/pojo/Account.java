package com.example.web.pojo;

import common.config.snowflake.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户表(Account)实体类
 *
 * @author zhangguiyuan
 * @description 账户表(Account)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_account")
public class Account implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 机构 ID
     */
    @Column(name = "organ_id")
    private Long organId;

    /**
     * 身份 ID
     */
    @Column(name = "identity_id")
    private Long identityId;

    /**
     * 账号所属平台：0 超级管理员 1 用户 5 机构管理员
     */
    private Byte platform;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 账户是否启用: 1 已启用, 0 未启用(禁用)
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

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

    /**
     * 账户角色集合
     */
    @Transient
    private List<Role> roles;

}
