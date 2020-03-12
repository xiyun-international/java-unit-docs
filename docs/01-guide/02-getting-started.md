---

order: 2
group:
  title: 介绍
  order: 1
---

# 快速上手

本节将介绍什么是 Mockito 及如何在单元测试中快速使用 `Mockito`。我们还提供了后续所有演示 demo 的[**源码**](https://github.com/xiyun-international/java-unit-docs/tree/master/source)，您可以实际运行我们提供的 demo 来查看运行结果。

本节的演示 demo 与业务一节的演示 demo 相同。这里只介绍 Mockito 用法。

## Mokito

单元测试的思路是：想在不涉及依赖关系的情况下，测试代码的有效性，而我们就是通过 Mockito 来隔离这种依赖关系。Mockito 是一个针对 Java 的 Mock 框架。它能以一种可控的方式去获取一个假对象，通过假对象来模拟真实的对象行为。

## 准备工作

我们采用的测试工具是 JUnit + Mockito。在这里，我将告诉您的是：您不需要准备任何的环境、依赖的包。Spring Boot 集成了 Mockito，它会帮您引入较新的版本。在您创建项目时 Spring Boot 也会自动帮您引入 starter-test 模块。

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

**您只需要在编写测试代码时，静态的引入 Mockito 包即可。**

```java
import static org.mockito.Mockito.*;
```



**非Spring boot工程需填加以下依赖。**

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>3.3.0</version>
    <scope>test</scope>
</dependency>

```



## 演示 Demo

### Mapper 代码

```java
/**
 * 通过手机号查询用户
 * @param mobile
 * @return
 */
UserDO selectByMobile(String mobile);
```

### Service 代码

这里为用户登录场景。通过手机号查询用户信息，判断用户是否未注册、登录密码是否正确，并返回信息。

```java
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public CallResult login(UserDO userDO) {
        UserDO userResult = userMapper.selectByMobile(userDO.getMobile());
        if (userResult == null) {
            log.info("没有该用户信息，请先注册！");
            return CallResult.fail(CallResult.RETURN_STATUS_UNREGISTERED, "没有该用户信息，请先注册！");
        }
        if (!userDO.getPassword().equals(userResult.getPassword())) {
            return CallResult.fail(CallResult.RETURN_STATUS_PASW_INCORRECT, "您的密码不正确！");
        }
        return CallResult.success(CallResult.RETURN_STATUS_OK, "登录成功！", userResult);
    }
}
```

### 测试代码

1. 通过 mock 方法，模拟出依赖的 UserMapper。
2. 将模拟的 mapper 设置进 UserServiceImpl 中，并设置桩代码。当 mockUserMapper.selectByMobile 方法输入参数为 mobile 时，return 模拟的结果。
3. 执行被测试业务方法。
4. 验证业务方法是否执行，及验证业务状态码是否符合预期。

```java
@SpringBootTest
class MiddleStageTestServiceByAnnotationApplicationTests {

    @Test
    @DisplayName("登录测试")
    void login() {

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

        //1.模拟登录业务中，依赖的dao层查询接口
        UserMapper mockUserMapper = mock(UserMapper.class);
        //将模拟的接口注入
        UserServiceImpl userService = new UserServiceImpl(mockUserMapper);

        //2.当程序运行时，模拟查询结果，返回我们指定的预期结果
        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userResult);
        //3.执行登录方法
        CallResult loginCallResult = userService.login(userDO);

        //4.验证是否执行
        verify(mockUserMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_PASW_INCORRECT, loginCallResult.getCode());
    }
}
```

## 运行结果

```java
2020-03-08 19:44:39.795  INFO 19020 --- [main] s.t.w.MiddleStageTestWebApplicationTests : 测试通过
```
