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
 * 角色表(Role)实体类
 *
 * @author zhanghuiyuan
 * @description 角色表(Role)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_role")
public class Role implements Serializable {

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
     * 角色标签
     */
    @Column(name = "role_label")
    private String roleLabel;

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

    @Transient
    private List<Resource> resources;

}
