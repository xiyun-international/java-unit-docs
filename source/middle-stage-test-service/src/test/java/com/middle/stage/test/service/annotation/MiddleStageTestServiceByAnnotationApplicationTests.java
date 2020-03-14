package com.middle.stage.test.service.annotation;

import com.middle.stage.test.commons.CallResult;
import com.middle.stage.test.service.impl.UserServiceImpl;
import com.middle.stage.test.data.UserDO;
import com.middle.stage.test.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class MiddleStageTestServiceByAnnotationApplicationTests {

    @Mock
    //通过注解模拟依赖的接口或类
    private UserMapper mockUserMapper;

    @InjectMocks
    //通过注解自动注入依赖关系
    private UserServiceImpl userService;

    //登录参数
    static UserDO userDO;

    //模拟查询结果
    static UserDO fakerUserResult;

    static String mobile = "17612345678";

    static String password = "123456";


    @BeforeAll
    static void beforLoginTest() {
        userDO = new UserDO();
        userDO.setMobile(mobile);
        userDO.setPassword(password);

        fakerUserResult = new UserDO();
        fakerUserResult.setMobile("13888888888");
        fakerUserResult.setPassword("123456");
    }

    @Test
    @DisplayName("登录测试")
    void login() {

        //其它方法只要运行这个函数，就模拟返回 fakerUserResult 数据
        when(mockUserMapper.selectByMobile(mobile)).thenReturn(fakerUserResult);

        //! userDO 的 mobile 是 17612345678，但因为设置了桩代码，返回值应该是 13888888888
        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserMapper).selectByMobile(mobile);

        //验证业务状态
        Assertions.assertEquals(CallResult.RETURN_STATUS_OK, loginCallResult.getCode());

        //! 桩代码设置成功
        UserDO login = (UserDO) loginCallResult.getContent();
        System.out.println(login.getMobile());
        Assertions.assertEquals(fakerUserResult.getMobile(), login.getMobile());

        log.info("[测试通过]");
    }
}
