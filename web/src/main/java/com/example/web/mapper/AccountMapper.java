package com.example.web.mapper;

import common.config.mybatis.MyMapper;
import com.example.web.pojo.Account;

import java.util.List;

/**
 * 账户表(Account)表数据库访问层Mapper
 *
 * @author zhangguiyuan
 * @description 账户表(Account)表数据库访问层Mapper
 * @date 2023/3/3 13:33
 */
public interface AccountMapper extends MyMapper<Account, Long> {

    /**
     * 获取身份关联账号信息
     *
     * @param identityId 身份 ID
     * @return
     */
    List<Account> findByIdentityId(Long identityId);

}

