package common.config.api.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public interface LinkedNode<T> {

    /**
     * 获取当前节点 ID
     *
     * @return
     */
    Long getId();

    /**
     * 设置当前节点 ID
     */
    T setId(Long id);

    /**
     * 如果当前节点的前驱节点为 0，则当前节点为头节点
     *
     * @return
     */
    Long getPrevId();

    /**
     * 设置当前节点的前驱节点 ID
     *
     * @param prevId
     */
    T setPrevId(Long prevId);

    /**
     * 获取当前节点名称
     *
     * @return
     */
    String getName();

    /**
     * 是否是顶级树节点
     *
     * @return
     */
    @JsonIgnore
    default boolean isHeader() {
        return getPrevId() == 0;
    }
}
