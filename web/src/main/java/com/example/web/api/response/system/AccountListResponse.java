package com.example.web.api.response.system;

import com.example.web.api.response.system.vo.AccountVo;
import common.config.api.base.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
@Accessors(chain = true)
public class AccountListResponse extends PageResponse {

    @ApiModelProperty(value = "用户列表")
    private List<AccountVo> accountList;

}
