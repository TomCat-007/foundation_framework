package com.example.web.api.response.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
public class AccountVo implements Serializable {

    @ApiModelProperty(value = "主键 ID", required = true, example = "1271007571989368833")
    private Long id;

    @ApiModelProperty(value = "机构ID", required = true, example = "1271007571989368833")
    private Long organId;

    @ApiModelProperty(value = "姓名", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "性别: 1 男, 2 女")
    private Byte gender;

    @ApiModelProperty(value = "是否启用：1：已启用；0：未启用", allowableValues = "0,1", example = "1")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间", example = "2022-05-24 14:32")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "角色 ID 列表", example = "1,2,3")
    private String roleIds;

    @ApiModelProperty(value = "角色名称列表", example = "\"系统管理员\"")
    private String roles;

}
