package com.example.web.api.request.system;

import common.config.api.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AccountAddRequest extends BaseRequest {

    @NotNull
    @ApiModelProperty(value = "机构 ID", required = true, example = "1271007571989368833")
    private Long organId;

    @NotBlank
    @ApiModelProperty(value = "姓名", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "身份证", required = true)
    private String idCard;

    //    @EnumValue(intValues = {1, 2})
    @ApiModelProperty(value = "性别: 1 男, 2 女", required = true, allowableValues = "1,2", example = "1")
    private Byte gender;

    @NotBlank
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotNull
    @ApiModelProperty(value = "角色 ID 列表", required = true, example = "[1,2,3]")
    private List<Long> roleIds;

}
