package com.example.web.pojo;

import common.config.snowflake.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 身份表(Identity)实体类
 *
 * @author zhanghuiyuan
 * @description 身份表(Identity)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_identity")
public class Identity implements Serializable {

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
     * 身份标识码
     */
    @Column(name = "identity_code")
    private String identityCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 手机号是否认证: 1 已认证, 0 未认证
     */
    @Column(name = "phone_number_verified")
    private Boolean phoneNumberVerified;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否认证: 1 已认证, 0 未认证
     */
    @Column(name = "email_verified")
    private Boolean emailVerified;

    /**
     * 是否启用: 0 未启用, 1 已启用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

    /**
     * 是否过期: 0 未过期, 1 已过期
     */
    @Column(name = "is_expired")
    private Boolean expired;

    /**
     * 是否锁定: 0 未锁定, 1 已锁定
     */
    @Column(name = "is_locked")
    private Boolean locked;

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
