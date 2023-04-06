package com.example.web.controller.system;

import cn.hutool.core.util.ObjectUtil;
import com.example.web.api.request.system.ResourceAddRequest;
import com.example.web.api.request.system.ResourceEditRequest;
import com.example.web.convert.ResourceConverter;
import com.example.web.enums.error.BusinessErrorCodeEnum;
import com.example.web.pojo.Resource;
import com.example.web.service.ResourceService;
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

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/resource")
@Api(value = "资源", tags = {"资源管理"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    private final ResourceService resourceService;

    private final ResourceConverter resourceConverter;

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "资源详情", notes = "资源详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源 ID", example = "1283337745732866048")
    })
    public Rest<BaseResponse> detail(@PathVariable Long id) {
        if (id == null || id == 0) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        Resource resource = resourceService.selectByPrimaryKey(id);
        if (resource == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        return Rest.success(resourceConverter.convert(resource));
    }

    @PostMapping("/add")
    @ApiOperation(value = "资源添加", notes = "资源添加", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "ResourceAddResponse", properties = {
            @DynamicParameter(name = "resourceId", value = "资源 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> add(@Validated @RequestBody ResourceAddRequest request) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(request, resource);
        resourceService.insertSelective(resource);
        return Rest.vector("resourceId", resource.getId(), Long.class);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "资源编辑", notes = "资源编辑", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "ResourceEditResponse", properties = {
            @DynamicParameter(name = "resourceId", value = "资源 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> edit(@Validated @RequestBody ResourceEditRequest request) {
        Long resourceId = request.getId();
        Resource resource = resourceService.selectByPrimaryKey(resourceId);

        if (ObjectUtil.isNull(resource)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, resourceId);
        }

        BeanUtils.copyProperties(request, resource);
        resourceService.updateByPrimaryKeySelective(resource);
        return Rest.vector("resourceId", resource.getId(), Long.class);
    }

}
