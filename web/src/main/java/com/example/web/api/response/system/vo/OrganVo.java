package com.example.web.api.response.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class OrganVo implements Serializable {

    @ApiModelProperty(value = "主键 ID", required = true, example = "1271007571989368833")
    private Long id;

    @ApiModelProperty(value = "上级机构ID，一级机构为 0", required = true, example = "1271007571989368833")
    private Long parentId;

    @ApiModelProperty(value = "前驱节点 ID", required = true, example = "1271007571989368833")
    private Long prevId;

    @ApiModelProperty(value = "机构编码", required = true, example = "45ux")
    private String code;

    @ApiModelProperty(value = "名称", required = true, example = "河北省卫健委")
    private String name;

    @ApiModelProperty(value = "是否启用：1：已启用；0：未启用", allowableValues = "0,1", example = "1")
    private Boolean enabled;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "联系人电话")
    private String contactMobile;

    @ApiModelProperty(value = "人员数")
    private Integer personNumber;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

}