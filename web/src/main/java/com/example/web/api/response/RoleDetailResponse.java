package com.example.web.api.response;

import common.config.api.base.BaseResponse;
import common.config.api.base.TreeNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class RoleDetailResponse extends BaseResponse {

    @ApiModelProperty(value = "角色 ID")
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "完整菜单树")
    private List<TreeNodeVO> treeData;

    @ApiModelProperty(value = "角色拥有的元素 ID 集合")
    private List<Long> checkedKeys;

}
