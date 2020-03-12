package com.middle.stage.test.optimization.service;


import org.springframework.stereotype.Service;

/**
 *
 *
 */
@Service
public interface ShopCommonService {

    /**
     * 餐别编辑推送isv
     *
     * @param opType     操作类型
     * @param obType     操作对象类型
     * @param merchantId 商家id
     * @param canteenId  门店id
     * @param equId      设备类型id
     */
    void pushDinnerToIsv(Integer opType, Integer obType, Integer merchantId, Integer canteenId, Integer equId);
}
