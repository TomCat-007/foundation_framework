package common.convert;


import common.config.api.base.TreeNode;
import common.config.api.base.TreeNodeVO;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
public interface TreeNodeConverter {
    TreeNodeVO convert2TreeNodeVO(TreeNode treeNode);
}
