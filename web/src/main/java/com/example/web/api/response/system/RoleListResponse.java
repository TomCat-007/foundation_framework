package com.example.web.api.response.system;

import common.config.api.base.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class RoleListResponse extends PageResponse implements Serializable {

    @ApiModelProperty(value = "角色列表")
    private List<RoleVO> roles;

    @Data
    @ApiModel
    @Accessors(chain = true)
    public static class RoleVO implements Serializable {
        @ApiModelProperty(value = "角色 ID")
        private Long id;

        @ApiModelProperty(value = "角色名")
        private String name;

        @ApiModelProperty(value = "备注")
        private String remark;

        @ApiModelProperty(value = "添加时间")
        private LocalDateTime createTime;
    }

}