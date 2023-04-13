package com.example.web.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.config.api.base.TreeNode;
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
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 页面元素表(Element)实体类
 *
 * @author zhangguiyuan
 * @description 页面元素表(Element)实体类
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_element")
public class Element implements TreeNode<Element>, Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 上级元素 ID, 顶级元素 ID 为 0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 当前节点的前驱节点 ID, 如果当前节点的前驱节点 ID 为 0, 则当前节点为头节点
     */
    @Column(name = "prev_id")
    private Long prevId;

    /**
     * 资源 ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 元素名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;


    /**
     * 是否可见：1：可见；0：不可见
     */
    @Column(name = "is_visible")
    private Boolean visible;

    /**
     * 元素类型：1：menu；5：page；10：button；
     */
    private Byte type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 元素序号
     */
    @Column(name = "order_num")
    private Integer orderNum;

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

    @Transient
    private Resource resource;

    @Transient
    private List<Element> children;

    @Override
    public void setChildren(List<Element> children) {
        this.children = children;
    }
}
