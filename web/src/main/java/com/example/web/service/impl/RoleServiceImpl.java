package com.example.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.web.api.response.system.vo.AccountRoleVo;
import com.example.web.mapper.RoleMapper;
import com.example.web.pojo.AccountRoleUnion;
import com.example.web.pojo.Role;
import com.example.web.pojo.RoleElementUnion;
import com.example.web.service.AccountRoleUnionService;
import com.example.web.service.RoleElementUnionService;
import com.example.web.service.RoleService;
import common.config.mybatis.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表(Role)表服务实现类
 *
 * @author zhangguiyuan
 * @description 角色表(Role)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleElementUnionService roleElementUnionService;

    private final AccountRoleUnionService accountRoleUnionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeElements(Long roleId, List<Long> elementIds) {
        // 已经存在的元素 ID 集合
        List<Long> existedElementIds = roleElementUnionService.selectByProperty(RoleElementUnion::getRoleId, roleId)
                .stream()
                .mapToLong(RoleElementUnion::getElementId)
                .boxed()
                .collect(Collectors.toList());

        // 需要删除的元素 ID 集合
        List<Long> needDeleteElementIds = new ArrayList<>(existedElementIds);
        // 需要添加的元素 ID 集合
        List<Long> needAddElementIds = new ArrayList<>();
        if (CollUtil.isNotEmpty(elementIds)) {// 为空则全部清空现有元素
            needDeleteElementIds.removeAll(elementIds);
            needAddElementIds = new ArrayList<>(elementIds);
            needAddElementIds.removeAll(existedElementIds);
        }

        if (CollUtil.isNotEmpty(needDeleteElementIds)) {
            Weekend<RoleElementUnion> weekend = Weekend.of(RoleElementUnion.class);
            weekend
                    .weekendCriteria()
                    .andEqualTo(RoleElementUnion::getRoleId, roleId)
                    .andIn(RoleElementUnion::getElementId, needDeleteElementIds);

            roleElementUnionService.deleteByExample(weekend);
        }
        if (CollUtil.isNotEmpty(needAddElementIds)) {
            needAddElementIds
                    .forEach(elementId -> {
                        RoleElementUnion roleElementUnion = new RoleElementUnion()
                                .setElementId(elementId)
                                .setRoleId(roleId);
                        roleElementUnionService.insertSelective(roleElementUnion);
                    });
        }
    }

    @Override
    public void changeRolesByAccount(Long accountId, List<Long> roleIds) {
        List<AccountRoleUnion> oldList = accountRoleUnionService.selectByProperty(AccountRoleUnion::getAccountId, accountId);
        List<Long> oldIds = oldList.stream().map(AccountRoleUnion::getRoleId).collect(Collectors.toList());

        // 需要删除的角色 ID 集合
        List<Long> needDeleteIds = new ArrayList<>(oldIds);
        needDeleteIds.removeAll(roleIds);

        // 需要添加的角色 ID 集合
        List<Long> needAddIds = new ArrayList<>(roleIds);
        needAddIds.removeAll(oldIds);

        if (CollUtil.isNotEmpty(needDeleteIds)) {
            Weekend<AccountRoleUnion> weekend = Weekend.of(AccountRoleUnion.class);
            weekend
                    .weekendCriteria()
                    .andEqualTo(AccountRoleUnion::getAccountId, accountId)
                    .andIn(AccountRoleUnion::getRoleId, needDeleteIds);

            accountRoleUnionService.deleteByExample(weekend);
        }
        needAddIds.forEach(r -> {
            AccountRoleUnion union = new AccountRoleUnion();
            union.setAccountId(accountId);
            union.setRoleId(r);
            accountRoleUnionService.insertSelective(union);
        });
    }

    @Override
    public List<AccountRoleVo> getAccountRoleByAccountIds(List<Long> accountIds) {
        return roleMapper.getAccountRoleByAccountIds(accountIds);
    }
}
