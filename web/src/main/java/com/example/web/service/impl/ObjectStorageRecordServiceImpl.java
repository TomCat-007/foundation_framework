package com.example.web.service.impl;

import com.example.web.mapper.ObjectStorageRecordMapper;
import com.example.web.pojo.ObjectStorageRecord;
import com.example.web.service.ObjectStorageRecordService;
import common.config.mybatis.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 对象存储记录(ObjectStorageRecord)表服务实现类
 *
 * @author zhangguiyuan
 * @description 对象存储记录(ObjectStorageRecord)表服务实现类
 * @date 2023/3/3 13:33
 */
@Slf4j
@Service
public class ObjectStorageRecordServiceImpl extends BaseServiceImpl<ObjectStorageRecord, Long> implements ObjectStorageRecordService {

    @Resource
    private ObjectStorageRecordMapper objectStorageRecordMapper;

//    @Override
//    public boolean record(ObjectDetail objectDetail) {
//        ObjectStorageRecord objectStorageRecord = new ObjectStorageRecord();
//        BeanUtils.copyProperties(objectDetail, objectStorageRecord);
//        objectStorageRecord.setUserMetadata(JsonUtil.emptyObjectNode());
//        objectStorageRecord.setMetadata(JsonUtil.emptyObjectNode());
//        return objectStorageRecordMapper.insertSelective(objectStorageRecord) > 0;
//    }
//
//    @Override
//    public ObjectDetail findByObjectUrl(String objectUrl) {
//        ObjectStorageRecord objectStorageRecord = objectStorageRecordMapper.selectOneByProperty(ObjectStorageRecord::getObjectUrl, objectUrl);
//        if (ObjectUtil.isNull(objectStorageRecord)) {
//            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, objectUrl);
//        }
//        return BeanUtil.copyProperties(objectStorageRecord, ObjectDetail.class);
//    }
//
//    @Override
//    public boolean finishUpload(String objectName) {
//        ObjectStorageRecord objectStorageRecord = objectStorageRecordMapper.selectOneByProperty(ObjectStorageRecord::getObjectName, objectName);
//        if (ObjectUtil.isNull(objectStorageRecord)) {
//            log.warn("[{}] 不存在", objectName);
//            return true;
//        }
//        objectStorageRecord = new ObjectStorageRecord().setId(objectStorageRecord.getId()).setFileStatus(5);
//        return objectStorageRecordMapper.updateByPrimaryKeySelective(objectStorageRecord) > 0;
//    }
//
//    @Override
//    public boolean delete(String objectName) {
//        ObjectStorageRecord objectStorageRecord = objectStorageRecordMapper.selectOneByProperty(ObjectStorageRecord::getObjectName, objectName);
//        if (ObjectUtil.isNull(objectStorageRecord)) {
//            log.warn("[{}] 不存在", objectName);
//            return true;
//        }
//        return objectStorageRecordMapper.deleteByProperty(ObjectStorageRecord::getObjectName, objectName) > 0;
//    }
}
