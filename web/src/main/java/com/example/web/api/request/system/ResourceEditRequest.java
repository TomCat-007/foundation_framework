package com.example.web.api.request.system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
@Accessors(chain = true)
public class ResourceEditRequest implements Serializable {

    @NotNull
    @ApiModelProperty(value = "资源 ID", required = true, example = "1271007571897094144")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "资源名", required = true, example = "添加菜单")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "资源标识", required = true, example = "system.menu.add")
    private String sign;

    @NotBlank
    @ApiModelProperty(value = "资源路径", required = true, example = "/system/menu/add")
    private String path;

    @NotNull
    @ApiModelProperty(value = "资源类型", required = true, notes = "资源类型：1：application/json；5：application/octet-stream；", example = "1")
    private Integer type;

    @ApiModelProperty(value = "扩展属性", example = "{\"title\":\"标题\"}")
    private ObjectNode props = JsonUtil.emptyObjectNode();

    @ApiModelProperty(value = "备注", example = "备注一下呗")
    private String remark;

}