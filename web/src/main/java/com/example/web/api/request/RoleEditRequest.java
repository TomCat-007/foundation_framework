package com.example.web.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class RoleEditRequest implements Serializable {

    @ApiModelProperty(value = "角色 ID", required = true, example = "1282491858190209024")
    private Long roleId;

    /**
     * 角色名
     */
    @NotBlank
    @ApiModelProperty(value = "角色名", required = true, example = "form")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注一下")
    private String remark;

     /**
      * 元素 ID 集合
      */
     @ApiModelProperty(value = "元素集合 ID", required = true)
     private List<Long> elementIds;

}
