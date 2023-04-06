package com.example.web.convert;

import com.example.web.api.response.system.vo.OrganVo;
import com.example.web.pojo.Organ;
import common.config.api.base.TreeNodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganConverter {

    OrganVo convert(Organ organ);

    List<OrganVo> convert(List<Organ> organList);

    @Mappings({
            @Mapping(source = "id", target = "key"),
            @Mapping(source = "name", target = "title")
    })
    TreeNodeVO convert2Tree(Organ organ);

    @Mappings({
            @Mapping(source = "id", target = "key"),
            @Mapping(source = "name", target = "title")
    })
    List<TreeNodeVO> convert2Tree(List<Organ> organList);

}