package com.example.web.convert;

import com.example.web.api.response.system.ResourceDetailResponse;
import com.example.web.pojo.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceConverter {

    ResourceDetailResponse convert(Resource resource);

}
