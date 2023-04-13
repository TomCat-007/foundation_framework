package com.example.web.service.impl;

import common.config.mybatis.BaseServiceImpl;
import com.example.web.mapper.IdentityMapper;
import com.example.web.pojo.Identity;
import com.example.web.service.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 身份表(Identity)表服务实现类
 *
 * @author zhangguiyuan
 * @description 身份表(Identity)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class IdentityServiceImpl extends BaseServiceImpl<Identity, Long> implements IdentityService {

    @Resource
    private IdentityMapper identityMapper;

}
