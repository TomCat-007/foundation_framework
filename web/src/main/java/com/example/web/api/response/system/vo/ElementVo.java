package com.example.web.api.response.system.vo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class ElementVo implements Serializable {

    @ApiModelProperty(value = "元素 ID")
    private Long id;

    @ApiModelProperty(value = "上级元素 ID")
    private Long parentId;

    @ApiModelProperty(value = "资源 ID")
    private Long resourceId;

    @ApiModelProperty(value = "元素名称，当类型为 1 菜单时，即菜单名，为 5 时为按钮名")
    private String name;

    @ApiModelProperty(value = "元素路径")
    private String path;

    @ApiModelProperty(value = "前端组件名")
    private String component;

    @ApiModelProperty(value = "是否可见", notes = "是否可见：1：可见；0：不可见")
    private Boolean visible;

    @ApiModelProperty(value = "元素类型：1：菜单；5：页面；10：按钮")
    private Integer type;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "元素序号")
    private Integer orderNum;

    @ApiModelProperty(value = "扩展属性")
    private ObjectNode props;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "资源")
    private ResourceVo resource;

    @ApiModelProperty(value = "子级元素列表")
    private List<ElementVo> children;


}
