package com.example.web.controller.system;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.web.api.request.ElementAssignRequest;
import com.example.web.api.request.RoleEditRequest;
import com.example.web.api.request.RoleSearchRequest;
import com.example.web.api.response.RoleDetailResponse;
import com.example.web.api.response.system.RoleListResponse;
import com.example.web.convert.RoleConverter;
import com.example.web.enums.error.BusinessErrorCodeEnum;
import com.example.web.enums.error.SysManageErrorCodeEnum;
import com.example.web.pojo.AccountRoleUnion;
import com.example.web.pojo.Role;
import com.example.web.pojo.RoleElementUnion;
import com.example.web.service.AccountRoleUnionService;
import com.example.web.service.ElementService;
import com.example.web.service.RoleElementUnionService;
import com.example.web.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.PageConvertUtil;
import common.util.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
@Api(value = "角色", tags = {"角色管理"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleService roleService;

    private final ElementService elementService;

    private final RoleElementUnionService roleElementUnionService;

    private final RoleConverter roleConverter;

    private final AccountRoleUnionService accountRoleUnionService;

    @GetMapping("/detail/{roleId}")
    @ApiOperation(value = "角色详情", notes = "角色详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色 ID", example = "1283337745732866048")
    })
    public Rest<RoleDetailResponse> detail(@PathVariable Long roleId) {
        if (roleId == null || roleId == 0) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, roleId);
        }
        Role role = roleService.selectByPrimaryKey(roleId);
        if (ObjectUtil.isNull(role)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, roleId);
        }

        List<Long> checkedKeys = roleElementUnionService.selectByProperty(RoleElementUnion::getRoleId, roleId)
                .stream()
                .map(RoleElementUnion::getElementId)
                .collect(Collectors.toList());

        RoleDetailResponse response = new RoleDetailResponse()
                .setId(roleId)
                .setName(role.getRoleLabel())
                .setTreeData(elementService.allTreeNode())
                .setCheckedKeys(checkedKeys);

        return Rest.success(response);
    }

    @PostMapping("/search")
    @ApiOperation(value = "角色查询", notes = "角色查询", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<RoleListResponse> search(@Validated @RequestBody RoleSearchRequest request) {
        Weekend<Role> weekend = Weekend.of(Role.class);
        WeekendCriteria<Role, Object> weekendCriteria = weekend.weekendCriteria();
        if (StrUtil.isNotBlank(request.getName())) {
            weekendCriteria.andLike(Role::getRoleLabel, "%" + request.getName() + "%");
        }
        PageHelper.startPage(request.currentPage(), request.currentSize());

        List<Role> roles = roleService.selectByExample(weekend);

        RoleListResponse response = new RoleListResponse()
                .setRoles(roleConverter.convert(roles));
        response.setPage(PageConvertUtil.convert(roles));

        return Rest.success(response);
    }

    @GetMapping("/delete/{roleId}")
    @ApiOperation(value = "角色删除", notes = "角色删除", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色 ID", example = "1283337745732866048")
    })
    @DynamicResponseParameters(name = "RoleDeleteResponse", properties = {
            @DynamicParameter(name = "deleted", value = "删除标志：true：删除成功；false：删除失败", example = "true", dataTypeClass = Boolean.class)
    })
    public Rest<BaseResponse> delete(@PathVariable Long roleId) {
        if (accountRoleUnionService.existsWithProperty(AccountRoleUnion::getRoleId, roleId)) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ROLE_IS_USING);
        }
        boolean deleted = roleService.deleteByPrimaryKey(roleId);
        return Rest.vector("deleted", deleted, Boolean.class);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "角色添加/编辑", notes = "角色添加/编辑", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "RoleEditResponse", properties = {
            @DynamicParameter(name = "roleId", value = "角色 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> edit(@Validated @RequestBody RoleEditRequest request) {
        Role role;

        if (ObjectUtil.isEmpty(request.getRoleId())) {
            // 新增请求
            if (roleService.existsWithProperty(Role::getRoleLabel, request.getName())) {
                throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_REPEAT);
            }
            role = new Role()
                    .setRoleLabel(request.getName())
                    .setRemark(request.getRemark());
            roleService.insertSelective(role);
        } else {
            // 编辑请求
            if (!roleService.existsWithPrimaryKey(request.getRoleId())) {
                throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, request.getRoleId());
            }
            role = new Role()
                    .setId(request.getRoleId())
                    .setRoleLabel(request.getName())
                    .setRemark(request.getRemark());
            roleService.updateByPrimaryKeySelective(role);
        }
        roleElementUnionService.updateRoleElement(role.getId(), request.getElementIds());
        return Rest.vector("roleId", role.getId(), Long.class);
    }

    @PostMapping("/element/assign")
    @ApiOperation(value = "权限分配", notes = "权限分配", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "RoleAssignResponse", properties = {
            @DynamicParameter(name = "roleId", value = "角色 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> roleAssign(@Validated @RequestBody ElementAssignRequest request) {
        Long roleId = request.getRoleId();
        if (!roleService.existsWithPrimaryKey(roleId)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, roleId);
        }
        roleService.changeElements(roleId, request.getElementIds());
        return Rest.vector("roleId", roleId, Long.class);
    }

    @PostMapping("/drop")
    @ApiOperation(value = "角色下拉", notes = "角色下拉", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<RoleListResponse> drop() {
        List<Role> roles = roleService.selectAll();

        RoleListResponse response = new RoleListResponse()
                .setRoles(roleConverter.convert(roles));
        return Rest.success(response);
    }

}
