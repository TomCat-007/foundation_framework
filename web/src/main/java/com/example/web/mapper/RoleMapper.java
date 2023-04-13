package com.example.web.mapper;

import com.example.web.api.response.system.vo.AccountRoleVo;
import com.example.web.pojo.Role;
import common.config.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表(Role)表数据库访问层Mapper
 *
 * @author zhangguiyuan
 * @description 角色表(Role)表数据库访问层Mapper
 * @date 2023/3/3 13:33
 */
public interface RoleMapper extends MyMapper<Role, Long> {

    List<AccountRoleVo> getAccountRoleByAccountIds(@Param("accountIds") List<Long> accountIds);

}

