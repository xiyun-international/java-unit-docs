package com.middle.stage.test.service;

import com.middle.stage.test.commons.CallResult;
import com.middle.stage.test.data.UserDO;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface UserService {
    CallResult login(UserDO userDO);
}
