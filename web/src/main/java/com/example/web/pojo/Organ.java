package com.example.web.pojo;

import common.config.api.base.TreeNode;
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
 * 机构表(Organ)实体类
 *
 * @author zhangguiyuan
 * @description 机构表(Organ)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_organ")
public class Organ implements TreeNode<Organ>, Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级 ID,0 表示顶级
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 前驱节点 ID,0 表示头部节点
     */
    @Column(name = "prev_id")
    private Long prevId;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;


    /**
     * 租户是否启用：1：已启用；0：未启用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;


    /**
     * 是否删除：1：已删除；0：未删除
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

    @Transient
    private List<Organ> children;

    @Override
    public void setChildren(List<Organ> children) {
        this.children = children;
    }
}
