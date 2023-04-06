package com.example.web.api.response.system;

import common.config.api.base.BaseResponse;
import common.config.api.base.TreeNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TreeNodeResponse extends BaseResponse {

    @ApiModelProperty(value = "树节点数据")
    private List<TreeNodeVO> treeData;

}
