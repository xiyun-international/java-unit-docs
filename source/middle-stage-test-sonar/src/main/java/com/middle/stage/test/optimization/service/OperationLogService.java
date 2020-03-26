package com.middle.stage.test.optimization.service;


import com.middle.stage.test.optimization.model.enums.ObjectTypeForLogEnum;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public interface OperationLogService {

    /**
     * 保存操作日志
     *
     * @param objectId         日志对象id
     * @param objectType       日志对象类型
     * @param operationType    日志操作类型
     * @param message          日志操作描述
     * @param operationOldData 日志对象原始数据,json字符串
     * @param operationNewData 日志对象当前数据, json 字符串
     * @param operationUserId  日志操作人id
     */
    void saveLog(Long objectId, ObjectTypeForLogEnum objectType,
                 Integer operationType, String message, String operationOldData, String operationNewData,
                 Integer operationUserId, Integer merchantId);
}
