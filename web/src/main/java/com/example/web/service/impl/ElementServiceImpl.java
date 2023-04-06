package com.example.web.service.impl;

import com.example.web.mapper.ElementMapper;
import com.example.web.pojo.Element;
import com.example.web.service.ElementService;
import common.config.api.base.TreeNodeVO;
import common.config.mybatis.BaseServiceImpl;
import common.convert.TreeNodeConverter;
import common.util.TreeNodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 页面元素表(Element)表服务实现类
 *
 * @author zhanghuiyuan
 * @description 页面元素表(Element)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class ElementServiceImpl extends BaseServiceImpl<Element, Long> implements ElementService {

    @Resource
    private ElementMapper elementMapper;

    @Resource
    private TreeNodeConverter treeNodeConverter;

    @Override
    public List<TreeNodeVO> allTreeNode() {
        List<Element> allElements = this.selectAll();

        TreeNodeUtil.toTree(allElements);
        return allElements
                .stream()
                .map(treeNodeConverter::convert2TreeNodeVO)
                .collect(Collectors.toList());
    }


}
