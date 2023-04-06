package com.example.web.controller.system;

import cn.hutool.core.util.StrUtil;
import com.example.web.DictCache;
import com.example.web.api.request.system.DictItemAddRequest;
import com.example.web.api.request.system.DictListRequest;
import com.example.web.api.response.system.DictListResponse;
import com.example.web.api.response.system.DictTreeResponse;
import com.example.web.convert.DictConverter;
import com.example.web.enums.error.DictErrorCodeEnum;
import com.example.web.pojo.Dictionary;
import com.example.web.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.PageConvertUtil;
import common.util.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.weekend.Weekend;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Api(value = "数据字典", tags = {"数据字典管理"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class DictController {

    private final DictConverter dictConverter;

    private final DictCache dictCache;

    private final DictionaryService dictionaryService;

    @PostMapping(value = "/admin/system/dict/list")
    @ApiOperation(value = "字典-列表", notes = "字典-列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<DictListResponse> list(@RequestBody DictListRequest request) {
        PageHelper.startPage(request.currentPage(), request.currentSize());

        Weekend<Dictionary> weekend = Weekend.of(Dictionary.class);
        weekend.weekendCriteria().andEqualTo(Dictionary::getParentId, 0L).andEqualTo(Dictionary::getEnabled, true);

        List<Dictionary> list = dictionaryService.selectByExample(weekend);

        DictListResponse response = new DictListResponse();
        response.setDictList(dictConverter.convert(list));
        response.setPage(PageConvertUtil.convert(list));
        return Rest.success(response);
    }

    @RequestMapping(value = "/admin/system/dict/item/list")
    @ApiOperation(value = "字典值-列表", notes = "字典值-列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<DictTreeResponse> itemList(@ApiParam(value = "字典项 ID", name = "dictId") @NonNull @Min(value = 1, message = "最小值为1")
                                           @RequestParam("dictId") Long dictId) {
        Dictionary dictionary = dictionaryService.selectByPrimaryKey(dictId);

        if (dictionary == null || !dictionary.getEnabled()) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_NOT_FOUND, dictId);
        }

        List<DictTreeResponse.DictTreeVO> list = dictCache.getCachedTree(dictionary.getCode());

        DictTreeResponse response = new DictTreeResponse();
        response.setItemList(list);
        return Rest.success(response);
    }

    @PostMapping(value = "/admin/system/dict/item/add")
    @ApiOperation(value = "字典值-添加", notes = "字典值-添加", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> itemAdd(@Validated @RequestBody DictItemAddRequest request) {
        Long dictId = request.getDictId();
        if (dictId == 0) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_NOT_FOUND, dictId);
        }
        Dictionary parent = dictionaryService.selectByPrimaryKey(dictId);
        if (parent == null || !parent.getEnabled()) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_NOT_FOUND, dictId);
        }
        // 重名判断
        Weekend<Dictionary> weekend = Weekend.of(Dictionary.class);
        weekend.weekendCriteria().andEqualTo(Dictionary::getName, request.getName()).andEqualTo(Dictionary::getParentId, parent.getId());

        if (dictionaryService.selectCountByExample(weekend) > 0) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_EXISTS, request.getName());
        }
        String label = parent.getLabel();

        // 获取同组最新 orderNum
        Dictionary last = dictionaryService.selectLastByParentId(parent.getId());
        int orderNum = last == null ? 1 : last.getOrderNum() + 1;

        Dictionary dictionary = new Dictionary()
                .setParentId(parent.getId())
                .setCode(parent.getCode() + "_" + String.format("%04d", orderNum))
                .setName(request.getName())
                .setType(parent.getItemType())
                .setLabel(label)
                .setOrderNum(orderNum)
                .setEnabled(true);

        int i = dictionaryService.insertSelective(dictionary);
        // 成功则更新修改时间
        if (i == 1) {
            // 更新父级修改时间
            Dictionary parentDict = dictionaryService.selectByPrimaryKey(dictionary.getParentId());
            parentDict.setUpdateTime(LocalDateTime.now());
            dictionaryService.updateByPrimaryKeySelective(parentDict);
            dictCache.resetCache();
        }
        return Rest.vector("dictId", dictionary.getId(), Long.class);
    }

    @PostMapping(value = "/admin/system/dict/item/del")
    @ApiOperation(value = "字典值-删除", notes = "字典值-删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> itemDel(@ApiParam(value = "字典项 ID", name = "dictId")
                                      @RequestParam("dictId") Long dictId) {
        if (dictId == null || dictId == 0) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_NOT_FOUND, dictId);
        }
        Dictionary dictionary = dictionaryService.selectByPrimaryKey(dictId);
        if (dictionary == null || !dictionary.getEnabled()) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_NOT_FOUND, dictId);
        }
        if (dictionaryService.existsWithProperty(Dictionary::getParentId, dictId)) {
            throw ServiceExceptionUtil.fail(DictErrorCodeEnum.ELEMENT_HAS_CHILDREN, dictId);
        }

        boolean b = dictionaryService.deleteByPrimaryKey(dictId);
        // 成功则更新修改时间
        if (b) {
            // 更新父级修改时间
            Dictionary parentDict = dictionaryService.selectByPrimaryKey(dictionary.getParentId());
            parentDict.setUpdateTime(LocalDateTime.now());
            dictionaryService.updateByPrimaryKeySelective(parentDict);
            dictCache.resetCache();
        }
        return Rest.success();
    }

    @RequestMapping(value = {"/admin/dict/tree", "/applet/dict/tree"})
    @ApiOperation(value = "获取字典树", notes = "获取字典树", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<DictTreeResponse> tree(@ApiParam(value = "字典 code(检查部位: CHECK_PART, 检查科别: CHECK_DEPARTMENT)", name = "code")
                                       @RequestParam(value = "code", required = false) String code) {
        List<DictTreeResponse.DictTreeVO> tree;

        if (StrUtil.isNotBlank(code)) {
            tree = dictCache.getCachedTree(code);
        } else {
            tree = dictCache.getCachedTree();
        }
        return Rest.success(new DictTreeResponse().setItemList(tree));
    }
}