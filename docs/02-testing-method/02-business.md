---
order: 2
group:
  title: 单元测试
  order: 2
---

# 业务性测试

## 介绍&原则

业务测试也就是对我们的 Service、biz 等业务代码进行测试。通常业务代码会有很多的依赖关系，而对于业务代码的测试我们最需要注意的原则有：

- 隔离依赖
- 全自动&非交互式
- 测试粒度足够小
- 遵守 BCDE 原则

## 演示 Demo

### Service 代码

这里为用户登录场景。通过传递的用户数据查询，判断用户是否注册、密码是否正确及返回信息。

```java
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    /**
     * Mockito使用注解注入依赖关系，需提供构造器
     *
     * @param userDOMapper
     */
    public UserServiceImpl(UserDOMapper userDOMapper) {
        this.userDOMapper = userDOMapper;
    }

    @Override
    public CallResult login(UserDO userDO) {
        UserDO userEntity = userDOMapper.selectByMobile(userDO.getMobile());
        if (userEntity == null) {
            log.info("没有该用户信息，请先注册！");
            return CallResult.fail(CallResult.RETURN_STATUS_UNREGISTERED, "没有该用户信息，请先注册！");
        }
        if (!userDO.getPassword().equals(userEntity.getPassword())) {
            return CallResult.fail(CallResult.RETURN_STATUS_PASW_INCORRECT, "您的密码不正确！");
        }
        return CallResult.success(CallResult.RETURN_STATUS_OK, "登录成功！", userEntity);
    }
}
```

### 测试代码

- 通过 @BeforeAll 注解，在测试方法运行前准备测试用例。userDO 为用户登录参数，userResult 为模拟的查询结果。
- 通过 when 方法设置当输入参数为 mobile 时，模拟查询过程返回模拟的查询结果。
- 执行业务方法，通过 verify 方法验证模拟的方法是否执行。再通过断言验证返回的业务状态码是否服务我们的预期。

```java
@Slf4j
@SpringBootTest
class MiddleStageTestServiceByAnnotationApplicationTests {

    @Mock
    //通过注解模拟依赖的接口或类
    private UserDOMapper mockUserDOMapper;
    @InjectMocks
    //通过注解自动注入依赖关系
    private UserServiceImpl userService;
    //登录参数
    static UserDO userDO;

    //模拟查询结果
    static UserDO userResult;
    static String mobile = "17612345678";
    static String password = "123456";


    @BeforeAll
    static void beforInsertTest() {
        userDO = new UserDO();
        userDO.setMobile(mobile);
        userDO.setPassword(password);

        userResult = new UserDO();
        userResult.setMobile(mobile);
        userResult.setPassword("654321");
    }

    @Test
    @DisplayName("登录测试")
    void login() {

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

		//设置桩代码，模拟查询过程
        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userResult);
		//登录
        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserDOMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_OK, loginCallResult.getCode());
    	log.info("测试通过");

    }
}
```

### 运行结果

```java
2020-03-08 19:36:08.053  INFO 17720 --- [main] eTestServiceByAnnotationApplicationTests : 测试通过
```
