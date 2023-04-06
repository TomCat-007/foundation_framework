package com.example.web;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.web.api.response.system.DictTreeResponse;
import com.example.web.convert.DictConverter;
import com.example.web.pojo.Dictionary;
import com.example.web.service.DictionaryService;
import common.util.TreeNodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RList;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DictCache {

    private final RedissonClient redissonClient;

    private final DictionaryService dictionaryService;

    private final DictConverter dictConverter;

    // 全量缓存 map
    private static final String KEY_ALL_TREE_MAP = "DICT:TREE:MAP";
    // 全量缓存 list
    private static final String KEY_ALL_TREE_LIST = "DICT:TREE:LIST";

    /**
     * 获取字典项树结构
     *
     * @return
     */
    public List<DictTreeResponse.DictTreeVO> getCachedTree(String enumCode) {
        if (StrUtil.isEmpty(enumCode)) {
            return new ArrayList<>();
        }
        RLocalCachedMap<String, List<DictTreeResponse.DictTreeVO>> cachedTreeMap = redissonClient.getLocalCachedMap(KEY_ALL_TREE_MAP, LocalCachedMapOptions.defaults());
        if (cachedTreeMap.isExists()) {
            return cachedTreeMap.get(enumCode);
        }
        Map<String, List<DictTreeResponse.DictTreeVO>> treeMap = initTreeMap();
        cachedTreeMap.putAll(treeMap);
        return treeMap.get(enumCode);
    }

    /**
     * 获取字典项树结构
     *
     * @return
     */
    public List<DictTreeResponse.DictTreeVO> getCachedTree() {
        RList<DictTreeResponse.DictTreeVO> cachedTree = redissonClient.getList(KEY_ALL_TREE_LIST);
        if (cachedTree.isExists()) {
            return cachedTree.readAll();
        }
        List<DictTreeResponse.DictTreeVO> tree = initTree();
        cachedTree.addAll(tree);
        return tree;
    }

    /**
     * 获取字典值 map
     *
     * @param enumCode 字典code
     * @return
     */
    public Map<String, String> getCodeNameMap(String enumCode) {
        if (StrUtil.isEmpty(enumCode)) {
            return new HashMap<>();
        }
        List<DictTreeResponse.DictTreeVO> cachedTree = getCachedTree(enumCode);
        return cachedTree.stream().collect(Collectors.toMap(DictTreeResponse.DictTreeVO::getCode, DictTreeResponse.DictTreeVO::getName));
    }

    /**
     * 重置缓存
     */
    public void resetCache() {
        RLocalCachedMap<String, List<DictTreeResponse.DictTreeVO>> cachedTreeMap = redissonClient.getLocalCachedMap(KEY_ALL_TREE_MAP, LocalCachedMapOptions.defaults());
        if (cachedTreeMap.isExists()) {
            cachedTreeMap.clear();
        }
        Map<String, List<DictTreeResponse.DictTreeVO>> treeMap = initTreeMap();
        if (CollUtil.isNotEmpty(treeMap)) {
            cachedTreeMap.putAll(treeMap);
        }

        RList<DictTreeResponse.DictTreeVO> cachedTree = redissonClient.getList(KEY_ALL_TREE_LIST);
        if (cachedTree.isExists()) {
            cachedTree.clear();
        }
        List<DictTreeResponse.DictTreeVO> treeList = initTree();
        if (CollUtil.isNotEmpty(treeList)) {
            cachedTree.addAll(treeList);
        }
    }

    private Map<String, List<DictTreeResponse.DictTreeVO>> initTreeMap() {
        return initTree().stream().collect(Collectors.toMap(DictTreeResponse.DictTreeVO::getCode, DictTreeResponse.DictTreeVO::getChildren));
    }

    private List<DictTreeResponse.DictTreeVO> initTree() {
        List<Dictionary> list = dictionaryService.selectByProperty(Dictionary::getEnabled, true);
        for (Dictionary dictionary : list) {
            if (dictionary.getParentId() == 0) {
                dictionary.setChildren(new ArrayList<>());// 防止当二级子类为空时 initTreeMap的 NPL
            }
        }
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        TreeNodeUtil.toTree(list);// 迭代树
        return dictConverter.convertTree(list);
    }
}
