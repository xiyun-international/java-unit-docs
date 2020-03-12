---
order: 6
group:
  title: 单元测试
  order: 2
---

# 代码覆盖率

## 介绍

设想一下，假如在做支付系统，整个交易环节流程如下：
![](../assets/flowchart.png)

当一个环节出现问题时，将导致整体交易失败。所以我们做单元测试尽可能的保证每一个环节都会被测试到，这样可以很大程度规避 Bug，从而减少核心业务的`出错几率`，保证服务的稳定性。

单元测试的基本目标：代码覆盖率达到 70% ；核心的应用、业务、模块的语句覆盖率要达到 100%。[《Java 开发手册》](https://github.com/alibaba/p3c/blob/master/p3c-gitbook/%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95.md#L17)


## 工具

可以通过 IDEA 自带的工具来查看单元测试的代码覆盖率。

只需要将鼠标光标停放到被测试的方法上，通过 Ctrl + Shift + T 生成单元测试方法，运行测试方法时选择 Run login() whith Coverage 选项，在测试完成后，通过 Coverage 工具栏查看覆盖率即可。

## 演示 [Demo](https://github.com/xiyun-international/java-unit-docs/tree/master/source/middle-stage-test-coverage)

这里的演示 Demo 与业务测试一节的 Demo 完全一致。只是单元测试方法是通过 Ctrl + Shift + T (idea for windows 快捷键）创建。您在实际运行时，直接选择 Run login() whith Coverage 选项运行即可。

```java
@Slf4j
@SpringBootTest
class UserServiceImplTest {
    @Mock
    //通过注解模拟依赖的接口或类
    private UserMapper mockUserMapper;

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
    static void beforLoginTest() {
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

        when(mockUserMapper.selectByMobile(mobile)).thenReturn(userResult);

        CallResult loginCallResult = userService.login(userDO);

        //验证是否执行
        verify(mockUserMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_PASW_INCORRECT, loginCallResult.getCode());
        log.info("测试通过");
    }
}
```

## 运行结果

当以这种方式运行单元测试后，右侧工具栏会显示出，相关包、代码的覆盖率情况。您可以具体点进去找到您的业务代码查看覆盖率。图中央红框部分，绿色部分展示了在测试过程中执行到的代码，红色部分为未执行代码。

![](../assets/idea.png)

```java
2020-03-09 20:49:24.351  INFO 9372 --- [main] c.m.s.test.coverage.UserServiceImplTest  : 测试通过
```
