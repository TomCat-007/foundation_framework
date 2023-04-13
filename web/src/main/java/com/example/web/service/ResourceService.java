package com.example.web.service;

import common.config.mybatis.BaseService;
import com.example.web.pojo.Resource;

import java.util.List;

/**
 * 资源表(Resource)表服务接口
 *
 * @author zhangguiyuan
 * @description 资源表(Resource)表服务接口
 * @date 2023/3/3 13:33
 */
public interface ResourceService extends BaseService<Resource, Long> {

    /**
     * 根据角色 ID 获取
     *
     * @param roleIds 角色 ID 列表
     * @return
     */
    List<Resource> findByRoleIds(List<Long> roleIds);

}
