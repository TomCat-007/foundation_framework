package com.example.web.service.impl;

import com.example.web.pojo.Organ;
import common.config.mybatis.BaseServiceImpl;
import com.example.web.mapper.OrganMapper;
import com.example.web.pojo.Organ;
import com.example.web.service.OrganService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 机构表(Organ)表服务实现类
 *
 * @author zhanghuiyuan
 * @description 机构表(Organ)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class OrganServiceImpl extends BaseServiceImpl<Organ, Long> implements OrganService {

    @Resource
    private OrganMapper organMapper;

}
