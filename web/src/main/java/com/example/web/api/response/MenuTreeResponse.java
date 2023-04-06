package com.example.web.api.response;

import com.example.web.api.response.system.vo.ElementVo;
import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class MenuTreeResponse extends BaseResponse {

    @ApiModelProperty
    private List<ElementVo> elements;

    @ApiModelProperty
    private List<Long> checkedElementIds;

}
