package com.example.web.api.response.system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@ApiModel
@Accessors(chain = true)
public class ResourceDetailResponse extends BaseResponse {

    @ApiModelProperty(value = "资源 ID")
    private Long id;

    @ApiModelProperty(value = "资源名", example = "员工查询")
    private String name;

    @ApiModelProperty(value = "资源标识", example = "system.menu.add")
    private String sign;

    @ApiModelProperty(value = "资源路径", example = "/system/menu/add")
    private String path;

    @ApiModelProperty(value = "资源类型", notes = "资源类型：1：application/json；5：application/octet-stream；", example = "1")
    private Integer type;

    @ApiModelProperty(value = "是否需要授权", example = "true")
    private Boolean requireAuth;

    @ApiModelProperty(value = "扩展参数", example = "title隐私资源")
    private ObjectNode props;

    @ApiModelProperty(value = "备注", example = "备注一下呗")
    private String remark;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;

}