package com.middle.stage.test.coverage.service;


import com.middle.stage.test.coverage.commons.CallResult;
import com.middle.stage.test.coverage.data.MarkttingModel;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface ShopService {
    CallResult pay(MarkttingModel markttingModel);
}
