package com.example.web.mapper;

import common.config.mybatis.MyMapper;
import com.example.web.pojo.Dictionary;

/**
 * 字典表(Dictionary)表数据库访问层Mapper
 *
 * @author zhangguiyuan
 * @description 字典表(Dictionary)表数据库访问层Mapper
 * @date 2023/3/3 13:33
 */
public interface DictionaryMapper extends MyMapper<Dictionary, Long> {

    /**
     * 根据父级 ID 获取最新一条子类数据(id 排序)
     *
     * @param parentId 父级 ID
     * @return
     */
    Dictionary selectLastByParentId(Long parentId);

}

