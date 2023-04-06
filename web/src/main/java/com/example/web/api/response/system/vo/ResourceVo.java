package com.example.web.api.response.system.vo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ResourceVo implements Serializable {

    @ApiModelProperty(value = "资源 ID")
    private Long id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源标识")
    private String sign;

    @ApiModelProperty(value = "资源路径")
    private String path;

    @ApiModelProperty(value = "资源类型：1：application/json；5：application/octet-stream；")
    private Integer type;

    @ApiModelProperty(value = "扩展属性")
    private ObjectNode props;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否需要授权：true：需要；false：不需要")
    private Boolean requireAuth;

}
