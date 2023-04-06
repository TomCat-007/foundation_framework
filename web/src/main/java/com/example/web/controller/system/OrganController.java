package com.example.web.controller.system;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.web.api.request.OrganEditRequest;
import com.example.web.api.request.system.OrganAddRequest;
import com.example.web.api.request.system.OrganListRequest;
import com.example.web.api.response.system.OrganListResponse;
import com.example.web.api.response.system.TreeNodeResponse;
import com.example.web.convert.OrganConverter;
import com.example.web.enums.error.BusinessErrorCodeEnum;
import com.example.web.enums.error.SysManageErrorCodeEnum;
import com.example.web.pojo.Account;
import com.example.web.pojo.Organ;
import com.example.web.service.AccountService;
import com.example.web.service.OrganService;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.PageConvertUtil;
import common.util.ServiceExceptionUtil;
import common.util.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/system/organ")
@Api(value = "机构", tags = "机构", consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganController {

    /**
     * 基础字符，排除易混淆字符: o, O, i, I, l, L, p, P, q, Q
     */
    private static final String BASE_CHAR_WITHOUT = "abcdefghjkmnrstuvwxyz";

    private static final String BASE_NUMBER = "1234567890";

    private static final String BASE_STRING = BASE_NUMBER + BASE_CHAR_WITHOUT;

    private final OrganService organService;

    private final OrganConverter organConverter;

    private final AccountService accountService;

    @PostMapping("/add")
    @ApiOperation(value = "机构添加", notes = "机构添加", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "OrganAddResponse", properties = {
            @DynamicParameter(name = "organId", value = "机构 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> add(@Validated @RequestBody OrganAddRequest request) {
        if (request.getParentId() == 0 || !organService.existsWithPrimaryKey(request.getParentId())) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.PARENT_NOT_EXISTS);
        }
        if (organService.existsWithProperty(Organ::getName, request.getName())) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.NAME_IS_USING);
        }
        Organ organ = new Organ();
        organ.setCode(RandomUtil.randomString(BASE_STRING, 4));
        organ.setParentId(request.getParentId());
        organ.setName(request.getName());
//        organ.setContactMobile(request.getContactMobile());

        organService.insertSelective(organ);
        return Rest.vector("organId", organ.getId(), Long.class);
    }

    @PostMapping("/list")
    @ApiOperation(value = "机构列表", notes = "机构列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<OrganListResponse> list(@RequestBody OrganListRequest request) {
        Weekend<Organ> weekend = Weekend.of(Organ.class);
        WeekendCriteria<Organ, Object> organWeekend = weekend.weekendCriteria();

        if (request.getParentId() != null) {
            organWeekend.andEqualTo(Organ::getParentId, request.getParentId());
        }
        if (StrUtil.isNotBlank(request.getName())) {
            organWeekend.andLike(Organ::getName, "%" + request.getName() + "%");
        }
        if (request.getSource() == null) {
            PageHelper.startPage(request.currentPage(), request.currentSize());
        }
        List<Organ> organList = organService.selectByExample(weekend);
        OrganListResponse response = new OrganListResponse()
                .setOrganList(organConverter.convert(organList));
        response.setPage(PageConvertUtil.convert(organList));

        return Rest.success(response);
    }

    @GetMapping("/tree")
    @ApiOperation(value = "机构树", notes = "机构树", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<TreeNodeResponse> tree(@ApiParam(value = "机构类型: 1 管理机构, 2 医疗机构", name = "type")
                                       @RequestParam(required = false) Byte type) {
        List<Organ> organList = organService.selectAll();
        TreeNodeUtil.toTree(organList);

        return Rest.success(new TreeNodeResponse()
                .setTreeData(organConverter.convert2Tree(organList)));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "机构编辑", notes = "机构编辑", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> edit(@RequestBody OrganEditRequest request) {
        Organ organ = organService.selectByPrimaryKey(request.getId());
        if (organ == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, request.getId());
        }
        Organ update = new Organ();
        update.setId(organ.getId());
        if (StrUtil.isNotBlank(request.getName())) {
            update.setName(request.getName());
        }
//        if (StrUtil.isNotEmpty(request.getContactMobile())) {
//            update.setContactMobile(request.getContactMobile());
//        }

//        if (update.getName() != null || update.getContactMobile() != null) {
        organService.updateByPrimaryKeySelective(update);
//        }

        return Rest.success();
    }

    @PostMapping("/{organId}/disable")
    @ApiOperation(value = "机构禁用/启用", notes = "机构禁用/启用", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> disable(@PathVariable Long organId) {
        Organ organ = organService.selectByPrimaryKey(organId);
        if (organ == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, organId);
        }
        Organ update = new Organ();
        update.setId(organ.getId());
        update.setEnabled(!organ.getEnabled());

        organService.updateByPrimaryKeySelective(update);
        return Rest.success();
    }

    @PostMapping("/{organId}/del")
    @ApiOperation(value = "机构删除", notes = "机构删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> del(@PathVariable Long organId) {
        Organ organ = organService.selectByPrimaryKey(organId);
        if (organ == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, organId);
        }
        // 判断是否有下级、是否使用
        if (organService.existsWithProperty(Organ::getParentId, organId)) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ORGAN_HAS_CHILDREN);
        }
        if (accountService.existsWithProperty(Account::getOrganId, organId)) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ORGAN_HAS_ACCOUNT);
        }

        organService.deleteByPrimaryKey(organId);
        return Rest.success();
    }

}
