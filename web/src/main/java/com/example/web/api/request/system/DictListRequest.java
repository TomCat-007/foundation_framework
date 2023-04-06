package com.example.web.api.request.system;

import common.config.api.base.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
@Accessors(chain = true)
public class DictListRequest extends PageRequest implements Serializable {

}