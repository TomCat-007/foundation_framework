//package com.example.web;
//
//import cn.hutool.core.util.RandomUtil;
//import com.example.web.enums.error.BusinessErrorCodeEnum;
//import com.example.web.pojo.Organ;
//import com.example.web.service.OrganService;
//import common.config.security.config.ContextHolder;
//import common.util.ServiceExceptionUtil;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zhangguiyuan
// * @description
// * @date 2023/3/3 13:33
// */
//@Component
//@RequiredArgsConstructor
//public class BarcodeGenerator {
//
//    /**
//     * 基础字符，排除易混淆字符: o, O, i, I, l, L, p, P, q, Q
//     */
//    private static final String BASE_CHAR_WITHOUT = "abcdefghjkmnrstuvwxyz";
//
//    private static final String BASE_NUMBER = "1234567890";
//
//    private static final String BASE_STRING = BASE_NUMBER + BASE_CHAR_WITHOUT;
//
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
//
//    private final RedissonClient redissonClient;
//
//    private final OrganService organService;
//
//    public String generate() {
//        return generate(ContextHolder.currentOrganId());
//    }
//
//    public String generate(Long organId) {
//        Organ organ = organService.selectByPrimaryKey(organId);
//        if (organ == null) {
//            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, organId);
//        }
//        return generate(organ.getCode());
//    }
//
//    public String generate(String organCode) {
//        String dateStr = LocalDate.now().format(DATE_TIME_FORMATTER);// 时间戳
//        RIdGenerator idGenerator = getIdGenerator(organCode + ":" + dateStr);
//        String randomStr = RandomUtil.randomString(BASE_CHAR_WITHOUT, 2);
//        return organCode + dateStr + randomStr + String.format("%06d", idGenerator.nextId());
//    }
//
//    private RIdGenerator getIdGenerator(String name) {
//        RIdGenerator idGenerator = redissonClient.getIdGenerator(name);
//        if (!idGenerator.isExists()) {
//            RLock lock = redissonClient.getLock("LOCK:" + name);
//            lock.lock(10L, TimeUnit.SECONDS);
//            if (!idGenerator.isExists()) {
//                idGenerator.expireAsync(Duration.ofDays(1L));
//                idGenerator.tryInit(1, 1);
//            }
//            lock.unlock();
//        }
//        return idGenerator;
//    }
//
//}
