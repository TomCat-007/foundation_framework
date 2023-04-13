//package com.example.web;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.StrUtil;
//import com.jianxinyun.health.web.api.response.system.RegionInfoResponse;
//import com.jianxinyun.health.web.api.response.system.RegionResponse;
//import com.jianxinyun.health.web.convert.RegionConverter;
//import com.jianxinyun.health.web.enums.RegionLevelEnum;
//import com.jianxinyun.health.web.model.single.Region;
//import com.jianxinyun.health.web.service.single.RegionService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.LocalCachedMapOptions;
//import org.redisson.api.RBucket;
//import org.redisson.api.RLocalCachedMap;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 区划缓存
// *
// * @author zhangguiyuan
// * @description 区划缓存
// * @date 2023/3/3 13:33
// */
//@Slf4j
//@Component
//@AllArgsConstructor
//public class RegionCache {
//
//    private final RedissonClient redissonClient;
//
//    private final RegionService regionService;
//
//    private final RegionConverter regionConverter;
//
//    // 省市区三级区划缓存
//    private static final String KEY_ALL_TREE = "REGION:TREE:ALL";
//    // 市县街道三级区划缓存
//    private static final String KEY_CITY_TREE = "REGION:TREE:{}:CITY";
//    // 获取下级树结构（两级）
//    private static final String KEY_PARENT_TREE = "REGION:TREE:{}:PARENT";
//    // 所有区划
//    private static final String KEY_MAP = "REGION:MAP:ALL";
//
//    public RegionInfoResponse regionInfo(String regionId) {
//        RLocalCachedMap<String, RegionInfoResponse> regionMap = redissonClient.getLocalCachedMap(KEY_MAP, LocalCachedMapOptions.defaults());
//        RegionInfoResponse regionInfo = regionMap.get(regionId);
//        if (regionInfo != null) {
//            return regionInfo;
//        }
//        regionInfo = regionConverter.convertInfo(regionService.selectByPrimaryKey(regionId));
//        if (regionInfo == null) {
//            return null;
//        }
//        regionMap.put(regionId, regionInfo);
//        return regionInfo;
//    }
//
//    /**
//     * 根据省份 ID 获取市县街道三级区划
//     *
//     * @param provinceId 省份 ID
//     * @return
//     */
//    public List<RegionResponse.RegionVO> regionTreeByProvince(String provinceId) {
//        RBucket<List<RegionResponse.RegionVO>> bucket = redissonClient.getBucket(StrUtil.format(KEY_CITY_TREE, provinceId));
//        List<RegionResponse.RegionVO> regionVOS = bucket.get();
//        if (CollUtil.isNotEmpty(regionVOS)) {
//            return regionVOS;
//        }
//
//        List<Region> regionList = regionService.selectByProperty(Region::getProvinceId, provinceId);
//        List<RegionResponse.RegionVO> tree = toTree(regionList, provinceId);
//
//        bucket.set(tree);
//        return tree;
//    }
//
//    /**
//     * 根据父级 ID 获取下属两级区划
//     *
//     * @param parentId 父级 ID
//     * @return
//     */
//    public List<RegionResponse.RegionVO> regionTreeByParentId(String parentId) {
//        RBucket<List<RegionResponse.RegionVO>> bucket = redissonClient.getBucket(StrUtil.format(KEY_PARENT_TREE, parentId));
//        List<RegionResponse.RegionVO> regionVOS = bucket.get();
//        if (CollUtil.isNotEmpty(regionVOS)) {
//            return regionVOS;
//        }
//
//        List<Region> regionList = regionService.selectByProperty(Region::getParentId, parentId);
//
//        if (CollUtil.isNotEmpty(regionList)) {
//            List<String> regionIds = regionList.stream().map(Region::getId).collect(Collectors.toList());
//            List<Region> children = regionService.selectInByProperty(Region::getParentId, regionIds);
//            if (CollUtil.isNotEmpty(children)) {
//                regionList.addAll(children);
//            }
//        }
//        List<RegionResponse.RegionVO> tree = toTree(regionList, parentId);
//
//        bucket.set(tree);
//        return tree;
//    }
//
//    private List<RegionResponse.RegionVO> toTree(List<Region> regionList, String parentId) {
//        List<RegionResponse.RegionVO> tree = new ArrayList<>();
//        regionList
//                .stream()
//                .filter(r -> r.getParentId().equals(parentId))
//                .forEach(parent -> {
//                    RegionResponse.RegionVO parentVo = regionConverter.convert(parent);
//                    recursiveTree(parentVo, regionList);
//                    tree.add(parentVo);
//                });
//        return tree;
//    }
//
//    private List<RegionResponse.RegionVO> toTree(List<Region> regionList, RegionLevelEnum topLevel) {
//        List<RegionResponse.RegionVO> tree = new ArrayList<>();
//        regionList
//                .stream()
//                .filter(r -> topLevel.getCode().equals(r.getLevel()))
//                .forEach(parent -> {
//                    RegionResponse.RegionVO parentVo = regionConverter.convert(parent);
//                    recursiveTree(parentVo, regionList);
//                    tree.add(parentVo);
//                });
//        return tree;
//    }
//
//    private void recursiveTree(RegionResponse.RegionVO parent, List<Region> regionList) {
//        regionList
//                .stream()
//                .filter(r -> parent.getId().equals(r.getParentId()))
//                .forEach(record -> {
//                    RegionResponse.RegionVO recordVo = regionConverter.convert(record);
//                    recursiveTree(recordVo, regionList);
//                    if (CollUtil.isEmpty(parent.getChildren())) {
//                        parent.setChildren(new ArrayList<>());
//                    }
//                    parent.getChildren().add(recordVo);
//                });
//    }
//
//}
