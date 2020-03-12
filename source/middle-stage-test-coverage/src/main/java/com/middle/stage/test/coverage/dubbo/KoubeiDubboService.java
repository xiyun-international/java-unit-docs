package com.middle.stage.test.coverage.dubbo;


import com.middle.stage.test.coverage.commons.CallResult;
import com.middle.stage.test.coverage.data.ShopDO;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface KoubeiDubboService {
    CallResult getAliUserId(String userId);
}
