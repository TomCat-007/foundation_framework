package com.example.web.mapper;

import common.config.mybatis.MyMapper;
import com.example.web.pojo.Resource;

import java.util.List;

/**
 * 资源表(Resource)表数据库访问层Mapper
 *
 * @author zhanghuiyuan
 * @description 资源表(Resource)表数据库访问层Mapper
 * @date 2023/3/3 13:33
 */
public interface ResourceMapper extends MyMapper<Resource, Long> {

    /**
     * 根据角色 ID 获取
     *
     * @param roleIds 角色 ID 列表
     * @return
     */
    List<Resource> findByRoleIds(List<Long> roleIds);

}

