package common.util;

import cn.hutool.core.collection.CollUtil;
import common.config.api.base.TreeNode;
import common.config.api.base.TreeNodeVO;
import common.convert.TreeNodeConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 递归树结构工具类
 *
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
public class TreeNodeUtil {

    /**
     * 迭代树结构
     *
     * @param treeNodeList list
     * @param <T>          TreeNode
     */
    public static <T extends TreeNode<T>> void toTree(List<T> treeNodeList) {
        toTree(treeNodeList, false);
    }

    /**
     * 迭代树结构
     *
     * @param treeNodeList list
     * @param sortByChain  是否排序
     * @param <T>          TreeNode
     */
    public static <T extends TreeNode<T>> void toTree(List<T> treeNodeList, boolean sortByChain) {
        List<T> parentNode = treeNodeList.stream().filter(TreeNode::isTopNode).collect(Collectors.toList());
        parentNode.forEach(parent -> {
            toTree(parent, treeNodeList);
        });
        treeNodeList.clear();
        treeNodeList.addAll(parentNode);
        if (sortByChain) {
            LinkedListUtil.sort(treeNodeList);
        } else {
            sort(treeNodeList);
        }
    }

    private static <T extends TreeNode<T>> void toTree(T parent, List<T> treeNodeList) {
        List<T> children = treeNodeList
                .stream()
                .filter(node -> parent.getId().equals(node.getParentId()))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(children)) {
            parent.setChildren(children);
        }
        children.forEach(c -> toTree(c, treeNodeList));
    }

    public static <T extends TreeNode<T>> List<TreeNodeVO> toTreeNodes(List<T> treeNodeList) {
        TreeNodeConverter treeNodeConverter = BeanFactoryUtil.getBean(TreeNodeConverter.class);
        LinkedListUtil.sort(treeNodeList);
        return treeNodeList.stream()
                .map(treeNodeConverter::convert2TreeNodeVO)
                .collect(Collectors.toList());
    }

    private static <T extends TreeNode<T>> void sort(List<T> treeNodeList) {
        Comparator<TreeNode<T>> comparator = Comparator.comparingInt(TreeNode::getOrderNum);
        if (CollUtil.isNotEmpty(treeNodeList)) {
            treeNodeList.forEach(t -> {
                if (CollUtil.isNotEmpty(t.getChildren())) {
                    sort(t.getChildren());
                }
            });
            treeNodeList.sort(comparator);
        }
    }

}
