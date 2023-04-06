package com.example.web.api.response.system;

import com.example.web.api.response.system.vo.OrganVo;
import common.config.api.base.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class OrganListResponse extends PageResponse {

    @ApiModelProperty(value = "机构列表")
    private List<OrganVo> organList;

}
