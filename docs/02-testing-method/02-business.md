---
order: 2
group:
  title: 编写测试用例
  order: 2
---

# 业务性测试



## 介绍&原则

业务测试也就是对我们的Service、biz等业务代码进行测试。通常业务代码会有很多的依赖关系，而对于业务代码的测试我们最需要注意的原则有：

- 隔离依赖
- 全自动&非交互式
- 测试粒度足够小
- 遵守BCDE原则



## 演示Demo



### Service代码

```
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

```
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
    void login() {

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

		//设置桩代码，模拟查询过程
        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userEntity);
		//执行查询
        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserDOMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_OK, loginCallResult.getCode());
    }
}
```



### 代码介绍



#### 业务代码

这里模拟了用户登录的场景。通过传递的用户数据去查询，判断用户是否注册，密码是否正确。通过状态值返回。



#### 测试代码

- 通过@BeforeAll注解，设计测试用例。userDO为用户登录参数，userEntity为模拟的查询结果。
- 通过Mockito的when方法，当输入参数为mobile时，模拟查询过程，返回我们模拟的查询结果。
- 执行业务方法，通过verify方法验证模拟的方法是否执行。再通过断言验证返回的业务状态码是否服务我们的预期。



### 运行结果

由于我期望的状态码为1(CallResult.RETURN_STATUS_OK)，实际处理的状态码为-3，密码不匹配的状态码。所以抛出了异常。

```
org.opentest4j.AssertionFailedError: 
Expected :1
Actual   :-3
```

