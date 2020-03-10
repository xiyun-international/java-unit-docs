---
order: 6
group:
  title: 单元测试
  order: 2
---

# 代码覆盖率

## 介绍

当您看到这里时请您先思考几个问题，当您运行单元测试后，您是否知道被测代码的覆盖程度？要怎么评定所写测试代码的好与坏？依据哪些指标来做衡量标准？

如果您看过单元测试原则，那您一定会知道，代码覆盖率通常会被拿来作为衡量单元测试好坏的指标。

单元测试的基本目标：代码覆盖率达到 70% ；核心应用、核心业务、核心模块的语句覆盖率要达到 100%。所以这可能会让您费尽心思去设计测试用例，但是试想一下，假设您的业务场景可能与下图业务一样至关重要。

![](/assets/flowchart.png)

如果其中某一个环节出现问题，都将导致交易失败，这种损失是我们所不能承受的。所以在单元测试时，我们应尽可能的来保证其中每一个环节的运行覆盖率，这样可以很大程度上的规避 bug，减少核心业务的出错几率，保证服务的稳定性。

## 工具

您可以通过 idea 自带的工具来查看单元测试的代码覆盖率。

您只需要将鼠标光标停放到被测试的方法上，通过 Ctrl + Shift + T (idea for windows 快捷键）生成单元测试方法，运行测试方法时选择 Run login() whith Coverage 选项，在测试完成后，通过 Coverage 工具栏查看覆盖率即可。

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

![](/assets/idea.png)

```java
2020-03-09 20:49:24.351  INFO 9372 --- [main] c.m.s.test.coverage.UserServiceImplTest  : 测试通过
```
