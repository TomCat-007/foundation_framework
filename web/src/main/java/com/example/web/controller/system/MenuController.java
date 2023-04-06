package com.example.web.controller.system;

import cn.hutool.core.util.ObjectUtil;
import com.example.web.api.request.system.MenuAddRequest;
import com.example.web.api.request.system.MenuEditRequest;
import com.example.web.api.response.system.TreeNodeResponse;
import com.example.web.enums.error.BusinessErrorCodeEnum;
import com.example.web.enums.error.SysManageErrorCodeEnum;
import com.example.web.pojo.Element;
import com.example.web.pojo.RoleElementUnion;
import com.example.web.service.ElementService;
import com.example.web.service.ResourceService;
import com.example.web.service.RoleElementUnionService;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/system/menu")
@Api(value = "菜单", tags = "菜单", consumes = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final ElementService elementService;

    private final ResourceService resourceService;

    private final RoleElementUnionService roleElementUnionService;

    @PostMapping("/add")
    @ApiOperation(value = "菜单添加", notes = "菜单添加", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "MenuAddResponse", properties = {
            @DynamicParameter(name = "elementId", value = "元素 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> add(@Validated @RequestBody MenuAddRequest request) {
        Element element = new Element();
        BeanUtils.copyProperties(request, element);
        elementService.insertSelective(element);
        return Rest.vector("elementId", element.getId(), Long.class);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "菜单编辑", notes = "菜单编辑", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "MenuEditResponse", properties = {
            @DynamicParameter(name = "elementId", value = "元素 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> edit(@Validated @RequestBody MenuEditRequest request) {
        Long elementId = request.getId();
        Element originElement = elementService.selectByPrimaryKey(elementId);
        if (ObjectUtil.isNull(originElement)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, elementId);
        }
        BeanUtils.copyProperties(request, originElement);
        elementService.updateByPrimaryKeySelective(originElement);

        return Rest.vector("elementId", elementId, Long.class);
    }

    @GetMapping("/delete/{elementId}")
    @ApiOperation(value = "元素删除", notes = "元素删除", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "elementId", value = "元素 ID", example = "1283337745732866048")
    })
    @DynamicResponseParameters(name = "ElementDeleteResponse", properties = {
            @DynamicParameter(name = "deleted", value = "删除标志：true：删除成功；false：删除失败", example = "true", dataTypeClass = Boolean.class)
    })
    public Rest<BaseResponse> delete(@PathVariable Long elementId) {
        boolean existChildren = elementService.existsWithProperty(Element::getParentId, elementId);
        if (existChildren) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ELEMENT_HAS_CHILDREN, elementId);
        }
        boolean deleted = elementService.deleteByPrimaryKey(elementId);
        return Rest.vector("deleted", deleted, Boolean.class);
    }

    @GetMapping("/bind/{elementId}/{resourceId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "elementId", value = "元素 ID", example = "1283337745732866048"),
            @ApiImplicitParam(name = "resourceId", value = "资源 ID", example = "1271007571989368833")
    })
    @ApiOperation(value = "资源绑定", notes = "资源绑定", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "ResourceBindResponse", properties = {
            @DynamicParameter(name = "elementId", value = "元素 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> bind(@PathVariable Long elementId, @PathVariable Long resourceId) {
        Element element = elementService.selectByPrimaryKey(elementId);
        if (ObjectUtil.isNull(element)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, elementId);
        }

        if (!resourceService.existsWithPrimaryKey(resourceId)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, resourceId);
        }
        element.setResourceId(resourceId);
        elementService.updateByPrimaryKeySelective(element);
        return Rest.vector("elementId", elementId, Long.class);
    }

    @GetMapping("/unbind/{elementId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "elementId", value = "元素 ID", example = "1283337745732866048"),
    })
    @ApiOperation(value = "资源解除绑定", notes = "资源解除绑定", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "ResourceUnbindResponse", properties = {
            @DynamicParameter(name = "elementId", value = "元素 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> unbind(@PathVariable Long elementId) {
        Element element = elementService.selectByPrimaryKey(elementId);
        if (ObjectUtil.isNull(element)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, elementId);
        }
        element.setResourceId(0L);
        elementService.updateByPrimaryKeySelective(element);
        return Rest.vector("elementId", elementId, Long.class);
    }

    @GetMapping("/tree/all")
    @ApiOperation(value = "完整菜单树", notes = "完整菜单树", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<TreeNodeResponse> allTreeNode() {
        return Rest.success(new TreeNodeResponse().setTreeData(elementService.allTreeNode()));
    }

    @GetMapping("/element/id/list/product/{roleId}")
    @ApiOperation(value = "角色拥有元素 ID 列表", notes = "角色包含的元素 ID 列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "roleId", value = "角色 ID", example = "1293385366908309505")
    @DynamicResponseParameters(name = "RoleElementIdsResponse", properties = {
            @DynamicParameter(name = "elementIds", value = "元素 ID 集合", dataTypeClass = List.class)
    })
    public Rest<BaseResponse> roleElementIds(@PathVariable("roleId") @NotNull Long roleId) {
        List<RoleElementUnion> productElementUnions = roleElementUnionService.selectByProperty(RoleElementUnion::getRoleId, roleId);
        List<Long> elementIds = productElementUnions.stream().mapToLong(RoleElementUnion::getElementId).boxed().collect(Collectors.toList());
        return Rest.vector("elementIds", elementIds, List.class);
    }

}
