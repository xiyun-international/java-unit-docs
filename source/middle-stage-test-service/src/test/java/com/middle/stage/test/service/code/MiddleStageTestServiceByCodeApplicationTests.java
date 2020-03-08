package com.middle.stage.test.service.code;

import com.middle.stage.test.dao.data.UserDO;
import com.middle.stage.test.dao.mapper.UserDOMapper;
import com.middle.stage.test.service.commons.CallResult;
import com.middle.stage.test.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class MiddleStageTestServiceByCodeApplicationTests {

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

        //模拟登录业务中，依赖的dao层查询接口
        UserDOMapper mockUserDOMapper = mock(UserDOMapper.class);
        //将模拟的接口注入
        UserServiceImpl userService = new UserServiceImpl(mockUserDOMapper);

        //当程序运行时，模拟查询结果，返回我们指定的预期结果
        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userEntity);

        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserDOMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_PASW_INCORRECT, loginCallResult.getCode());
    }

}
