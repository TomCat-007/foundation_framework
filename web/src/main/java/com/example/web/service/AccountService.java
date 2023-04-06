package com.example.web.service;

import com.example.web.api.request.system.AccountAddRequest;
import com.example.web.pojo.Account;
import common.config.mybatis.BaseService;

/**
 * 账户表(Account)表服务接口
 *
 * @author zhanghuiyuan
 * @description 账户表(Account)表服务接口
 * @date 2023/3/3 13:33
 */
public interface AccountService extends BaseService<Account, Long> {

    Long createUserByRequest(AccountAddRequest request);

}
