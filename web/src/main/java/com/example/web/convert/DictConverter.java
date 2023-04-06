package com.example.web.convert;

import com.example.web.api.response.system.DictListResponse;
import com.example.web.api.response.system.DictTreeResponse;
import com.example.web.pojo.Dictionary;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DictConverter {

    List<DictListResponse.DictVO> convert(List<Dictionary> dictList);

    List<DictTreeResponse.DictTreeVO> convertTree(List<Dictionary> dictList);

}
