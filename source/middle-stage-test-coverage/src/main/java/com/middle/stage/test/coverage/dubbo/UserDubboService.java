package com.middle.stage.test.coverage.dubbo;


import com.middle.stage.test.coverage.commons.CallResult;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface UserDubboService {
    CallResult getUserInfo(String userId);
}
