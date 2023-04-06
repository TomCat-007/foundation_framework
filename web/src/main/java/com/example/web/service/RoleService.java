package com.example.web.service;

import com.example.web.api.response.system.vo.AccountRoleVo;
import com.example.web.pojo.Role;
import common.config.mybatis.BaseService;

import java.util.List;

/**
 * 角色表(Role)表服务接口
 *
 * @author zhanghuiyuan
 * @description 角色表(Role)表服务接口
 * @date 2023/3/3 13:33
 */
public interface RoleService extends BaseService<Role, Long> {

    /**
     * 变更角色元素
     *
     * @param roleId     角色 ID
     * @param elementIds 元素 ID 列表
     */
    void changeElements(Long roleId, List<Long> elementIds);

    void changeRolesByAccount(Long accountId, List<Long> roleIds);

    List<AccountRoleVo> getAccountRoleByAccountIds(List<Long> accountIds);

}
