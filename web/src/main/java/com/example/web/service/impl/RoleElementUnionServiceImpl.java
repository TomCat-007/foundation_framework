package com.example.web.service.impl;

import common.config.mybatis.BaseServiceImpl;
import com.example.web.mapper.RoleElementUnionMapper;
import com.example.web.pojo.RoleElementUnion;
import com.example.web.service.RoleElementUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色与页面元素关联表(RoleElementUnion)表服务实现类
 *
 * @author zhangguiyuan
 * @description 角色与页面元素关联表(RoleElementUnion)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class RoleElementUnionServiceImpl extends BaseServiceImpl<RoleElementUnion, Long> implements RoleElementUnionService {

    @Resource
    private RoleElementUnionMapper roleElementUnionMapper;

    @Override
    public void updateRoleElement(Long roleId, List<Long> elementIds) {
        roleElementUnionMapper.deleteByProperty(RoleElementUnion::getRoleId, roleId);
        elementIds.forEach(elementId -> {
            RoleElementUnion roleElementUnion = new RoleElementUnion()
                    .setRoleId(roleId)
                    .setElementId(elementId);
            roleElementUnionMapper.insertSelective(roleElementUnion);
        });
    }
}
