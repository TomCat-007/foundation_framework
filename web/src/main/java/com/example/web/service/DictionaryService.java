package com.example.web.service;

import common.config.mybatis.BaseService;
import com.example.web.pojo.Dictionary;

/**
 * 字典表(Dictionary)表服务接口
 *
 * @author zhanghuiyuan
 * @description 字典表(Dictionary)表服务接口
 * @date 2023/3/3 13:33
 */
public interface DictionaryService extends BaseService<Dictionary, Long> {

    /**
     * 根据父级 ID 获取最新一条子类数据(id 排序)
     *
     * @param parentId 父级 ID
     * @return
     */
    Dictionary selectLastByParentId(Long parentId);
}
