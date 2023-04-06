package com.example.web.api.request.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
@Accessors(chain = true)
public class DictItemAddRequest implements Serializable {

    @NotNull
    @ApiModelProperty(value = "父级 ID", example = "1354997435847544832")
    private Long dictId;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "字典项值")
    private String name;

}