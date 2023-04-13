package common.convert.impl;


import common.config.api.base.TreeNode;
import common.config.api.base.TreeNodeVO;
import common.convert.TreeNodeConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
 @Component
public class TreeNodeConverterImpl implements TreeNodeConverter {
    @Override
    public TreeNodeVO convert2TreeNodeVO(TreeNode treeNode) {
        if (treeNode == null) {
            return null;
        }

        TreeNodeVO treeNodeVO = new TreeNodeVO();

        treeNodeVO.setTitle(treeNode.getName());
        treeNodeVO.setKey(treeNode.getId());
        treeNodeVO.setPrevId(treeNode.getPrevId());
        List<TreeNode> list = treeNode.getChildren();
        if (list != null) {
            treeNodeVO.setChildren(convertChildren(list));
        }
        return treeNodeVO;
    }

    private List<TreeNodeVO> convertChildren(List<TreeNode> children) {
        if (children == null) {
            return null;
        }
        List<TreeNodeVO> treeNodeVOS = new ArrayList<>(children.size());
        for (TreeNode treeNode : children) {
            treeNodeVOS.add(convert2TreeNodeVO(treeNode));
        }
        return treeNodeVOS;
    }
}
