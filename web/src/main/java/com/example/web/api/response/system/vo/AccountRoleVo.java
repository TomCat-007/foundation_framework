package com.example.web.api.response.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
public class AccountRoleVo implements Serializable {

    @ApiModelProperty(value = "账号 ID", required = true, example = "1271007571989368833")
    private Long accountId;

    @ApiModelProperty(value = "角色 ID", required = true, example = "1271007571989368833")
    private Long roleId;

    @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
    private String name;

}
