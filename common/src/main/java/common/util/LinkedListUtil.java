package common.util;

import cn.hutool.core.collection.CollUtil;
import common.config.api.base.LinkedNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public class LinkedListUtil {

    /**
     * 链表排序
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends LinkedNode<T>> List<LinkedNode<T>> sort(List<T> nodeList) {
        LinkedNode<T> header = nodeList.stream().filter(treeNode -> treeNode.getPrevId().equals(0L)).findFirst().get();

        List<LinkedNode<T>> sortedNodeList = new ArrayList<>(nodeList.size());
        LinkedNode<T> prev;
        prev = header;
        sortedNodeList.add(header);
        Iterator<T> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            for (LinkedNode<T> node : nodeList) {
                if (node.getPrevId().equals(prev.getId())) {
                    prev = node;
                    sortedNodeList.add(node);
                    break;
                }
            }
        }
        return sortedNodeList;
    }
//
//    /**
//     * 计算需要更新的节点并返回
//     * @param nodeMove
//     * @param <T>
//     * @return
//     */
//    public static <T extends LinkedNode<T>> List<LinkedNode<T>> calculateNeedUpdateNodes(LinkedNodeMove<T> nodeMove) {
//        List<LinkedNode<T>> needUpdateNodes = new ArrayList<>(4);
//        // 当前节点
//        LinkedNode<T> currentLinkedNode = nodeMove.getCurrentLinkedNode();
//        // 当前节点 ID
//        Long currentLinkedNodeId = currentLinkedNode.getId();
//
//        // 当前节点的前驱节点
//        LinkedNode<T> prevLinkedNode = nodeMove.getPrevLinkedNode();
//        // 当前节点的后继节点
//        LinkedNode<T> nextLinkedNode = nodeMove.getNextLinkedNode();
//        // 目标位置的前驱节点
//        LinkedNode<T> targetPrevLinkedNode = nodeMove.getTargetPrevLinkedNode();
//        // 目标位置的后继节点
//        LinkedNode<T> targetNextLinkedNode = nodeMove.getTargetNextLinkedNode();
//        // 当前链表范围
//        List<T> nodeList = nodeMove.getNodeList();
//
//        // 目标前驱节点为 null，说明是将某个节点移动到了头节点位置，中间位置的移动会导致目标节点的前驱节点和当前节点的前驱节点不一致，这两种情况都说明节点位置有变动
//        if (ObjectUtil.isNull(targetPrevLinkedNode) || !currentLinkedNodeId.equals(targetPrevLinkedNode.getId())) {
//            // 当前节点的前驱和目标位置的前驱不一致，说明位置有变动
//            // 确定当前链表范围
//            if (ObjectUtil.isNull(targetPrevLinkedNode) || ObjectUtil.isNull(targetNextLinkedNode)) {
//                // 如果目标位置的前驱或者后继为 null，说明是将某个节点移动到了头部或者尾部
//                if (ObjectUtil.isNull(targetPrevLinkedNode)) {
//                    // 如果目标位置的前驱为 null，说明是将某个节点移动到了头部
//                    // 寻找头节点
//                    T headerOrgan = LinkedListUtil.findHeader(nodeList);
//                    // 节点移动
//                    if (ObjectUtil.isNull(nextLinkedNode)) {
//                        // 如果当前节点的后继节点为 null，说明是直接将尾节点移动到了头部
//                        currentLinkedNode.setPrevId(0L);
//                        headerOrgan.setPrevId(currentLinkedNodeId);
//                        // 节点更新
//                        needUpdateNodes.add(currentLinkedNode);
//                        needUpdateNodes.add(headerOrgan);
////                        organService.updateByPrimaryKeySelective(currentLinkedNode);
////                        organService.updateByPrimaryKeySelective(headerOrgan);
//                    } else {
//                        // 说明是将中间节点移动到了头部
//                        nextLinkedNode.setPrevId(prevLinkedNode.getId());
//                        currentLinkedNode.setPrevId(0L);
//                        headerOrgan.setPrevId(currentLinkedNodeId);
//                        // 节点更新
//                        needUpdateNodes.add(nextLinkedNode);
//                        needUpdateNodes.add(currentLinkedNode);
//                        needUpdateNodes.add(headerOrgan);
////                        organService.updateByPrimaryKeySelective(nextLinkedNode);
////                        organService.updateByPrimaryKeySelective(currentLinkedNode);
////                        organService.updateByPrimaryKeySelective(headerOrgan);
//                    }
//                }
//                if (ObjectUtil.isNull(targetNextLinkedNode)) {
//                    // 如果目标位置的后继为 null，说明是将某个节点移动到了尾部
//                    // 寻找尾节点
//                    T tailOrgan = LinkedListUtil.findTail(nodeList);
//                    // 节点移动
//                    if (ObjectUtil.isNull(prevLinkedNode)) {
//                        // 如果当前节点的前驱节点为 null，说明是直接将头节点移动到了尾部
//                        nextLinkedNode.setPrevId(0L);
//                        currentLinkedNode.setPrevId(tailOrgan.getId());
//                        // 节点更新
//                        needUpdateNodes.add(nextLinkedNode);
//                        needUpdateNodes.add(currentLinkedNode);
////                        organService.updateByPrimaryKeySelective(nextLinkedNode);
////                        organService.updateByPrimaryKeySelective(currentLinkedNode);
//                    } else {
//                        // 说明是将中间节点移动到了尾部
//                        nextLinkedNode.setPrevId(prevLinkedNode.getId());
//                        currentLinkedNode.setPrevId(tailOrgan.getId());
//                        // 节点更新
//                        needUpdateNodes.add(nextLinkedNode);
//                        needUpdateNodes.add(currentLinkedNode);
////                        organService.updateByPrimaryKeySelective(nextLinkedNode);
////                        organService.updateByPrimaryKeySelective(currentLinkedNode);
//                    }
//                }
//            } else {
//                // 中间位置移动
//                nextLinkedNode.setPrevId(prevLinkedNode.getId());
//                currentLinkedNode.setPrevId(targetPrevLinkedNode.getId());
//                targetNextLinkedNode.setPrevId(currentLinkedNodeId);
//                // 节点更新
//                needUpdateNodes.add(nextLinkedNode);
//                needUpdateNodes.add(currentLinkedNode);
//                needUpdateNodes.add(targetNextLinkedNode);
////                organService.updateByPrimaryKeySelective(nextLinkedNode);
////                organService.updateByPrimaryKeySelective(currentLinkedNode);
////                organService.updateByPrimaryKeySelective(targetNextLinkedNode);
//            }
//        }
//
//        return needUpdateNodes;
//    }

    /**
     * 查找头节点
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends LinkedNode<T>> T findTail(List<T> nodeList) {
        T tailLinkedNode = null;
        if (CollUtil.isNotEmpty(nodeList) && nodeList.size() == 1) {
            tailLinkedNode = nodeList.get(0);
        } else {
            for (T currentLinkedNode : nodeList) {
                if (!currentLinkedNode.getPrevId().equals(0L)) {
                    // 非头节点
                    // 是否存在当前节点的 ID 是某个节点的前驱
                    boolean exist = false;
                    for (T organ : nodeList) {
                        if (organ.getPrevId().equals(currentLinkedNode.getId())) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        tailLinkedNode = currentLinkedNode;
                        break;
                    }
                }
            }
        }
        return tailLinkedNode;
    }

    /**
     * 查找尾结点
     *
     * @param nodeList
     * @param <T>
     * @return
     */
    public static <T extends LinkedNode<T>> T findHeader(List<T> nodeList) {
        return nodeList.stream().filter(linkedNode -> linkedNode.getPrevId().equals(0L)).findFirst().get();
    }

}
