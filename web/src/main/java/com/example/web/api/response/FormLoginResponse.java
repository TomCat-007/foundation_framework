package com.example.web.api.response;

import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
@Accessors(chain = true)
public class FormLoginResponse extends BaseResponse {

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "身份 ID", example = "1293385366337884161")
    private Long identityId;

    @ApiModelProperty(value = "当前账户 ID", example = "1293385366337884160")
    private Long currentAccountId;

    @ApiModelProperty(value = "当前登录账号拥有的账户列表")
    private List<Long> accountIds;

    @ApiModelProperty(value = "是否关注公众号：1已关注，0未关注")
    private Integer subscribe;

}
