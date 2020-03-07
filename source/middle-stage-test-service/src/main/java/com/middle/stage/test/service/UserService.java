package com.middle.stage.test.service;

import com.middle.stage.test.dao.data.UserDO;
import com.middle.stage.test.service.commons.CallResult;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface UserService {
    CallResult login(UserDO userDO);
}
