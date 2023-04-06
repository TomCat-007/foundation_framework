package com.example.web.api.request.system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.config.api.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MenuEditRequest extends BaseRequest {

    @NotNull
    @ApiModelProperty(value = "元素 ID", required = true, example = "1271007571989368833")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "资源名", required = true, example = "添加菜单")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "前端路径", required = true, example = "/system/resource")
    private String path;

    @NotBlank
    @ApiModelProperty(value = "前端组件名", required = true, example = "AuthorityManager")
    private String component;

    @ApiModelProperty(value = "图标", example = "abc")
    private String icon;

    @ApiModelProperty(value = "备注", example = "备注一下呗")
    private String remark;

    @ApiModelProperty(value = "是否可见：1：可见；0：不可见", example = "true")
    private Boolean visible;

    @ApiModelProperty(value = "序号", example = "1")
    private Integer orderNum;

    @ApiModelProperty(value = "扩展属性", example = "{\"title\":\"标题\"}")
    private ObjectNode props;

}
