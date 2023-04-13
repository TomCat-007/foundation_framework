package com.example.web.service;

import com.example.web.pojo.RoleElementUnion;
import common.config.mybatis.BaseService;

import java.util.List;

/**
 * 角色与页面元素关联表(RoleElementUnion)表服务接口
 *
 * @author zhangguiyuan
 * @description 角色与页面元素关联表(RoleElementUnion)表服务接口
 * @date 2023/3/3 13:33
 */
public interface RoleElementUnionService extends BaseService<RoleElementUnion, Long> {

    /**
     * 更新角色的菜单权限集合
     *
     * @param roleId     角色 ID
     * @param elementIds 菜单集合
     */
    void updateRoleElement(Long roleId, List<Long> elementIds);

}
