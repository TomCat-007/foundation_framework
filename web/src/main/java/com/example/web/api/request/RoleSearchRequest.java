package com.example.web.api.request;

import common.config.api.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel
@Accessors(chain = true)
public class RoleSearchRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "角色名", example = "form")
    private String name;

}
