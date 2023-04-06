package com.example.web.api.request;

import common.config.api.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrganEditRequest extends BaseRequest {

    @NotNull
    @ApiModelProperty(value = "机构 ID", required = true, example = "1271007571989368833")
    private Long id;

    @ApiModelProperty(value = "机构名称", required = true, example = "河北省卫健委")
    private String name;

    @ApiModelProperty(value = "管理员手机号, 管理机构时为空")
    private String contactMobile;

}
