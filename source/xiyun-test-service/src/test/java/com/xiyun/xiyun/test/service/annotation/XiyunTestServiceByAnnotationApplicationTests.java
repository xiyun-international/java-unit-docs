package com.xiyun.xiyun.test.service.annotation;

import com.xiyun.xiyun.test.dao.data.UserDO;
import com.xiyun.xiyun.test.dao.mapper.UserDOMapper;
import com.xiyun.xiyun.test.service.commons.CallResult;
import com.xiyun.xiyun.test.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class XiyunTestServiceByAnnotationApplicationTests {

    @Mock
    //通过注解模拟依赖的接口或类
    private UserDOMapper mockUserDOMapper;

    @InjectMocks
    //通过注解自动注入依赖关系
    private UserServiceImpl userService;

    //登录参数
    static UserDO userDO;

    //模拟查询结果
    static UserDO userEntity;

    static String mobile = "17612345678";

    static String password = "123456";


    @BeforeAll
    static void beforInsertTest() {
        userDO = new UserDO();
        userDO.setMobile(mobile);
        userDO.setPassword(password);

        userEntity = new UserDO();
        userEntity.setMobile("17612345678");
        userEntity.setPassword("654321");
    }

    @Test
    @DisplayName("登录测试")
    void loginTest() {

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userEntity);

        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserDOMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_PASW_INCORRECT, loginCallResult.getCode());
    }

}
