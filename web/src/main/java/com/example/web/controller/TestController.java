package com.example.web.controller;

import com.example.web.pojo.Dictionary;
import com.example.web.service.DictionaryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/app")
public class TestController {

    @Resource
    private DictionaryService dictionaryService;

    @PostMapping("/test1")
    @ApiOperation(value = "创建应用", notes = "创建应用", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test1() {
        System.out.println("测试接口");
        Dictionary dictionary = new Dictionary();

        dictionary.setCode("sad3123a");
        dictionary.setName("23313");
        dictionaryService.insertSelective(dictionary);
        return "测试接口";
    }

}
