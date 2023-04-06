package com.example.web.api.request.system;

import common.config.api.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AccountListRequest extends PageRequest {

    @ApiModelProperty(value = "管理机构 ID", example = "1271007571989368833")
    private Long organId;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "手机号", example = "15900000000")
    private String mobile;

    @ApiModelProperty(value = "是否启用：1：已启用；0：未启用", allowableValues = "0,1", example = "1")
    private Byte enabled;

}
