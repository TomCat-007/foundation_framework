package common.config.api.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public interface TreeNode<T> extends LinkedNode<T> {

    /**
     * 获取当前节点 ID
     *
     * @return
     */
    @Override
    Long getId();

    /**
     * 获取当前节点名称
     *
     * @return
     */
    @Override
    String getName();

    /**
     * 获取父级节点 ID
     *
     * @return
     */
    Long getParentId();

    /**
     * 获取节点排序值
     *
     * @return
     */
    Integer getOrderNum();

    /**
     * 获取子节点集合
     *
     * @return
     */
    List<T> getChildren();


    /**
     * 设置子节点列表
     */
    void setChildren(List<T> children);

    /**
     * 是否是顶级树节点
     *
     * @return
     */
    @JsonIgnore
    default boolean isTopNode() {
        return getParentId() == 0;
    }
}
