package com.example.web.convert;

import com.example.web.api.response.system.vo.AccountVo;
import com.example.web.pojo.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountConverter {

    @Mapping(target = "roles", ignore = true)
    AccountVo convert(Account account);
    @Mapping(target = "roles", ignore = true)
    List<AccountVo> convert(List<Account> accountList);

}
