package com.example.web.controller.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.web.api.request.system.AccountAddRequest;
import com.example.web.api.request.system.AccountEditRequest;
import com.example.web.api.request.system.PasswordResetRequest;
import com.example.web.api.request.system.AccountListRequest;
import com.example.web.api.response.system.AccountListResponse;
import com.example.web.api.response.system.vo.AccountRoleVo;
import com.example.web.api.response.system.vo.AccountVo;
import com.example.web.convert.AccountConverter;
import com.example.web.enums.error.BusinessErrorCodeEnum;
import com.example.web.enums.error.SysManageErrorCodeEnum;
import com.example.web.pojo.Account;
import com.example.web.pojo.Identity;
import com.example.web.pojo.Organ;
import com.example.web.pojo.Role;
import com.example.web.service.AccountService;
import com.example.web.service.IdentityService;
import com.example.web.service.OrganService;
import com.example.web.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.PageConvertUtil;
import common.util.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@RequestMapping(value = "/system/account")
@Api(value = "用户管理", tags = "用户管理", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    private final OrganService organService;

    private final RoleService roleService;

    private final IdentityService identityService;

    private final PasswordEncoder passwordEncoder;

    private final AccountConverter accountConverter;

    @PostMapping("/add")
    @ApiOperation(value = "用户添加", notes = "用户添加", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "AccountAddResponse", properties = {
            @DynamicParameter(name = "accountId", value = " 用户 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> add(@Validated @RequestBody AccountAddRequest request) {
        Organ organ = organService.selectByPrimaryKey(request.getOrganId());
        if (organ == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, request.getOrganId());
        }
        if (!organ.getEnabled()) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ORGAN_IS_DISABLE, request.getOrganId());
        }
        List<Role> roles = roleService.selectByIdList(request.getRoleIds());
        if (CollUtil.isEmpty(roles) || roles.size() != request.getRoleIds().size()) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ROLE_NOT_EXISTS);
        }
        if (accountService.existsWithProperty(Account::getMobile, request.getMobile())) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.MOBILE_IS_USING, request.getMobile());
        }
        Long accountId = accountService.createUserByRequest(request);

        return Rest.vector("accountId", accountId, Long.class);
    }

    @PostMapping("/list")
    @ApiOperation(value = "用户列表", notes = "用户列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<AccountListResponse> list(@RequestBody AccountListRequest request) {
        PageHelper.startPage(request.currentPage(), request.currentSize());
        Weekend<Account> weekend = Weekend.of(Account.class);
        WeekendCriteria<Account, Object> weekendCriteria = weekend.weekendCriteria();
        if (request.getOrganId() != null) {
            weekendCriteria.andEqualTo(Account::getOrganId, request.getOrganId());
        }
        if (StrUtil.isNotBlank(request.getName())) {
            weekendCriteria.andLike(Account::getName, "%" + request.getName() + "%");
        }
        if (StrUtil.isNotBlank(request.getMobile())) {
            weekendCriteria.andLike(Account::getMobile, "%" + request.getMobile() + "%");
        }
        if (request.getEnabled() != null) {
            weekendCriteria.andEqualTo(Account::getEnabled, request.getEnabled());
        }
        weekend.orderBy("enabled").desc().orderBy("createTime").desc();

        List<Account> accounts = accountService.selectByExample(weekend);
        List<AccountVo> list = accountConverter.convert(accounts);

        AccountListResponse response = new AccountListResponse();
        response.setPage(PageConvertUtil.convert(accounts));

        if (CollUtil.isEmpty(accounts)) {
            response.setAccountList(list);
            return Rest.success(response);
        }
        List<Long> accountIds = accounts.stream().map(Account::getId).collect(Collectors.toList());
        List<AccountRoleVo> accountRoles = roleService.getAccountRoleByAccountIds(accountIds);

        if (CollUtil.isNotEmpty(accountRoles)) {
            Map<Long, List<AccountRoleVo>> collect = accountRoles.stream().collect(Collectors.groupingBy(AccountRoleVo::getAccountId));
            list.forEach(a -> {
                List<AccountRoleVo> vos = collect.getOrDefault(a.getId(), new ArrayList<>());
                a.setRoleIds(vos.stream().map(v -> v.getRoleId().toString()).collect(Collectors.joining(",")));
                a.setRoles(vos.stream().map(AccountRoleVo::getName).collect(Collectors.joining(",")));
            });
        }
        response.setAccountList(list);
        return Rest.success(response);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "用户编辑", notes = "用户编辑", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public Rest<BaseResponse> edit(@Validated @RequestBody AccountEditRequest request) {
        Account account = accountService.selectByPrimaryKey(request.getId());
        if (account == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, request.getId());
        }
        List<Role> roles = roleService.selectByIdList(request.getRoleIds());
        if (CollUtil.isEmpty(roles) || roles.size() != request.getRoleIds().size()) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ROLE_NOT_EXISTS);
        }
        if (!account.getMobile().equals(request.getMobile())
                && accountService.existsWithProperty(Account::getMobile, request.getMobile())) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.MOBILE_IS_USING, request.getMobile());
        }
        Account update = new Account();
        update.setId(account.getId());
        update.setName(request.getName());
        update.setMobile(request.getMobile());
        update.setGender(request.getGender());

        accountService.updateByPrimaryKeySelective(update);

        roleService.changeRolesByAccount(account.getId(), request.getRoleIds());

        if (!account.getMobile().equals(request.getMobile())) {
            Identity identity = new Identity();
            identity.setId(account.getIdentityId());
            identity.setPhoneNumber(request.getMobile());
            identity.setUsername(request.getMobile());

            identityService.updateByPrimaryKeySelective(identity);
        }

        return Rest.success();
    }

    @PostMapping("/{accountId}/disable")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "用户禁用/启用", notes = "用户禁用/启用", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> disable(@PathVariable Long accountId) {
        Account account = accountService.selectByPrimaryKey(accountId);
        if (account == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, accountId);
        }
        Account update = new Account();
        update.setId(account.getId());
        update.setEnabled(!account.getEnabled());

        accountService.updateByPrimaryKeySelective(update);

        Identity identity = new Identity();
        identity.setId(account.getIdentityId());
        identity.setEnabled(true);

        identityService.updateByPrimaryKeySelective(identity);

        return Rest.success();
    }

    @PostMapping("/password/reset")
    @ApiOperation(value = "重置密码", notes = "重置密码", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> reset(@Validated @RequestBody PasswordResetRequest request) {
        Account account = accountService.selectByPrimaryKey(request.getId());
        if (account == null) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, request.getId());
        }
        String mobile = account.getMobile();
        Identity identity = new Identity();
        identity.setId(account.getIdentityId());
        identity.setPassword(passwordEncoder.encode(mobile.length() < 6 ? "123456" : mobile.substring(mobile.length() - 6)));

        identityService.updateByPrimaryKeySelective(identity);

        return Rest.success();
    }

}
