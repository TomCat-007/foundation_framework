package com.example.web.api.response.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.config.api.base.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class DictListResponse extends PageResponse implements Serializable {

    @ApiModelProperty(value = "字典项列表")
    private List<DictVO> dictList;

    @Data
    @ApiModel
    @Accessors(chain = true)
    public static class DictVO implements Serializable {

        @ApiModelProperty(value = "字典 ID", example = "1354997435847544832")
        private Long id;

        @ApiModelProperty(value = "字典 code", example = "V_PROFESSION")
        private String code;

        @ApiModelProperty(value = "字典名称", example = "职业")
        private String name;

        @ApiModelProperty(value = "标签", example = "标签")
        private String label;

        @ApiModelProperty(value = "字典类型: 1 文本, 2 数值, 5 区划", example = "1")
        private Byte type;

        @ApiModelProperty(value = "子类类型: 1 文本, 2 数值, 5 区划", example = "1")
        private Byte itemType;

        @ApiModelProperty(value = "最后修改时间", example = "2022-02-15 13:45")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
        private LocalDateTime updateTime;

    }

}
