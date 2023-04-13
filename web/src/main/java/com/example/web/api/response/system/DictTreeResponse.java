package com.example.web.api.response.system;

import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
@Accessors(chain = true)
public class DictTreeResponse extends BaseResponse implements Serializable {

    @ApiModelProperty(value = "字典值列表")
    private List<DictTreeVO> itemList;

    @Data
    @ApiModel
    @Accessors(chain = true)
    public static class DictTreeVO implements Serializable {

        @ApiModelProperty(value = "字典值 ID", example = "1354997435847544832")
        private Long id;

        @ApiModelProperty(value = "上级 ID, 0 表示顶级", example = "1354997435847544832")
        private Long parentId;

        @ApiModelProperty(value = "字典值 code", example = "P_WORKER")
        private String code;

        @ApiModelProperty(value = "字典值", example = "工人")
        private String name;

        @ApiModelProperty(value = "标签", example = "标签")
        private String label;

        @ApiModelProperty(value = "类型: 1 文本, 2 数值, 5 区划")
        private Byte type;

        @ApiModelProperty(value = "子类类型: 1 文本, 2 数值, 5 区划", example = "1")
        private Byte itemType;

        @ApiModelProperty(value = "子项")
        private List<DictTreeVO> children;

    }

}
