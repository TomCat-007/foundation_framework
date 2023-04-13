package com.example.web.service.impl;

import com.example.web.mapper.DictionaryMapper;
import com.example.web.pojo.Dictionary;
import com.example.web.service.DictionaryService;
import common.config.mybatis.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典表(Dictionary)表服务实现类
 *
 * @author zhangguiyuan
 * @description 字典表(Dictionary)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, Long> implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    /**
     * 根据父级 ID 获取最新一条子类数据(id 排序)
     *
     * @param parentId 父级 ID
     * @return
     */
    @Override
    public Dictionary selectLastByParentId(Long parentId) {
        return dictionaryMapper.selectLastByParentId(parentId);
    }
}
