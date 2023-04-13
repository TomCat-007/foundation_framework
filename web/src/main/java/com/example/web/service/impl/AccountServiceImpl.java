package com.example.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.web.api.request.system.AccountAddRequest;
import com.example.web.mapper.AccountMapper;
import com.example.web.pojo.Account;
import com.example.web.pojo.AccountRoleUnion;
import com.example.web.pojo.Identity;
import com.example.web.service.AccountRoleUnionService;
import com.example.web.service.AccountService;
import com.example.web.service.IdentityService;
import common.config.mybatis.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户表(Account)表服务实现类
 *
 * @author zhangguiyuan
 * @description 账户表(Account)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {

    private final AccountMapper accountMapper;

    private final IdentityService identityService;

    private final AccountRoleUnionService accountRoleUnionService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUserByRequest(AccountAddRequest request) {
        String mobile = request.getMobile();

        Identity identity = new Identity()
                .setIdentityCode(IdUtil.nanoId(16))
                .setUsername(mobile)
                .setPassword(passwordEncoder.encode(mobile.length() < 4 ? "123456" : mobile.substring(mobile.length() - 6)))
                .setPhoneNumber(mobile)
                .setEnabled(true);

        identityService.insertSelective(identity);

        Account account = new Account();
        BeanUtils.copyProperties(request, account);

        account.setIdentityId(identity.getId());
        account.setEnabled(true);

        this.insertSelective(account);

        for (Long roleId : request.getRoleIds()) {
            AccountRoleUnion accountRoleUnion = new AccountRoleUnion();
            accountRoleUnion.setAccountId(account.getId());
            accountRoleUnion.setRoleId(roleId);
            accountRoleUnionService.insertSelective(accountRoleUnion);
        }

        return account.getId();
    }

}
