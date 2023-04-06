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
 * 字典表(Dictionary)实体类
 *
 * @author zhanghuiyuan
 * @description 字典表(Dictionary)实体类
 * @date 2023/3/3 13:33
 */

@Data
@Accessors(chain = true)
@Table(name = "sys_dictionary")
public class Dictionary implements TreeNode<Dictionary>, Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

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
     * 编码
     */
    private String code;

    /**
     * 标签, 通常用于渲染表头
     */
    private String label;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型: 1 文本, 2 数值, 5 区划
     */
    private Byte type;

    /**
     * 子类型: 0: 无, 1 文本, 2 数值, 5 区划
     */
    @Column(name = "item_type")
    private Byte itemType;

    /**
     * 元素序号
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 备注描述
     */
    private String description;

    /**
     * 是否启用:1:已启用:0:未启用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

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
    private List<Dictionary> children;

    @Override
    public void setChildren(List<Dictionary> children) {
        this.children = children;
    }
}

