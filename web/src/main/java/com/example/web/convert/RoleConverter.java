package com.example.web.convert;

import com.example.web.api.response.system.RoleListResponse;
import com.example.web.pojo.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleListResponse.RoleVO convert(Role role);

    List<RoleListResponse.RoleVO> convert(List<Role> roles);
}
