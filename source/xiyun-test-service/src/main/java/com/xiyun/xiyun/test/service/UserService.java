package com.xiyun.xiyun.test.service;

import com.xiyun.xiyun.test.dao.data.UserDO;
import com.xiyun.xiyun.test.service.commons.CallResult;

/**
 * @author zyq
 * @date 2020/3/7
 */
public interface UserService {
    CallResult login(UserDO userDO);
}
