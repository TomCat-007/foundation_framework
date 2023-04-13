package com.example.web.service.impl;

import common.config.mybatis.BaseServiceImpl;
import com.example.web.mapper.AccountRoleUnionMapper;
import com.example.web.pojo.AccountRoleUnion;
import com.example.web.service.AccountRoleUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 账户与角色关联表(AccountRoleUnion)表服务实现类
 *
 * @author zhangguiyuan
 * @description 账户与角色关联表(AccountRoleUnion)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class AccountRoleUnionServiceImpl extends BaseServiceImpl<AccountRoleUnion, Long> implements AccountRoleUnionService {

    @Resource
    private AccountRoleUnionMapper accountRoleUnionMapper;

}
