package com.xiyun.xiyun.test.service;

import com.xiyun.xiyun.test.dao.mapper.UserDOMapper;
import com.xiyun.xiyun.test.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class XiyunTestServiceApplicationTests {

    @Mock
    private UserDOMapper mockUserDOMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("登录测试")
    void loginTest() {

        UserServiceImpl mock = mock(UserServiceImpl.class);
        UserDOMapper mock = mock(UserDOMapper.class);



    }

}
