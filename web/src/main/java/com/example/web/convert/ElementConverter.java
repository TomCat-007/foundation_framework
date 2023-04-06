package com.example.web.convert;

import com.example.web.api.response.system.vo.ElementVo;
import com.example.web.pojo.Element;
import common.config.api.base.TreeNodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ElementConverter {

    ElementVo convert2ElementVo(Element element);

    @Mappings({
            @Mapping(source = "id", target = "key"),
            @Mapping(source = "name", target = "title")
    })
    TreeNodeVO convert2TreeNode(Element element);

}
