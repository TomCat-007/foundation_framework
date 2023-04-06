package com.example.web.api.request;

import common.config.api.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class ElementAssignRequest extends BaseRequest {

    @NotNull
    @ApiModelProperty(value = "角色 ID", required = true, example = "1271007571897094144")
    private Long roleId;

    @NotNull
    @ApiModelProperty(value = "元素 ID 集合", required = true)
    private List<Long> elementIds;
}
