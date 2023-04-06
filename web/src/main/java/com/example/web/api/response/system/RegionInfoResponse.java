package com.example.web.api.response.system;

import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 区划表(Region)实体类
 *
 * @author zhanghuiyuan
 * @description 区划表(Region)实体类
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
@Accessors(chain = true)
public class RegionInfoResponse extends BaseResponse {

    /**
     * 地区编码
     */
    private String id;

    /**
     * 上级 ID，0 表示顶级
     */
    private String parentId;

    /**
     * 前驱节点 ID，0 表示头部节点
     */
    private String prevId;

    private String provinceId;

    private String provinceName;

    private String cityId;

    private String cityName;

    private String countyId;

    private String countyName;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 名称
     */
    private String name;

    /**
     * 级别: 1 省/自治区/直辖市 2 地级市/地区/自治州/盟市 3 市辖区/县级市/县/自治县/旗/自治旗/林区/特区 4 乡镇/街道 5 社区/村委
     */
    private Byte level;

}