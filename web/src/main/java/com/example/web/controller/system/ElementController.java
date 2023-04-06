package com.example.web.controller.system;

import com.example.web.api.response.MenuTreeResponse;
import com.example.web.api.response.system.TreeNodeResponse;
import com.example.web.api.response.system.vo.ElementVo;
import com.example.web.convert.ElementConverter;
import com.example.web.pojo.Element;
import com.example.web.service.ElementService;
import common.config.api.base.Rest;
import common.util.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/element")
@Api(value = "元素", tags = {"元素管理"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ElementController {

    private final ElementService elementService;

    private final ElementConverter elementConverter;

    @GetMapping("/tree/all")
    @ApiOperation(value = "完整元素树节点", notes = "完整元素树节点", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<TreeNodeResponse> allTreeNode() {
        List<Element> allElements = elementService.selectAll();

        TreeNodeUtil.toTree(allElements);
        TreeNodeResponse response = new TreeNodeResponse()
                .setTreeData(allElements
                        .stream()
                        .map(elementConverter::convert2TreeNode)
                        .collect(Collectors.toList()));
        return Rest.success(response);
    }

    @GetMapping("/tree/table")
    @ApiOperation(value = "菜单树表格", notes = "菜单树表格形式", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<MenuTreeResponse> treeTable() {
        List<Element> elements = elementService.selectAll();
        TreeNodeUtil.toTree(elements);
        List<ElementVo> parentDTOs = elements
                .stream()
                .map(elementConverter::convert2ElementVo)
                .collect(Collectors.toList());
        MenuTreeResponse response = new MenuTreeResponse()
                .setElements(parentDTOs);
        return Rest.success(response);
    }

}
