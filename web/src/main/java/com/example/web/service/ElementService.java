package com.example.web.service;

import com.example.web.pojo.Element;
import common.config.api.base.TreeNodeVO;
import common.config.mybatis.BaseService;

import java.util.List;

/**
 * 页面元素表(Element)表服务接口
 *
 * @author zhanghuiyuan
 * @description 页面元素表(Element)表服务接口
 * @date 2023/3/3 13:33
 */
public interface ElementService extends BaseService<Element, Long> {

    List<TreeNodeVO> allTreeNode();

}
