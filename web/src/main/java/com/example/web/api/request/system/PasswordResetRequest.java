package com.example.web.api.request.system;

import common.config.api.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
public class PasswordResetRequest extends BaseRequest {

    @NotNull
    @ApiModelProperty(value = "用户 ID", example = "1271007571989368833")
    private Long id;

}
