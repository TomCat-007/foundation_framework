package com.example.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import common.config.mybatis.BaseServiceImpl;
import com.example.web.mapper.ResourceMapper;
import com.example.web.pojo.Resource;
import com.example.web.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源表(Resource)表服务实现类
 *
 * @author zhanghuiyuan
 * @description 资源表(Resource)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 根据角色 ID 获取
     *
     * @param roleIds 角色 ID 列表
     * @return
     */
    @Override
    public List<Resource> findByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return resourceMapper.findByRoleIds(roleIds);
    }
}
